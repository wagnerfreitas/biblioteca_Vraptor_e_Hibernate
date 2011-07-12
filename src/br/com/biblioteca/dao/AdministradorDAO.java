package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entidades.Administrador;

public interface AdministradorDAO {
	
	public void adiciona(Administrador administrador);	
	
	public List<Administrador> lista();	
	
	public void delete(Administrador administrador);	
	
	public Administrador login(String nome, String senha);
}