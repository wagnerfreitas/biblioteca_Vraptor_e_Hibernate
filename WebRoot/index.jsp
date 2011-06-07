<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Biblioteca</title>
		<style>
			#geral {
				width: 600px;
				margin: 0 auto;
				text-align: center;
			}
			button {
				width: 100px;
			}
			.celulaDivisoria {
				width: 70px;
			}
			#tabela{
				border-bottom:1px dotted red;
			}
			#Usuario, #Livro, #Emprestimo{
				display: none;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.8.13.custom.css" />
		<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.13.custom.min.js"></script>
		<script type="text/javascript" src="js/index.js"></script>
	</head>
	<body>
		<div id="geral">
			<h1>
				Biblioteca
			</h1>
			O que deseja fazer?
			<table id="tabela">
				<tr>
					<td>
						Adicionar novo usuário:
					</td>
					<td>
						<button id="adicionarUsuario">
							Adicionar
						</button>
					</td>
					<td class="celulaDivisoria">
					</td>
					<td width="150px">
						Adicionar novo livro:
					</td>
					<td>
						<button id="adicionarLivro">
							Adicionar
						</button>
					</td>
				</tr>
				<tr>
					<td>
						Pesquisar usu&aacute;rio:
					</td>
					<td>
						<button id="pesquisarUsuario">
							Pesquisar
						</button>
					</td>
					<td class="celulaDivisoria">
					</td>
					<td>
						Pesquisar livro:
					</td>
					<td>
						<button id="pesquisarLivro">
							Pesquisar
						</button>
					</td>
				</tr>
				<tr>
					<td>
						Pesquisar empr&eacute;stimos:
					</td>
					<td>
						<button id="pesquisarEmprestimo">
							Pesquisar
						</button>
					</td>
					<td class="celulaDivisoria">
					</td>
				</tr>
			</table>
			<div id="result"></div>
			<form id="Usuario">
				<h1>Pesquisar</h1>
				<table>
					<tr>
						<td>
							Digite o nome do usuário:
						</td>
						<td>
							<input type="text" name="usuario.nome" />
						</td>
						<td>
							<input type="submit" value="Enviar" />
						</td>
					</tr>
				</table>
			</form>
			<form id="Livro" method="get" action="livros">
				<h1>Pesquisar</h1>
				<table>
					<tr>
						<td>
							Digite o nome do livro:
						</td>
						<td>
							<input type="text" id="pesquisaLivroNome" name="nome" />
						</td>
						<td>
							<input type="button" value="Enviar" id="btn-PesquisaLivro" />
						</td>
					</tr>
				</table>
			</form>
			<form id="Emprestimo" action="emprestimo/lista">
				<h1>Pesquisar</h1>
				<table>
					<tr>
						<td>
							Pesquisar empréstimo por livro: 
						</td>
						<td>
							<input type="text" name="emprestimo.livro" />
						</td>
						<td>
							<input type="submit" value="Enviar" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>