<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Empréstimos</title>
		<style type="text/css">
			#devolverLivro{
				display: none;
			}
			#gerarRelatorio{
				float:right;
				width:110px;
				background: none;
				border: none;
				text-decoration: underline;
				color: blue;
			}
			#div {
				width: 700px;
			}
			
			label { display: block; margin-top: 10px; }
			label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 12px }
		</style>
		<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.8.14.custom.css" />
	</head>
	<body>
		<div id="geral">
		<div id="reader">
				<div id="reader-left" class="lFloat"></div>
				<div id="reader-right" class="rFloat"></div>
				<div id="reader-logout" class="rFloat"><a href="logout" class="rFloat">sair</a></div>
				<div id="login" class="rFloat">Bem vindo, <strong>${usuario}</strong></div>
				<div id="reader-center"><strong>Lista de Empréstimos</strong></div>
				<div id="sombra" class="cFloat"></div>
			</div>
			<div id="content">
				<table>
					<tr>
						<td><strong> - Nome - </strong></td>
						<td><strong> - Livro - </strong></td>
						<td><strong> - Data de empréstimo - </strong></td>
					</tr>
					<c:forEach items="${emprestimos}" var="emprestimo">
						<tr livroId="${emprestimo.livro.id}">
							<td style="width: 220px">
								- ${emprestimo.usuario.nome}
							</td>
							<td>
								${emprestimo.livro.nome}
							</td>
							<td align="center">
								<fmt:formatDate value="${emprestimo.dataDeEmprestimo}" pattern="dd/MM/yyyy" />
							</td>
						<c:forEach items="${permissoesDoUsuario}" var="permissao">
							<c:if test="${permissao.nome == 'PERM_ADMIN' || permisao.nome == 'PERM_DEVOLVER_LIVRO'}">
								<td><button class="devolver">Devolver</button></td>
							</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
				</table>
				<div id="div">
					<a href="../biblioteca">Voltar</a><br/>
						<form id="formRelatorio" action="relatorio/emprestimos" method="post">
							<input type="hidden" name="nomeDoLivro" value="${nomeDoLivro}" />
							<input type="hidden" name="ordenarPor" value="${ordenarPor}" />
							<input type="submit" value="Gerar relatório" id="gerarRelatorio" />
						</form>
				</div>				
				<div id="devolverLivro">
					<form method="post" id="formDevolve">
						<input type="hidden" id="IdLivro" name="id" />
						<table>
							<tr>
								<td>Data de devolução: </td>
								<td><input type="text" id="calendario" name="dataDeDevolucao" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<div id="modal-msgs"></div>
			<div id="footer"></div>
		</div>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/emprestimo.js"></script>
</html>