package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.UsuarioDAO;
import com.br.biblioteca.entitades.Usuario;

@Resource
public class UsuarioController {
	
	private Result result;
	private UsuarioDAO usuarioDAO;
	
	public UsuarioController(Result result, UsuarioDAO usuarioDAO){
		this.result = result;
		this.usuarioDAO = usuarioDAO;
	}
	
	@Get
	@Path("/usuarios")
	public void index(String nome){
		List<Usuario> usuarios = usuarioDAO.pesquisa(nome);
		result.include("usuarios", usuarios);
	}
	
	@Get
	@Path("/usuarios/list")
	public void usuarios(String pesquisarUsuario){
		List<Usuario> usuarios = usuarioDAO.pesquisa(pesquisarUsuario);
		result.include("list", usuarios);
	}
	
	@Get
	@Path("/usuario/novo")
	public void novo(){
	}
	
	@Post
	public void novo(Usuario usuario){
		if(usuario.getNome().equals("") || usuario.getEmail().equals("")){
			throw new NullPointerException();
		}
		usuarioDAO.adiciona(usuario);
		result.forwardTo("../index.jsp");
	}
	
	@Post
	public void delete(List<Long> idDelete){
		for (Long id : idDelete) {
			Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(id);
			usuario.setUsuarioAtivo(false);
			usuarioDAO.atualiza(usuario);
		}
		result.forwardTo("../index.jsp");
	}
}