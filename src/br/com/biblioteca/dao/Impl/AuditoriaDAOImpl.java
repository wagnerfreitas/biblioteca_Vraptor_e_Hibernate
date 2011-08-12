package br.com.biblioteca.dao.Impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.biblioteca.dao.AuditoriaDAO;
import br.com.biblioteca.dao.BibliotecaUtilAud;
import br.com.biblioteca.entidades.Auditoria;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class AuditoriaDAOImpl implements AuditoriaDAO{
	
	private Session session;
	
	public AuditoriaDAOImpl(BibliotecaUtilAud bibliotecaUtil) {
		this.session = bibliotecaUtil.getSession();
	}

	public void salva(Auditoria auditoria) {
		try {
			Transaction tx = session.beginTransaction();
			session.save(auditoria);
			tx.commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Auditoria> list(Date dataInicio, Date dataFim) {
		try {
			if(dataInicio == null || dataFim == null) {
				throw new RuntimeException("Data nula");
			}
			Criteria criteria = session.createCriteria(Auditoria.class);
				criteria.add(Restrictions.between("data", dataInicio, dataFim));
			return criteria.list();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao pesquisar");
		}
	}
}