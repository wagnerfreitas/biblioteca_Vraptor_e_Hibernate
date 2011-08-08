var $formDevolve = $("#formDevolve");
$(document).ready(function(){
	$(".devolver").click(function(){
		turnFormDevolverValid();
		var valor = $(this).parent().parent().attr("livroId");
		$("#IdLivro").val(valor); 

		$("#devolverLivro").dialog({
			modal: true,
			title: "Devolver livro",
			width: 450,
			buttons: {
				Devolver: function() {
					if($formDevolve.valid()){
						$.post("emprestimo/devolve" , $formDevolve.serialize())
							.success(function(msg){
								alert(msg.message);
								location.reload();
							})
							.error(function(erro){
								alert(erro.mensagem);
							});						
					}
				}
			}
		});					
	});
	
	$("#calendario").datepicker();
	
	$formDevolve.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#btn-devolve").click();
		}
	});
});

function turnFormDevolverValid(){
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