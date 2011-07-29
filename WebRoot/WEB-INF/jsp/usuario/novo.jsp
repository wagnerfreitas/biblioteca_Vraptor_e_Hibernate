	<style type="text/css">
		label { display: block; margin-top: 10px; }
		label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
	</style>
<form method="post" id="usuarioNovo">
	<table>
		<tr>
			<td>Nome:</td><td><input type="text" name="usuario.nome" /></td>
			<td>Senha:</td><td><input type="password" name="usuario.senha" id="password"/></td>
		</tr>
		<tr>
			<td>Email:</td><td><input type="text" name="usuario.email" /></td>
			<td>Confirmar senha:</td><td><input type="password" name="comfirm_password" id="comfirm_password" /></td>
		</tr>
		<tr>
		</tr>
	</table>
	Membro: <input type="radio" value="MEMBRO" name="usuario.tipoDePerfil" />
	Moderador: <input type="radio" value="MODERADOR" name="usuario.tipoDePerfil" />
	Administrador: <input type="radio" value="ADMINISTRADOR" name="usuario.tipoDePerfil" />
</form>