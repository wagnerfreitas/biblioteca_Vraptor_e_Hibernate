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
		var id = $(this).parent().parent().children(':nth-child(1)').text();
		var emprestimo = $(this).parent().parent().children(':nth-child(2)').text();
		var nome = $(this).parent().parent().children(':nth-child(3)').text();
		var email = $(this).parent().parent().children(':nth-child(4)').text();
		$("#IdUsuario").val(id);		
		$("#emprestimo").val(emprestimo);			
		$("#usuarioNome").val(nome);
		$("#usuarioEmail").val(email);
	});
	
	$("#formAtualiza").validate({
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
});