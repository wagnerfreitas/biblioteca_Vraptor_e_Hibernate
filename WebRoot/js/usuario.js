﻿var $formAtualiza = $("#formAtualiza"),
	$formDeletar = $("#formDeletar");

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
		turnFormUsuarioValid($formAtualiza);
		var id = $(this).parent().parent().attr("usuarioId").trim(),
		nome = $(this).parent().parent().children(':nth-child(1)').text().trim(),
		email = $(this).parent().parent().children(':nth-child(2)').text().trim();
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

function turnFormUsuarioValid($form){
	$form.validate({
			rules:{
				'usuario.nome':{
					required: true,
					minlength: 3
				},
				'usuario.email':{
					required: true,
					email: true
				},
				'usuario.senha':{
					required: true,
					minlength: 6
				},
				'confirm_password':{
					required: true,
					minlength: 6,
					equalTo: '#password'
				},
				'usuario.tipoDePerfil': {
					required: true
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
				'usuario.senha':{
					required: 'Digite a senha',
					minlength: 'A senha deve conter no minímo 6 caracteres'
				},
				'confirm_password':{
					required: 'Digite a senha',
					minlength: 'A senha deve conter no minímo 6 caracteres',
					equalTo: 'Senhas não conferrem'							
				},
				'usuario.tipoDePerfil': {
					required: 'Escolha uma opção'
				}
			}
	});
}