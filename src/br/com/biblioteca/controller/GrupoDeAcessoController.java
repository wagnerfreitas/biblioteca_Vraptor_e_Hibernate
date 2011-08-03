package br.com.biblioteca.controller;

import br.com.biblioteca.entidades.GrupoDeAcesso;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class GrupoDeAcessoController {
	
	private Result result;
	
	public GrupoDeAcessoController(Result result) {
		this.result = result;
	}
	
	@Post
	@Path("grupo/novo")
	public void grupo(GrupoDeAcesso grupoDeAcesso) {
		
	}
}