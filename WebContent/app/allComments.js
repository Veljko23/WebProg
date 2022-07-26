var app = new Vue({
	el: '#allComments',
	data: {
		comments: null
	},
	mounted() {
		axios.get('rest/comments')
			.then(response => (this.comments = response.data))
	},
	methods: {
		
	}
});
