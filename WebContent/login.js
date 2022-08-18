$(document).ready(function() {
	$('#forma').submit(function(event) {
		event.preventDefault();
		let username = $('input[name="username"]').val();
		let password = $('input[name="password"]').val();
		$('#error').hide();
		if(username === undefined || username === ''){
			$('#errorUsername').text('Field not entered')
			$('#errorUsername').show().delay(3000).fadeOut()
			return
		}
		if(password === undefined || password === ''){
			$('#errorPassword').text('Field not entered')
			$('#errorPassword').show().delay(3000).fadeOut()
			return
		}
		$.post({
			url: 'rest/users/login',
			data: JSON.stringify({username: username, password: password}),
			contentType: 'application/json',
			success: function(product) {
				$('#success').text('Uspesno ste se ulogovali.');
				$("#success").show().delay(3000).fadeOut();
			},
			error: function(message) {
				$('#error').text(message.responseText);
				$("#error").show().delay(3000).fadeOut();
			}
		});
	});
});
