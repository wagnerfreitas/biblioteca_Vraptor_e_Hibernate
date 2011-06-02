package com.br.biblioteca.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.br.biblioteca.entitades.Livro;

public class LivroDAO {
	private Session session;
	
	public LivroDAO(){
		this.session = new BibliotecaUtil().getSession();
	}
	public void adiciona(Livro livro){
		Transaction tx = session.beginTransaction();
		session.save(livro);
		tx.commit();
	}
	public void atualiza(Livro livro){
		Transaction tx = session.beginTransaction();
		session.update(livro);
		tx.commit();
	}
	public void remove(Livro livro){
		Transaction tx = session.beginTransaction();
		session.delete(livro);
		tx.commit();
	}
	@SuppressWarnings("unchecked")
	public List<Livro> listaDeLivro(String nomeDoLivro){
		return session.createCriteria(Livro.class)
			.add(Restrictions.like("nome", "%" + nomeDoLivro + "%"))
		.list();
	}
}
