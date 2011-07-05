package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.entitades.Emprestimo;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class EmprestimoDAOImpl implements EmprestimoDAO {
	private Session session;
	
	public EmprestimoDAOImpl(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}

	@SuppressWarnings("unchecked")
	public List<Emprestimo> pesquisarEmprestimo(String nomeDoLivro) {
		List<Emprestimo> emprestimos = this.session
				.createCriteria(Emprestimo.class)
					.add(Restrictions.isNull("dataDeDevolucao"))
					.addOrder(Order.asc("dataDeEmprestimo"))
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
	
	@SuppressWarnings("unchecked")
	public List<Emprestimo> procuraPorIdUsuario(Long id) {
		 List<Emprestimo> emprestimos =  this.session
			.createCriteria(Emprestimo.class)
				.add(Restrictions.isNull("dataDeDevolucao"))
				.add(Restrictions.eq("usuario.id", id))
			.list();
		 return emprestimos;
	}

	public Emprestimo procuraPorIdLivro(Long id) {
		return (Emprestimo) this.session
			.createCriteria(Emprestimo.class)
				.add(Restrictions.isNull("dataDeDevolucao"))
				.add(Restrictions.eq("livro.id", id))
			.uniqueResult();
	}
}