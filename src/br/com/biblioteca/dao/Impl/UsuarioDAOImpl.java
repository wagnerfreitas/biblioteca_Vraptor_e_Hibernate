package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entitades.Usuario;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class UsuarioDAOImpl implements UsuarioDAO{
	private Session session;
	
	public UsuarioDAOImpl(BibliotecaUtil bibliotecaUtil){
		this.session = bibliotecaUtil.getSession();
	}
	
	public void adiciona(Usuario usuario) {
		if(usuario.getNome() == null || usuario.getNome() == "") {
			throw new RuntimeException("Nome nulo");
		}
		else if(usuario.getEmail() == null || usuario.getEmail().equals("")) {
			throw new RuntimeException("Email nulo");			
		}else if(pesquisa(usuario.getNome()).size() >= 1){
			throw new RuntimeException("\"" + usuario.getNome() + "\" já está cadastrado");			
		}
		try {
			Transaction tx = session.beginTransaction();
			usuario.setUsuarioAtivo(true);
			session.save(usuario);
			tx.commit();
		} catch (HibernateException e) {
			throw new RuntimeException("Erro ao tentar adicionar usuário");
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
		Criteria criteria = session.createCriteria(Usuario.class);
		try {
			if(nome != null ||  nome == ""){
				criteria.add(Restrictions.like("nome", "%" + nome + "%"));
				criteria.add(Restrictions.eq("usuarioAtivo", true));
				criteria.addOrder(Order.asc("nome"));
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar usuário");
		}
		return criteria.list();
	}
	
	public Usuario pesquisarUsuarioPorId(Long id){
		return (Usuario) this.session
			.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
			.uniqueResult();
	}
}