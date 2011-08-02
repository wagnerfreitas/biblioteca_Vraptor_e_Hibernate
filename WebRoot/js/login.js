var $formLogin = $("#formLogin");
$(document).ready(function(){
	turnFormLoginValid();
	$("#loginEnviar").click(function(){
		if($formLogin.valid()){
			$.post("login", $formLogin.serialize())
				.success(function(msg){
					alert(msg.message);
					location.reload();
				})
				.error(function(msg){
					alert(msg.message);
				});
		}
	});
	
	$formLogin.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#loginEnviar").click();
		}
	});
});
function turnFormLoginValid(){
	$formLogin.validate({
		rules:{
			'nome':{
				required: true,
				minlength: 3
			},
			'senha':{
				required: true,
				minlength: 3
			}
		},
		messages:{
			'nome':{
				required: 'Digite seu nome',
				minlength: 'O nome deve conter no mínimo 3 caracteres'
			},
			'senha':{
				required: 'Digite sua senha',
				minlength: 'A senha deve conter no mínimo 3 caracteres'
			}
		}
	});
}