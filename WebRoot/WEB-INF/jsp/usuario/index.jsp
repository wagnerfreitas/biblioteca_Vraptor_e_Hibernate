<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Usuários</title>
	</head>
	<body> 
	Adicionar usu&aacute;rio 
	<form method="post" action="usuario/add">
		<table>
			<tr>
				<td>Nome: </td>
				<td><input type="text" name="usuario.nome" /></td>
			</tr>
			<tr>
				<td>Email: </td>
				<td><input type="text" name="usuario.email"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Enviar" /></td>
			</tr>
		</table>
	</form>
	</body>
</html>