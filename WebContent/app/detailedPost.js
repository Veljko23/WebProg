var app = new Vue({
	el: '#detailedPost',
	data: {
		post: {},
		currentUser: {},
		commentsForPost: [],
		writeComment: false,
		comment: {}
	},
	mounted() {
		axios.get('rest/users/currentUser')
		.then(response => {
			this.currentUser = response.data;
			[day, month, year] = this.currentUser.birdthDate.split('.');
			this.currentUser.birdthDate = year + '-' + month + '-' + day;
		})
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
		},
		deleteCommentUser: function(selectedComment){
			
			axios.delete('rest/comments/' + selectedComment.id)
			.then(response => {
				alert('Comment deleted succesfully ')
				axios.get('rest/posts/getCommentsForPost/' + this.post.id)
					.then(response => {
						this.commentsForPost = response.data;

					})
			})
		}
		
	}
});
