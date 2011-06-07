package com.br.biblioteca.controller;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.EmprestimoDAO;
import com.br.biblioteca.entitades.Emprestimo;

@Resource
public class EmprestimoController {
	@SuppressWarnings("unused")
	private Result result;
	private EmprestimoDAO emprestimoDAO;
	public EmprestimoController(Result result, EmprestimoDAO emprestimoDAO){
		this.result = result;
		this.emprestimoDAO = emprestimoDAO;
	}
	@Get
	@Path("/emprestimo")
	public List<Emprestimo> pesquisaEmprestimoPorLivro(String nomeDoLivro){
		return emprestimoDAO.pesquisarEmprestimo(nomeDoLivro);
	}
	@Get
	@Path("/emprestimo/novo")
	public void novo(Emprestimo emprestimo){
		
	}
}
