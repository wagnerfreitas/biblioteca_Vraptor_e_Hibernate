package br.com.biblioteca.controller;

import java.util.List;

import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.GrupoDePerfilDAO;
import br.com.biblioteca.entidades.Acao;
import br.com.biblioteca.entidades.GrupoDePerfil;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;

@Resource
public class GrupoDePerfilController {
	
	private GrupoDePerfilDAO grupoDePerfilDAO;
	private GrupoDePerfil grupoDePerfil;
	private AcaoDAO acaoDAO;
	
	public GrupoDePerfilController(AcaoDAO acaoDAO, GrupoDePerfilDAO grupoDePerfilDAO) {
		this.acaoDAO = acaoDAO;
		this.grupoDePerfilDAO = grupoDePerfilDAO;
	}
	
	@Post
	@Path("grupo/novo")
	public void novo(String nome, List<Long> id) {
		grupoDePerfil = new GrupoDePerfil();
		grupoDePerfil.setNome(nome);
		for (Long idAcao : id) {
			List<Acao> acoes = acaoDAO.pesquisaAcoesPorId(idAcao);
			grupoDePerfil.setIdAcao(acoes);
		}
		grupoDePerfilDAO.novo(grupoDePerfil);
	}
}