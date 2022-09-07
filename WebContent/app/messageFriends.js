var app = new Vue({
	el: '#messagesFriends',
	data: {
		currentUser:{},
		user:{},
		messagesFriends:{}
	},
	mounted() {
		axios.get('rest/users/currentUser')
		.then(response => {
			this.currentUser = response.data;
			[day, month, year] = this.currentUser.birdthDate.split('.');
			this.currentUser.birdthDate = year + '-' + month + '-' + day;
			axios.get('rest/messages/getAllUserMessageFriends')
			.then(response => {
				this.messagesFriends = response.data;
			})
		})
		
	},
	methods: {
			
			userDetails: function(user) {
				axios.post('rest/users/setUserForView', user)
				.then(response => {
					
					window.location.href = "http://localhost:8080/WebProg/chat.html"
					
				})
			}

		
	}
});
