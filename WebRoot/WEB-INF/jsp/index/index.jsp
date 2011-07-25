<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
			#relatorioDeAuditoria {
				border: none;
				background: none;
				text-decoration: underline;
				color: blue;
				width: 150px;
			}
			.celulaDivisoria {
				width: 70px;
			}
			#tabela{
				border-bottom:1px dotted red;
			}
			#Usuario, #Livro, #Emprestimo, #formAuditoria{
				display: none;
			}
			#login{
				float:right;
			}
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
		</style>
		<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.8.13.custom.css" />
	</head>
	<body>
		<div id="geral">
			<div id="login">
				Bem vindo, ${nome}&nbsp;&nbsp;&nbsp;
				<a href="logout">Sair</a>
			</div><br>	
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
					<td>
						<button id="relatorioDeAuditoria">Relatório de auditoria</button>
					</td>
				</tr>
			</table>
			<div id="result"></div>
			<form id="Usuario" method="get" action="usuarios">
				<table>
					<tr>
						<td>
							Digite o nome do usuário:
						</td>
						<td>
							<input type="text" name="nome" />
						</td>
					</tr>
				</table>
			</form>
			<form id="Livro" method="get" action="livros">
				<table>
					<tr>
						<td>
							Digite o nome do livro:
						</td>
						<td>
							<input type="text" id="pesquisaLivroNome" name="nome" />
						</td>
					</tr>
				</table>
			</form>
			<form id="Emprestimo" method="get" action="emprestimos">
				<table>
					<tr>
						<td>
							Ordenar por: 
						</td>
					</tr>
					<tr>
						<td>
							Nome do usuário	
							<input type="radio" name="ordenarPor" checked="checked" value="nomeDoUsuario" />
						</td>
						<td>
							Data de empréstimo
							<input type="radio" name="ordenarPor" value="dataDeEmprestimo" />
						</td>
					</tr>
					<tr>
						<td>
							Pesquisar empréstimo por livro: 
						</td>
						<td>
							<input type="text" name="nomeDoLivro" />
						</td>
					</tr>
				</table>
			</form>
			<form method="post" id="formAuditoria" action="relatorio/auditoria">
				<table>
					<tr>
						<td>De:	</td><td><input type="text" id="dataIninio" name="dataIninio" /></td>			
						<td>Até: </td><td><input type="text" id="dataFim" name="dataFim" /></td>	
					</tr>
				</table>
			</form>
		</div>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.13.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="js/index.js"></script>
	<script type="text/javascript" src="js/livro.js"></script>
	<script type="text/javascript" src="js/usuario.js"></script>
</html>