var $formDevolve = $("#formDevolve");
$(document).ready(function(){
	tornFormDevolverValid();
	$("#btn-devolve").click(function(){
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
	});
	$(".devolver").click(function(){
		$("#devolverLivro").show();					
		var valor = $(this).parent().parent().children(':nth-child(1)').text();
		$("#IdEmprestimo").val(valor); 
	});
	$("#calendario").datepicker();
	
	$formDevolve.find("input").keydown(function(event){
		if(event.keyCode === 13){
			$("#btn-devolve").click();
		}
	});
});

function tornFormDevolverValid(){
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