package br.com.biblioteca;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.EmprestimoController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.entidades.Administrador;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class EmprestimoControllerTest {
	
	private Result result;
	private EmprestimoController emprestimoController;
	private ArrayList<Emprestimo> emprestimos;
	private Date dataDeEmprestimo;
	private Date dataDeDevolucao;
	private Livro livro;
	private Usuario usuario;
	private Emprestimo emprestimo;
	private Administrador administrador;
	
	@Mock
	private EmprestimoDAO emprestimoDAO;
	@Mock
	private LivroDAO livroDAO;
	@Mock 
	private AdminSession adminSession;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.emprestimoController = new EmprestimoController(result, emprestimoDAO, livroDAO, adminSession);
	}
	
	@Test
	public void listaEmprestimos() {
//		dado
		queEuTenhoUmaListaDeEmprestimos();
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(emprestimoDAO.pesquisarEmprestimo(livro.getNome(), "")).thenReturn(emprestimos);
		emprestimoController.index("", livro.getNome());
		
//		então
		assertTrue(result.included().containsKey("emprestimos"));
	}
	
	@Test
	public void erroAoListaEmprestimos() {
//		dado
		queEuTenhoUmEmprestimo();
		
//		quando
		doThrow(new RuntimeException("Erro ao pesquisar empréstimo")).when(emprestimoDAO).pesquisarEmprestimo(livro.getNome(), "");
		emprestimoController.index(livro.getNome(), "");
		
//		então
		assertEquals("Erro ao pesquisar empréstimo", result.included().get("error"));
	}
	
	@Test
	public void devolverLivroComIdDoEmprestimoNulo() {
//		dado
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		emprestimo.setId(null);
		
//		quando
		emprestimoController.devolve(emprestimo.getId(), dataDeDevolucao);

//		então
		assertEquals("Id do empréstimo nulo", result.included().get("message"));
	}
	
	@Test
	public void devolverLivroComDataDeDevolucaoNula() {
//		dado
		queEuTenhoUmEmprestimo();
		
//		quando
		emprestimoController.devolve(emprestimo.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Data de devolução nula", result.included().get("message"));
	}
	
	@Test
	public void erroAoPesquisarEmprestimoAoTentarDevolverLivro() {
//		dado
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		
//		quando
		doThrow(new RuntimeException("Erro ao pesquisar empréstimo")).when(emprestimoDAO).procuraPorId(emprestimo.getId());
		emprestimoController.devolve(emprestimo.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao pesquisar empréstimo", result.included().get("message"));
	}
	
	@Test
	public void erroAoAtualizarEmprestimoAoTentarDevolverLivro() {
//		dado
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		
//		quando
		when(emprestimoDAO.procuraPorId(emprestimo.getId())).thenReturn(emprestimo);
		doThrow(new RuntimeException("Erro ao devolver livro")).when(emprestimoDAO).atualiza(emprestimo);
		emprestimoController.devolve(emprestimo.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao devolver livro", result.included().get("message"));
	}
	
	@Test
	public void erroAoAtualizarLivroAoTentarDevolver() {
//		dado
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		
//		quando
		when(emprestimoDAO.procuraPorId(emprestimo.getId())).thenReturn(emprestimo);
		doThrow(new RuntimeException("Erro/Livro")).when(livroDAO).atualiza(livro);
		emprestimoController.devolve(emprestimo.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro/Livro", result.included().get("message"));
	}
	
	@Test
	public void devolverLivro() {
//		dado
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		
//		quando
		when(emprestimoDAO.procuraPorId(emprestimo.getId())).thenReturn(emprestimo);
		emprestimoController.devolve(emprestimo.getId(), dataDeDevolucao);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" devolvido com sucesso", result.included().get("message"));
	}
	
	public void queEuTenhoUmAdministrador() {
		administrador = new Administrador();
		administrador.setNome("Admin");
		administrador.setSenha("123");
	}
	
	public void queEuTenhoUmLivro() {
		livro = new Livro();
		livro.setId(1L);
		livro.setNome("Nome");
		livro.setAutor("Autor");
		livro.setEdicao("Edição");
		livro.setEmprestado(false);
		livro.setLivroDeletado(false);
	}
	
	public void queEuTenhoUmUsuario() {
		usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Usuario");
		usuario.setEmail("usuario@email.com");
		usuario.setUsuarioAtivo(true);
	}
	
	public void queEuTenhoUmEmprestimo() {
		queEuTenhoUmaDataDeEmprestimo();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		
		emprestimo = new Emprestimo();
		livro.setEmprestado(true);
		emprestimo.setId(1L);
		emprestimo.setUsuario(usuario);
		emprestimo.setLivro(livro);
		emprestimo.setDataDeEmprestimo(dataDeEmprestimo);
	}
	
	public void queEuTenhoUmaDataDeEmprestimo() {
		dataDeEmprestimo = new Date();
	}
	
	public void queEuTenhoUmaDataDeDevolucao() {
		dataDeDevolucao = new Date();
	}
	
	public void queEuTenhoUmaListaDeEmprestimos() {
		queEuTenhoUmEmprestimo();
		emprestimos = new ArrayList<Emprestimo>();
		emprestimos.add(emprestimo);
	}
}