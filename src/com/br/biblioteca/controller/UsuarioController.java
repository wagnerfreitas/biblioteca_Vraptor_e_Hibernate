package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

import com.br.biblioteca.entitades.Usuario;

@Resource
public class UsuarioController {
	@Get
	@Path("/usuario")
	public List<Usuario> index(String nomeDoUsuario){
		return null;
	}
	@Get
	@Path("/usuario/novo")
	public void novo(Usuario usuario){
	}
}