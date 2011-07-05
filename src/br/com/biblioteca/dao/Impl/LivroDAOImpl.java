package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.entitades.Livro;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class LivroDAOImpl implements LivroDAO {
	private Session session;
	
	public LivroDAOImpl(BibliotecaUtil bibliotecaUtil){
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
	public List<Livro> pesquisa(String nomeDoLivro){
		Criteria criteria =  session.createCriteria(Livro.class);
		if (nomeDoLivro != null || nomeDoLivro == "") {
			criteria.add(Restrictions.like("nome", "%" + nomeDoLivro + "%"));
			criteria.add(Restrictions.eq("livroDeletado", false));
			criteria.addOrder(Order.asc("nome"));
		}
		return criteria.list();
	}
	
	public Livro pesquisarLivroPorId(Long id) {
		return (Livro) this.session
			.createCriteria(Livro.class)
				.add(Restrictions.eq("id", id))
			.uniqueResult();
	}
}