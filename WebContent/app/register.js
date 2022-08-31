var app = new Vue({
	el: '#register',
	data: {
		newUser: {},
		error: 'field not entered',
		triedRegistering: false,
		confirmedPassword: '',
		errorForConfirming: ''
	},
	mounted() {
		
	},
	methods: {
		registerUser: function() {
			
			this.triedRegistering = true
			if(!this.newUser.username || !this.newUser.email || !this.newUser.birdthDate || !this.newUser.name || !this.newUser.surname || !this.newUser.gender
					|| !this.newUser.password ){
						event.preventDefault();
						return;
					}
					if(this.newUser.password !== this.confirmedPassword){		
						this.errorForConfirming = 'password not matching'
						event.preventDefault();
						return;
					}else{
						this.errorForConfirming = ''
					}
			
			this.newUser.role = 'USER';
			this.newUser.privateProfile = false;
			const str = this.newUser.birdthDate;
			
			if(str.split('-').length < 2){
				event.preventDefault();
				return;
			}
			
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
