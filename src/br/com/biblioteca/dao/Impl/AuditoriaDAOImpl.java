package br.com.biblioteca.dao.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.biblioteca.dao.AuditoriaDAO;
import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.entidades.Auditoria;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class AuditoriaDAOImpl implements AuditoriaDAO{
	
	private Session session;
	
	public AuditoriaDAOImpl(BibliotecaUtil bibliotecaUtil) {
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
}