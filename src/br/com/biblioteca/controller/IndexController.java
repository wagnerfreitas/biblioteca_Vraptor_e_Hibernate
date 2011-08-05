package br.com.biblioteca.controller;

import java.util.List;

import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.entidades.Acao;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndexController {

	private Result result;
	private AdminSession adminSession;
	private AcaoDAO acaoDAO;
	
	public IndexController(Result result, AdminSession adminSession, AcaoDAO acaoDAO){
		this.result = result;
		this.adminSession = adminSession;
		this.acaoDAO = acaoDAO;
	}

	@Get
	@Path("/")
	public void index() {
		if(adminSession.getUsuario() == null) {
			result.redirectTo(LoginController.class).login();
		} else {
			List<Acao> acoes = acaoDAO.acoes();
			result.include("acoes", acoes)
				.include("usuario", adminSession.getUsuario().getNome());
		}
	}
}