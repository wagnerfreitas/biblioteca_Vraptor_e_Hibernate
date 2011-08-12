var $formDevolve = $("#formDevolve"),
	$modalMsgs = $("#modal-msgs");

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
								$modalMsgs.html(msg.message).dialog({
									title: "Mensagem",
									buttons: {
										Ok: function() {
											location.reload();
									}}
								}).prev().find(".ui-dialog-titlebar-close").hide();
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