package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Resource;

import com.br.biblioteca.dao.LivroDAO;
import com.br.biblioteca.entitades.Livro;

@Resource
public class LivroController {
	public void fomulario() {
		
	}
	public void adiciona(Livro livro){
		new LivroDAO().adiciona(livro);
	}
	public List<Livro> lista(String nomeDoLivro){
		return new LivroDAO().listaDeLivro(nomeDoLivro);
	}
}
