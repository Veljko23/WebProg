var app = new Vue({
	el: '#detailedUser',
	data: {
		user: {},
		userForViewPost: {},
		currentUser: {}
	},
	mounted() {
		axios.get('rest/users/getUserForView')
			.then(response => {
				this.user = response.data;
				axios.get('rest/users/getUserForViewPost/' + this.user.id)
				.then(response =>{
					this.userForViewPost = response.data;
				})
			})
		axios.get('rest/users/currentUser')
				.then(response => {
					this.currentUser = response.data;
					[day, month, year] = this.currentUser.birdthDate.split('.');
					this.currentUser.birdthDate = year + '-' + month + '-' + day;
				})
	},
	methods: {
		clickedPost: function(post) {
			axios.post('rest/posts/setPost', post)
			.then(response => {
				window.location.href = "http://localhost:8080/WebProg/detailedPost.html"
			})
		},
		request: function() {
			friendRequest = {senderId: this.currentUser.id, receiverId: this.user.id}
			axios.post('rest/requests/', friendRequest)
				.then(response => {
					alert('Request successfully sent');
				})
		}

	}
});
