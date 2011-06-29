package com.br.biblioteca.dao;

import java.util.List;

import org.hibernate.Criteria;
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
	private Criteria criteria = session.createCriteria(Usuario.class);
	
	public UsuarioDAO(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}
	
	public void adiciona(Usuario usuario) {
		try {
			Transaction tx = session.beginTransaction();
			usuario.setUsuarioAtivo(true);
			usuario.setEmprestimoAtivo(false);
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
			session.update(usuario);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> pesquisa(String nome){
		if(nome != null ||  nome == ""){
			criteria.add(Restrictions.like("nome", "%" + nome + "%"));
			criteria.add(Restrictions.eq("usuarioAtivo", true));
			criteria.addOrder(Order.asc("nome"));
		}
		return criteria.list();
	}
	
	public Usuario pesquisarUsuarioPorId(Long id){
		criteria.add(Restrictions.eq("id", id));
		return (Usuario) criteria.uniqueResult();
	}
}