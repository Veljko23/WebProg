var app = new Vue({
	el: '#detailedPost',
	data: {
		post: {},
		commentsForPost: [],
		writeComment: false,
		comment: {}
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
		showComment: function() {
			this.writeComment = true;

		},
		saveComment: function(){
			this.writeComment = false;
			axios.post('rest/comments/' + this.post.id, this.comment)
					.then(response => {
						this.commentsForPost.push(response.data);

					})
		}
		

	}
});
