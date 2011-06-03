package com.br.biblioteca.controller;

import java.util.List;

import com.br.biblioteca.entitades.Usuario;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;

@Resource
public class UsuarioController {
	@Post
	@Path("/usuario/add")
	public void adiciona(Usuario usuario){
		
	}
	@Get
	@Path("/usuario")
	public List<Usuario> index(String nomeDoUsuario){
		return null;
	}
}
