package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.List;

import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.AdministradorDAO;
import br.com.biblioteca.entidades.Administrador;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class AdministradorController {
	
	private Result result;
	private AdministradorDAO administradorDAO;
	private AdminSession adminSession;
	
	public AdministradorController(Result result, AdministradorDAO administradorDAO, AdminSession adminSession) {
		this.result = result;
		this.administradorDAO = administradorDAO;
		this.adminSession = adminSession;
	}
	
	@Get
	@Path("/administradores")
	public void lista() {
		try {
			List<Administrador> administradores = administradorDAO.lista();
			result.include("administradores", administradores);
			result.include("usuario", adminSession.getAdministrador().getNome());
		} catch (Exception e) {
			result.include("error", e.getMessage());
		}
	}
	
	@Get
	@Path("/amdmin/add")
	public void add(){
	}
	
	@Post
	@Path("/adimin/novo")
	public void novo(Administrador administrador){
		String message;
		if(administrador.getNome() == null || administrador.getNome() == "") {
			message = "Nome nulo";
		} else if(administrador.getSenha() == null || administrador.getSenha() == "") {
			message = "Senha nula";
		}else{
			try {
				administradorDAO.adiciona(administrador);
				message = "\"" + administrador.getNome() + "\" adicionado com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}
		result.include("message", message);
		result.use(json()).from(message, "message").serialize();
	}
	
	@Post
	@Path("/admin/deletar")
	public void deletar(Administrador administrador){
		String message;
		if(administrador.getId() == null){
			message = "Erro ao tentar apagar";
		}else{
			try {
				administradorDAO.delete(administrador);
				message = "\"" + administrador.getNome() + "\" apagado com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}
		result.include("message", message);
		result.use(json()).from(message, "message").serialize();
	}
}