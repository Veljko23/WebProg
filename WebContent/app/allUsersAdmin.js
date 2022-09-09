var app = new Vue({
	el: '#allUsersAdmin',
	data: {
		users: null,
		currentUser:{},
		user: {},
		searchName: "",
		searchSurname: "",
		searchEmail:"",
		searchedUsers:[],
		sortType:""
	},
	mounted() {
		axios.get('rest/users')
			.then(response => {
					this.users = response.data;
					this.searchedUsers = this.users;
			})
		axios.get('rest/users/currentUser')
				.then(response => {
					this.currentUser = response.data;
					
				})
	},
	methods: {
		search: function(){
			this.searchedUsers = []
			for(u of this.users){
				if(u.name.toLowerCase().includes(this.searchName.toLowerCase())){
					if(u.surname.toLowerCase().includes(this.searchSurname.toLowerCase())){
						if(u.email.toLowerCase().includes(this.searchEmail.toLowerCase())){
							this.searchedUsers.push(u);
						}
					}
				}
			}
		},
			
			userDetails: function(user) {
				axios.post('rest/users/setUserForView', user)
				.then(response => {
					if(this.currentUser.id === user.id){
						window.location.href = "http://localhost:8080/WebProg/personalData.html"
					}else{
						window.location.href = "http://localhost:8080/WebProg/detailedUserAdmin.html"
					}
				})
			},
			blockUser: function(selectedUser){
				selectedUser.privateProfile = true;
				axios.put('rest/users/blockUser/' + this.currentUser.id, selectedUser)
				.then(response => {
					alert('User blocked!');
				})
				.catch(response => {alert('Greska u blokiranju!')})
				
				event.preventDefault();
				return;
			},
			unblockUser: function(selectedUser){
				selectedUser.privateProfile = false;
				axios.put('rest/users/unblockUser/' + this.currentUser.id, selectedUser)
				.then(response => {
					alert('User unblocked!');
				})
				.catch(response => {alert('Greska u deblokiranju!')})
				event.preventDefault();
				return;
			},
			logout: function(){
				axios.post('rest/users/logout/')
				.then(response => {
					alert('Logout!')
					window.location.href = "http://localhost:8080/WebProg/login.html"
				})
			}

		
	}
});
