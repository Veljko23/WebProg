var app = new Vue({
	el: '#allUsers',
	data: {
		users: null,
		currentUser:{},
		searchName: "",
		searchSurname: "",
		searchBirdthBegin: "",
		searchBirdthEnd:"",
		searchedUsers:[],
		sortType:""
	},
	mounted() {
		axios.get('rest/users/currentUser')
		.then(response => {
			this.currentUser = response.data;
			[day, month, year] = this.currentUser.birdthDate.split('.');
			this.currentUser.birdthDate = year + '-' + month + '-' + day;
		})
		axios.get('rest/users')
			.then(response => {
					this.users = response.data;
					this.searchedUsers = this.users;
			})
	},
	methods: {
			search: function(){
				this.searchedUsers = []
				for(u of this.users){
					if(u.name.toLowerCase().includes(this.searchName.toLowerCase())){
						if(u.surname.toLowerCase().includes(this.searchSurname.toLowerCase())){
							if(this.searchBirdthBegin !== "" && this.searchBirdthEnd !== ""){
								const beginDate = new Date(this.searchBirdthBegin);
								const endDate = new Date(this.searchBirdthEnd);
								
								[day, month, year] = u.birdthDate.split('.');
								newDate = year + '-' + month + '-' + day;
								
								const userDate = new Date(newDate)
								
								if(userDate > beginDate && userDate < endDate){
									this.searchedUsers.push(u);
								}
							}else{
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
						window.location.href = "http://localhost:8080/WebProg/detailedUser.html"
					}
				})
			},
			
			sortUsers: function(){
				if(this.sortType === "Name"){
	                this.searchedUsers.sort((a, b) => (a.name.localeCompare(b.name)))
	            }else if(this.sortType === "Surname"){
	            	this.searchedUsers.sort((a, b) => (a.surname.localeCompare(b.surname)))
	            }else if(this.sortType === "BirdthDate"){
	            	this.searchedUsers.sort((a, b) =>{
	            		
	            		[daya, montha, yeara] = a.birdthDate.split('.');
	            		newDatea = yeara + '-' + montha + '-' + daya;
	            		const userDatea = new Date(newDatea);
	            		
	            		[dayb, monthb, yearb] = b.birdthDate.split('.');
	            		newDateb = yearb + '-' + monthb + '-' + dayb;
	            		const userDateb = new Date(newDateb);
	            		
	            		return (userDatea > userDateb) ? 1 : -1;
	            	})
					
	            }
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
