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
			#geral{
				width: 900px;
			}
			#login{
				float:right;
			}
			#div{
				width:800px;
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
		<link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.8.14.custom.css" />
	</head>
	<body>
		<div id="geral">
			<div id="login"> 
				cBem vindo, ${usuario}&nbsp;&nbsp;&nbsp; 
				<a href="logout">Sair</a>
			</div><br>
			
			<h1>Lista de Empréstimos</h1>
			<table>
				<tr>
					<td>
						<strong> - Nome - </strong> 
					</td>
					<td>
						<strong> - Livro - </strong>
					</td>
					<td>
					 	<strong> - Data de empréstimo - </strong> 
					</td>
				</tr>
				<c:forEach items="${emprestimos}" var="emprestimo">
					<tr emprestimoId="${emprestimo.id}">
						<td style="width: 220px">
							-${emprestimo.usuario.nome}
						</td>
						<td>
							${emprestimo.livro.nome}
						</td>
						<td align="center">
							<fmt:formatDate value="${emprestimo.dataDeEmprestimo}" pattern="dd/MM/yyyy" />
						</td>
						<td><button class="devolver">Devolver</button></td>
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
					<input type="hidden" id="IdEmprestimo" name="id" />
					<table>
						<tr>
							<td>Data de devolução: </td>
							<td><input type="text" id="calendario" name="dataDeDevolucao" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/emprestimo.js"></script>
</html>