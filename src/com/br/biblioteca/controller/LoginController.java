package com.br.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.AdminSession;
import com.br.biblioteca.dao.AdministradorDAO;
import com.br.biblioteca.entitades.Administrador;
import com.br.biblioteca.entitades.Usuario;

@Resource
public class LoginController {
	private Result result;
	private AdministradorDAO administradorDAO;
	private AdminSession userSession;
	
	public LoginController(Result result, AdministradorDAO administradorDAO, AdminSession userSession){
		this.result = result;
		this.administradorDAO = administradorDAO;
		this.userSession = userSession;
	}
	
	@Get
	@Path("/login")
	public void login(){
		if(userSession.getAdministrador() != null){
			result.redirectTo(IndexController.class).index();
		}
	}
	
	@Post
	@Path("/login")
	public void login(Usuario usuario){
		String message;
		Administrador admin = administradorDAO.login(usuario.getNome(), usuario.getSenha());
		if(admin != null){
			userSession.setAdministrador(admin);
			message = "Bem Vindo " + userSession.getAdministrador().getNome();
			result.use(json()).from(message, "message").serialize();
			result.redirectTo(IndexController.class).index();
		}else{
			result.redirectTo(LoginController.class).erro();
		}
	}
	
	@Get
	@Path("/logout")
	public void logout(){
		userSession.setAdministrador(null);
		result.redirectTo(this).login();
	}
	
	@Get
	@Path("/login/erro")
	public void erro(){
		String message = "Erro ao efetuar login";
		result.use(json()).from(message, "message").serialize();
	}
}