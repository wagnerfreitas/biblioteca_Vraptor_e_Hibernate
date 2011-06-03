package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;

import com.br.biblioteca.entitades.Livro;

@Resource
public class LivroController {
	
	@Post
	@Path("/livro/add")
	public void adiciona(Livro livro){
//		new LivroDAO().adiciona(livro);
	}
	
	@Get
	@Path("/livro")
	public List<Livro> index(String nomeDoLivro){
		return null;
//		return new LivroDAO().listaDeLivro(nomeDoLivro);
	}
}
