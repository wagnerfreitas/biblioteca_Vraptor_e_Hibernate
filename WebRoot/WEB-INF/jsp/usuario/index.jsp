<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"	content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="css/custom-theme/jquery-ui-1.8.14.custom.css" />
		<title>Lista de Usuários</title>
		
		<style type="text/css">
			#AtualizarUsuario{
				display: none;
			}
			#geral {
				width: 800px;
			}
			#login{
				float:right;
			}
			#div{
				width:525px;
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
			
			<h1>Lista de Usuários</h1>
			<form method="post" id="formDeletar">
				<table>
					<tr>
						<td style="width: 200px"> - Nome - </td>
						<td style="width: 200px"> - Email - </td>
						<td style="width: 122px"> - Apagar usuários - </td>
					</tr>
					<c:forEach items="${usuarios}" var="usuario">
						<tr usuarioId="${usuario.id}">
							<td>
								<c:if test="${permissaoDoUsuario == 'MEMBRO'}">
									${usuario.nome}
								</c:if>
								<c:if test="${permissaoDoUsuario != 'MEMBRO'}">
									<a href="#" class="nome">${usuario.nome}</a>
								</c:if>
							</td>
							<td>${usuario.email}</td>
							<c:if test="${permissaoDoUsuario != 'MEMBRO'}">
								<td style="text-align: center">
									<input type="checkbox" name="idDelete" class="idDelete" value="${usuario.id}" />
								</td>
							</c:if>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="3" align="right"><input type="button" id="deletarUsuario" style="display: none" value="Deletar usuários" /></td>
					</tr>			
				</table>
			</form>
			
			<div id="div">
				<a href="../biblioteca">Voltar</a><br/>
				<c:if test="${permissaoDoUsuario != 'MEMBRO'}">
					<form id="formRelatorio" action="relatorio/usuarios" method="post">
						<input type="hidden" name="filtro_relatorio" value="${nome}" />
						<input type="submit" value="Gerar relatório" id="gerarRelatorio" />
					</form>
				</c:if>
			</div>
			
			<div id="AtualizarUsuario">
				<form id="formAtualiza" method="post">
					<input type="hidden" id="IdUsuario" name="id" />
					<table>
						<tr>
							<td>Nome:</td><td><input type="text" id="usuarioNome" name="nome" /></td>
						</tr>
						<tr>
							<td>Email: </td><td><input type="text" id="usuarioEmail" name="email" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="message-toolbar"></div>
	</body>
	<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/usuario.js"></script>
</html>