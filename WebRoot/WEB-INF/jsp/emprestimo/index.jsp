<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Empréstimos</title>
	</head>
	<body>
	Lista de Empréstimos
		<table>
			<c:forEach items="${emprestimoList}" var="emprestimo">
				<tr>
					<td>
						Nome:
					</td>
					<td style="width: 220px">
						${emprestimo.usuario}
					</td>
					<td>
						Livro:
					</td>
					<td>
						${emprestimo.livro}
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>