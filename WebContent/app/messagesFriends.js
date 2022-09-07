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
					if(this.currentUser.id === user.id){
						window.location.href = "http://localhost:8080/WebProg/personalData.html"
					}else{
						window.location.href = "http://localhost:8080/WebProg/detailedUser.html"
					}
				})
			}

		
	}
});
