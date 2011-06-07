package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.EmprestimoDAO;
import com.br.biblioteca.dao.UsuarioDAO;
import com.br.biblioteca.entitades.Emprestimo;
import com.br.biblioteca.entitades.Usuario;

@Resource
public class UsuarioController {
	
	private Result result;
	private UsuarioDAO usuarioDAO;
	private EmprestimoDAO emprestimoDAO;
	
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
	@Path("/usuario/novo")
	public void novo(){
	}
	
	@Post
	public void novo(Usuario usuario){
		usuarioDAO.adiciona(usuario);
		result.forwardTo("../index.jsp");
	} 
	@Post
	public void delete(Long id){
		Usuario usuario = usuarioDAO.pesquisarUsuarioPorId(id);
		List<Emprestimo> emprestimos = emprestimoDAO.procuraPorIdUsuario(id);
		for (Emprestimo emp : emprestimos){
			if(emp.getUsuario() != null && usuario.getId() == emp.getUsuario().getId()){
				System.out.println("ahhhhhhh vacilouuuu");
			}
		}
		usuarioDAO.atualiza(usuario);
	}
}