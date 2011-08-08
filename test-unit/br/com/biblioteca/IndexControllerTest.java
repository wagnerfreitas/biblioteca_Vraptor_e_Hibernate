package br.com.biblioteca;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.IndexController;
import br.com.biblioteca.dao.AcaoDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;


public class IndexControllerTest {
	
	private Result result;
	private IndexController indexController;
	private Usuario usuario;
	@Mock
	private AcaoDAO acaoDAO; 
	@Mock
	private UsuarioSession usuarioSession;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.indexController = new IndexController(result, usuarioSession, acaoDAO);
	}
	
	@Test
	public void usuarioLogado(){
//		dado
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		indexController.index();
		
//		então
		assertEquals(usuarioSession.getUsuario().getNome(), result.included().get("usuario"));
	}
	
	@Test
	public void usuarioDeslogado() {
//		quando
		when(usuarioSession.getUsuario()).thenReturn(null);
		indexController.index();
	}
	
	public void queEuTenhoUmUsuario() {
		usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Usuario");
		usuario.setSenha("123");
		usuario.setEmail("usuario@usuario.com");
		usuario.setAtivo(true);
	}
}