package com.br.biblioteca.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.UsuarioDAO;
import com.br.biblioteca.entitades.Usuario;

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
		try {
			Usuario user = usuarioDAO.login(usuario.getNome(), usuario.getSenha());
			userSession.setUsario(user);
			result.redirectTo(IndexController.class).index();
		} catch (Exception e) {
			e.printStackTrace();
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
	}
}