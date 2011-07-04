package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.AdministradorDAO;
import br.com.biblioteca.entitades.Administrador;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class LoginController {
	private Result result;
	private AdministradorDAO administradorDAO;
	private AdminSession adminSession;
	
	public LoginController(Result result, AdministradorDAO administradorDAO, AdminSession adminSession){
		this.result = result;
		this.administradorDAO = administradorDAO;
		this.adminSession = adminSession;
	}
	
	@Get
	@Path("/login")
	public void login(){
		if(adminSession.getAdministrador() != null){
			result.redirectTo(IndexController.class).index();
		}
	}
	
	@Post
	@Path("/login")
	public void login(Administrador administrador){
		String message;
		Administrador admin = administradorDAO.login(administrador.getNome(), administrador.getSenha());
		if(admin != null){
			adminSession.setAdministrador(admin);
			message = "Bem Vindo " + adminSession.getAdministrador().getNome();
			result.use(json()).from(message, "message").serialize();
			result.redirectTo(IndexController.class).index();
		}else{
			result.redirectTo(LoginController.class).erro();
		}
	}
	
	@Get
	@Path("/logout")
	public void logout(){
		adminSession.setAdministrador(null);
		result.redirectTo(this).login();
	}
	
	@Get
	@Path("/login/erro")
	public void erro(){
		String message = "Erro ao efetuar login";
		result.use(json()).from(message, "message").serialize();
	}
}