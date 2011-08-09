package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.biblioteca.controller.helper.AuditoriaHelper;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.GrupoDePerfilDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.GrupoDePerfil;
import br.com.biblioteca.entidades.Permissao;
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
	private UsuarioSession usuarioSession;
	private AuditoriaHelper auditoriaHelper;
	private GrupoDePerfilDAO grupoDePerfilDAO;
	
	public UsuarioController(Result result, UsuarioDAO usuarioDAO, UsuarioSession usuarioSession, 
			EmprestimoDAO emprestimoDAO, AuditoriaHelper auditoriaHelper, GrupoDePerfilDAO grupoDePerfilDAO) {
		this.result = result;
		this.usuarioDAO = usuarioDAO;
		this.emprestimoDAO = emprestimoDAO;
		this.usuarioSession = usuarioSession;
		this.auditoriaHelper = auditoriaHelper;
		this.grupoDePerfilDAO = grupoDePerfilDAO;
	}
	
	@Get
	@Path("/usuarios")
	public void index(String nome){
		try {
			List<Usuario> usuarios = usuarioDAO.pesquisa(nome);
			result.include("usuarios", usuarios)
				.include("nome", nome)
				.include("usuario", usuarioSession.getUsuario().getNome());
					
		} catch (Exception e) {
			result.include("error", e.getMessage());
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
			result.use(json()).from(e.getMessage()).serialize();
		}
	}
	
	@Get
	@Path("/usuario/add")
	@Permissao({"PERM_ADMIN", "PERM_ADD_USUARIO"})
	public void novo(){
		try {
			List<GrupoDePerfil> grupos = grupoDePerfilDAO.grupos();
			result.include("grupos", grupos);
		} catch (Exception e) {
			result.forwardTo(this).novo();
		}
	}
	
	@Post
	@Path("/usuario/novo")
	@Permissao({"PERM_ADMIN", "PERM_ADD_USUARIO"})
	public void novo(Long idGrupo, Usuario usuario){
		String message;
		try {
			GrupoDePerfil grupoDePerfil = grupoDePerfilDAO.pesquisaPorId(idGrupo);
			usuario.setGrupoDePerfil(grupoDePerfil);
			
			usuarioDAO.adiciona(usuario);
			auditoriaHelper.auditoria(usuario.getNome(), "ADICIONOU", new Date());
			
			message = "\""+ usuario.getNome() + "\" adicionado com sucesso!";
		} catch (Exception e) {
			message = e.getMessage();
		}
		result.include("message", message)
			.use(json()).from(message, "message").serialize();
	}
	
	@Put @Post
	@Path("/usuario/atualiza")
	@Permissao({"PERM_ADMIN", "PERM_ATUALIZAR_USUARIO"})
	public void atualiza(Long id, String nome, String email){
		String message;
		if(id == null) {
			message = "Id do usuário nulo";
		} else if(nome == null  ||  nome == "") {
			message = "Nome do usuário nulo";
		} else if(email == null || email == "") {
			message = "Email do usuário nulo";
		} else {
			try {
				Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(id);
				usuario.setNome(nome);
				usuario.setEmail(email);

				usuarioDAO.atualiza(usuario);
				auditoriaHelper.auditoria(usuario.getNome(), "ATUALIZOU", new Date());
				
				message = "\"" + usuario.getNome() + "\" atualizado com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}
		result.include("message", message)
			.use(json()).from(message, "message").serialize();
	}
	
	@Post
	@Path("/usuario/delete")
	@Permissao({"PERM_ADMIN", "PERM_DELETAR_USUARIO"})
	public void delete(List<Long> idDelete){
		List<String> messages = new ArrayList<String>();
		String message = null;
		try {
			for (Long id : idDelete) {
				Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(id);
				if(emprestimoDAO.procuraPorIdUsuario(id).size() > 0) {
					message = "\"" + usuario.getNome() + "\" com empréstimo ativo\n";
				} else {
					usuario.setAtivo(false);
					usuarioDAO.atualiza(usuario);
					auditoriaHelper.auditoria(usuario.getNome(), "DELETOU", new Date());
					
					message = "Usuario(s) deletado(s) com sucesso\n";
				}
				messages.add(message);
			}
		} catch (Exception e) {
			message = "Erro";
		}		
		result.include("message", message)
			.use(json()).from(messages, "message").serialize();
	}
}