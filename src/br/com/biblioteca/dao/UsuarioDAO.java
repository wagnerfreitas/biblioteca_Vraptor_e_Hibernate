package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entidades.Usuario;

public interface UsuarioDAO {
	
	public void adiciona(Usuario usuario);
	
	public void atualiza(Usuario usuario);
	
	public List<Usuario> pesquisa(String nome);
	
	public Usuario pesquisarUsuarioPorId(Long id);
}