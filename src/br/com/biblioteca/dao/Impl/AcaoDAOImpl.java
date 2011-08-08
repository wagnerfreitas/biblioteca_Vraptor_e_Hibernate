package br.com.biblioteca.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.entidades.Acao;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class AcaoDAOImpl implements AcaoDAO{

	private Session session; 
	
	public AcaoDAOImpl(BibliotecaUtil bibliotecaUtil) {
		this.session = bibliotecaUtil.getSession();
	}
	
	public void novo(Acao acao) {
		try {
			Transaction tx = session.beginTransaction();
			session.save(acao);
			tx.commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao adicionar ação");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Acao> acoes() {
		try {
			Criteria criteria = session.createCriteria(Acao.class);
			return criteria.list();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao trazer lista de ações");
		}
	}
	
	public Acao pesquisaAcoesPorId(Long id) {
		try {
			Criteria criteria = session.createCriteria(Acao.class);
				criteria.add(Restrictions.eq("id", id));
			return (Acao) criteria.uniqueResult();
		} catch (Exception e) {
			throw new RuntimeException("Erro");
		}
	}
}