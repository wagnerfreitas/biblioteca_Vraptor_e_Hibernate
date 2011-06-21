package com.br.biblioteca.dao;

import java.io.Serializable;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

import com.br.biblioteca.entitades.Usuario;

@Component
@SessionScoped
public class UserSession implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario usario;

	public Usuario getUsario() {
		return usario;
	}

	public void setUsario(Usuario usario) {
		this.usario = usario;
	}
}