package com.br.biblioteca.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.br.biblioteca.entitades.Livro;

@Component
@RequestScoped
public class LivroDAO {
	private Session session;
	
	public LivroDAO(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}
	
	public void adiciona(Livro livro){
		Transaction tx = session.beginTransaction();
		livro.setEmprestado(false);
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
	
	@SuppressWarnings("unchecked")
	public List<Livro> listaDeLivro(){
		return session.createCriteria(Livro.class).list();
	}
}