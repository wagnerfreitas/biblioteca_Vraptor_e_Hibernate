<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Login</title>
		<style type="text/css">
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 11px }
		</style>
		<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.8.14.custom.css" />
	</head>
	<body>
	<div id="divLogin" class="bordaRedonda">
		<h1 class="titleLogin bordaRedonda">Login</h1>
		<form id="formLogin" method="post">
			<p><label for="nome" class="labelNome">Nome: </label><input type="text" name="nome" id="nome" /></p>
			<p><label for="senha" class="labelSenha">Senha: </label><input type="password" name="senha" id="senha" /></p>
			<h1><input type="button" id="loginEnviar" value="Logar" /></h1>
		</form>
	</div>
	<div id="msg-modal"></div>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
</html>