var $formUsuario = $('form#Usuario');
var $formLivro = $('form#Livro');
var $formEmprestimo = $('form#Emprestimo'); 

$(document).ready(function(){
	$("#pesquisarUsuario").click(function(){
		exibirForm($formUsuario);
	});
	
	$("#pesquisarLivro").click(function(){
		exibirForm($formLivro);
	});
	
	$('#pesquisarEmprestimo').click(function(){
		exibirForm($formEmprestimo);
	});
	
	$("#adicionarUsuario").click(function(){
		postarDados("usuario/add");
	});
	
	$("#adicionarLivro").click(function(){
		 postarDados("livro/add");
	});
});

function exibirForm(formulario){
	$('form').hide();
	formulario.show();
};

function postarDados(url){
	$('form').hide();
	$.ajax({
		url: url,
		type: "GET",
		success: function(result){
			$("#result").html(result);
		},
		failure: function(){
			alert("Erro");
		}
	});
};