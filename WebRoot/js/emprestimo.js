var $formDevolve = $("#formDevolve");
var $formRelatorio = $("#formRelatorio");
$(document).ready(function(){
	tornFormDevolverValid();
	$("#btn-devolve").click(function(){
		postarDados($formDevolve, "emprestimo/devolve");
		location.reload();
	});
	
	$("#gerarRelatorio").click(function(){
		postarDados($formRelatorio, "relatorio/emprestimos")
	});
	
	function postarDados(formulario, rota) {
		if(formulario.valid()) {
			$.post(rota,formulario.serialize())
				.success(function(msg) {
					alert(msg.message);
				})
				.error(function(erro){
					alert(erro.message);
				}); 
		}
	};
	
	$(".devolver").click(function(){
		$("#devolverLivro").show();					
		var valor = $(this).parent().parent().attr("emprestimoId");
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