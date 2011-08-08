package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.biblioteca.controller.helper.AuditoriaHelper;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class LivroController {
	
	private Result result;
	private LivroDAO livroDAO;
	private EmprestimoDAO emprestimoDAO;
	private UsuarioDAO usuarioDAO;
	private UsuarioSession usuarioSession;
	private AuditoriaHelper auditoriaHelper;
	
	public LivroController(Result result, LivroDAO livroDAO, EmprestimoDAO emprestimoDAO, 
			UsuarioDAO usuarioDAO, UsuarioSession usuarioSession, AuditoriaHelper auditoriaHelper) {
		this.result = result;
		this.livroDAO = livroDAO;
		this.emprestimoDAO = emprestimoDAO;
		this.usuarioDAO = usuarioDAO;
		this.usuarioSession = usuarioSession;
		this.auditoriaHelper = auditoriaHelper;
	}
	
	@Get
	@Path("/livros")
	public void index(String nome){
		try {
			List<Livro> livros = livroDAO.pesquisa(nome);
			result.include("livros", livros)
				.include("nome", nome)
				.include("usuario", usuarioSession.getUsuario().getNome());
		} catch (Exception e) {
			result.include("error", e.getMessage());	
		}
	}
	
	@Get
	@Path("/livro/add")
//	@Permissao({TipoDePerfil.MODERADOR, TipoDePerfil.ADMINISTRADOR})
	public void novo(){
	}
	
	@Post
	@Path("/livro/novo")
//	@Permissao({TipoDePerfil.MODERADOR, TipoDePerfil.ADMINISTRADOR})
//	@Permissao({"PERM_LIVRO_NOVO", "PERM_ADMIN"})
	public void novo(Livro livro) {
		String message;
		try {
			livroDAO.adiciona(livro);
			auditoriaHelper.auditoria(livro.getNome(), "ADICIONOU", new Date());
			
			message = "\"" + livro.getNome() + "\" adicionado com sucesso";
		} catch (Exception e) {
			message = e.getMessage();
		}
		result.include("message", message);
		result.use(json()).from(message, "message").serialize();
	} 
	
	@Post
	@Path("livro/emprestar")
	public void emprestar(Long iDUsuario, Long idLivro, Date dataDeEmprestimo){
		String message;
		
		if(idLivro == null || iDUsuario == null) {
			message = "Erro ao tentar realizar empréstimo";
		} else if(dataDeEmprestimo == null || dataDeEmprestimo.equals("")) {
			message = "Data nula";
		} else {
			try {
				Emprestimo emprestimo = new Emprestimo();
				Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(iDUsuario);
				Livro livro = livroDAO.pesquisarLivroPorId(idLivro);
				livro.setEmprestado(true);
				
				emprestimo.setUsuario(usuario);
				emprestimo.setLivro(livro);
				emprestimo.setDataDeEmprestimo(dataDeEmprestimo);
				
				livroDAO.atualiza(livro);
				emprestimoDAO.empresta(emprestimo);
				auditoriaHelper.auditoria(livro.getNome() + " - " + usuario.getNome(), "EMPRESTOU", dataDeEmprestimo);
				
				message = "\"" + livro.getNome() + "\" emprestado com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}
		result.include("message", message);
		result.use(json()).from(message, "message").serialize();
	}
	
	@Put @Post
	@Path("/livro/atualiza")
//	@Permissao({TipoDePerfil.MODERADOR, TipoDePerfil.ADMINISTRADOR})
	public void atualiza(Livro livro){
		String message;
		
		if(livro.getNome() == null || livro.getNome() == "") {
			message = "Nome do livro nulo";
		} else if(livro.getAutor() == null || livro.getAutor() == "") {
			message = "Autor nulo";
		} else if(livro.getEdicao() == null || livro.getEdicao() == "") {
			message = "Edição nula";
		} else {
			try {
				livroDAO.atualiza(livro);
				auditoriaHelper.auditoria(livro.getNome(), "ATUALIZOU", new Date());
				
				message =  "\"" + livro.getNome() + "\" atualizado com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}		
		result.include("message", message);
		result.use(json()).from(message, "message").serialize();
	}
	
	@Post
	@Path("livro/remove")
//	@Permissao({TipoDePerfil.MODERADOR, TipoDePerfil.ADMINISTRADOR})
	public void remove(List<Long> IdRemove){
		Livro livro;
		String message = null;
		try {
			for (Long id : IdRemove) {
				livro = livroDAO.pesquisarLivroPorId(id);
				if(livro.isEmprestado()) {
					message = "\"" + livro.getNome() + "\" está emprestado";
				} else {
					livro.setLivroDeletado(true);
					livroDAO.atualiza(livro);
					auditoriaHelper.auditoria(livro.getNome(), "DELETOU", new Date());
					
					message = "Livro(s) deletado(s) com sucesso";
				}
			}
		} catch (Exception e) {
			message = e.getMessage();
		}
		result.include("message", message);
		result.use(json()).from(message, "message").serialize();
	}
	
	@Post
	@Path("livro/devolve")
	public void devolve(Long id, Date dataDeDevolucao){
		String message;
		if(id == null){
			message = "Id no livro nulo";
		} else if (dataDeDevolucao == null) {
			message = "Date de devolução nula";
		} else {
			try {
				Emprestimo emprestimo = emprestimoDAO.procuraPorIdLivro(id);
				Livro livro = emprestimo.getLivro();
				Usuario usuario = emprestimo.getUsuario();
				
				emprestimo.setDataDeDevolucao(dataDeDevolucao);
				livro.setEmprestado(false);
				
				emprestimoDAO.atualiza(emprestimo);
				livroDAO.atualiza(livro);
				auditoriaHelper.auditoria(usuario.getNome() + " - " + livro.getNome(), "DEVOLVEU", dataDeDevolucao);
				
				message = "\"" + livro.getNome() + "\" devolvido(a) com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}
		result.include("message", message);
		result.use(json()).from(message, "message").serialize();
	}   
}