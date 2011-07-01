var $formEmpresta = $("#formEmpresta");
var $formDevolve = $("#fomDevolve");
var $formAtualiza = $("#formAtualiza");
var $formRemove = $(".formRemove");

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
		var livroEmprestado = $(this).parent().parent().attr("livroEmprestado");
		var nome = $(this).parent().parent().children(':nth-child(1)').text();
		var autor = $(this).parent().parent().children(':nth-child(2)').text();
		var edicao = $(this).parent().parent().children(':nth-child(3)').text();
		$("#idLivro").val(id);
		$("#nome").val(nome);
		$("#autor").val(autor);
		$("#edicao").val(edicao);
		$("#livroEmprestado").val(livroEmprestado);
	});
	
	$('.calendario').datepicker();
		
	$('#btn-pesquisar').click(function(){
		$.get("usuarios/list/"+ $("#pesquisarUsuario").val())
			.success(function(retorno){
				var usuarios = retorno.list;
				var sHtml = '<table><thead><tr><td style=\"width:150px\"> - Nome - </td><td style=\"width:150px\"> - Email - </td><td> - Emprestar - </td></tr></thead>';
				for(i = 0; i < usuarios.length; i++){
					sHtml += '<tr>'+
						'<td>'+ usuarios[i].nome +'</td>'+
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
	
	$("#btn-emprestar").click(function(){
		tornFormEmprestaValid()
		postarDados("livro/emprestar", $formEmpresta);
	});
	
	$formEmpresta.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#btn-emprestar").click();
		}
	});
	
	$("#btn-atualiza").click(function(){
		tornFormAtualizaValid();
		postarDados("livro/atualiza", $formAtualiza);
	});
	
	$formAtualiza.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#btn-atualiza").click();
		}
	});
	
	$("#btn-devolver").click(function(){
		postarDados("livro/devolve", $formDevolve);
	});
	
	$formDevolve.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#btn-devolver").click();	
		}
	});

	$("#apagarLivros").click(function(){
		postarDados("livro/remove", $formRemove);
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

function tornFormAtualizaValid(){
	$formAtualiza.validate({
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
	});
}

function tornFormEmprestaValid(){
	$formEmpresta.validate({
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

function tornFormDevolveValid(){
	$formDevolve.validate({
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
}

function setIdUsuario(val){
	$("#IDUsuario").val(val);
}