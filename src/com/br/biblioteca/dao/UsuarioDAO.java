package com.br.biblioteca.dao;

import java.util.List;

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
		Transaction tx = session.beginTransaction();
		usuario.setUsuarioAtivo(true);
		session.save(usuario);
		tx.commit();
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
}