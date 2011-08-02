package br.com.biblioteca;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.IndexController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.entidades.TipoDePerfil;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;


public class IndexControllerTest {
	
	private Result result;
	private IndexController indexController;
	private Usuario usuario;
	
	@Mock
	private AdminSession adminSession;
	private TipoDePerfil tipoDePerfil;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.indexController = new IndexController(result, adminSession);
	}
	
	@Test
	public void usuarioLogado(){
//		dado
		queEuTenhoUmPerfil();
		queEuTenhoUmUsuario();
		
//		quando
		when(adminSession.getUsuario()).thenReturn(usuario);
		indexController.index();
		
//		então
		assertEquals(adminSession.getUsuario().getNome(), result.included().get("usuario"));
	}
	
	@Test
	public void usuarioDeslogado() {
//		quando
		when(adminSession.getUsuario()).thenReturn(null);
		indexController.index();
		
//		então
		assertEquals(null, result.included().get("null"));
	}
	
	public void queEuTenhoUmPerfil() {
		tipoDePerfil = TipoDePerfil.ADMINISTRADOR;
	}
	
	public void queEuTenhoUmUsuario() {
		usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Usuario");
		usuario.setSenha("123");
		usuario.setEmail("usuario@usuario.com");
		usuario.setTipoDePerfil(tipoDePerfil);
		usuario.setUsuarioAtivo(true);
	}
}