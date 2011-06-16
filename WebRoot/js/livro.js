var $formEmpresta = $("#formEmpresta");
$(document).ready(function(){
	$(".emprestar").click(function(){
		$("#atualizaLivro").hide();
		$("#DevolverLivro").hide();
		$("#EmprestarLivro").show();
		var valor = $(this).parent().parent().attr("idLivro");
		$("#IDLivro").val(valor);
		return false;
	});
	$(".devolver").click(function(){
		$("#atualizaLivro").hide();
		$("#EmprestarLivro").hide();
		$("#DevolverLivro").show();
		var valor = $(this).parent().parent().attr("idLivro");
		$("#id").val(valor);
		return false;
	});
	
	$(".nome").click(function(){	
		$("#EmprestarLivro").hide();
		$("#DevolverLivro").hide();
		$("#atualizaLivro").show();
		var id = $(this).parent().parent().attr("idLivro");
		var nome = $(this).parent().parent().children(':nth-child(1)').text();
		var autor = $(this).parent().parent().children(':nth-child(2)').text();
		var edicao = $(this).parent().parent().children(':nth-child(3)').text();
		$("#idLivro").val(id);
		$("#nome").val(nome);
		$("#autor").val(autor);
		$("#edicao").val(edicao);
	});
	
	$('.calendario').datepicker();
		
	$('#btn-pesquisar').click(function(){
		$.get("usuarios/list/"+ $("#pesquisarUsuario").val())
			.success(function(retorno){
				var usuarios = retorno.list;
				var sHtml = '<table><thead><tr><td> - Nome - </td><td> - Email - </td><td> - Emprestar - </td></tr></thead>';
				for(i = 0; i < usuarios.length; i++){
					sHtml += '<tr>'+
						'<td style=\"width:150px\">'+ usuarios[i].nome +'</td>'+
						'<td>'+ usuarios[i].email +'</td>'+
						'<td style=\"text-align: center;\"><input type="radio" onclick="setIdUsuario(this.value)" name="emprestar" class="usuario_id" value="'+ usuarios[i].id +'" /></td>' + 
						'</tr>';
				}
		sHtml += '</table>';
			
		$("#retornoUsuarios")
			.html(sHtml)
				.dialog({
				width: 600, 
				title: 'Usuários'
			});
			
		})
		.error(function(erro){
			$("#retornoUsuarios").dialog(erro);
		});
	});
	
	$("#btn-emprestar").click(function(){
		tornFormEmprestaValid()
		if($formEmpresta.valid()){
			$.post("livro/emprestar", $formEmpresta.serialize())
				.success(function(msg){
					alert(msg.message)
					location.reload();
				})
				.error(function(erro){
					alert(erro.message)
				});
		}
	});
	
	$formEmpresta.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#btn-emprestar").click();
		}
	});
	
	$('#pesquisarUsuario').keydown(function(event){
		if(event.keyCode === 13){
			$('#btn-pesquisar').click();
		}
	});
	
	$('.IdRemove').click(function(e){
		e.stopPropagation();	
		if($('.IdRemove').is(':checked')){
			$("#apagarLivros").show();
		}else{
			$('#apagarLivros').hide();
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
function tornFormEmprestaValid(){
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
				required: 'Selecione um usuário',
			},
			'dataDeEmprestimo':{
				required: 'Digite a data de empréstimo',
			}
		}
	});
}

function setIdUsuario(val){
	$("#IDUsuario").val(val);
}