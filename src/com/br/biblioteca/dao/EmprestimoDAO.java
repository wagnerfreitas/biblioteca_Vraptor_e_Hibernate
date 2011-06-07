package com.br.biblioteca.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.br.biblioteca.entitades.Emprestimo;

public class EmprestimoDAO {
	private Session session;
	
	public EmprestimoDAO(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}

	@SuppressWarnings("unchecked")
	public List<Emprestimo> pesquisarEmprestimo(String nomeDoLivro) {
		List<Emprestimo> emprestimos = this.session.createCriteria(
				Emprestimo.class).add(Restrictions.isNull("dataDeDevolucao"))
				.createCriteria("livro")
					.add(Restrictions.like("nome", "%" + nomeDoLivro + "%"))
				.list();
		return emprestimos;
	}
}
