var $formAtualiza = $("#formAtualiza");
var $formDeletar = $("#formDeletar");

$(document).ready(function(){
	$('.idDelete').click(function(e){
		e.stopPropagation();	
		if($('.idDelete').is(':checked')){
			$("#deletarUsuario").show();
		}else{
			$('#deletarUsuario').hide();
		}
	});
	
	$(".nome").click(function(){
		turnFormValid($formAtualiza);
		var id=$(this).parent().parent().attr("usuarioId");
		var nome = $(this).parent().parent().children(':nth-child(1)').text();
		var email = $(this).parent().parent().children(':nth-child(2)').text();
		$("#IdUsuario").val(id);		
		$("#usuarioNome").val(nome);
		$("#usuarioEmail").val(email);

		$("#AtualizarUsuario").dialog({
			modal: true,
			title: "Atualizar dados",
			width: 450,
			buttons: {
				Atualizar: function(){
					postarDados("usuario/atualiza", $formAtualiza);				
				}
			}
		});
	});
	
	$("#deletarUsuario").click(function(){
		postarDados("usuario/delete", $formDeletar)
	});

	$formAtualiza.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#AtualizarUsuario").parent().find("button").click();
		}
	});
});

function postarDados(rota, formulario){
	if(formulario.valid()){
		$.post(rota, formulario.serialize())
			.success(function(msg){
				alert(msg.message);
				location.reload();
			})
			.error(function(erro){
				alert(erro.message);
			});
	}
}

function turnFormValid($form){
	$form.validate({
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