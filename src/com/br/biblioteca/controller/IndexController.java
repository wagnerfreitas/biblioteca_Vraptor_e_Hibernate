package com.br.biblioteca.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndexController {

	private Result result;
	private UserSession userSession;
	
	public IndexController(Result result, UserSession userSession){
		this.result = result;
		this.userSession = userSession;
	}

	@Get
	@Path("/")
	public void index() {
		if(userSession.getUsario() == null){
			result.redirectTo(LoginController.class).login();
		}else{
			result.include("nome", userSession.getUsario().getNome());
		}
	}
}