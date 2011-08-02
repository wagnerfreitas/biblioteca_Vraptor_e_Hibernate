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
		<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
	</head>
	<body>
	<div id="divLogin">
		<h1 class="titleLogin">Biblioteca</h1>
		<form id="formLogin" method="post">
			<label for="nome" class="labelNome">Nome: </label><input type="text" name="nome" />
			<label for="senha" class="labelSenha">Senha: </label><input type="password" name="senha" />
			<input type="button" id="loginEnviar" value="Enviar" />
		<!-- 
			<table>
				<tr>
					<td>Nome:</td>
					<td><input type="text" name="nome" /></td>
				</tr>
				<tr>
					<td>Senha:</td>
					<td><input type="password" name="senha" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="button" id="loginEnviar" value="Enviar" /></td>
				</tr>
			</table>  -->
		</form>
	</div>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
</html>