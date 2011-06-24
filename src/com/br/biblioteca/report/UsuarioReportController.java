package com.br.biblioteca.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.interceptor.download.Download;

import com.br.biblioteca.dao.UsuarioDAO;
import com.br.biblioteca.entitades.Usuario;

public class UsuarioReportController {
	
	private final JasperMaker jasperMaker;
	private final UsuarioDAO usuarioDAO;
	
	public UsuarioReportController(JasperMaker jasperMaker, UsuarioDAO usuarioDAO) {
		this.jasperMaker = jasperMaker;
		this.usuarioDAO = usuarioDAO;
	}
	
	@Post
	@Path("/relatorio/usuarios/{nome}")
	public Download relatorioUsuarios(String nome){
		List<Usuario> usuarios = usuarioDAO.pesquisa(nome);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nome", nome);
		return jasperMaker.makePdf("", usuarios, "Usuarios.pdf", true, parametros);
	}
}