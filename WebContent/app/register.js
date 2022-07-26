var app = new Vue({
	el: '#register',
	data: {
		newUser: {}
	},
	mounted() {
		
	},
	methods: {
		registerUser: function() {
			this.newUser.role = 'USER';
			const str = this.newUser.birdthDate;
			
			const [year, month, day] = str.split('-');
			this.newUser.birdthDate = day + '.' + month + '.' + year + '.';
			
			axios.post('rest/users/register', this.newUser)
					.then(response => {alert('Uspesno ste se registovali')})
					.catch(response => {alert('Korisnicko ime vec postoji')})
			event.preventDefault();
			return;
		}
	}
});
