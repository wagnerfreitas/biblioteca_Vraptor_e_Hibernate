package com.br.biblioteca.dao;

import java.util.List;

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
		return (Emprestimo) this.session
			.createCriteria(Emprestimo.class)
				.add(Restrictions.eq("id", id))
			.uniqueResult();
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
	
	@SuppressWarnings("unchecked")
	public List<Emprestimo> procuraPorIdUsuario(Long id){
		List<Emprestimo> emprestimos = this.session
			.createCriteria(Emprestimo.class)
				.add(Restrictions.isNull("dataDeDevolucao"))
				.add(Restrictions.eq("usuario.id", id))
			.list();
		return emprestimos;
	}
}