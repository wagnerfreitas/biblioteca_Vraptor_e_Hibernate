<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<body>
		Adicionar Livro
		<form action="livro/adiciona" method="post">
			<table>
				<tr>
					<td>
						Nome:
					</td>
					<td>
						<input name="livro.nome" />
					</td>
				</tr>
				<tr>
					<td>
						Autor:
					</td>
					<td>
						<input name="livro.autor" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="submit" value="Enviar" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>