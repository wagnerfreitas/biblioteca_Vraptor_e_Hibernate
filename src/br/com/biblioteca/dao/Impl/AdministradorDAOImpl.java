package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.AdministradorDAO;
import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.entidades.Administrador;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class AdministradorDAOImpl implements AdministradorDAO{

	private Session session;

	public AdministradorDAOImpl(BibliotecaUtil bibliotecaUtil) {
		this.session = bibliotecaUtil.getSession();
	}

	public void adiciona(Administrador administrador) {
		try {
			Transaction tx = session.beginTransaction();
			session.save(administrador);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Administrador> lista() {
		Criteria criteria = session.createCriteria(Administrador.class);
		return criteria.list();
	}

	public void delete(Administrador administrador) {
		try {
			Transaction tx = session.beginTransaction();
			session.delete(administrador);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Administrador login(String nome, String senha) {
		Criteria criteria = session.createCriteria(Administrador.class);
		criteria.add(Restrictions.eq("nome", nome));
		criteria.add(Restrictions.eq("senha", senha));
		return (Administrador) criteria.uniqueResult();
	}
}