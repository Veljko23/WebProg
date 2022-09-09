var app = new Vue({
	el: '#newPost',
	data: {
		currentUser: {},
		newPost: {},
		error: 'field not entered'
	},
	mounted() {
		axios.get('rest/users/currentUser')
			.then(response => {
				this.currentUser = response.data;
				[day, month, year] = this.currentUser.birdthDate.split('.');
				this.currentUser.birdthDate = year + '-' + month + '-' + day;
			})

	},
	methods: {

		newPostCreate: function() {
				this.newPost.picture = this.newPost.picture.split("\\").pop()
				axios.post('rest/posts', this.newPost)
				.then(response => { alert('Uspesno kreiran post') })
				.catch(respones => { alert('Greska pri kreiranju posta') })
				window.location.href = "http://localhost:8080/WebProg/personalData.html"
			event.preventDefault();
			return;
		}
	}
});
