<head>
	<style type="text/css">
label {
	display: block;
	margin-top: 10px;
}

label.error {
	float: none;
	color: red;
	margin: 0 .5em 0 0;
	vertical-align: top;
	font-size: 12px
}
</style>
</head>
<form id="administradorNovo" method="post">
	<table>
		<tr>
			<td>Nome:</td><td><input type="text" name="administrador.nome" /></td>
		</tr>
		<tr>
			<td>Senha:</td><td><input type="password" id="password" name="administrador.senha" /></td>
		</tr>
		<tr>
			<td>Confimar Senha:</td><td><input type="password" name="confirm_password" /></td>
		</tr>
	</table>
</form>