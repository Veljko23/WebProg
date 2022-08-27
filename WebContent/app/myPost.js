var app = new Vue({
	el: '#myPost',
	data: {
		post: {},
		commentsForPost: []
	},
	mounted() {
		axios.get('rest/posts/getPost')
			.then(response => {
				this.post = response.data;
				axios.get('rest/posts/getCommentsForPost/' + this.post.id)
					.then(response => {
						this.commentsForPost = response.data;

					})
			})
	},
	methods: {
		deletePost: function(){
			axios.delete('rest/posts/' + this.post.id)
			.then(response => {
				alert('Post deleted succesfully ')
				window.location.href = "http://localhost:8080/WebProg/personalData.html"
			})
			
		}

	}
});
