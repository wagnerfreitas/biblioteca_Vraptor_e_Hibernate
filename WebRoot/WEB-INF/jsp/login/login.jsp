<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Login</title>
		<style type="text/css">
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
		</style>
	</head>
	<body>
		<form id="formLogin" method="post">
			<h1>Faça Login</h1>
			<table>
				<tr>
					<td>Nome:</td>
					<td><input type="text" name="nome" /></td>
				</tr>
				<tr>
					<td>
						Senha:
					</td>
					<td>
						<input type="password" name="senha" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="button" id="login" value="Enviar" /></td>
				</tr>
			</table>
		</form>	
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
</html>