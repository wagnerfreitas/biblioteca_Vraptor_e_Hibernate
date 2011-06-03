<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"	content="text/html; charset=ISO-8859-1">
		<title>Empréstimos</title>
	</head>
	<body>
	Lista de livro emprestados
		<table>
		<c:forEach var="livro" items="${livroList}">
			<tr>
				<td>Nome: </td>
				<td style="width:150px">${livro.nome}</td>
				<td>Autor: </td>
				<td style="width:150px">${livro.autor}</td>
			</tr>
		</c:forEach> 
	</table> 
	</body>
</html>