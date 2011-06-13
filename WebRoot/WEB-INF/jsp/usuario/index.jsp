<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"	content="text/html; charset=ISO-8859-1">
		<title>Lista de Usuários</title>
		<style type="text/css">
			form{
				width:740px;
			}
			li {
				list-style: none;
				float: left;
			}
			.btn-deletar{
				float: right;
			}
		</style>
		<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$('.idDelete').click(function(){
					if($('.idDelete:checked')){
						$("#deletarUsuario").show();
					}
				});
			});
		</script>
	</head>
	<body>
		<h1>Lista de Usuários</h1>
		<form action="usuario/delete" method="post">
			<table>
				<tr>
					<td style="width: 200px"> - Nome - </td>
					<td style="width: 200px"> - Email - </td>
					<td> - Apagar usuários - </td>
				</tr>
				<c:forEach items="${usuarios}" var="usuario">
					<tr>
						<td style="display: none">${usuario.id}</td>
						<td>. ${usuario.nome}</td>
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
				<td><input type="submit" id="deletarUsuario" style="display: none" value="Deletar usuários" /></td>
				</tr>			
			</table>
		</form>
		<a href="index.jsp">Voltar</a>
	</body>
</html>