package br.com.biblioteca.dao.Impl;

import java.io.Serializable;

import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.entidades.Administrador;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@SuppressWarnings("serial")
@Component
@SessionScoped
public class AdminSessionImpl implements Serializable , AdminSession{
	
	private Administrador administrador;

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
}