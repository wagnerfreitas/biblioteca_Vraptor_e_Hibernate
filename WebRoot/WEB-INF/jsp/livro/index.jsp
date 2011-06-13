<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Lista de Livros</title>
		<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.8.13.custom.css" />
		<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.13.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery.ui.datepicker-pt-BR.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$(".emprestar").click(function(){
					$("#DevolverLivro").hide();
					$("#EmprestarLivro").show();
					var valor = $(this).parent().parent().children(':nth-child(1)').text();
					$("#IDLivro").val(valor);
				});
				$(".devolver").click(function(){
					$("#EmprestarLivro").hide();
					$("#DevolverLivro").show();
					var valor = $(this).parent().parent().children(':nth-child(1)').text();
					$("#id").val(valor);
				});
				$('.calendario').datepicker();
				
				$('#btn-pesquisar').click(function(){
					$.ajax({
						url: "usuarios/list",
						data: "$btn-pesquisar=" + $("#pesquisarUsuario").val(),
						type: "POST",
						success: function(retorno){
							$("#retornoUsuarios").html(retorno);
							$("#retornoUsuarios").dialog({ width: 600 } ,{ title: 'Usu�rios' });
						},
						failure: function(){
							$("#retornoUsuarios").alert("Erro");							
						}
					});
				});
				$("#ApagarLivros").click(function(){
					$(".formRemove").submit();
				});
			});
		</script>
		<style type="text/css">
			#EmprestarLivro, #DevolverLivro{
				display: none;
			}
			button{
				width:100px;
			}
			#ApagarLivros{
				width:110px;
			}
		</style>
	</head>
	<body>
		<h1>Lista de livros</h1>
		<form class="formRemove" method="post" action="livro/remove">
			<table>
				<tr>
					<td> - Nome - </td>
					<td> - Autor - </td>
					<td></td>
					<td>Apagar livros</td>
				</tr>
				<c:forEach items="${livros}" var="livro">
					<tr>
						<td style="display: none">${livro.id}</td>
						<td style="width: 220px">. ${livro.nome}</td>
						<td style="width: 120px">${livro.autor}</td>
						<td>
							<c:if test="${livro.emprestado}">
								<button class="devolver">Devolver</button>
							</c:if>
	
							<c:if test="${!livro.emprestado}">
								<button class="emprestar">Emprestar</button>
						<td style="text-align: center;">
							<input type="checkbox" name="IdRemove" value="${livro.id}" />
						</td>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td><button id="ApagarLivros">Apagar livros</button></td>
				</tr>
			</table>
		</form>	
		
		<div id="EmprestarLivro">
			<h1>Emprestar livro</h1>
			<table>
				<tr>
					<td style="width: 172px">Pesquisar usu�rio: </td>
					<td><input type="text" name="pesquisarUsuario" id="pesquisarUsuario"/></td>
					<td><input type="button" value="Pesquisar" id="btn-pesquisar"/></td>
				</tr>
			</table>
			<form method="post" action="livro/emprestar">
				<table>
					<tr>
						<td>
							<input type="hidden" name="idLivro" id="IDLivro">
						</td>
					</tr>
					<tr>
						<td>
							Digite o ID do usu�rio:
						</td>
						<td>
							<input type="text" name="IdUsuario" id="IDUsuario" />
						</td>
					</tr>
					<tr>
						<td>
							Digite a data do empr�stimo:
						</td>
						<td>
							<input type="text" class="calendario" name="dataDeEmprestimo" />
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" value="Enviar" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="DevolverLivro">
		<h1>Devolver livro</h1>
			<form method="post" action="livro/devolve">
				<table>
					<tr>
						<td><input type="hidden" name="id" id="id" /></td>
					</tr>
					<tr>
						<td>Data de devolu��o: </td>
						<td><input type="text" class="calendario" name="dataDeDevolucao" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Enviar" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="retornoUsuarios"></div>
		<a href="index.jsp">Voltar</a><br/>
	</body>
</html>