package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.dao.GrupoDePerfilDAO;
import br.com.biblioteca.entidades.GrupoDePerfil;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class GrupoDePerfilDAOImpl implements GrupoDePerfilDAO {

	private Session session;
	
	public GrupoDePerfilDAOImpl(BibliotecaUtil bibliotecaUtil) {
		this.session = bibliotecaUtil.getSession();
	}
	
	public void novo(GrupoDePerfil grupoDePerfil) {
		try {
			Transaction tx = session.beginTransaction();
			session.save(grupoDePerfil);
			tx.commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao cadastrar grupo");
		}
	}

	@SuppressWarnings("unchecked")
	public List<GrupoDePerfil> grupos() {
		Criteria criteria = session.createCriteria(GrupoDePerfil.class);
		return criteria.list();	
	}
}