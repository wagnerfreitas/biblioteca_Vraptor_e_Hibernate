package com.br.biblioteca.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.UserSession;
import com.br.biblioteca.dao.UsuarioDAO;
import com.br.biblioteca.entitades.Usuario;
import static br.com.caelum.vraptor.view.Results.json;

@Resource
public class LoginController {
	private Result result;
	private UsuarioDAO usuarioDAO;
	private UserSession userSession;
	
	public LoginController(Result result, UsuarioDAO usuarioDAO, UserSession userSession){
		this.result = result;
		this.usuarioDAO = usuarioDAO;
		this.userSession = userSession;
	}
	
	@Get
	@Path("/login")
	public void login(){
		if(userSession.getUsario() != null){
			result.redirectTo(IndexController.class).index();
		}
	}
	
	@Post
	@Path("/login")
	public void login(Usuario usuario){
		String message;
		Usuario user = usuarioDAO.login(usuario.getNome(), usuario.getSenha());
		if(user != null){
			userSession.setUsario(user);
			message = "Bem Vindo " + userSession.getUsario().getNome();
			result.use(json()).from(message, "message").serialize();
			result.redirectTo(IndexController.class).index();
		}else{
			result.redirectTo(LoginController.class).erro();
		}
	}
	
	@Get
	@Path("/logout")
	public void logout(){
		userSession.setUsario(null);
		result.redirectTo(this).login();
	}
	
	@Get
	@Path("/login/erro")
	public void erro(){
		String message = "Erro ao efetuar login";
		result.use(json()).from(message, "message").serialize();
	}
}