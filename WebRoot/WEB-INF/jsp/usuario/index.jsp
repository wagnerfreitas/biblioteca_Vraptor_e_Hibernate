<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
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
	</head>
	<body>
		<h1>Lista de Usuários</h1>
			<c:forEach items="${usuarios}" var="usuario">
			<div class="form">
			<form method="post" action="usuario/delete">
				<ul>
					<li style="display: none">${usuario.id}</li>
					<li>- Nome:&nbsp;</li>
					<li style="width: 200px">${usuario.nome}</li>
					<li>- Email:&nbsp;</li>
					<li>${usuario.email}</li>
				</ul>
				<ul>
					<li class="btn-deletar">
						<input type="hidden" class="deletausuario" name="id" value="${usuario.id}" />
						<input type="submit" class="deletar" value="Deletar" />
					</li>
				</ul>
			</form>
		</div>
		<br />
			</c:forEach>
		<a href="index.jsp">Voltar</a>
	</body>
</html>