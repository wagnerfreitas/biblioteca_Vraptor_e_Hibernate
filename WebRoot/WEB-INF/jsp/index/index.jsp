<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Biblioteca</title>
		<link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.8.14.custom.css" />
		<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
		<style>
			#relatorioDeAuditoria, #mudarSenha {
				border: none;
				background: none;
				text-decoration: underline;
				color: blue;
				width: 150px;
				margin-left: -12px;
			}
			.celulaDivisoria {
				width: 70px;
			}
			#Usuario, #Livro, #Emprestimo, #formAuditoria, #adicionarGrupoDeAcesso, #formMudarSenha{
				display: none;
			}
			#Emprestimo fieldset {
				border: solid 1px #ccc;
				width: 80%;
				margin-top: 10px;
			}
			#Emprestimo fieldset label {
				display: inline;
				margin-left: 10px;
			}
						
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
		</style>
	</head>
	<body>
		<div id="geral">
			<div id="reader">
				<div id="reader-left" class="lFloat"></div>
				<div id="reader-right" class="rFloat"></div>
				<div id="reader-logout" class="rFloat"><a href="logout" class="rFloat">sair</a></div>
				<div id="login" class="rFloat">Bem vindo, <strong>${usuario}</strong></div>
				<div id="reader-center"><strong>Biblioteca</strong></div>
				<div id="sombra" class="cFloat"></div>
			</div>
			<div id="content">
				<table id="tabela">
					<tr>
						<c:forEach items="${permissoesDoUsuario}" var="permissao">
							<c:if test="${permissao.nome == 'PERM_ADMIN' || permissao.nome == 'PERM_ADD_USUARIO'}">
								<td>Adicionar novo usuário:</td>
								<td><button id="adicionarUsuario">Adicionar</button></td>
								<td class="celulaDivisoria"></td>
							</c:if>
							<c:if test="${permissao.nome == 'PERM_ADMIN' || permissao.nome == 'PERM_ADD_LIVRO'}">
								<td width="150px">Adicionar novo livro:</td>
								<td><button id="adicionarLivro">Adicionar</button></td>
							</c:if>
						</c:forEach>
					</tr>
					<tr>
						<td>Pesquisar usu&aacute;rio:</td>
						<td><button id="pesquisarUsuario">Pesquisar</button></td>
						<td class="celulaDivisoria"></td>
						<td>Pesquisar livro:</td>
						<td><button id="pesquisarLivro">Pesquisar</button></td>
					</tr>
					
					<tr>
						<td>Pesquisar empr&eacute;stimos:</td>
						<td><button id="pesquisarEmprestimo">Pesquisar</button></td>
						<td class="celulaDivisoria"></td>
						<td>Adicionar grupo de acesso:</td>
						<td><button id="adicionarAcesso">Adicionar</button></td>
					</tr>	
	
					<tr>
						<c:forEach items="${permissoesDoUsuario}" var="permissao">
							<c:if test="${permissao.nome == 'PERM_ADMIN' || permissao.nome == 'PERM_GERAR_RELATORIOS'}">
									<td><button id="relatorioDeAuditoria">Relatório de auditoria</button></td>
							</c:if>
						</c:forEach>
						<td><button id="mudarSenha">Mudar senha</button></td>
					</tr>
															
				</table>
				
				<form id="Usuario" method="get" action="usuarios">
					<table>
						<tr>
							<td>Digite o nome do usuário:</td>
							<td><input type="text" name="nome" /></td>
						</tr>
					</table>
				</form>
				
				<form id="Livro" method="get" action="livros">
					<table>
						<tr>
							<td>Digite o nome do livro:</td>
							<td><input type="text" id="pesquisaLivroNome" name="nome" /></td>
						</tr>
					</table>
				</form>
				
				<form id="adicionarGrupoDeAcesso" method="post" action="grupo/novo">
					<table>
						<tr>
							<td>Digite o nome do grupo: </td>
							<td><input type="text" name="nome" /></td>
						</tr>
					</table>
					<c:forEach items="${acoes}" var="acao">
						<input type="checkbox" name="id" value="${acao.id}" /> ${acao.descricao}<br />
					</c:forEach>
				</form>
				
				<form id="Emprestimo" method="get" action="emprestimos">
					Pesquisar empréstimo por livro:<input type="text" name="nomeDoLivro" />
					<fieldset>
						<legend> Ordenar por:</legend>
							<label for="ordenarPorNomeLivro">Nome do usuário </label><input type="radio" id="ordenarPorNomeLivro" name="ordenarPor" checked="checked" value="nomeDoUsuario" />
							<label for="ordenarPorDataEmprestimo">Data de empréstimo </label><input type="radio" id="ordenarPorDataEmprestimo" name="ordenarPor" value="dataDeEmprestimo" />
					</fieldset>
				</form>
				
				<form method="post" id="formMudarSenha">
					<input type="hidden" value="${usuarioGetId}" name="usuarioId" />
					<table>
						<tr>
							<td>Nova senha: </td><td><input type="password" name="senha" id="updateSenha"/></td>
						</tr>
						<tr>
							<td>Confimar senha: </td><td><input type="password" name="confirm_updateSenha" id="confirm_updateSenha" /></td>
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
			<div id="msg-modal"></div>
			<div id="result"></div>
			<div id="footer"></div>
		</div>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="js/index.js"></script>
	<script type="text/javascript" src="js/livro.js"></script>
	<script type="text/javascript" src="js/usuario.js"></script>
</html>