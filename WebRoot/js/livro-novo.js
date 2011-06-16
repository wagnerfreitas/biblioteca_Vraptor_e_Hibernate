var $livroNovo;
$(document).ready(function(){
	$livroNovo = $("#livroNovo");
	turnFormValid();
	$("#btn").click(function(){
		if($livroNovo.valid()){
			$.post('livro/novo', $livroNovo.serialize())
				.success(function(msg){
					if(confirm(msg.message + "\nDeseja adicionar outro livro?")){
						$livroNovo[0].reset();
						$livroNovo.find("input:first").focus();
					}else{
						$livroNovo.hide();				
					}		
			})
			.error(function(msg){
				alert(erro.msg);
			});
		}
	});
});
	
function turnFormValid(){
	$("#livroNovo").validate({
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