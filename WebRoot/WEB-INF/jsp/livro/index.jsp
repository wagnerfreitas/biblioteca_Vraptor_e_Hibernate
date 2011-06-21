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
			#EmprestarLivro, #DevolverLivro, #atualizaLivro{
				display: none;
			}
			button{
				width:100px;
			}
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
		</style>
	</head>
	<body>
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
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><input type="button" style="display: none" id="apagarLivros" value="Apagar livros"></td>
				</tr>
			</table>
		</form>	
		
		<div id="EmprestarLivro">
			<h1>Emprestar livro</h1>
			<table>
				<tr>
					<td style="width: 172px">Pesquisar usuário: </td>
					<td><input type="text" name="pesquisarUsuario" id="pesquisarUsuario"/></td>
					<td><input type="button" value="Pesquisar" id="btn-pesquisar"/></td>
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
					<tr>
						<td></td>
						<td>
							<input type="button" id="btn-emprestar" value="Enviar" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="DevolverLivro">
		<h1>Devolver livro</h1>
			<form method="post" id="fomDevolve" action="livro/devolve">
				<input type="hidden" id="id" name="id" />
				<table>
					<tr>
						<td>Data de devolução: </td>
						<td><input type="text" class="calendario" name="dataDeDevolucao" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="button" id="btn-devolver" value="Enviar" /></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="atualizaLivro">
			<h1>Atualizar dados</h1>
			<form method="post" id="formAtualiza" action="livro/atualiza">
				<input type="hidden" name="livro.emprestado" id="livroEmprestado" />
				<table>
					<tr>
						<td>
							Nome: 
						</td>
						<td>
							<input type="hidden" name="livro.id" id="idLivro" />
							<input type="text" name="livro.nome" id="nome" />
						</td>
					</tr>
					<tr>
						<td>
							Autor:
						</td>
						<td>
							<input type="text" name="livro.autor" id="autor"/>
						</td>
					</tr>
					<tr>
						<td>
							Edição: 
						</td>
						<td><input type="text" name="livro.edicao" id="edicao"/></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="button" id="btn-atualiza" value="Enviar" /> 
						</td>
					</tr>
				</table>			
			</form>
		</div>
		
		<div id="retornoUsuarios"></div>
		<a href="../biblioteca">Voltar</a><br/>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.13.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/livro.js" charset="utf-8"></script>	
</html>