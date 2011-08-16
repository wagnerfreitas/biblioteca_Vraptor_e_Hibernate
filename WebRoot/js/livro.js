var $formEmpresta,
	$formDevolve,
 	$formAtualiza,
 	$formRemove,
	$msgModal,
	PESQUISA_USUARIO_VALOR_DEFAULT;
$(document).ready(function(){
	$formEmpresta = $("#formEmpresta");
	$formDevolve = $("#fomDevolve");
 	$formAtualiza = $("#formAtualiza");
 	$formRemove = $(".formRemove");
	$msgModal = $("#msg-modal");
	PESQUISA_USUARIO_VALOR_DEFAULT = '(Nome para pesquisa)';
	
	$(".emprestar").click(function(){
		$("#atualizaLivro").hide();
		$("#DevolverLivro").hide();
		$("#EmprestarLivro").dialog({
			modal:true, 
			title: "Emprestar livro", 
			width: 450,
			buttons: {
				Enviar: function(){
					tornFormEmprestaValid();
					postarDados("livro/emprestar", $formEmpresta);
				}
			}
		});
		var valor = $(this).parent().parent().attr("idLivro");
		$("#IDLivro").val(valor);
		return false;
	});
	$(".devolver").click(function(){
		$("#atualizaLivro").hide();
		$("#EmprestarLivro").hide();
		$("#DevolverLivro").dialog({
			modal: true,
			title: "Devolver livro",
			width: 450,
			buttons: {
				Enviar: function() {
					tornFormDevolveValid();
					postarDados("livro/devolve", $formDevolve);
				}
			}
		});
		var valor = $(this).parent().parent().attr("idLivro").trim();
		$("#id").val(valor);
		return false;
	});
	$('.IdRemove').click(function(e){
		e.stopPropagation();	
		if($('.IdRemove').is(':checked')){
			$("#apagarLivros").show();
		}else{
			$('#apagarLivros').hide();
		}
	});
	
	$(".nome").click(function(){	
		turnFormAtualizaValid();		
		var id = $(this).parent().parent().attr("idLivro"),
			livroEmprestado = $(this).parent().parent().attr("livroEmprestado"),
			nome = $(this).parent().parent().children(':nth-child(1)').text().trim(),
		 	autor = $(this).parent().parent().children(':nth-child(2)').text().trim(),
			edicao = $(this).parent().parent().children(':nth-child(3)').text().trim();
		$("#idLivro").val(id);
		$("#nome").val(nome);
		$("#autor").val(autor);
		$("#edicao").val(edicao);
		$("#livroEmprestado").val(livroEmprestado);

		$("#atualizaLivro").dialog({
			modal: true,
			width: 450,
			title: "Atualizar dados",
			buttons: {
				Atualizar: function() {
					postarDados("livro/atualiza", $formAtualiza);	
				}
			}
		});
	});
	
	$('.calendario').datepicker();
		
	$('#btn-pesquisar').click(function(){
		var $campo = $("#pesquisarUsuario"),
			valor = $.trim( $campo.val() );
		if ( valor === PESQUISA_USUARIO_VALOR_DEFAULT ){
			return;
		}
		if ( valor === '' ){
			$campo.val( PESQUISA_USUARIO_VALOR_DEFAULT );
			return;
		}
		$.get("usuarios/list/"+ $("#pesquisarUsuario").val())
			.success(function(retorno){
				var i, 
					usuarios = retorno.list,
					sHtml = '<table><thead><tr><td style=\"width:150px\"> - Nome - </td><td style=\"width:150px\"> - Email - </td><td> - Emprestar - </td></tr></thead>';
				for(i = 0; i < usuarios.length; i++){
					sHtml += '<tr>'+
						'<td class=\"usuarioNome\">'+ usuarios[i].nome +'</td>'+
						'<td>'+ usuarios[i].email +'</td>'+
						'<td style=\"text-align: center;\"><input type="radio" onclick="//setIdUsuario(this.value)" name="emprestar" class="usuario_id" value="'+ usuarios[i].id +'" /></td>' + 
						'</tr>';
				}
		sHtml += '</table>'
			+'<span id="selecioneUsuario" style=\"display: none; color: red\">Selecione um usuário</span>';
		
		$("#retornoUsuarios")
			.html(sHtml)
				.dialog({
					modal: true,
					width: 600, 
					title: 'Usuários',
					buttons: {
						Emprestar: function (){
							var $id = $(".usuario_id");
							if(!$id.is(":checked")){
								$("#selecioneUsuario")
									.show()
									.effect("highlight",{}, 3000);
							}else{
								setIdUsuario($id.val());
								$("#trResultadoNomePesquisa").show();
								var radio = $(".usuario_id:checked"),
									labelNome = radio.parent().parent().children(':nth-child(1)').text();
								$("#nomeResultadoPesquisa").html(labelNome)
									.effect("highlight", {}, 3000);
								$("#retornoUsuarios").dialog("close");
							}
						}
					}
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
	
	$formEmpresta.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#btn-emprestar").click();
		}
	});
	
	$formAtualiza.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#atualizaLivro").parent().find("button").click();
		}
	});
	
	$formDevolve.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#btn-devolver").click();	
		}
	});

	$("#apagarLivros").click(function(){
		postarDados("livro/remove", $formRemove);
	});

	$("#pesquisarUsuario").focus( function (){
		if ( $("#pesquisarUsuario").val() === PESQUISA_USUARIO_VALOR_DEFAULT ){
			$("#pesquisarUsuario").val('');
		}
	}); 

});	// $(document).ready()

function postarDados(rota, formulario){
	if(formulario.valid()){
		$.post(rota, formulario.serialize())
			.success(function(msg){
				$msgModal.html(msg.message).dialog({
					title: "Mensagem", 
					buttons: {
						Ok: function() {
							location.reload();
						}
					}
				}).prev().find(".ui-dialog-titlebar-close").hide();
			})
			.error(function(erro){
				alert(erro.message);
			});
	}
}

function turnFormAtualizaValid(){
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

function turnFormLivroValid($form){
	$form.validate({
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

function setIdUsuario(val){
	$("#IDUsuario").val(val);
}