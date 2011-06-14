package com.br.biblioteca.controller;

import java.util.Calendar;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

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
	
	public LivroController(Result result, LivroDAO livroDAO, EmprestimoDAO emprestimoDAO, UsuarioDAO usuarioDAO){
		this.result = result;
		this.livroDAO = livroDAO;
		this.emprestimoDAO = emprestimoDAO;
		this.usuarioDAO = usuarioDAO;
	}
	
	@Get
	@Path("/livros")
	public void index(String nome){
		List<Livro> livros = livroDAO.listaDeLivro(nome);
		result.include("livros", livros);
	}
	
	@Get
	@Path("/livro/novo")
	public void novo(){
	}
	
	@Post
	@Path("/livro/novo")
	public void novo(Livro livro) {
		if(livro.getNome().equals("") || livro.getAutor().equals("")){
			result.include("Erro" , "Nome ou email nulos");
			result.forwardTo("../index.jsp");
		}else{
			livroDAO.adiciona(livro);
			result.include("novo", livro);
			result.forwardTo("../index.jsp");
		}
	} 
	
	@Post
	@Path("livro/emprestar")
	public void emprestar(Long IdUsuario, Long idLivro, Calendar dataDeEmprestimo){
		Emprestimo emprestimo = new Emprestimo();

		Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(IdUsuario);
		usuario.setEmprestimoAtivo(true);
		usuarioDAO.atualiza(usuario);
		emprestimo.setUsuario(usuario);
		
		Livro livro = livroDAO.pesquisarLivroPorId(idLivro);
		livro.setEmprestado(true);
		livroDAO.atualiza(livro);
		
		emprestimo.setLivro(livro);
		emprestimo.setDataDeEmprestimo(dataDeEmprestimo);

		emprestimoDAO.empresta(emprestimo);
		result.forwardTo("/livros");
	}
	
	@Put @Post
	@Path("/livro/atualiza")
	public void atualiza(Livro livro){
		livroDAO.atualiza(livro);
		result.forwardTo("../index.jsp");
	}
	
	@Post
	@Path("livro/remove")
	public void remove(List<Long> IdRemove){
		Livro livro = null;
		for (Long id : IdRemove) {
			livro = livroDAO.pesquisarLivroPorId(id);
			livro.setLivroDeletado(true);
			livroDAO.atualiza(livro);
		}
		result.forwardTo("/index.jsp");
	}
	
	@Post
	@Path("livro/devolve")
	public void devolve(Long id, Calendar dataDeDevolucao){
		if(id.equals("") || dataDeDevolucao.equals("")){
			throw new RuntimeException();
		}else{
			Emprestimo emprestimo = emprestimoDAO.procuraPorIdLivro(id);
	
			Livro livro = emprestimo.getLivro();
			Usuario usuario = emprestimo.getUsuario();
			livro.setEmprestado(false);
			usuario.setEmprestimoAtivo(false);
			emprestimo.setDataDeDevolucao(dataDeDevolucao);
			
			usuarioDAO.atualiza(usuario);
			livroDAO.atualiza(livro);
			emprestimoDAO.atualiza(emprestimo);
			result.forwardTo("/index.jsp");
		}
	}   
}