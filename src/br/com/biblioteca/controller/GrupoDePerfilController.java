package br.com.biblioteca.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.GrupoDePerfilDAO;
import br.com.biblioteca.entidades.Acao;
import br.com.biblioteca.entidades.GrupoDePerfil;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class GrupoDePerfilController {
	
	private GrupoDePerfilDAO grupoDePerfilDAO;
	private AcaoDAO acaoDAO;
	private Result result;
	
	public GrupoDePerfilController(Result result, AcaoDAO acaoDAO, GrupoDePerfilDAO grupoDePerfilDAO) {
		this.acaoDAO = acaoDAO;
		this.grupoDePerfilDAO = grupoDePerfilDAO;
		this.result = result;
	}
	
	@Post
	@Path("grupo/novo")
	public void novo(String nome, List<Long> id) {
		GrupoDePerfil grupoDePerfil = new GrupoDePerfil();
		List<Acao> listaDeAcoes = new ArrayList<Acao>();
		grupoDePerfil.setNome(nome);
		for (Long idAcao : id) {
			Acao acao = acaoDAO.pesquisaAcoesPorId(idAcao);
			listaDeAcoes.add(acao);
		}
		grupoDePerfil.setAcoes(listaDeAcoes);
		grupoDePerfilDAO.novo(grupoDePerfil);
		result.redirectTo(IndexController.class).index();
	}
}