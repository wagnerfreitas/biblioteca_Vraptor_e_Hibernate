var $usuarioNovo;
$(document).ready(function(){
	$usuarioNovo = $("#usuarioNovo");
	turnFormValid();
	$("#btn").click(function(){
		if ($usuarioNovo.valid()){
			$.post('usuario/novo', $usuarioNovo.serialize())
				.success(function(msg){
					if (confirm(msg.message +'\nDeseja inserir outro usuário?')){
						$usuarioNovo[0].reset();
						$usuarioNovo.find("input:first").focus();
					} else { 
						$usuarioNovo.hide();
					}
				})
				.error(function(erro){
					alert(erro.message);
				});
			}
	});
	
	$usuarioNovo.find("input").keydown(function(event){
		if(event.keyCode === 13 ){
			$("#btn").click();
		}
	})
});
function turnFormValid(){
	$usuarioNovo.validate({
			rules:{
				'usuario.nome':{
					required: true,
					minlength: 3
				},
				'usuario.email':{
					required: true,
					email: true
				}
			},
			messages:{
				'usuario.nome':{
					required: 'Digite seu nome',
					minlength: 'O nome deve conter no mínimo 3 caracteres'
				},
				'usuario.email':{
					required: 'Digite seu email',
					email: 'Digite um email válido'
				},
			}
	});
}