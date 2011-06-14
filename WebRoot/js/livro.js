$(document).ready(function(){
	$(".emprestar").click(function(){
		$("#atualizaLivro").hide();
		$("#DevolverLivro").hide();
		$("#EmprestarLivro").show();
		var valor = $(this).parent().parent().children(':nth-child(1)').text();
		$("#IDLivro").val(valor);
		return false;
	});
	$(".devolver").click(function(){
		$("#atualizaLivro").hide();
		$("#EmprestarLivro").hide();
		$("#DevolverLivro").show();
		var valor = $(this).parent().parent().children(':nth-child(1)').text();
		$("#id").val(valor);
		return false;
	});
	
	$(".nome").click(function(){
		$("#EmprestarLivro").hide();
		$("#DevolverLivro").hide();
		$("#atualizaLivro").show();
		var id = $(this).parent().parent().children(':nth-child(1)').text();
		var nome = $(this).parent().parent().children(':nth-child(2)').text();
		var autor = $(this).parent().parent().children(':nth-child(3)').text();
		var edicao = $(this).parent().parent().children(':nth-child(4)').text();
		$("#idLivro").val(id);
		$("#nome").val(nome);
		$("#autor").val(autor);
		$("#edicao").val(edicao);
	});
	
	$('.calendario').datepicker();
		
	$('#btn-pesquisar').click(function(){
		$.ajax({
			url: "usuarios/list",
			data: "$btn-pesquisar=" + $("#pesquisarUsuario").val(),
			type: "POST",
			success: function(retorno){
				$("#retornoUsuarios").html(retorno);
				$("#retornoUsuarios").dialog({ width: 600 } ,{ title: 'Usuários' });
			},
			failure: function(){
				$("#retornoUsuarios").alert("Erro");							
			}
		});
	});
	$('.IdRemove').click(function(e){
		e.stopPropagation();	
		if($('.IdRemove').is(':checked')){
			$("#apagarLivros").show();
		}else{
			$('#apagarLivros').hide();
		}
	});
		
	$("#formEmpresta").validate({
		rules:{
			'IdUsuario':{
				required: true,
			},
			'dataDeEmprestimo':{
				required: true,
			},
		},
		messages:{
			'IdUsuario':{
				required: 'Digite o ID do usuário',
			},
			'dataDeEmprestimo':{
				required: 'Digite a data de empréstimo',
			}
		}
	});
	$("#fomDevolve").validate({
		rules:{
			'dataDeDevolucao':{
				required: true
			}		
		},
		messages:{
			'dataDeDevolucao':{
				required: 'Digite a data de devolução'
			}
		}		
	});
	
	$("#formAtualiza").validate({
		rules:{
			'livro.nome':{
				required: true,
				minlength: 3
			},
			'livro.autor':{
				required: true,
				minlength: 3
			},
			'livro.edicao':{
				required: true,
				minlength: 1
			}
		},
		messages:{
			'livro.nome':{
				required: 'Digite o nome do livro',
				minlength: 'O nome do livro deve conter no mínimo 3 caracteres'
			},
			'livro.autor':{
				required: 'Digite o nome do autor',
				minlength: 'O o nome do autor deve conter no mínimo 3 caracteres'
			},
			'livro.edicao':{
				required: 'Digite a edição do livro',
				minlength: 'A edição do livro deve conter no mínimo 1 caractere'
			}
		}
	})
});