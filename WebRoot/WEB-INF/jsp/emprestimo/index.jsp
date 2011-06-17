<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Empréstimos</title>
		<style type="text/css">
			#devolverLivro{
				display: none;
			}
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
		</style>
		<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.8.13.custom.css" />
	</head>
	<body>
	<h1>Lista de Empréstimos</h1>
		<table>
			<c:forEach items="${emprestimos}" var="emprestimo">
				<tr>
					<td style="display: none">${emprestimo.id}</td>
					<td>
						Nome: 
					</td>
					<td style="width: 220px">
						${emprestimo.usuario.nome}
					</td>
					<td>
						Livro:
					</td>
					<td style="width: 220px">
						${emprestimo.livro.nome}
					</td>
					<td>
						Data de empréstimo:
					</td>
					<td>
						<fmt:formatDate value="${emprestimo.dataDeEmprestimo.time}" pattern="dd/MM/yyyy" />
					</td>
					<td><button class="devolver">Devolver</button></td>
				</tr>
			</c:forEach>
		</table>
		<div id="devolverLivro">
			<h1>Devolver Livro</h1>
			<form method="post" id="formDevolve">
				<table>
					<tr>
						<td>Data de devolução: </td>
						<td>
							<input type="hidden" id="IdEmprestimo" name="id" />
							<input type="text" id="calendario" name="dataDeDevolucao" />
						</td>
						<td><input type="button" id="btn-devolve" value="Enviar" /></td>
					</tr>
				</table>
			</form>
		</div>
		<a href="index.jsp">Voltar</a>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.13.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/emprestimo.js"></script>
</html>