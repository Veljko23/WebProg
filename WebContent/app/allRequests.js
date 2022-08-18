var app = new Vue({
	el: '#allRequests',
	data: {
		requests: null
	},
	mounted() {
		axios.get('rest/requests')
			.then(response => (this.requests = response.data))
	},
	methods: {
		
	}
});
