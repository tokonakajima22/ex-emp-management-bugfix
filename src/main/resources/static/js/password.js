'use strict';
$(function() {
	$(document).on('keyup', '#password', function () {
		check_password();
	});

	$(document).on('keyup', '#confirmPassword', function () {
		check_password();
	});

	function check_password() {
		let hostUrl = 'http://localhost:8080/check';
		let inputPassword = $('#password').val();
		let inputConfirmPassword = $('#confirmPassword').val();
		$.ajax({
			url : hostUrl,
			type : 'POST',
			dataType : 'json',
			data : {
				password : inputPassword,
				confirmPassword : inputConfirmPassword
			},
			async : true
		// 非同期で処理を行う
		}).done(function(data) {
			// コンソールに取得データを表示
			console.log(data);
			console.dir(JSON.stringify(data));
			$('#validationErrorMessage2').html(data.validationErrorMessage2);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert('エラーが発生しました！');
			console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
			console.log('textStatus     : ' + textStatus);
			console.log('errorThrown    : ' + errorThrown.message);
		});
	}
});

