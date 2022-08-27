var app = new Vue({
	el: '#detailedUser',
	data: {
		user: {},
		userForViewPost: {}
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
	},
	methods: {
		clickedPost: function(post) {
			axios.post('rest/posts/setPost', post)
			.then(response => {
				window.location.href = "http://localhost:8080/WebProg/detailedPost.html"
			})
		}

	}
});
