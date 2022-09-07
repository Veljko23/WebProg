var app = new Vue({
	el: '#chat',
	data: {
		messages: [],
		message:{},
		currentUser: {},
		sendMessageButton: false
	},
	mounted() {
		axios.get('rest/users/currentUser')
		.then(response => {
			this.currentUser = response.data;
			[day, month, year] = this.currentUser.birdthDate.split('.');
			this.currentUser.birdthDate = year + '-' + month + '-' + day;
		})
		
		axios.get('rest/messages/chat')
		.then(response => {
			this.messages = response.data;
		})
		
		
	},
	methods: {
		showMessageButton: function(){
			this.sendMessageButton = true;
		},
		sendMessage: function(){
			axios.post('rest/messages/sendMessage', this.message)
			.then(response => {
				window.location.href = "http://localhost:8080/WebProg/chat.html"
			})
		}
			
		
	}
});
