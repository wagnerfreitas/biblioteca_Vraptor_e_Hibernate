<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"	content="text/html; charset=ISO-8859-1">
		<title>Lista de Usu�rios</title>
		
		<style type="text/css">
			#AtualizarUsuario{
				display: none;
			}
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
		</style>
		
		<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="js/usuario.js"></script>
	</head>
	<body>
		<h1>Lista de Usu�rios</h1>
		<form action="usuario/delete" method="post">
			<table>
				<tr>
					<td style="width: 200px"> - Nome - </td>
					<td style="width: 200px"> - Email - </td>
					<td> - Apagar usu�rios - </td>
				</tr>
				<c:forEach items="${usuarios}" var="usuario">
					<tr>
						<td style="display: none">${usuario.id}</td>
						<td style="display: none">${usuario.emprestimoAtivo}</td>
						<td><a href="#" class="nome">${usuario.nome}</a></td>
						<td>${usuario.email}</td>
						<td style="text-align: center">
							<c:if test="${!usuario.emprestimoAtivo}">
								<input type="checkbox" name="idDelete" class="idDelete" value="${usuario.id}" />
							</c:if>
						</td>
						
					</tr>
				</c:forEach>
				<tr>
				<td></td>
				<td></td>
				<td><input type="submit" id="deletarUsuario" style="display: none" value="Deletar usu�rios" /></td>
				</tr>			
			</table>
		</form>
		
		<div id="AtualizarUsuario">
			<form id="formAtualiza" action="usuario/atualiza" method="post">
				<h1>Atualizar dados</h1>
				<table>
					<tr>
						<td>
							Nome: 
						</td>
						<td>
							<input type="hidden" id="emprestimo" name="usuario.emprestimoAtivo" />
							<input type="hidden" id="IdUsuario" name="usuario.id" />
							<input type="text" id="usuarioNome" name="usuario.nome" />
						</td>
					</tr>
					<tr>
						<td>
							Email: 
						</td>
						<td>
							<input type="text" id="usuarioEmail" name="usuario.email" />
						</td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Enviar" /></td>
					</tr>
				</table>
			</form>
		</div>
		<a href="index.jsp">Voltar</a>
	</body>
</html>