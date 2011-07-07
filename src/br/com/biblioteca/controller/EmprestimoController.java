package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.entitades.Emprestimo;
import br.com.biblioteca.entitades.Livro;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class EmprestimoController {
	
	private Result result;
	private EmprestimoDAO emprestimoDAO;
	private LivroDAO livroDAO;
	private AdminSession adminSession;
	
	public EmprestimoController(Result result, EmprestimoDAO emprestimoDAO, LivroDAO livroDAO, AdminSession adminSession){
		this.result = result;
		this.emprestimoDAO = emprestimoDAO;
		this.livroDAO = livroDAO;
		this.adminSession = adminSession;
	}
	@Post
	@Path("/emprestimos")
	public void index(String ordenarPor, String nomeDoLivro){
		List<Emprestimo> emprestimos = emprestimoDAO.pesquisarEmprestimo(nomeDoLivro, ordenarPor);
		result.include("emprestimos", emprestimos);
		result.include("nome", nomeDoLivro);
		result.include("usuario", adminSession.getAdministrador().getNome());
	}
	
	@Post
	@Path("emprestimo/devolve")
	public void devolve(Long id, Date dataDeDevolucao){
		String message;
		if(id.equals("") || dataDeDevolucao.equals("")){
			message = "Erro do devolver livro";
		}else{
			Emprestimo emprestimo = emprestimoDAO.procuraPorId(id);
			emprestimo.setDataDeDevolucao(dataDeDevolucao);
			
			Livro livro = emprestimo.getLivro();
			livro.setEmprestado(false);
			
			emprestimoDAO.atualiza(emprestimo);
			livroDAO.atualiza(livro);
			message = "\"" + livro.getNome() + "\" devolvido com sucesso";
		}
		result.use(json()).from(message, "message").serialize();
	}
}