<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>		
	<link rel="stylesheet" type="text/css" href="usuario.css" />
	<title>Adicionar Usuário</title>
</head>
<form method="post" action="usuario/novo">
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
					<input type="submit" value="Enviar" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>