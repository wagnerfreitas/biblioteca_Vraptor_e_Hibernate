package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entidades.Emprestimo;

public interface EmprestimoDAO {
	
	public List<Emprestimo> pesquisarEmprestimo(String nomeDoLivro, String ordenarPor);
	
	public Emprestimo procuraPorId(Long id);

	public void atualiza(Emprestimo emprestimo);
	
	public void empresta(Emprestimo emprestimo);
	
	public List<Emprestimo> procuraPorIdUsuario(Long id);

	public Emprestimo procuraPorIdLivro(Long id);
}