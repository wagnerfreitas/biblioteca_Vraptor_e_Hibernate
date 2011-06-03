package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;

import com.br.biblioteca.entitades.Emprestimo;

@Resource
public class EmprestimoController {
	@Get
	@Path("/emprestimo")
	public List<Emprestimo> index(){
		return null;
	}
	@Post
	@Path("/emprestimo/add")
	public void add(Emprestimo emprestimo){
		
	}
}
