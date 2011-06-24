package com.br.biblioteca.dao;

import java.io.Serializable;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

import com.br.biblioteca.entitades.Administrador;

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