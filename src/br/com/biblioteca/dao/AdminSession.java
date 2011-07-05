package br.com.biblioteca.dao;

import br.com.biblioteca.entitades.Administrador;

public interface AdminSession {
	
	public Administrador getAdministrador();

	public void setAdministrador(Administrador administrador);
}