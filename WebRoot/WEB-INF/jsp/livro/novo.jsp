<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>
<head>
	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/livro-novo.js"></script>
	<style type="text/css">
		label { display: block; margin-top: 10px; }
		label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
	</style>
</head>
<form action="livro/novo" id="livroNovo" method="post">
	<fieldset>
		<legend>
			Adicionar livro
		</legend>
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
				<td>
					Edição: 
				</td>
				<td>
					<input type="text" name="livro.edicao" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="Enviar" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>