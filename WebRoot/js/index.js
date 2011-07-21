var templates = {},
	$formUsuario,
	$formLivro,
	$formEmprestimo, 
	$result,
	$usuarioNovo;

$(document).ready(function(){
	$formUsuario = $('form#Usuario');
	$formLivro = $('form#Livro');
	$formEmprestimo = $('form#Emprestimo'); 
	$result = $("#result");
	
	$("#pesquisarUsuario").click(function(){
		exibirFormDialog($formUsuario, "Pesquisar usuário", 450)
	});
	
	$("#pesquisarLivro").click(function(){
		exibirFormDialog($formLivro, "Pesquisar livro", 450)
	});
	
	$('#pesquisarEmprestimo').click(function(){
		exibirForm($formEmprestimo);
	});
	
	$("#adicionarUsuario").click(function(){
		buscarPagina({
			getUrl: "usuario/add",
			callback: displayFormNovoUsuario
		});
	});
	
	$("#adicionarLivro").click(function(){
		 buscarPagina("livro/add", "Adicionar livro");
	});
});

function exibirFormDialog(formulario, titulo, width){
	formulario.dialog({
		modal: true,
		title: titulo,
		width: width,
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
							$form.hide();
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
};

displayFormNovoUsuario = function(result){
	displayAddForm({
		postUrl: 'usuario/novo',
		formId: 'usuarioNovo',
		formRulesFunction: makeFormUsuarioNovoValid,
		title: 'Adicionar usuário',
		label: 'usuário',
		result: result
	})
}