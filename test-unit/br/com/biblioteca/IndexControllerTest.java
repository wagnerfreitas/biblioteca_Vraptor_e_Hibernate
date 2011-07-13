package br.com.biblioteca;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;	
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.IndexController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.entidades.Administrador;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;


public class IndexControllerTest {
	
	private Result result;
	private IndexController indexController;
	private Administrador administrador;
	
	@Mock
	private AdminSession adminSession;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.indexController = new IndexController(result, adminSession);
	}
	
	@Test
	public void usuarioLogado(){
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		indexController.index();
		
//		então
		assertEquals(administrador.getNome(), result.included().get("nome"));
	}
	
	@Test
	public void usuarioDeslogado() {
//		quando
		when(adminSession.getAdministrador()).thenReturn(null);
		indexController.index();
		
//		então
		assertEquals(null, result.included().get("null"));
	}
	
	public void queEuTenhoUmAdministrador() {
		administrador = new Administrador();
		administrador.setId(1L);
		administrador.setNome("Admin");
		administrador.setSenha("senha");
	}
}