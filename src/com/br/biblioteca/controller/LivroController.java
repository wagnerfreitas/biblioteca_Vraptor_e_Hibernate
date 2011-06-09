package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.LivroDAO;
import com.br.biblioteca.entitades.Livro;

@Resource
public class LivroController {
	
	private Result result;
	private LivroDAO livroDAO;
	
	public LivroController(Result result, LivroDAO livroDAO){
		this.result = result;
		this.livroDAO = livroDAO;
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
			result.forwardTo(LivroController.class).index("");
		}
	} 
}