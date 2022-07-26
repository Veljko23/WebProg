var app = new Vue({
	el: '#allUsers',
	data: {
		users: null
	},
	mounted() {
		axios.get('rest/users')
			.then(response => (this.users = response.data))
	},
	methods: {
		
	}
});
