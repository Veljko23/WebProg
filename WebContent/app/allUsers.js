var app = new Vue({
	el: '#allUsers',
	data: {
		users: null,
		searchName: "",
		searchSurname: "",
		searchBirdthBegin: "",
		searchBirdthEnd:"",
		searchedUsers:[]
	},
	mounted() {
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
			}
		
	}
});
