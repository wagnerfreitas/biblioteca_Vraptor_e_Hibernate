package com.br.biblioteca.controller;

import java.util.Calendar;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
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
	public void emprestar(Long IdUsuario, Long idLivro, Calendar dataDeEmprestimo){
		Emprestimo emprestimo = new Emprestimo();

		Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(IdUsuario);
		emprestimo.setUsuario(usuario);
		
		Livro livro = livroDAO.pesquisarLivroPorId(idLivro);
		livro.setEmprestado(true);
		livroDAO.atualiza(livro);
		
		emprestimo.setLivro(livro);
		emprestimo.setDataDeEmprestimo(dataDeEmprestimo);

		emprestimoDAO.empresta(emprestimo);
		result.forwardTo("../index.jsp");
	}
	
	
	//modifiquei aki
	@Post
	public void remove(Long id){
		Livro livro = livroDAO.pesquisarLivroPorId(id);
		Emprestimo emprestimo = emprestimoDAO.procuraPorIdLivro(id);
		if(livro.getId().equals(emprestimo.getLivro().getId())){
			throw new RuntimeException();
		}
		livro.setLivroDeletado(true);
		livroDAO.atualiza(livro);
	}
	//até aki
	
	
	@Post
	public void devolve(Long id, Calendar dataDeDevolucao){
		Emprestimo emprestimo = emprestimoDAO.procuraPorIdLivro(id);
		Livro livro = emprestimo.getLivro();
		livro.setEmprestado(false);
		emprestimo.setDataDeDevolucao(dataDeDevolucao);
		livroDAO.atualiza(livro);
		emprestimoDAO.atualiza(emprestimo);
		result.forwardTo("../index.jsp");
	}   
}