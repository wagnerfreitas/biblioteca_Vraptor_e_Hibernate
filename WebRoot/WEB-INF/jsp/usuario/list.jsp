<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table>
	<c:forEach items="${usuarios}" var="usuario">
		<tr>
			<td style="display: none">${usuario.id}</td>
			<td>Nome: </td>
			<td>${usuario.nome}</td>
			<td>Email: </td>
			<td>${usuario.email}</td>
			<td><input type="radio" name="idUsuario" value="${usuario.id}" /></td>
		</tr>
	</c:forEach>
</table>