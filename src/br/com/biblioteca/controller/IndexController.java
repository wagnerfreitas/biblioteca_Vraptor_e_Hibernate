package br.com.biblioteca.controller;

import br.com.biblioteca.dao.AdminSession;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndexController {

	private Result result;
	private AdminSession adminSession;
	
	public IndexController(Result result, AdminSession adminSession){
		this.result = result;
		this.adminSession = adminSession;
	}

	@Get
	@Path("/")
	public void index() {
		if(adminSession.getUsuario() == null){
			result.redirectTo(LoginController.class).login();
			result.include("null", null);
			}else{
			result.include("usuario", adminSession.getUsuario().getNome())
				.include("permissaoDoUsuario", adminSession.getUsuario().getTipoDePerfil());
		}
	}
}