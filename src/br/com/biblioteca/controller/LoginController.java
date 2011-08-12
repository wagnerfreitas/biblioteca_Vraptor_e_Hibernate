package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class LoginController {
	private Result result;
	private UsuarioDAO usuarioDAO;
	private UsuarioSession usuarioSession;
	
	public LoginController(Result result, UsuarioDAO usuarioDAO, UsuarioSession usuarioSession){
		this.result = result;
		this.usuarioDAO = usuarioDAO;
		this.usuarioSession = usuarioSession;
	}
	
	@Get
	@Path("/login")
	public void login(){
		if(usuarioSession.getUsuario() != null){
			result.redirectTo(IndexController.class).index();
		}
	}
	
	@Post
	@Path("/login")
	public void login(String nome, String senha){
		try {
			Usuario usuarioLogado = usuarioDAO.login(nome, senha);
			if(usuarioLogado != null){
				usuarioSession.setUsuario(usuarioLogado);
				result.redirectTo(IndexController.class).index();
			} else {
				result.redirectTo(this).erro();
			}
		} catch (Exception e) {
			result.include("message", e.getMessage());
			result.use(json()).from(e.getMessage(), "message").serialize();
		}
	}
	
	@Get
	@Path("/logout")
	public void logout(){
		usuarioSession.setUsuario(null);
		result.redirectTo(this).login();
	}
	
	@Get
	@Path("/login/erro")
	public void erro(){
		String message = "Erro ao efetuar login";
		result.use(json()).from(message, "message").serialize();
	}
	
	@Get
	public void negado() {
		String message = "Acesso negado";
		result.use(json()).from(message, "message").serialize();
	}
}