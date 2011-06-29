package com.br.biblioteca.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.br.biblioteca.entitades.Emprestimo;

@Component
@RequestScoped
public class EmprestimoDAO {
	private Session session;
	private Criteria criteria = session.createCriteria(Emprestimo.class);
	
	public EmprestimoDAO(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}

	@SuppressWarnings("unchecked")
	public List<Emprestimo> pesquisarEmprestimo(String nomeDoLivro) {
		List<Emprestimo> emprestimos = this.session
				.createCriteria(Emprestimo.class)
					.add(Restrictions.isNull("dataDeDevolucao"))
				.createCriteria("livro")
					.add(Restrictions.like("nome", "%" + nomeDoLivro + "%"))
				.list();
		return emprestimos;
	}
	
	public Emprestimo procuraPorId(Long id){
		criteria.add(Restrictions.eq("id", id));
		return (Emprestimo) criteria.uniqueResult();
	}
	
	public Emprestimo procuraPorIdLivro(Long id) {
		criteria.add(Restrictions.isNull("dataDeDevolucao"));
		criteria.add(Restrictions.eq("livro.id", id));
		return (Emprestimo) criteria.uniqueResult();
	}
	
	public void atualiza(Emprestimo emprestimo){
		try {
			Transaction tx = session.beginTransaction();
			session.update(emprestimo);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException();
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	public void empresta(Emprestimo emprestimo){
		try {
			Transaction tx = session.beginTransaction();
			session.save(emprestimo);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException();
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Emprestimo> procuraPorIdUsuario(Long id){
//		criteria.add(Restrictions.isNull("dataDeDevolucao"));
//		criteria.add(Restrictions.eq("usuario.id", id));
//		return criteria.list();
//		
////		List<Emprestimo> emprestimos = this.session
////			.createCriteria(Emprestimo.class)
////				.add(Restrictions.isNull("dataDeDevolucao"))
////				.add(Restrictions.eq("usuario.id", id))
////			.list();
////		return emprestimos;
//	}
}