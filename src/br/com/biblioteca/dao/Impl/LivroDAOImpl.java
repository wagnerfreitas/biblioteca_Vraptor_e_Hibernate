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
import br.com.biblioteca.entidades.Livro;
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
		if(livro.getNome() == null || livro.getNome() == ""){
			throw new RuntimeException("Nome do livro nulo");
		} else if (livro.getAutor() == null || livro.getAutor() == ""){
			throw new RuntimeException("Nome do autor nulo");
		} else if( livro.getEdicao() == null || livro.getEdicao() == ""){
			throw new RuntimeException("Edição nula");
		} else if (pesquisa(livro.getNome()).size() > 0){
			throw new RuntimeException("\"" + livro.getNome() + "\" já cadastrado");
		}
		try {
			Transaction tx = session.beginTransaction();
			livro.setLivroDeletado(false);
			livro.setEmprestado(false);
			session.save(livro);
			tx.commit();	
		} catch (HibernateException e) {
			throw new RuntimeException("Erro ao tentar adicionar livro");
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
			throw new RuntimeException("Erro/Livro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Livro> pesquisa(String nomeDoLivro){
		try {
			Criteria criteria =  session.createCriteria(Livro.class);
			if (nomeDoLivro != null || nomeDoLivro == "") {
				criteria.add(Restrictions.like("nome", "%" + nomeDoLivro + "%"));
				criteria.add(Restrictions.eq("livroDeletado", false));
				criteria.addOrder(Order.asc("nome"));
			}
			return criteria.list();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar livro");
		}
	}
	
	public Livro pesquisarLivroPorId(Long id) {
		try {
			Criteria criteria = session.createCriteria(Livro.class);
				criteria.add(Restrictions.eq("id", id));
			return (Livro) criteria.uniqueResult();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar");
		}
	}
}