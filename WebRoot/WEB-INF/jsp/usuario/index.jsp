<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Lista de Usuários</title>
	</head>
	<body>
		<h1>Lista de Usuários</h1>
		<table>
			<c:forEach items="${usuarios}" var="usuario">
				<tr>
					<td>Nome: </td>
					<td style="width:220px">${usuario.nome}</td>
					<td>Email: </td>
					<td><a href="mailto:${usuario.email}">${usuario.email}</a></td>
				</tr>
			</c:forEach>
		</table>
		<a href="index.jsp">Voltar</a>
	</body>
</html>