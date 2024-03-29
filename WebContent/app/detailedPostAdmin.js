var app = new Vue({
	el: '#detailedPostAdmin',
	data: {
		post: {},
		user: {},
		currentUser: {},
		commentsForPost: [],
		writeComment: false,
		comment: {},
		showDeleteButton: false,
		message: {}
	},
	mounted() {
		axios.get('rest/users/currentUser')
		.then(response => {
			this.currentUser = response.data;
			[day, month, year] = this.currentUser.birdthDate.split('.');
			this.currentUser.birdthDate = year + '-' + month + '-' + day;
		})
		axios.get('rest/users/getUserForView')
			.then(response => {
				this.user = response.data;
				axios.get('rest/users/getUserForViewPost/' + this.user.id)
				.then(response =>{
					this.userForViewPost = response.data;
				})
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
		},
		showDeletePost: function(){
			this.showDeleteButton = true;
		}
		,
		deletePost: function(){
			axios.post('rest/messages/' + this.user.id, this.message)
			.then(response => {
				alert('Message successfully sent');
			axios.delete('rest/posts/' + this.post.id)
			.then(response => {
				alert('Post deleted succesfully ')
				window.location.href = "http://localhost:8080/WebProg/detailedUserAdmin.html"
			})
			.catch('Greska u brisanju!')
			})
		}
		
	}
});
