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
			url: "usuario/novo",
			type : "GET",
			success: function(result){
				$("#result").html(result);
			},
			failure: function(){
				$("#result").alert("Erro");
			}
		});
	});
	
	$('#pesquisaLivroNome').keydown(function(event){
		if(event.keyCode === 13){
			$('#enviaPesquisaLivro').click();
		}
	});
	
	$("#enviaPesquisaLivro").click(function(){
		$("#Livro").submit();
		location = 'livros/busca/' + $("#pesquisaLivroNome").val();
	});
	
	$("#adicionarLivro").click(function(){
		$('form').hide();
		$.ajax({
			url: "livro/novo",
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