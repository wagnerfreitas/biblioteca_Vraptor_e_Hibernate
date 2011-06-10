<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>
<head>		
	<title>Adicionar Usuário</title>
	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.validate.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#usuarioNovo").validate({
				rules:{
					'usuario.nome':{
						required: true,
						minlength: 3
					},
					'usuario.email':{
						required: true,
						minlength: 3
					}
				},
				messages:{
					'usuario.nome':{
						required: 'Digite seu nome',
						minlength: 'O nome deve conter no mínimo 3 caracteres'
					},
					'usuario.email':{
						required: 'Digite seu email',
						minlength: 'O email deve conter no mínimo 3 caracteres'
					}
				}
			});
		});
	</script>
	
	<style type="text/css">
		label { display: block; margin-top: 10px; }
		label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
	</style>
	
</head>
<form method="post" id="usuarioNovo" action="usuario/novo">
	<fieldset>
		<legend>
			Adicionar usuário
		</legend>
		<table>
			<tr>
				<td>
					<label for="nome">
						Nome:
					</label>
				</td>
				<td>
					<input type="text" name="usuario.nome" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="email">
						Email:
					</label>
				</td>
				<td>
					<input type="text" name="usuario.email" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" id="btn"  value="Enviar" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>