var app = new Vue({
	el: '#detailedUser',
	data: {
		user: {},
		userForViewPost: {},
		currentUser: {},
		userRequest: {},
		userFriend: {},
		mutualFriends: {},
		message:{},
		messageForSend: false,
		alreadyFriends: false,
		existFriend: false
	},
	mounted() {
		axios.get('rest/users/getUserForView')
			.then(response => {
				this.user = response.data;
				axios.get('rest/users/getUserForViewPost/' + this.user.id)
				.then(response =>{
					this.userForViewPost = response.data;
					axios.get('rest/users/currentUser')
					.then(response => {
						this.currentUser = response.data;
						[day, month, year] = this.currentUser.birdthDate.split('.');
						this.currentUser.birdthDate = year + '-' + month + '-' + day;
						
						
						axios.get('rest/requests/request/' + this.currentUser.id + '/' + this.user.id)
						.then(response => {
							this.userRequest = response.data;
							axios.get('rest/users/existFriend/' + this.currentUser.id + '/' + this.user.id)
							.then(response => {
								this.userFriend = response.data;
								if(response.data === true){
									this.existFriend = true;
								}else{
									this.existFriend = false;
								}
								axios.get('rest/users/mutualFriends/' + this.currentUser.id + '/' + this.user.id)
								.then(response => {
									this.mutualFriends = response.data;
								})
							})
							
						})
					})
					
				})
				
			})
		
	},
	methods: {
		userDetails: function(user) {
			axios.post('rest/users/setUserForView', user)
			.then(response => {
				window.location.href = "http://localhost:8080/WebProg/detailedUser.html"
			})
		},
		clickedPost: function(post) {
			axios.post('rest/posts/setPost', post)
			.then(response => {
				window.location.href = "http://localhost:8080/WebProg/detailedPost.html"
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
		showMessageButton: function(){
			this.messageForSend = true;
		},
		messageSend: function() {
			message = {senderId: this.currentUser.id, receiverId: this.user.id, senderName:this.currentUser.name}
			axios.post('rest/messages/' + this.user.id, this.message)
				.then(response => {
					this.messageForSend = false;
					alert('Message successfully sent');
				})
		},
		removeFriend: function(){
			axios.delete('rest/users/removeFriend/' + this.currentUser.id + '/' + this.user.id)
			.then(response => {
				alert('Uklonjen prijatelj!')
				window.location.href = "http://localhost:8080/WebProg/personalData.html"
			})
		}

	}
});
