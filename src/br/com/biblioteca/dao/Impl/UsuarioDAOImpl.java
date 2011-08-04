package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Usuario;
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
		} else if (usuario.getEmail() == null || usuario.getEmail() == "") {
			throw new RuntimeException("Email nulo");			
		} else if (pesquisaNaAdicaoDeUsuario(usuario.getNome()).size() > 0){
			throw new RuntimeException("\"" + usuario.getNome() + "\" já está cadastrado");			
		}
		try {
			Transaction tx = session.beginTransaction();
			usuario.setAtivo(true);
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
			throw new RuntimeException("Erro/Usuário");
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
			return criteria.list();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar usuário");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> pesquisaNaAdicaoDeUsuario(String nome){
		Criteria criteria = session.createCriteria(Usuario.class);
		try {
			if(nome != null ||  nome == ""){
				criteria.add(Restrictions.eq("nome", nome));
				criteria.add(Restrictions.eq("usuarioAtivo", true));
			}
			return criteria.list();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar usuário");
		}
	}
	
	public Usuario pesquisarUsuarioPorId(Long id){
		try {
			Criteria criteria = session.createCriteria(Usuario.class);
				criteria.add(Restrictions.eq("id", id));
			return (Usuario) criteria.uniqueResult();
		} catch (Exception e) {
			throw new RuntimeException("Erro na pesquisa");
		}
	}
	
	public Usuario login(String nome, String senha) {
		try {
			Criteria criteria = session.createCriteria(Usuario.class);
				criteria.add(Restrictions.eq("nome", nome));
				criteria.add(Restrictions.eq("senha", senha));
			return (Usuario) criteria.uniqueResult();
		} catch(NonUniqueResultException e) {
			throw new RuntimeException("Usuário duplicado no sistema");
		} catch (Exception e) {
			throw new RuntimeException("Não foi possível acessar o sistema");
		}
	}
}