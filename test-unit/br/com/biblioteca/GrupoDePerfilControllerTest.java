package br.com.biblioteca;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.GrupoDePerfilController;
import br.com.biblioteca.controller.helper.AuditoriaHelper;
import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.GrupoDePerfilDAO;
import br.com.biblioteca.entidades.Acao;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class GrupoDePerfilControllerTest {
	
	@Mock
	private AcaoDAO acaoDAO;
	@Mock
	private GrupoDePerfilDAO grupoDePerfilDAO;
	@Mock
	private AuditoriaHelper auditoriaHelper;
	
	private Result result;
	private GrupoDePerfilController grupoDePerfilController;
	private Acao acao;
	private ArrayList<Long> listAcoes;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.grupoDePerfilController = new GrupoDePerfilController(result, acaoDAO, grupoDePerfilDAO, auditoriaHelper);
	}
	
	@Test
	public void erroNaPesquisaDeAcoes() {
//		dado
		queEuTenhoUmaListaDeCodigosDeAcao();
		
//		quando
		doThrow(new RuntimeException("Erro ao tentar adicionar grupo de ação")).when(acaoDAO).pesquisaAcoesPorId(1L);
		grupoDePerfilController.novo("Grupo De Perfil", listAcoes);

//		então
		assertEquals("Erro ao tentar adicionar grupo de ação", result.included().get("message"));
		
	}
	
	@Test
	public void grupoDeAcaoAdicionadoComSucesso() {
//		dado
		queEuTenhoUmaAcao();
		queEuTenhoUmaListaDeCodigosDeAcao();
		
//		quando
		when(acaoDAO.pesquisaAcoesPorId(1L)).thenReturn(acao);
		grupoDePerfilController.novo("Grupo de Ação", listAcoes);
		
//		entao
		assertEquals("Grupo de perfil adicionado com sucesso", result.included().get("message"));
	}
	
	public void queEuTenhoUmaListaDeCodigosDeAcao() {
		listAcoes = new ArrayList<Long>();
		listAcoes.add(1L);
	}
		
	public void queEuTenhoUmaAcao() {
		acao = new Acao();
		acao.setId(1L);
		acao.setNome("Nome");
		acao.setDescricao("Descricao");
	}
}