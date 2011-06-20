<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>
<head>		
	<title>Adicionar Usuário</title>
	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/usuario-novo.js"></script>
	
	<style type="text/css">
		label { display: block; margin-top: 10px; }
		label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
	</style>
	
</head>
<form method="post" id="usuarioNovo">
	<fieldset>
		<legend>
			Adicionar usuário
		</legend>
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
					Email:
				</td>
				<td>
					<input type="text" name="usuario.email" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="button" id="btn" value="Enviar" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>