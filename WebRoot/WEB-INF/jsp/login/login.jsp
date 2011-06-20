<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Login</title>
	</head>
	<body>
		<form action="login" method="post">
		<h1>Faça Login</h1>
			<table>
				<tr>
					<td>
						Nome:
					</td>
					<td>
						<input type="text" name="usuario.nome" />
					</td>
				</tr>
				<tr>
					<td>
						Senha:
					</td>
					<td>
						<input type="password" name="usuario.senha" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Enviar" /></td>
				</tr>
			</table>
		</form>	
	</body>
</html>