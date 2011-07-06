package br.com.biblioteca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.LivroController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entitades.Administrador;
import br.com.biblioteca.entitades.Livro;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class LivroControllerTest {
	
	private Result result;
	private LivroController livroController;
	private Administrador administrador; 
	
	@Mock
	private LivroDAO livroDAO;
	@Mock
	private EmprestimoDAO emprestimoDAO;
	@Mock
	private UsuarioDAO usuarioDAO;
	@Mock
	private AdminSession adminSession;
	
	
	public LivroControllerTest() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.livroController = new LivroController(result, livroDAO, emprestimoDAO, usuarioDAO, adminSession);
	}
	
	@Test
	public void testListaLivros() {
//		dado
		queEuTenhoUmAdministrador();

//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(livroDAO.pesquisa("livro")).thenReturn(new ArrayList<Livro>());
		
//		entao
		livroController.index("livro");
		assertTrue(result.included().containsKey("livros"));
	} 
	
	
	public void queEuTenhoUmAdministrador() {
		administrador = new Administrador();
		administrador.setNome("wagner");
		administrador.setSenha("123");
	}
}