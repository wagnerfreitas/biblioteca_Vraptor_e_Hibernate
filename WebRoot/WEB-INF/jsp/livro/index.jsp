<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Lista de Livros</title>
	</head>
	<body>
		<h1>Lista de livro</h1>
		<table>
			<c:forEach items="${livroList}" var="livro">
				<tr>
					<td>Nome: </td>
					<td style="width: 220px">${livro.nome}</td>
					<td>Autor: </td>
					<td>${livro.autor}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>