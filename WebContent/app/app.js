var app = new Vue({
	el: '#products',
	data: {
		products: null,
		title: "Primer Vue.js tehnologije na spisku proizvoda",
		mode: "BROWSE",
		selectedProduct: {},
		error: '',
		putanja:""
	},
	mounted() {
		axios.get('rest/products')
			.then(response => (this.products = response.data))
	},
	methods: {
		editProduct: function(product) {
			this.selectedProduct = { ...product }
			this.mode = 'EDIT'
		},
		showForm: function() {
			this.mode = 'CREATE'
			this.selectedProduct = { id: '', name: null, price: null }
		},
		createOrEditProduct: function(event) {
			this.error = ""
			if (!this.selectedProduct.name || !this.selectedProduct.price) {
				this.error = "Unesite naziv i cenu";
				event.preventDefault();
				return;
			}
			this.selectedProduct.putanja = this.putanja;
			if (this.mode == 'CREATE') {
				axios.post('rest/products', this.selectedProduct)
					.then((response) => {
						alert('Novi proizvod uspešno kreiran')
						this.mode = 'BROWSE'
						this.products.push(response.data)
					})

			} else {
				axios.put('rest/products/' + this.selectedProduct.id, this.selectedProduct)
					.then((response) => {
						alert('Proizvod je uspešno izmenjen ')
						this.mode = 'BROWSE'
						this.products = this.products.filter((p) => p.id !== this.selectedProduct.id)
						this.products.push(response.data)
					})
			}

			event.preventDefault();
		},
		deleteProduct: function(product) {
			this.mode = 'BROWSE'
			axios.delete('rest/products/' + product.id)
				.then(() => {
					alert('Proizvod je uspesno obrisan')
					this.products = this.products.filter((p) => p.id !== product.id)
				})
		},
		loadFile: function(event) {
			console.log(event.target.value);
			this.putanja = URL.createObjectURL(event.target.files[0]);
		}
	}
});
