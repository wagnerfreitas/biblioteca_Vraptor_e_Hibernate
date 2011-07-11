package br.com.biblioteca;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.LivroController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entitades.Administrador;
import br.com.biblioteca.entitades.Emprestimo;
import br.com.biblioteca.entitades.Livro;
import br.com.biblioteca.entitades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import static org.mockito.Mockito.doThrow;

public class LivroControllerTest {
	
	private Result result;
	private LivroController livroController;
	private Administrador administrador;
	private Livro livro;
	private Usuario usuario;
	private Emprestimo emprestimo;
	private Date dataDeEmprestimo;
	
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
	
	@Test
	public void testNomeDoLivroNuloAoAdicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setNome(null);
		
//		quando
		doThrow(new RuntimeException("Nome do livro nulo")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Nome do livro nulo", result.included().get("message"));
	}
	
	@Test
	public void testNomeDoLivroVazioAoAdicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setNome("");
		
//		quando
		doThrow(new RuntimeException("Nome do livro nulo")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Nome do livro nulo", result.included().get("message"));
	}
	
	@Test
	public void testAutorNuloAoAdicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setAutor(null);
		
//		quando
		doThrow(new RuntimeException("Nome do autor nulo")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Nome do autor nulo", result.included().get("message"));
	}
	
	@Test
	public void testAutorVazioAoAdicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setAutor("");
		
//		quando
		doThrow(new RuntimeException("Nome do autor nulo")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Nome do autor nulo", result.included().get("message"));
	}
	
	@Test
	public void testEdicaoNulaAoAdicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setEdicao(null);
		
//		quando
		doThrow(new RuntimeException("Edição nula")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Edição nula", result.included().get("message"));
	}
	
	@Test
	public void testEdicaoVaziaAoAdicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setEdicao("");
		
//		quando
		doThrow(new RuntimeException("Edição nula")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Edição nula", result.included().get("message"));
	}
	
	@Test
	public void testIdDoUsuarioNuloAoEmprestarLivro() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		usuario.setId(null);
		queEuTenhoUmaDataDeEmprestimo();
		
//		quando
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Erro ao tentar realizar empréstimo", result.included().get("message"));
	}
	
	@Test
	public void testIdDoLivroNuloAoEmprestarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setId(null);
		queEuTenhoUmUsuario();
		queEuTenhoUmaDataDeEmprestimo();
		
//		quando
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Erro ao tentar realizar empréstimo", result.included().get("message"));
	}
	
	@Test
	public void testDateDeEmprestimoNuloAoAdicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		
//		quando
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Data nula", result.included().get("message"));
	}
	
	@Test
	public void testPesquisarUsuarioAoEmprestarLivro() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaDataDeEmprestimo();
		
//		quando
		doThrow(new RuntimeException("Erro na pesquisa")).when(usuarioDAO).pesquisarUsuarioPorId(usuario.getId());
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Erro na pesquisa", result.included().get("message"));
	}
	
	@Test
	public void testPesquisarLivroAoEmprestarLivro() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaDataDeEmprestimo();
		
//		quando
		doThrow(new RuntimeException("Erro ao pesquisar")).when(livroDAO).pesquisarLivroPorId(livro.getId());
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Erro ao pesquisar", result.included().get("message"));
	}
	
	@Test
	public void testEmprestarLivro() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaDataDeEmprestimo();
		queEuTenhoUmEmprestimo();
		
//		quando
		when(usuarioDAO.pesquisarUsuarioPorId(usuario.getId())).thenReturn(usuario);
		when(livroDAO.pesquisarLivroPorId(livro.getId())).thenReturn(livro);
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" emprestado com sucesso", result.included().get("message"));
	}
	
	
	
	public void queEuTenhoUmEmprestimo() {
		emprestimo = new Emprestimo();
		emprestimo.setUsuario(usuario);
		emprestimo.setLivro(livro);
		emprestimo.setDataDeEmprestimo(dataDeEmprestimo);
	}
	
	public void queEuTenhoUmaDataDeEmprestimo() {
		dataDeEmprestimo = new Date();
	}
	
	public void queEuTenhoUmUsuario() {
		usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Usuario");
		usuario.setEmail("email@email.com");
		usuario.setUsuarioAtivo(true);
	}
	
	public void queEuTenhoUmLivro() {
		livro = new Livro();
		livro.setId(1L);
		livro.setNome("Livro");
		livro.setAutor("Autor");
		livro.setEdicao("Edição");
		livro.setEmprestado(false);
		livro.setLivroDeletado(false);
	}
	
	public void queEuTenhoUmAdministrador() {
		administrador = new Administrador();
		administrador.setNome("Admin");
		administrador.setSenha("123");
	}
}