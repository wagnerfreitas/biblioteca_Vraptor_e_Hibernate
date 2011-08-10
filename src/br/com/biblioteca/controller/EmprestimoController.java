package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.biblioteca.controller.helper.FinalizarEmprestimoHelper;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Permissao;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class EmprestimoController {
	
	private Result result;
	private EmprestimoDAO emprestimoDAO;
	private UsuarioSession usuarioSession;
	private FinalizarEmprestimoHelper finalizarEmprestimoHelper;
	
	public EmprestimoController(Result result, EmprestimoDAO emprestimoDAO,
			UsuarioSession usuarioSession,  FinalizarEmprestimoHelper finalizarEmprestimoHelper){
		this.result = result;
		this.emprestimoDAO = emprestimoDAO;
		this.usuarioSession = usuarioSession;
		this.finalizarEmprestimoHelper = finalizarEmprestimoHelper;
	}
	
	@Get
	@Path("/emprestimos")
	@Permissao({"PERM_ADMIN", "PERM_LISTAR_EMPRESTIMO"})
	public void index(String nomeDoLivro, String ordenarPor){
		try {
			List<Emprestimo> emprestimos = emprestimoDAO.pesquisarEmprestimo(nomeDoLivro, ordenarPor);
			result.include("emprestimos", emprestimos)
				.include("nomeDoLivro", nomeDoLivro)
				.include("ordenarPor", ordenarPor)
				.include("permissoesDoUsuario", usuarioSession.getUsuario().getGrupoDePerfil().getAcoes())
				.include("usuario", usuarioSession.getUsuario().getNome());
		} catch (Exception e) {
			result.include("error", e.getMessage());
		}
	}
	
	@Post
	@Path("emprestimo/devolve")
	@Permissao({"PERM_ADMIN", "PERM_DEVOLVER_LIVRO"})
	public void devolve(Long id, Date dataDeDevolucao){
		String message;
		if(id == null) {
			message = "Id do empréstimo nulo";
		} else if (dataDeDevolucao == null) {
			message = "Data de devolução nula";
		} else {
			if (finalizarEmprestimoHelper.finalizarEmprestimo(id, dataDeDevolucao)) {
				message = "\"Livro\" devolvido com sucesso";
			} else {
				message = "Erro ao devolver livro";
			} 
		}
		result.include("message", message)
			.use(json()).from(message, "message").serialize();
			
	}
}