package br.com.biblioteca.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.biblioteca.controller.helper.AuditoriaHelper;
import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.GrupoDePerfilDAO;
import br.com.biblioteca.entidades.Acao;
import br.com.biblioteca.entidades.GrupoDePerfil;
import br.com.biblioteca.entidades.Permissao;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class GrupoDePerfilController {
	
	private Result result;
	private AcaoDAO acaoDAO;
	private GrupoDePerfilDAO grupoDePerfilDAO;
	private AuditoriaHelper auditoriaHelper;
	
	public GrupoDePerfilController(Result result, AcaoDAO acaoDAO, GrupoDePerfilDAO grupoDePerfilDAO, AuditoriaHelper auditoriaHelper) {
		this.result = result;
		this.acaoDAO = acaoDAO;
		this.grupoDePerfilDAO = grupoDePerfilDAO;
		this.auditoriaHelper = auditoriaHelper;
	}
	
	@Post
	@Path("grupo/novo")
	@Permissao({"PERM_ADMIN", "PERM_ADD_GRUPO_ACESSO"})
	public void novo(String nome, List<Long> id) {
		String message;
		try {
			GrupoDePerfil grupoDePerfil = new GrupoDePerfil();
			List<Acao> listaDeAcoes = new ArrayList<Acao>();
			for (Long idAcao : id) {
				Acao acao = acaoDAO.pesquisaAcoesPorId(idAcao);
				listaDeAcoes.add(acao);
			}
			grupoDePerfil.setNome(nome);
			grupoDePerfil.setAcoes(listaDeAcoes);
			grupoDePerfilDAO.novo(grupoDePerfil);
			message = "Grupo de perfil adicionado com sucesso";
			auditoriaHelper.auditoria("Grupo de Perfil: " + grupoDePerfil.getNome(), "ADICIONOU", new Date());
			
			result.include("message", message)
				.use(json()).from(message, "message").serialize();
		} catch (Exception e) {
			message = "Erro ao tentar adicionar grupo de ação";
		}
		result.include("message", message);
	}
}