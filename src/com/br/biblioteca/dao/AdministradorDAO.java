package com.br.biblioteca.dao;

import java.util.List;

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
	
	public Administrador login(String nome, String senha){
		Administrador administrador = (Administrador) this.session.createCriteria(Administrador.class)
				.add(Restrictions.eq("nome", nome))
				.add(Restrictions.eq("senha", senha))
				.uniqueResult();
		if (administrador != null) {
			return administrador;
		} else {
			return null;
		}
	}
}