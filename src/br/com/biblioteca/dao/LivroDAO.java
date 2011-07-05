package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entitades.Livro;

public interface LivroDAO {
	
	public void adiciona(Livro livro);
	
	public void atualiza(Livro livro);
	
	public List<Livro> pesquisa(String nomeDoLivro);
	
	public Livro pesquisarLivroPorId(Long id);
}