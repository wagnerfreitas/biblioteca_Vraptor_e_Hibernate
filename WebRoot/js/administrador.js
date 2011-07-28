$(document).ready(function(){
});
function turnFormAdministradorValid($form) {
	$form.validate({
		rules:{
			'administrador.nome': {
				required: true,
				minlength: 3
			},
			'administrador.senha': {
				required: true,
				minlength: 6
			},
			'confirm_password': {
				required: true, 
				equalTo: "#password",
				minlength: 6
			}
		},
		messages: {
			'administrador.nome': {
				required: 'Digite o nome do administrador',
				minlength: 'O nome deve conter no mínimo 3 caracteres'
			},
			'administrador.senha': {
				required: 'Digite a senha',
				minlength: 'A senha deve conter no mínimo 6 caracteres'
			},
			'confirm_password': {
				required: 'Digite a senha',
				minlength: 'A senha deve conter no mínimo 6 caracteres',
				equalTo: 'Senhas não conferem'				
			}
		}
	});
}