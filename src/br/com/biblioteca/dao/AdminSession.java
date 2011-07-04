package br.com.biblioteca.dao;

import java.io.Serializable;

import br.com.biblioteca.entitades.Administrador;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@SuppressWarnings("serial")
@Component
@SessionScoped
public class AdminSession implements Serializable {
	
	private Administrador administrador;

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
}