<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Lista de Livros</title>
		<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.8.13.custom.css" />
		<style type="text/css">
			#geral {
				width: 800px;
			}
			#EmprestarLivro, #DevolverLivro, #atualizaLivro{
				display: none;
			}
			button{
				width:100px;
			}
			#login{
				float:right;
			}
			#div{
				width:600px;
			}
			#gerarRelatorio{
				float:right;
				width:110px;
				background: none;
				border: none;
				text-decoration: underline;
				color: blue;
			}
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
		</style>
	</head>
	<body>
		<div id="geral">
			<div id="login">
				Bem vindo, ${usuario}&nbsp;&nbsp;&nbsp;
				<a href="logout">Sair</a>
			</div><br>
				
			<h1>Lista de livros</h1>
			<form class="formRemove" method="post">
				<table>
					<thead>
						<tr>
							<td width="150px"> - Nome - </td>
							<td width="150"> - Autor - </td>
							<td width="80"> - Edição - </td>
							<td></td>
							<td width="105" align="center"> - Apagar -</td>
						</tr>
					</thead>
					<c:forEach items="${livros}" var="livro">
						<tr idLivro="${livro.id}" livroEmprestado="${livro.emprestado}">
							<td><a href="#" class="nome">${livro.nome}</a></td>
							<td>${livro.autor}</td>
							<td>${livro.edicao}</td>
							<c:if test="${livro.emprestado}">
								<td>
									<button class="devolver">Devolver</button>
								</td>
							</c:if>
							<c:if test="${!livro.emprestado}">
								<td>	
									<button class="emprestar">Emprestar</button>
								</td>
								<td style="text-align: center;">
									<input type="checkbox" name="IdRemove" class="IdRemove" value="${livro.id}" />
								</td>
							</c:if>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="5" align="right"><input type="button" style="display: none" id="apagarLivros" value="Apagar livros"></td>
					</tr>
				</table>
			</form>
			
			<div id="div">
				<a href="../biblioteca">Voltar</a><br/>
				<form id="formRelatorio" action="relatorio/livros" method="post">
					<input type="hidden" name="filtro_relatorio" value="${nome}" />
					<input type="submit" value="Gerar relatório" id="gerarRelatorio" />
				</form>
			</div>	
			
			<div id="EmprestarLivro">
				<table>
					<tr>
						<td style="width: 188px">Pesquisar usuário: </td>
						<td><input type="text" name="pesquisarUsuario" id="pesquisarUsuario"/></td>
						<td><input type="button" value="Pesquisar" id="btn-pesquisar"/></td>
					</tr>
					<tr id="trResultadoNomePesquisa" style="display: none">
						<td colspan="2">
							Nome: <span id="nomeResultadoPesquisa"></span>
						</td>
					</tr>
				</table>
				<form method="post" id="formEmpresta" action="livro/emprestar">
					<input type="hidden" name="idLivro" id="IDLivro" />
					<input type="hidden" name="iDUsuario" id="IDUsuario" />
					<table>
						<tr>
							<td>
								Digite a data do empréstimo:
							</td>
							<td>
								<input type="text" class="calendario" name="dataDeEmprestimo" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="DevolverLivro">
				<form method="post" id="fomDevolve" action="livro/devolve">
					<input type="hidden" id="id" name="id" />
					<table>
						<tr>
							<td>Data de devolução: </td>
							<td><input type="text" class="calendario" name="dataDeDevolucao" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="atualizaLivro">
				<form id="formAtualiza">
					<input type="hidden" name="livro.emprestado" id="livroEmprestado" />
					<input type="hidden" name="livro.id" id="idLivro" />
					<table>
						<tr>
							<td>Nome: </td><td><input type="text" name="livro.nome" id="nome" /></td>
						</tr>
						<tr>
							<td>Autor: </td><td><input type="text" name="livro.autor" id="autor"/></td>
						</tr>
						<tr>
							<td>Edição: </td><td><input type="text" name="livro.edicao" id="edicao"/></td>
						</tr>
					</table>			
				</form>
			</div>
			
			<div id="retornoUsuarios"></div>
		</div>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.13.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/livro.js" charset="utf-8"></script>	
</html>