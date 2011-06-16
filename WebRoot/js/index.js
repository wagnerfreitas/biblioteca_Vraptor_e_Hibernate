$(document).ready(function(){
	$("#pesquisarUsuario").click(function(){
		$('form').hide();
		$('form#Usuario').show();
	});
	
	$("#pesquisarLivro").click(function(){
		$('form').hide();
		$('form#Livro').show();
	});
	
	$('#pesquisarEmprestimo').click(function(){
		$('form').hide();
		$('form#Emprestimo').show();
	});
	
	$("#adicionarUsuario").click(function(){
		$('form').hide();
		$.ajax({
			url: "usuario/add",
			type : "GET",
			success: function(result){
				$("#result").html(result);
			},
			failure: function(){
				$("#result").alert("Erro");
			}
		});
	});
	
	$("#btn-PesquisaLivro").click(function(){
		$("#Livro").submit();
	});
	
	$("#adicionarLivro").click(function(){
		$('form').hide();
		$.ajax({
			url: "livro/add",
			type: "GET",
			success: function(result){
				$("#result").html(result);
			},
			failure: function(){
				$("#result").alert("Erro");
			}
		});
	});
});