package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entitades.Administrador;

public interface AdministradorDAOImpl {
	
	public void adiciona(Administrador administrador);	
	
	public List<Administrador> lista();	
	
	public void delete(Administrador administrador);	
	
	public Administrador login(String nome, String senha);
}