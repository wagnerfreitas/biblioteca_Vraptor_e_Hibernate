package com.br.biblioteca.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
		try {
			Transaction tx = session.beginTransaction();
			livro.setLivroDeletado(false);
			livro.setEmprestado(false);
			session.save(livro);
			tx.commit();	
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
		finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	public void atualiza(Livro livro){
		try {
			Transaction tx = session.beginTransaction();
			session.update(livro);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Livro> listaDeLivro(String nomeDoLivro){
		Criteria criteria =  session.createCriteria(Livro.class);
		if (nomeDoLivro != null || nomeDoLivro == "") {
			criteria.add(Restrictions.like("nome", "%" + nomeDoLivro + "%"));
		}
			
		criteria.add(Restrictions.eq("livroDeletado", false));
		return criteria.list();
	}
	
	public Livro pesquisarLivroPorId(Long id) {
		return (Livro) this.session
			.createCriteria(Livro.class)
				.add(Restrictions.eq("id", id))
			.uniqueResult();
	}
}