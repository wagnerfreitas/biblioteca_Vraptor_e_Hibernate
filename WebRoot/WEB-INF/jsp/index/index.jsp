<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Biblioteca</title>
		<link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.8.14.custom.css" />
		<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
		<style>
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
				<div id="menu">
					<ul id="nav">
						<li><a href="#">Adicionar</a>
							<ul>
								<c:forEach items="${permissoesDoUsuario}" var="permissao">
									<c:if test="${permissao.nome == 'PERM_ADMIN' || permissao.nome == 'PERM_ADD_USUARIO'}">
										<li><a href="#" id="adicionarUsuario">Usuário</a></li>
									</c:if>
									<c:if test="${permissao.nome == 'PERM_ADMIN' || permissao.nome == 'PERM_ADD_LIVRO'}">
										<li><a href="#" id="adicionarLivro">Livro</a></li>
									</c:if>
									<c:if test="${permissao.nome == 'PERM_ADMIN' || permissao.nome == 'PERM_ADD_GRUPO_ACESSO'}">
										<li><a href="#" id="adicionarAcesso">Adicionar grupo de acesso</a></li>
									</c:if>
								</c:forEach>
							</ul>
						</li>
						<li><a href="#">Pesquisar</a>
							<ul>
								<li><a href="#" id="pesquisarUsuario">Usuário</a></li>
								<li><a href="#" id="pesquisarLivro">Livro</a></li>
								<li><a href="#" id="pesquisarEmprestimo">Empréstimo</a></li>
							</ul>
						</li>
						<li><a href="#">Administração</a>
							<ul>
								<li><a href="#" id="mudarSenha">Mudar senha</a></li>
							</ul>
						</li>
						<c:forEach items="${permissoesDoUsuario}" var="permissao">
							<c:if test="${permissao.nome == 'PERM_ADMIN' || permissao.nome == 'PERM_GERAR_RELATORIOS'}">
								<li><a href="#">Relatório</a>
									<ul>
										<li><a href="#" id="relatorioDeAuditoria">Auditoria</a></li>
									</ul>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<div id="listDeLivros" class="bordaRedonda">
					<div class="titleGridList bordaRedonda">Livros</div>
					<table>
						<c:forEach items="${listLivros}" var="livro">
							<tr>
								<td>Nome: ${livro.nome}</td>
								<td>Autor: ${livro.autor}</td>
								<td>Edição: ${livro.edicao}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
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
					<table>
						<tr>
							<td>Senha atual: </td><td><input type="password" name="senhaAtual" /></td>
						</tr>
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
			<div id="footer" class="cFloat"></div>
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