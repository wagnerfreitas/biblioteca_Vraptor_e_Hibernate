package br.com.biblioteca.dao.Impl;

import java.io.Serializable;

import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@SuppressWarnings("serial")
@Component
@SessionScoped
public class AdminSessionImpl implements Serializable , AdminSession{
	
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}