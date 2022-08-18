var app = new Vue({
	el: '#allMessages',
	data: {
		messages: null
	},
	mounted() {
		axios.get('rest/messages')
			.then(response => (this.messages = response.data))
	},
	methods: {
		
	}
});
