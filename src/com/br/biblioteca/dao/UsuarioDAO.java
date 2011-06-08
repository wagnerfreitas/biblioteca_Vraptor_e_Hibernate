package com.br.biblioteca.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.br.biblioteca.entitades.Usuario;

@Component
@RequestScoped
public class UsuarioDAO {
	private Session session;
	
	public UsuarioDAO(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}
	
	public void adiciona(Usuario usuario) {
		try {
			Transaction tx = session.beginTransaction();
			usuario.setUsuarioAtivo(true);
			session.save(usuario);
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
	public void atualiza(Usuario usuario){
		try {
			Transaction tx = session.beginTransaction();
			usuario.setUsuarioAtivo(false);
			session.update(usuario);
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
	
	@SuppressWarnings("unchecked")
	public List<Usuario> pesquisa(String nome){
		List<Usuario> usuarios = this.session
			.createCriteria(Usuario.class)
				.add(Restrictions.like("nome", "%" + nome + "%"))
				.add(Restrictions.eq("usuarioAtivo", true))
				.addOrder(Order.asc("nome"))
			.list();
		return usuarios; 
	}
	
	public Usuario pesquisarUsuarioPorId(Long id){
		return (Usuario) this.session
			.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
			.uniqueResult();
	}
}