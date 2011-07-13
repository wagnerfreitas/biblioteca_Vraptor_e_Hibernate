package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class EmprestimoDAOImpl implements EmprestimoDAO {
	private Session session;
	
	public EmprestimoDAOImpl(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}
	
	public void empresta(Emprestimo emprestimo){
		try {
			Transaction tx = session.beginTransaction();
			session.save(emprestimo);
			tx.commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao emprestar livro");
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Emprestimo> pesquisarEmprestimo(String nomeDoLivro, String ordenarPor) {
		try {
			Criteria criteria = session.createCriteria(Emprestimo.class);
			criteria.add(Restrictions.isNull("dataDeDevolucao"));
			if(ordenarPor.equals("nomeDoUsuario")){
				criteria.createCriteria("usuario")
				.addOrder(Order.asc("nome"));
			}else if(ordenarPor.equals("dataDeEmprestimo")){
				criteria.addOrder(Order.asc("dataDeEmprestimo"));
			}
			criteria.createCriteria("livro")
				.add(Restrictions.like("nome", "%" + nomeDoLivro + "%"));
			return criteria.list();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar empréstimo");
		}
	}
	
	public Emprestimo procuraPorId(Long id){
		try {
			Criteria criteria = session.createCriteria(Emprestimo.class);
			criteria.add(Restrictions.eq("id", id));
			return (Emprestimo) criteria.uniqueResult();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar empréstimo");
		}
	}
	
	public void atualiza(Emprestimo emprestimo){
		try {
			Transaction tx = session.beginTransaction();
			session.update(emprestimo);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException("Erro ao devolver livro");
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
		try {
			Criteria criteria = session.createCriteria(Emprestimo.class);
				criteria.add(Restrictions.isNull("dataDeDevolucao"));
				criteria.add(Restrictions.eq("livro.id", id));
			return (Emprestimo) criteria.uniqueResult();		
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar livro emprestado");
		}
	}
}