package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.List;

import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class UsuarioController {
	
	private Result result;
	private UsuarioDAO usuarioDAO;
	private EmprestimoDAO emprestimoDAO;
	private AdminSession adminSession;
	
	public UsuarioController(Result result, UsuarioDAO usuarioDAO, AdminSession adminSession, EmprestimoDAO emprestimoDAO){
		this.result = result;
		this.usuarioDAO = usuarioDAO;
		this.adminSession = adminSession;
		this.emprestimoDAO = emprestimoDAO;
	}
	
	@Get
	@Path("/usuarios")
	public void index(String nome){
		try {
			List<Usuario> usuarios = usuarioDAO.pesquisa(nome);
			result.include("usuarios", usuarios);
			result.include("nome", nome);
			result.include("usuario", adminSession.getAdministrador().getNome());
		} catch (Exception e) {
			String message = e.getMessage();
			result.include("error", message);
		}
	}
	
	@Get
	@Path("/usuarios/list/{nome}")
	public void list(String nome){
		try {
			List<Usuario> usuarios = usuarioDAO.pesquisa(nome);
			result.include("usuarios", usuarios);
			result.use(json()).from(usuarios).serialize();
		} catch (Exception e) {
			result.include("error", e.getMessage());
		}
	}
	
	@Get
	@Path("/usuario/add")
	public void novo(){
	}
	
	@Post
	@Path("/usuario/novo")
	public void novo(Usuario usuario){
		String message;
		try {
			usuarioDAO.adiciona(usuario);
			message = "\""+ usuario.getNome() + "\" adicionado com sucesso!";
		} catch (Exception e) {
			message = e.getMessage();
		}
//		Linha usada no teste
		result.include("message", message);
//		.
		result.use(json()).from(message, "message").serialize();
	}
	
	@Put @Post
	@Path("/usuario/atualiza")
	public void atualiza(Usuario usuario){
		String message;
		
		if(usuario.getId() == null){
			message = "Id do usuário nulo";
		} else if(usuario.getNome() == null  ||  usuario.getNome() == "") {
			message = "Nome do usuário nulo";
		} else if(usuario.getEmail() == null || usuario.getEmail() == ""){
			message = "Email do usuário nulo";
		}else{
			try {
				usuario.setUsuarioAtivo(true);
				usuarioDAO.atualiza(usuario);
				message = "\"" + usuario.getNome() + "\" atualizado com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}
//		Linha usada no teste
		result.include("message", message);
//		.
		result.use(json()).from(message, "message").serialize();
	}
	
	@Post
	@Path("/usuario/delete")
	public void delete(List<Long> idDelete){
		String message;
		for (Long id : idDelete) {
			Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(id);
			if(emprestimoDAO.procuraPorIdUsuario(id).size() > 0){
				message = "\"" + usuario.getNome() + "\" com empréstimo ativo";
			}else{
				usuario.setUsuarioAtivo(false);
				usuarioDAO.atualiza(usuario);
				message = "Usuario(s) deletado(s) com sucesso";
			}
//			Linha usada no teste
			result.include("message", message);
//			.
			result.use(json()).from(message, "message").serialize();
		}
	}
}