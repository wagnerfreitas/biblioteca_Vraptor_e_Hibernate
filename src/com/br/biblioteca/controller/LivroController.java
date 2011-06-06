package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
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
	public void index(){
		List<Livro> livros = livroDAO.listaDeLivro();
		result.include("livros", livros);
	}
	
	@Get
	@Path("/livros/busca")
	public void busca(String nome){
		List<Livro> livros = livroDAO.listaDeLivro(nome);
		result.include("livros", livros);
	}
	@Get
	@Path("/livros")
	public void index(String nomeDoLivro){
		List<Livro> livros = livroDAO.listaDeLivro(nomeDoLivro);
		result.include("livros", livros);
	}
	@Get
	@Path("/livro/novo")
	public void novo(Livro livro){
		livroDAO.adiciona(livro);
	}
}