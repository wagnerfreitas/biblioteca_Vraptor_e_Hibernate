<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<form action="livro/adiciona" method="post">
	<fieldset>
		<legend>
			Adicionar Livro
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
				<td></td>
				<td>
					<input type="submit" value="Enviar" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>