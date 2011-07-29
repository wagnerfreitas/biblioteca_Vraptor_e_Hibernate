package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;
import br.com.biblioteca.dao.AdminSession;
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
	private AdminSession adminSession;
	
	public LoginController(Result result, UsuarioDAO usuarioDAO, AdminSession adminSession){
		this.result = result;
		this.usuarioDAO = usuarioDAO;
		this.adminSession = adminSession;
	}
	
	@Get
	@Path("/login")
	public void login(){
		if(adminSession.getUsuario() != null){
			result.redirectTo(IndexController.class).index();
		}
	}
	
	@Post
	@Path("/login")
	public void login(Usuario usuario){
		String message;
		try {
			Usuario usuarioLogado = usuarioDAO.login(usuario.getNome(), usuario.getSenha());
			if(usuarioLogado != null){
				adminSession.setUsuario(usuarioLogado);
				message = "Bem Vindo " + adminSession.getUsuario().getNome();
				result.use(json()).from(message, "message").serialize();
				result.redirectTo(IndexController.class).index();
			} else {
				result.redirectTo(this).erro();
			}
		} catch (Exception e) {
			result.use(json()).from(e.getMessage(), "message").serialize();
		}
	}
	
	@Get
	@Path("/logout")
	public void logout(){
		adminSession.setUsuario(null);
		result.redirectTo(this).login();
	}
	
	@Get
	@Path("/login/erro")
	public void erro(){
		String message = "Erro ao efetuar login";
		result.use(json()).from(message, "message").serialize();
	}
}