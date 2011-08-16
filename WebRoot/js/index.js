var templates = {},
	$formUsuario,
	$formLivro,
	$formEmprestimo, 
	$result,
	$usuarioNovo,
	$relatorioDeAuditoria,
	$formAuditoria,
	$adicionarGrupoDeAcesso,
	$msModal,
	$mudarSenha,
	$formMudarSenha;

$(document).ready(function(){
	$formUsuario = $('form#Usuario');
	$formLivro = $('form#Livro');
	$formEmprestimo = $('form#Emprestimo'); 
	$result = $("#result");
	$relatorioDeAuditoria = $("#relatorioDeAuditoria");
	$formAuditoria = $("form#formAuditoria");
	$adicionarGrupoDeAcesso = $("#adicionarGrupoDeAcesso");
	$msModal = $("#msg-modal");
	$mudarSenha =  $("#mudarSenha");
	$formMudarSenha = $("#formMudarSenha");
	
	$("#pesquisarUsuario").click(function() {
		exibirFormDialog($formUsuario, "Pesquisar usuário", 470)
	});
	
	$relatorioDeAuditoria.click(function() {
		turnFormAuditoriaValid();
		exibirFormDialog($formAuditoria, "Digite as datas", 450);
	});
	
	$("#pesquisarLivro").click(function() {
		exibirFormDialog($formLivro, "Pesquisar livro", 450)
	});
	
	$("#adicionarAcesso").click(function() {
		exibirFormDialog($adicionarGrupoDeAcesso,"Adicionar grupo de acesso",450);
	});
	
	$('#pesquisarEmprestimo').click(function() {
		exibirFormDialog($formEmprestimo, "Pesquisar empréstimo", 550);
	});
	
	$mudarSenha.click(function() {
		$formMudarSenha.dialog({
			title: "Mudar senha",
			width: 350,
			buttons: {
				Enviar: function() {
					$turnformMudarSenhaValid(); 
					if($formMudarSenha.valid()) {
					$.post("update/senha", $formMudarSenha.serialize())
						.success(function(msg) {
							$msModal.html(msg.message).dialog({
								title: "Mensagem",
								buttons: {
									Ok: function() {
										$formMudarSenha.dialog("close");
										$msModal.dialog("close");		
									} 
								}
							}).prev().find(".ui-dialog-titlebar-close").hide();				
						})
						.error(function(erro) {
							erro = JSON.parse( erro.responseText);
							alert(erro.message);
						});
					}
				}
			}
		});
	});
	
	$formMudarSenha.find("input").keydown(function(event) {
		if(event.keyCode === 13) {
			$formMudarSenha.parent().find("button").click();
		}
	});
	
	$("#adicionarUsuario").click(function(){
		buscarPagina({
			getUrl: "usuario/add",
			callback: displayFormNovoUsuario
		});
	});
	
	$("#adicionarLivro").click(function(){
		buscarPagina({
			getUrl: "livro/add",
			callback: displayFormNovoLivro
		});
	});
	$("#adcionarAdministrador").click(function(){
		buscarPagina({
			getUrl: "admin/add",
			callback: displayFormNovoAdministrador
		});
	});
});

$("#dataIninio").datepicker();
$("#dataFim").datepicker();

$("#formAuditoria").find("input").keydown(function(event){
	if(event.keyCode === 13){
		$formAuditoria.parent().find("button").click();
	}
});

function exibirFormDialog(formulario, titulo, width){
	formulario.dialog({
		modal: true,
		title: titulo,
		width: width,
		resizable: false,
		buttons: {
			Enviar: function(){
				formulario.submit();
			}
		}
	});
}

function buscarPagina(options){
	var hasTemplate = templates[options.getUrl] != undefined;
	if (hasTemplate){
		options.callback(templates[options.getUrl]);
	} else {
		$.ajax({
			url: options.getUrl,
			type: "GET",
			success: function(result){
				templates[options.getUrl] = result;
				options.callback(result)
			},
			error: function(erro){
				$result.dialog(erro);
			}
		});
	}
};

displayAddForm = function(data){
	var buttons = {
		Enviar: function(){
			if ($form.valid()){
				$.post(data.postUrl, $form.serialize())
					.success(function(msg){
						$msModal.html(msg.message +'<br />Deseja inserir outro '+ data.label +'?').dialog({
							title: "Messagem",
							buttons: {
								Ok: function() {
									$msModal.dialog("close");
									$form[0].reset();
									$form.find("input:first").focus();
								},
								Cancelar: function() {
									$msModal.dialog("close");
									$result.dialog("close");
								}
							}
						}).prev().find(".ui-dialog-titlebar-close").hide();
					})
					.error(function(erro){
						alert(erro.message);
					});
			}
		}
	};
	$result
		.html(data.result)
		.dialog({
			modal: true,
			resizable: false,
			title: data.title,
			width: data.width,
			buttons: buttons
		});
	$form = $('#'+ data.formId);
	data.formRulesFunction($form);
	onEnterSubmit($form, $result.parent().find("button:contains('"+ data.submiterName +"')"));
};

displayFormNovoUsuario = function(result){
	displayAddForm({
		postUrl: 'usuario/novo',
		formId: 'usuarioNovo',
		formRulesFunction: turnFormUsuarioValid,
		title: 'Adicionar usuário',
		label: 'usuário',
		width: 500,
		result: result,
		submiterName: "Enviar"
	})
}

displayFormNovoLivro = function(result){
	displayAddForm({
		postUrl: 'livro/novo',
		formId: 'livroNovo',
		formRulesFunction: turnFormLivroValid,
		title: 'Adicionar livro',
		label: 'livro',
		width: 400,
		result: result,
		submiterName: "Enviar"
	});
};

displayFormNovoAdministrador = function(result) {
	displayAddForm({
		postUrl: 'adimin/novo',
		formId: 'administradorNovo',
		formRulesFunction: turnFormAdministradorValid,
		title: 'Adicionar administrador',
		label: 'administrador',
		result: result,
		submiterName: 'Enviar'
	});
};

onEnterSubmit = function($form, $submiter) {
	$form.find("input").keydown (function(event){
		if(event.keyCode === 13) {
			$submiter.click();
		}
	});
}

function $turnformMudarSenhaValid() {
	$formMudarSenha.validate({
		rules: {
			'senhaAtual':{
				required: true,
				minlength: 6
			},
			'senha': {
				required: true,
				minlength: 6
			},
			'confirm_updateSenha': {
				required: true,
				minlength: 6,
				equalTo: "#updateSenha"
			}			
		},
		messages: {
			'senhaAtual':{
				required: "Digite sua senha atual",
				minlength: "A senha deve ter 6 caracteres"
			},
			'senha': {
				required: "Digite a senha",
				minlength: "A senha deve ter 6 caracteres"
			},
			'confirm_updateSenha': {
				required: "Digite a senha",
				minlength: "A senha deve ter 6 caracteres",
				equalTo: "Senhas não conferem"
			}
		}
	});
};

function turnFormAuditoriaValid() {
	$formAuditoria.validate({
		rules:{
			'dataIninio':{
				required: true
			}, 
			'dataFim':{
				required: true
			}
		},
		messages:{
			'dataIninio':{
				required: 'Digite a data'
			},
			'dataFim':{
				required: 'Digite a data'
			}
		}
	});
}