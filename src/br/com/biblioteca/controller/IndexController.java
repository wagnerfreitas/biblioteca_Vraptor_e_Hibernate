package br.com.biblioteca.controller;

import java.util.List;

import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Acao;
import br.com.biblioteca.entidades.Livro;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndexController {

	private Result result;
	private UsuarioSession usuarioSession;
	private AcaoDAO acaoDAO;
	private LivroDAO livroDAO;
	
	public IndexController(Result result, UsuarioSession usuarioSession, AcaoDAO acaoDAO, LivroDAO livroDAO){
		this.result = result;
		this.acaoDAO = acaoDAO;
		this.livroDAO = livroDAO;
		this.usuarioSession = usuarioSession;
	}

	@Get
	@Path("/")
	public void index() {
		try {
			if(usuarioSession.getUsuario() == null) {
				result.redirectTo(LoginController.class).login();
			} else {
				List<Acao> acoes = acaoDAO.acoes();
				List<Livro> livros = livroDAO.pesquisa("");
				result.include("acoes", acoes)
					.include("listLivros", livros)
					.include("usuario", usuarioSession.getUsuario().getNome())
					.include("permissoesDoUsuario", usuarioSession.getUsuario().getGrupoDePerfil().getAcoes());
			}
		} catch (Exception e) {
			result.forwardTo(this).index();
		}
	}
}