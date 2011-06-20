package com.br.biblioteca.controller;

import javax.servlet.http.HttpSession;

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
	private HttpSession httpSession;
	public static final Boolean AUTENTICADO = true;
	public static final Boolean NAO_AUTENTICADO = false;
	
	public LoginController(Result result, UsuarioDAO usuarioDAO, UserSession userSession, HttpSession httpSession){
		this.result = result;
		this.usuarioDAO = usuarioDAO;
		this.userSession = userSession;
		this.httpSession = httpSession;
	}
	
	@Get
	@Path("/login")
	public void login(){
	}
	
	@Post
	@Path("/login")
	public void login(Usuario usuario){
		try {
			Usuario user = usuarioDAO.login(usuario.getNome(), usuario.getSenha());
			userSession.setUsario(user);
			this.httpSession.setAttribute("autenticado", AUTENTICADO);
			result.redirectTo(IndexController.class).index();
		} catch (Exception e) {
			e.printStackTrace();
			this.httpSession.setAttribute("autenticado", NAO_AUTENTICADO);
			result.redirectTo(LoginController.class).erro();
		}
	}
	@Get
	@Path("/logout")
	public void logout(){
		userSession.setUsario(null);
		this.httpSession.setAttribute("autenticado", NAO_AUTENTICADO);
		result.redirectTo(this).login();
	}
	
	@Get
	@Path("/login/erro")
	public void erro(){
	}
}