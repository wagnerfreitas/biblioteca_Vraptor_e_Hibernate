package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entitades.Emprestimo;

public interface EmprestimoDAO {
	
	public List<Emprestimo> pesquisarEmprestimo(String nomeDoLivro);
	
	public Emprestimo procuraPorId(Long id);

	public void atualiza(Emprestimo emprestimo);
	
	public void empresta(Emprestimo emprestimo);
	
	public List<Emprestimo> procuraPorIdUsuario(Long id);

	public Emprestimo procuraPorIdLivro(Long id);
}