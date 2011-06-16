var $usarioNovo;
$(document).ready(function(){
	$usarioNovo = $("#usuarioNovo");
	turnFormValid();
	$("#btn").click(function(){
		if ($usarioNovo.valid()){
			$.post('usuario/novo', $usarioNovo.serialize())
				.success(function(msg){
					if (confirm(msg.message +'\nDeseja inserir outro usuário?')){
						$usarioNovo[0].reset();
						$usarioNovo.find("input:first").focus();
					} else {
						$usarioNovo.hide();
					}
				})
				.error(function(erro){
					alert(erro.message);
				});
			}
	});
	
	$("#usuarioNovo input").keydown(function(event){
		if(event.keyCode === 13 ){
			$("#btn").click();
		}
	})
});
function turnFormValid(){
	$usarioNovo.validate({
			rules:{
				'usuario.nome':{
					required: true,
					minlength: 3
				},
				'usuario.email':{
					required: true,
					minlength: 3
				}
			},
			messages:{
				'usuario.nome':{
					required: 'Digite seu nome',
					minlength: 'O nome deve conter no mínimo 3 caracteres'
				},
				'usuario.email':{
					required: 'Digite seu email',
					minlength: 'O email deve conter no mínimo 3 caracteres'
				}
			}
	});
}