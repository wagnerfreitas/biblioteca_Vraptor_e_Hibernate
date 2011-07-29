package br.com.biblioteca.dao;

import br.com.biblioteca.entidades.Usuario;

public interface AdminSession {
	
	public Usuario getUsuario();

	public void setUsuario(Usuario usuario);
}