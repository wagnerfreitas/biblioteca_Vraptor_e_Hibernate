package com.br.biblioteca.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.br.biblioteca.entitades.Administrador;

@Component
@RequestScoped
public class AdministradorDAO {
	
	private Session session;
	
	public AdministradorDAO(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}
	public void adiciona(Administrador administrador){
		try {
			Transaction tx = session.beginTransaction();
			session.save(administrador);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException();
		}
		finally{
			if(session != null){
				session.close();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public List<Administrador> lista(){
		return session.createCriteria(Administrador.class).list();		
	}
	
	public void delete(Administrador administrador){
		try {
			Transaction tx = session.beginTransaction();
			session.delete(administrador);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException();
		} finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	public Administrador login(String nome, String senha){
		Criteria criteria = session.createCriteria(Administrador.class);
			criteria.add(Restrictions.eq("nome", nome));
			criteria.add(Restrictions.eq("senha", senha));
		return (Administrador) criteria.uniqueResult();
	}
}