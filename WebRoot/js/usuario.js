var $formAtualiza = $("#formAtualiza");
var $formDeletar = $("#formDeletar");
$(document).ready(function(){
	$('.idDelete').click(function(e){
		e.stopPropagation();	
		if($('.idDelete').is(':checked')){
			$("#deletarUsuario").show();
		}else{
			$('#deletarUsuario').hide();
		}
	});
	
	$(".nome").click(function(){
		$("#AtualizarUsuario").show();
		var id=$(this).parent().parent().attr("usuarioId");
		var emprestimo = $(this).parent().parent().attr("emprestimoAtivo");
		var nome = $(this).parent().parent().children(':nth-child(1)').text();
		var email = $(this).parent().parent().children(':nth-child(2)').text();
		$("#IdUsuario").val(id);		
		$("#emprestimo").val(emprestimo);			
		$("#usuarioNome").val(nome);
		$("#usuarioEmail").val(email);
	});
	
	$("#deletarUsuario").click(function(){
		if($formDeletar.valid()){
			$.post("usuario/delete", $formDeletar.serialize())
				.success(function(msg){
					alert(msg.message);
					location.reload();
				})
				.error(function(erro){
					alert("Erro ao deletar usuário" + erro);
				});
		}
	});

	$("#atualizarUsuario").click(function(){
		turnFormAtulizaValid();
		if($formAtualiza.valid()){
			$.post("usuario/atualiza", $formAtualiza.serialize())
				.success(function(msg){
					alert(msg.message)
					location.reload();
				})
				.error(function(erro){
					alert(erro.message)
				});
		}
	});
	
	$formAtualiza.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#atualizarUsuario").click();
		}
	});
});

function turnFormAtulizaValid(){
	$formAtualiza.validate({
		rules:{
			'usuario.nome':{
				required: true,
				minlength: 3
			},
			'usuario.email':{
				required: true,
				minlength: 3
			}
		},
		messages:{
			'usuario.nome':{
				required: 'Digite seu nome',
				minlength: 'O nome deve conter no mínimo 3 caracteres'
			},
			'usuario.email':{
				required: 'Digite seu email',
				minlength: 'O email deve conter no mínimo 3 caracteres'
			}
		}
	});
}