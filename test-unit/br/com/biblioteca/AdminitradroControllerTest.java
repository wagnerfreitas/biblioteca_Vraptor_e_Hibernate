package br.com.biblioteca;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.AdministradorController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.AdministradorDAO;
import br.com.biblioteca.entidades.Administrador;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class AdminitradroControllerTest {
	
	private Result result;
	private AdministradorController administradorController;
	private Administrador administrador;
	private ArrayList<Administrador> administradores;
	
	@Mock
	private AdministradorDAO administradorDAO;
	@Mock
	private AdminSession adminSession;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.administradorController = new AdministradorController(result, administradorDAO, adminSession);
	}
	
	@Test
	public void listaAdministradores() {
//		dado
		queEuTenhoUmaListaDeAdministradores();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(administradorDAO.lista()).thenReturn(administradores);
		administradorController.lista();
		
//		então
		assertTrue(result.included().containsKey("administradores"));
	}
	
	@Test
	public void erroAoListarAdministradores() {
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Erro ao listar administradores")).when(administradorDAO).lista();
		administradorController.lista();
		
//		então
		assertEquals("Erro ao listar administradores", result.included().get("error"));
	}
	
	@Test
	public void nomeDoAdministradorNuloAoTentarAdicionarUmNovo() {
//		dado
		queEuTenhoUmAdministrador();
		administrador.setNome(null);
		
//		quando
		administradorController.novo(administrador);
		
		assertEquals("Nome nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeDoAdministradorVazioAoTentarAdicionarUmNovo() {
//		dado
		queEuTenhoUmAdministrador();
		administrador.setNome("");
		
//		quando
		administradorController.novo(administrador);
		
		assertEquals("Nome nulo", result.included().get("message"));
	}
	
	@Test
	public void senhaDoAdministradorNuloAoTentarAdicionarUmNovo() {
//		dado
		queEuTenhoUmAdministrador();
		administrador.setSenha(null);
		
//		quando
		administradorController.novo(administrador);
		
		assertEquals("Senha nula", result.included().get("message"));
	}
	
	@Test
	public void senhaDoAdministradorVazioAoTentarAdicionarUmNovo() {
//		dado
		queEuTenhoUmAdministrador();
		administrador.setSenha("");
		
//		quando
		administradorController.novo(administrador);
		
		assertEquals("Senha nula", result.included().get("message"));
	}
	
	@Test
	public void erroAoAdicionarAdministrador() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		doThrow(new RuntimeException("Erro ao adicionar administrador")).when(administradorDAO).adiciona(administrador);
		administradorController.novo(administrador);
		
//		então
		assertEquals("Erro ao adicionar administrador", result.included().get("message"));
	}
	
	@Test
	public void adicionarAdministrador() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		administradorController.novo(administrador);
		
//		então
		assertEquals("\"" + administrador.getNome() + "\" adicionado com sucesso", result.included().get("message"));
	}
	
	@Test
	public void idNuloAoDeletarAdministrador() {
//		dado
		queEuTenhoUmAdministrador();
		administrador.setId(null);
		
//		quando
		administradorController.deletar(administrador);
		
//		então
		assertEquals("Erro ao tentar apagar", result.included().get("message"));
	}
	
	@Test
	public void erroAoTentarDeletarAdministrador() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		doThrow(new RuntimeException("Erro ao tentar deletar o administrador")).when(administradorDAO).delete(administrador);
		administradorController.deletar(administrador);
		
//		então
		assertEquals("Erro ao tentar deletar o administrador", result.included().get("message"));
	}
	
	@Test
	public void deletarAdministrador() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		administradorController.deletar(administrador);
		
//		então
		assertEquals("\"" + administrador.getNome() + "\" apagado com sucesso", result.included().get("message"));
	}
	
	public void queEuTenhoUmaListaDeAdministradores() {
		queEuTenhoUmAdministrador();
		administradores = new ArrayList<Administrador>();
		administradores.add(administrador);
	}
	
	public void queEuTenhoUmAdministrador() {
		administrador = new Administrador();
		administrador.setId(1L);
		administrador.setNome("Admin");
		administrador.setSenha("123");
	}
}