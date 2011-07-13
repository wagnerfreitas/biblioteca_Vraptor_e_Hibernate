package br.com.biblioteca;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.LoginController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.AdministradorDAO;
import br.com.biblioteca.entidades.Administrador;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class LoginControllerTest {
	
	private Result result;
	private LoginController loginController;
	private Administrador administrador;
	
	@Mock
	private AdministradorDAO administradorDAO;
	@Mock
	private AdminSession adminSession;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.loginController = new LoginController(result, administradorDAO, adminSession);
	}
	
	@Test
	public void usuarioLogado() {
//		dado
		queEuTenhoUmAdministrador();

//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		loginController.login();
	}
	
	@Test
	public void deveriaLogarUsuario() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(administradorDAO.login(administrador.getNome(), administrador.getSenha())).thenReturn(administrador);
		when(adminSession.getAdministrador()).thenReturn(administrador);
		loginController.login(administrador);
//		então
	}
	
	@Test
	public void deveriaNaoLogarUsuario() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(administradorDAO.login(administrador.getNome(), administrador.getSenha())).thenReturn(null);
		loginController.login(administrador);
//		então
	}
	
	@Test
	public void deveriaDeslogarUsuario() {
//		dado
		queEuTenhoUmAdministrador();

//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		
		loginController.logout();
	}
	
	@Test
	public void erroAoTentarLogar() {
		loginController.erro();
	}
	
	public void queEuTenhoUmAdministrador() {
		administrador = new Administrador();
		administrador.setId(1L);
		administrador.setNome("Admin");
		administrador.setSenha("senha");
	}
}