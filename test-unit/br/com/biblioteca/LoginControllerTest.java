package br.com.biblioteca;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.LoginController;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class LoginControllerTest {
	
	private Result result;
	private LoginController loginController;
	private Usuario usuario;
	
	@Mock
	private UsuarioDAO usuarioDAO;
	@Mock
	private UsuarioSession usuarioSession;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.loginController = new LoginController(result, usuarioDAO, usuarioSession);
	}
	
	@Test
	public void usuarioLogado() {
//		dado
		queEuTenhoUmUsuario();

//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		loginController.login();
	}
	
	@Test
	public void deveriaLogarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioDAO.login(usuario.getNome(), usuario.getSenha())).thenReturn(usuario);
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		loginController.login(usuario.getNome(), usuario.getSenha());
	}
	
	@Test
	public void deveriaNaoLogarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioDAO.login(usuario.getNome(), usuario.getSenha())).thenReturn(null);
		loginController.login(usuario.getNome(), usuario.getSenha());
	}
	
	@Test
	public void deveriaLancarExcacao() {
//		dado
		queEuTenhoUmUsuario();
		
//		quando
		doThrow(new RuntimeException("Não foi possível acessar o sistema")).when(usuarioDAO).login(usuario.getNome(), usuario.getSenha());
		loginController.login(usuario.getNome(), usuario.getSenha());
		assertEquals("Não foi possível acessar o sistema", result.included().get("message"));
	}
	
	@Test
	public void deveriaDeslogarUsuario() {
//		dado
		queEuTenhoUmUsuario();

//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		loginController.logout();
	}
	
	@Test
	public void erroAoTentarLogar() {
		loginController.erro();
	}
	
	public void queEuTenhoUmUsuario() {
		usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Usuario");
		usuario.setEmail("email@email.com");
		usuario.setAtivo(true);
	}
}