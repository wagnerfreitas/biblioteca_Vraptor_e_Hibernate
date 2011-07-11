package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entitades.Emprestimo;
import br.com.biblioteca.entitades.Livro;
import br.com.biblioteca.entitades.Usuario;
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
	private AdminSession adminSession;
	
	public LivroController(Result result, LivroDAO livroDAO, EmprestimoDAO emprestimoDAO, UsuarioDAO usuarioDAO, AdminSession adminSession){
		this.result = result;
		this.livroDAO = livroDAO;
		this.emprestimoDAO = emprestimoDAO;
		this.usuarioDAO = usuarioDAO;
		this.adminSession = adminSession;
	}
	
	@Get
	@Path("/livros")
	public void index(String nome){
		try {
			List<Livro> livros = livroDAO.pesquisa(nome);
			result.include("livros", livros);
			result.include("nome", nome);
			result.include("usuario", adminSession.getAdministrador().getNome());
		} catch (Exception e) {
			result.include("error", e.getMessage());	
		}
	}
	
	@Get
	@Path("/livro/add")
	public void novo(){
	}
	
	@Post
	@Path("/livro/novo")
	public void novo(Livro livro) {
		String message;
		try {
			livroDAO.adiciona(livro);
			message = "\"" + livro.getNome() + "\" adicionado com sucesso";
		} catch (Exception e) {
			message = e.getMessage();
		}
//		Linha usada no teste
		result.include("message", message);
//		.
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
				livroDAO.atualiza(livro);
				
				emprestimo.setUsuario(usuario);
				emprestimo.setLivro(livro);
				emprestimo.setDataDeEmprestimo(dataDeEmprestimo);
				
				emprestimoDAO.empresta(emprestimo);
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
				message =  "\"" + livro.getNome() + "\" atualizado com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}		
		result.use(json()).from(message, "message").serialize();
	}
	
	@Post
	@Path("livro/remove")
	public void remove(List<Long> IdRemove){
		Livro livro = null;
		String message;
		for (Long id : IdRemove) {
			livro = livroDAO.pesquisarLivroPorId(id);
			if(livro.isEmprestado()){
				message = "\"" + livro.getNome() + "\" está emprestado";
			}else{
				livro.setLivroDeletado(true);
				livroDAO.atualiza(livro);
				message = "Livro(s) deletado(s) com sucesso";
			}
			result.use(json()).from(message, "message").serialize();
		}
	}
	
	@Post
	@Path("livro/devolve")
	public void devolve(Long id, Date dataDeDevolucao){
		String message;
		if(id.equals("") || dataDeDevolucao.equals("")) {
			message = "Erro ao devolver livro";
		} else {
			Emprestimo emprestimo = emprestimoDAO.procuraPorIdLivro(id);
			emprestimo.setDataDeDevolucao(dataDeDevolucao);
			
			Livro livro = emprestimo.getLivro();
			livro.setEmprestado(false);

			emprestimoDAO.atualiza(emprestimo);
			livroDAO.atualiza(livro);

			message = "\"" + livro.getNome() + "\" devolvido com sucesso";
		}
		result.use(json()).from(message, "message").serialize();
	}   
}