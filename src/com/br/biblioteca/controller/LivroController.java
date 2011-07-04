package com.br.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.AdminSession;
import com.br.biblioteca.dao.EmprestimoDAO;
import com.br.biblioteca.dao.LivroDAO;
import com.br.biblioteca.dao.UsuarioDAO;
import com.br.biblioteca.entitades.Emprestimo;
import com.br.biblioteca.entitades.Livro;
import com.br.biblioteca.entitades.Usuario;

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
		List<Livro> livros = livroDAO.pesquisa(nome);
		result.include("livros", livros);
		result.include("nome", nome);
		result.include("usuario", adminSession.getAdministrador().getNome());
	}
	
	@Get
	@Path("/livro/add")
	public void novo(){
	}
	
	@Post
	@Path("/livro/novo")
	public void novo(Livro livro) {
		String message;
		if(livroDAO.pesquisa(livro.getNome()).size() >= 1){
			message = "\"" + livro.getNome() + "\" já cadastrado";
		}
		else if(livro.getNome().equals("") || livro.getAutor().equals("")){
			message = "Nome do livro ou autor nulos";
		}else{
			livroDAO.adiciona(livro);
			message = "\"" + livro.getNome() + "\" adicionado com sucesso";
		}
		result.use(json()).from(message, "message").serialize();
	} 
	
	@Post
	@Path("livro/emprestar")
	public void emprestar(Long iDUsuario, Long idLivro, Date dataDeEmprestimo){
		Emprestimo emprestimo = new Emprestimo();
		String message;
		if(idLivro.equals("") || iDUsuario.equals("") || dataDeEmprestimo.equals("")){
			message = "Erro";
		}else{
			Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(iDUsuario);
			
			Livro livro = livroDAO.pesquisarLivroPorId(idLivro);
			livro.setEmprestado(true);
			livroDAO.atualiza(livro);
			
			emprestimo.setUsuario(usuario);
			emprestimo.setLivro(livro);
			emprestimo.setDataDeEmprestimo(dataDeEmprestimo);
			
			emprestimoDAO.empresta(emprestimo);
			message = "\"" + livro.getNome() + "\" emprestado com sucesso";
		}
		result.use(json()).from(message, "message").serialize();
	}
	
	@Put @Post
	@Path("/livro/atualiza")
	public void atualiza(Livro livro){
		String message;
		if(livro.equals(null)){
			message = "Erro";
		}
		livroDAO.atualiza(livro);
		message =  "\"" + livro.getNome() + "\" atualizado com sucesso";
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
				message = "Livro está emprestado";
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
			message = "Erro";
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