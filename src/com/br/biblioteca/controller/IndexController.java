package com.br.biblioteca.controller;

import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndexController {

	private HttpSession httpSession;
	private Result result;

	public IndexController(HttpSession httpSession, Result result) {
		this.httpSession = httpSession;
		this.result = result;
	}

	@Get
	@Path("/")
	public void index() {
		if (!(Boolean) this.httpSession.getAttribute("autenticado")) {
			result.redirectTo(LoginController.class).login();
		}
	}
}