var app = new Vue({
	el: '#allPosts',
	data: {
		posts: null
	},
	mounted() {
		axios.get('rest/posts')
			.then(response => (this.posts = response.data))
	},
	methods: {
		
	}
});
