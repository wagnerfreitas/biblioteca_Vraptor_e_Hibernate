var templates = {},
	$formUsuario,
	$formLivro,
	$formEmprestimo, 
	$result,
	$usuarioNovo,
	$relatorioDeAuditoria,
	$formAuditoria;

$(document).ready(function(){
	$formUsuario = $('form#Usuario');
	$formLivro = $('form#Livro');
	$formEmprestimo = $('form#Emprestimo'); 
	$result = $("#result");
	$relatorioDeAuditoria = $("#relatorioDeAuditoria");
	$formAuditoria = $("form#formAuditoria");
	
	$("#pesquisarUsuario").click(function(){
		exibirFormDialog($formUsuario, "Pesquisar usuário", 470)
	});
	
	$relatorioDeAuditoria.click(function(){
		turnFormAuditoriaValid();
		exibirFormDialog($formAuditoria, "Digite as datas", 450);
	});
	
	$("#pesquisarLivro").click(function(){
		exibirFormDialog($formLivro, "Pesquisar livro", 450)
	});
	
	$('#pesquisarEmprestimo').click(function(){
		exibirFormDialog($formEmprestimo, "Pesquisar empréstimo", 550);
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
		buttons: {
			Pesquisar: function(){
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
						if (confirm(msg.message +'\nDeseja inserir outro '+ data.label +'?')){
							$form[0].reset();
							$form.find("input:first").focus();
						} else { 
							$result.dialog("close");
						}
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
			title: data.title,
			width: 400,
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
		formRulesFunction: turnFormValid,
		title: 'Adicionar usuário',
		label: 'usuário',
		result: result,
		submiterName: "Enviar"
	})
}

displayFormNovoLivro = function(result){
	displayAddForm({
		postUrl: 'livro/novo',
		formId: 'livroNovo',
		formRulesFunction: turnFormValid,
		title: 'Adicionar livro',
		label: 'livro',
		result: result,
		submiterName: "Enviar"
	});
};

onEnterSubmit = function($form, $submiter) {
	$form.find("input").keydown (function(event){
		if(event.keyCode === 13) {
			$submiter.click();
		}
	});
}

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