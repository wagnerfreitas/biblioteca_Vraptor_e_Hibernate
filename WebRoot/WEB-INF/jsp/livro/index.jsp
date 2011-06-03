<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Livros</title>
  </head>
  <body>
	Adicionar Livro
		<form action="livro/adiciona" method="post">
			<table>
				<tr>
					<td>
						Nome:
					</td>
					<td>
						<input type="text" name="livro.nome" />
					</td>
				</tr>
				<tr>
					<td>
						Autor:
					</td>
					<td>
						<input type="text" name="livro.autor" />
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
	<!--<table>
		<c:forEach var="livro" items="${livroList}">
			<tr>
				<td>Nome: </td>
				<td style="width:150px">${livro.nome}</td>
				<td>Autor: </td>
				<td style="width:150px">${livro.autor}</td>
			</tr>
		</c:forEach> 
	</table>   -->
  </body>
</html>