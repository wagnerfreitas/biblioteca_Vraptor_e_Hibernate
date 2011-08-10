package br.com.biblioteca.controller;

import java.util.List;

import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Acao;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndexController {

	private Result result;
	private UsuarioSession usuarioSession;
	private AcaoDAO acaoDAO;
	
	public IndexController(Result result, UsuarioSession usuarioSession, AcaoDAO acaoDAO){
		this.result = result;
		this.usuarioSession = usuarioSession;
		this.acaoDAO = acaoDAO;
	}

	@Get
	@Path("/")
	public void index() {
		try {
			if(usuarioSession.getUsuario() == null) {
				result.redirectTo(LoginController.class).login();
			} else {
				List<Acao> acoes = acaoDAO.acoes();
				result.include("acoes", acoes)
					.include("permissoesDoUsuario", usuarioSession.getUsuario().getGrupoDePerfil().getAcoes())
					.include("usuario", usuarioSession.getUsuario().getNome());
			}
		} catch (Exception e) {
			result.forwardTo(this).index();
		}
	}
}