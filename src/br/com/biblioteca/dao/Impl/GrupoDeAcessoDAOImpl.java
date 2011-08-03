package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.entidades.GrupoDeAcesso;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class GrupoDeAcessoDAOImpl {
	private Session session;
	
	public GrupoDeAcessoDAOImpl(BibliotecaUtil bibliotecaUtil) {
		this.session = bibliotecaUtil.getSession();
	}
	
	public void novo(GrupoDeAcesso grupoDeAcesso) {
		try {
			Transaction tx = session.beginTransaction();
			session.save(grupoDeAcesso);
			tx.commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<GrupoDeAcesso> grupoDeAcessos(String acoes) {
		Criteria criteria = session.createCriteria(GrupoDeAcesso.class);
			criteria.add(Restrictions.eq("acoes", acoes));
		return criteria.list();
	}
}