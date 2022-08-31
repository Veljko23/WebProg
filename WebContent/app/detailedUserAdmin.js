var app = new Vue({
	el: '#detailedUserAdmin',
	data: {
		user: {},
		userForViewPost: {},
		currentUser: {},
		message:{},
		messageForSend: false
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
				window.location.href = "http://localhost:8080/WebProg/detailedPostAdmin.html"
			})
		},
		request: function() {
			friendRequest = {senderId: this.currentUser.id, receiverId: this.user.id, senderName:this.currentUser.name}
			axios.post('rest/requests/', friendRequest)
				.then(response => {
					alert('Request successfully sent');
				})
		},
		blockUser: function(){
			this.user.privateProfile = true;
			axios.put('rest/users/blockUser/' + this.currentUser.id, this.user)
			.then(response => {
				alert('User blocked!');
			})
			.catch(response => {alert('Greska u blokiranju!')})
			event.preventDefault();
			return;
		},
		unblockUser: function(){
			this.user.privateProfile = false;
			axios.put('rest/users/unblockUser/' + this.currentUser.id, this.user)
			.then(response => {
				alert('User unblocked!');
			})
			.catch(response => {alert('Greska u deblokiranju!')})
			event.preventDefault();
			return;
		},
		deletePost: function(selectedPost){
			axios.delete('rest/posts/' + selectedPost.id)
			.then(response => {
				alert('Post deleted succesfully ')
				window.location.href = "http://localhost:8080/WebProg/detailedUserAdmin.html"
			})
			.catch('Greska u brisanju!')
			
		},
		showMessageButton: function(){
			this.messageForSend = true;
		},
		messageSend: function() {
			message = {senderId: this.currentUser.id, receiverId: this.user.id, senderName:this.currentUser.name}
			axios.post('rest/messages/' + this.user.id, this.message)
				.then(response => {
					alert('Message successfully sent');
				})
		}
		

	}
});
