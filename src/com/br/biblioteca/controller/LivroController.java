package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

import com.br.biblioteca.entitades.Livro;

@Resource
public class LivroController {
	@Get
	@Path("/livro")
	public List<Livro> index(String nomeDoLivro){
		return null;
//		return new LivroDAO().listaDeLivro(nomeDoLivro);
	}
	
	@Get
	@Path("/livro/novo")
	public void novo(Livro livro){
//		new LivroDAO().adiciona(livro);
	}
	
}
