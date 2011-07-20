package br.com.biblioteca.entidades;

import org.hibernate.envers.RevisionListener;

import br.com.biblioteca.dao.AdminSession;

public class MyRevisionListener implements RevisionListener{

	private AdminSession adminSession;
	
	public MyRevisionListener(AdminSession adminSession) {
		this.adminSession = adminSession;
	}
	
	public void newRevision(final Object revisionEntity) {
		((MyRevisionEntity) revisionEntity).setUser(adminSession.getAdministrador().getNome());
	}
}