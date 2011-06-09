package com.br.biblioteca.controller;

import java.util.Calendar;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.EmprestimoDAO;
import com.br.biblioteca.dao.LivroDAO;
import com.br.biblioteca.entitades.Emprestimo;
import com.br.biblioteca.entitades.Livro;

@Resource
public class EmprestimoController {
	
	private Result result;
	private EmprestimoDAO emprestimoDAO;
	LivroDAO livroDAO;
	
	public EmprestimoController(Result result, EmprestimoDAO emprestimoDAO, LivroDAO livroDAO){
		this.result = result;
		this.emprestimoDAO = emprestimoDAO;
		this.livroDAO = livroDAO;
	}
	@Get
	@Path("/emprestimos")
	public void index(String nomeDoLivro){
		List<Emprestimo> emprestimos = emprestimoDAO.pesquisarEmprestimo(nomeDoLivro);
		result.include("emprestimos", emprestimos);
	}
	@Post
	public void devolve(Long id, Calendar dataDeDevolucao){
		Emprestimo emprestimo = emprestimoDAO.procuraPorId(id);
		Livro livro = emprestimo.getLivro();
		livro.setEmprestado(false);
		emprestimo.setDataDeDevolucao(dataDeDevolucao);
		livroDAO.atualiza(livro);
		emprestimoDAO.atualiza(emprestimo);
		result.forwardTo("index.jsp");
	}
}