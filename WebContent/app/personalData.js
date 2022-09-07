var app = new Vue({
	el: '#personalData',
	data: {
		currentUser: {},
		posts:{},
		messages:{},
		newPost: {},
		requests:[],
		friends: [],
		error: 'field not entered',
		triedRegistering: false,
		confirmedPassword: '',
		errorForConfirming: ''
	},
	mounted() {
		axios.get('rest/users/currentUser')
				.then(response => {
					this.currentUser = response.data;
					[day, month, year] = this.currentUser.birdthDate.split('.');
					this.currentUser.birdthDate = year + '-' + month + '-' + day;
					
				})
		axios.get('rest/users/currentUserPosts')
				.then(response => {
					this.posts = response.data;
				})
		axios.get('rest/users/currentUserRequests')
				.then(response => {
					this.requests = response.data;
				})
		axios.get('rest/users/currentUserFriends')
				.then(response => {
					this.friends = response.data;
				})
	},
	methods: {
		changeUser: function() {
			
			this.triedRegistering = true
			if(!this.currentUser.username || !this.currentUser.email || !this.currentUser.birdthDate || !this.currentUser.name || !this.currentUser.surname || !this.currentUser.gender
					|| !this.currentUser.password ){
						event.preventDefault();
						return;
					}
					if(this.currentUser.password !== this.confirmedPassword){		
						this.errorForConfirming = 'password not matching'
						event.preventDefault();
						return;
					}else{
						this.errorForConfirming = ''
					}
			
			const str = this.currentUser.birdthDate;
			
			const [year, month, day] = str.split('-');
			this.currentUser.birdthDate = day + '.' + month + '.' + year + '.';
			
			axios.put('rest/users/' + this.currentUser.id, this.currentUser)
					.then(response => {alert('Uspesno izmenjeni podaci!')})
					.catch(response => {alert('Doslo je do greske prilikom izmene podataka!')})
			event.preventDefault();
			return;
		},
		clickedPicture: function(post) {
			axios.post('rest/posts/setPost', post)
			.then(response => {
				window.location.href = "http://localhost:8080/WebProg/myPost.html"
			})
		},
		acceptRequest: function(request){
			axios.get('rest/requests/acceptRequest/'+ request.id)
			.then(response => {
				this.requests = this.requests.filter((p) => p.id !== request.id)
			})
		},
		denyRequest: function(request){
			axios.get('rest/requests/denyRequest/'+ request.id)
			.then(response => {
				this.requests = this.requests.filter((p) => p.id !== request.id)
			})
		},
		userDetails: function(user) {
			axios.post('rest/users/setUserForView', user)
			.then(response => {
				window.location.href = "http://localhost:8080/WebProg/detailedUser.html"
			})
		},
		removeFriend: function(selectedFriend){
			axios.delete('rest/users/removeFriend/' + this.currentUser.id + '/' + selectedFriend.id)
			.then(response => {
				alert('Uklonjen prijatelj!')
				window.location.href = "http://localhost:8080/WebProg/personalData.html"
			})
		},
		createNewPost: function(){
			window.location.href = "http://localhost:8080/WebProg/newPost.html"
		}
		
	}
});
