$(document).ready(function(){
	$(".emprestar").click(function(){
		$("#DevolverLivro").hide();
		$("#EmprestarLivro").show();
		var valor = $(this).parent().parent().children(':nth-child(1)').text();
		$("#IDLivro").val(valor);
		return false;
	});
	$(".devolver").click(function(){
		$("#EmprestarLivro").hide();
		$("#DevolverLivro").show();
		var valor = $(this).parent().parent().children(':nth-child(1)').text();
		$("#id").val(valor);
		return false;
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
});