package br.com.biblioteca.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Permissao;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.interceptor.download.Download;

@Resource
public class UsuarioReportController {
	
	private final JasperMaker jasperMaker;
	private final UsuarioDAO usuarioDAO;
	
	public UsuarioReportController(JasperMaker jasperMaker, UsuarioDAO usuarioDAO) {
		this.jasperMaker = jasperMaker;
		this.usuarioDAO = usuarioDAO;
	}
	
	@Post
	@Path("relatorio/usuarios")
	@Permissao({"PERM_ADMIN", "PERM_GERAR_RELATORIOS"})
	public Download relatorioUsuarios(String filtro_relatorio){
		List<Usuario> usuarios = usuarioDAO.pesquisa(filtro_relatorio);
		Map<String, Object> parametros = new HashMap<String, Object>();
		Date data = new Date();
		parametros.put("TITLE", "Lista de usuários");
		parametros.put("DATE", data);
		return jasperMaker.makePdf("Usuario.jrxml", usuarios, "Usuarios.pdf", true, parametros);
	}
}