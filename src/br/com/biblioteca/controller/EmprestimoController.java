package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class EmprestimoController {
	
	private Result result;
	private EmprestimoDAO emprestimoDAO;
	private LivroDAO livroDAO;
	private UsuarioSession usuarioSession;
	private AuditoriaHelper auditoriaHelper;
	
	public EmprestimoController(Result result, EmprestimoDAO emprestimoDAO, LivroDAO livroDAO, 
			UsuarioSession usuarioSession, AuditoriaHelper auditoriaHelper){
		this.result = result;
		this.emprestimoDAO = emprestimoDAO;
		this.livroDAO = livroDAO;
		this.usuarioSession = usuarioSession;
		this.auditoriaHelper = auditoriaHelper;
	}
	@Get
	@Path("/emprestimos") 
	public void index(String nomeDoLivro, String ordenarPor){
		try {
			List<Emprestimo> emprestimos = emprestimoDAO.pesquisarEmprestimo(nomeDoLivro, ordenarPor);
			result.include("emprestimos", emprestimos)
				.include("nomeDoLivro", nomeDoLivro)
				.include("ordenarPor", ordenarPor)
				.include("usuario", usuarioSession.getUsuario().getNome());
		} catch (Exception e) {
			result.include("error", e.getMessage());
		}
	}
	
	@Post
	@Path("emprestimo/devolve")
	public void devolve(Long id, Date dataDeDevolucao){
		String message;
		if(id == null) {
			message = "Id do empréstimo nulo";
		} else if (dataDeDevolucao == null) {
			message = "Data de devolução nula";
		} else {
			try {
				Emprestimo emprestimo = emprestimoDAO.procuraPorId(id);
				Livro livro = emprestimo.getLivro();
				Usuario usuario = emprestimo.getUsuario();

				emprestimo.setDataDeDevolucao(dataDeDevolucao);
				livro.setEmprestado(false);
				
				auditoriaHelper.auditoria(usuario.getNome() + " - " + livro.getNome(), "DEVOLVEU", dataDeDevolucao);
				emprestimoDAO.atualiza(emprestimo);
				livroDAO.atualiza(livro);
				
				message = "\"" + livro.getNome() + "\" devolvido com sucesso";
			} catch (Exception e) {
				message = e.getMessage();
			}
		}
		result.include("message", message)
			.use(json()).from(message, "message").serialize();
	}
}