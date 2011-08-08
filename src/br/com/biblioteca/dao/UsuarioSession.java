package br.com.biblioteca.dao;

import br.com.biblioteca.entidades.Usuario;

public interface UsuarioSession {
	
	public Usuario getUsuario();

	public void setUsuario(Usuario usuario);
}