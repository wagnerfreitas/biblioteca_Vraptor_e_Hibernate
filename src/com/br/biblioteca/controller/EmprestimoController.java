package com.br.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.br.biblioteca.dao.AdminSession;
import com.br.biblioteca.dao.EmprestimoDAO;
import com.br.biblioteca.dao.LivroDAO;
import com.br.biblioteca.dao.UsuarioDAO;
import com.br.biblioteca.entitades.Emprestimo;
import com.br.biblioteca.entitades.Livro;
import com.br.biblioteca.entitades.Usuario;

@Resource
public class EmprestimoController {
	
	private Result result;
	private EmprestimoDAO emprestimoDAO;
	private LivroDAO livroDAO;
	private UsuarioDAO usuarioDAO;
	private AdminSession adminSession;
	
	public EmprestimoController(Result result, EmprestimoDAO emprestimoDAO, LivroDAO livroDAO, UsuarioDAO usuarioDAO, AdminSession adminSession){
		this.result = result;
		this.emprestimoDAO = emprestimoDAO;
		this.livroDAO = livroDAO;
		this.usuarioDAO = usuarioDAO;
		this.adminSession = adminSession;
	}
	@Get
	@Path("/emprestimos")
	public void index(String nomeDoLivro){
		List<Emprestimo> emprestimos = emprestimoDAO.pesquisarEmprestimo(nomeDoLivro);
		result.include("emprestimos", emprestimos);
		result.include("nome", nomeDoLivro);
		result.include("usuario", adminSession.getAdministrador().getNome());
	}
	
	@Post
	@Path("emprestimo/devolve")
	public void devolve(Long id, Date dataDeDevolucao){
		String message;
		if(id.equals("") || dataDeDevolucao.equals("")){
			message = "Erro";
		}else{
			Emprestimo emprestimo = emprestimoDAO.procuraPorId(id);
			Livro livro = emprestimo.getLivro();
			livro.setEmprestado(false);
			
			Usuario usuario = emprestimo.getUsuario();
			usuario.setEmprestimoAtivo(false);
			
			emprestimo.setDataDeDevolucao(dataDeDevolucao);
			usuarioDAO.atualiza(usuario);
			livroDAO.atualiza(livro);
			emprestimoDAO.atualiza(emprestimo);
			message = "\"" + livro.getNome() + "\" devolvido com sucesso";
		}
		result.use(json()).from(message, "message").serialize();
	}
}