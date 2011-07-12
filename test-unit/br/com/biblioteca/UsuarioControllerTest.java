package br.com.biblioteca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.UsuarioController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Administrador;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class UsuarioControllerTest{

	private static final long CODIGO_USUARIO = 1L;
	private Result result;
	private UsuarioController usuarioController;
	
	@Mock 
	private AdminSession adminSession;
	@Mock 
	private UsuarioDAO usuarioDAO;
	@Mock 
	private EmprestimoDAO emprestimoDAO;
	
	private Usuario usuario;
	private Livro livro;
	private Emprestimo emprestimo;
	private ArrayList<Emprestimo> emprestimos;
	private ArrayList<Long> usuarios;
	private Administrador administrador;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.usuarioController = new UsuarioController(result, usuarioDAO, adminSession, emprestimoDAO);
	}
	
	@Test
	public void testListaUsuarios() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(usuarioDAO.pesquisa("nome")).thenReturn(new ArrayList<Usuario>());

//		entao
		usuarioController.index("nome");
		assertTrue(result.included().containsKey("usuarios"));
	}
	
	@Test
	public void testListaUsuariosJson() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(usuarioDAO.pesquisa("nome")).thenReturn(new ArrayList<Usuario>());
		
//		então
		usuarioController.list("nome");
		assertTrue(result.included().containsKey("usuarios"));
	}
	
	@Test
	public void testNomeNuloAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome(null);
		
//		quando
		doThrow(new RuntimeException("Nome nulo")).when(usuarioDAO).adiciona(usuario);
		usuarioController.novo(usuario);
		
//		entao
		assertEquals("Nome nulo", result.included().get("message"));
	}
	
	@Test
	public void testEmailNuloAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setEmail(null);
		
//		quando
		doThrow(new RuntimeException("Email nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Email nulo", result.included().get("message"));
	}
	
	@Test
	public void testNomeVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome("");

//		quando
		doThrow(new RuntimeException("Nome nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Nome nulo", result.included().get("message"));
	}
	
	@Test
	public void testEmailVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setEmail("");
		
//		quando
		doThrow(new RuntimeException("Email nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Email nulo", result.included().get("message"));
	}
	
	@Test
	public void testAdicionaUsuarioQueJaExiste() {
//		dado 
		queEuTenhoUmUsuario();
		
//		quando
		doThrow(new RuntimeException("\"" + usuario.getNome() + "\" já está cadastrado")).when(usuarioDAO).adiciona(usuario);
		usuarioController.novo(usuario);
		
//		entao
		assertEquals("\"" + usuario.getNome() + "\" já está cadastrado",result.included().get("message"));
	}
	
	@Test
	public void testAdicionaUsuarioQueNaoExiste() {
//		dado 
		queEuTenhoUmUsuario();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
//		quando
		when(usuarioDAO.pesquisa(usuario.getNome())).thenReturn(usuarios);
		usuarioController.novo(usuario);
		
//		entao
		assertEquals("\""+ usuario.getNome() + "\" adicionado com sucesso!", result.included().get("message"));
	}
	
	@Test
	public void testIdNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setId(null);
		
//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Erro ao atualizar usuário", result.included().get("message"));
	}
	
	@Test
	public void testNomeNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome(null);

//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Erro ao atualizar usuário", result.included().get("message"));
	}
	
	@Test
	public void testEmailNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setEmail(null);
		
//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Erro ao atualizar usuário", result.included().get("message"));
	}
	
	@Test
	public void testNomeVazioAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome("");
		
//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Erro ao atualizar usuário", result.included().get("message"));
	}
	
	@Test
	public void testEmailVazioAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setEmail("");
		
//		quando
		
		
//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Erro ao atualizar usuário", result.included().get("message"));
	}
	
	@Test
	public void testDeleteUsuarioQuandoEsteTemEmprestimo() {
//		 dado
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaListaDeEmprestimos();
		queEuTenhoUmaListaDeCodigosDeUsuario();
		
//		 quando
		when(usuarioDAO.pesquisarUsuarioPorId(CODIGO_USUARIO)).thenReturn(usuario);
		when(emprestimoDAO.procuraPorIdUsuario(CODIGO_USUARIO)).thenReturn(emprestimos);
		
//		 entao
		usuarioController.delete(usuarios);
		assertEquals("\"" + usuario.getNome() + "\" com empréstimo ativo", result.included().get("message"));
		
	}
	
	@Test 
	public void testDeleteUsuarioQuandoEsteNaoTemEmprestimo() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmaListaDeEmprestimoVazia();
		queEuTenhoUmaListaDeCodigosDeUsuario();
		
//		quando
		when(usuarioDAO.pesquisarUsuarioPorId(CODIGO_USUARIO)).thenReturn(usuario);
		when(emprestimoDAO.procuraPorIdUsuario(CODIGO_USUARIO)).thenReturn(emprestimos);
		
//		entao
		usuarioController.delete(usuarios);
		assertEquals("Usuario(s) deletado(s) com sucesso", result.included().get("message"));
	}
	public void queEuTenhoUmaListaDeCodigosDeUsuario() {
		usuarios = new ArrayList<Long>();
		usuarios.add(1L);
	}

	public void queEuTenhoUmaListaDeEmprestimos() {
		emprestimos = new ArrayList<Emprestimo>();
		emprestimos.add(emprestimo);
	}
	
	public void queEuTenhoUmaListaDeEmprestimoVazia() {
		emprestimos = new ArrayList<Emprestimo>();
	}

	public void queEuTenhoUmEmprestimo() {
		emprestimo = criaEmprestimo();
		emprestimo.setUsuario(usuario);
		emprestimo.setLivro(livro);
	}
	
	public Emprestimo criaEmprestimo() {
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setDataDeEmprestimo(new Date());
		emprestimo.setUsuario(usuario);
		emprestimo.setLivro(livro);
		return emprestimo;
	}
	
	public void queEuTenhoUmAdministrador() {
		administrador = new Administrador();
		administrador.setNome("wagner");
		administrador.setSenha("123");
	}
	
	public void queEuTenhoUmLivro() {
		livro = new Livro();
		livro.setId(1L);
		livro.setNome("Nome");
		livro.setAutor("Autor");
		livro.setEdicao("Edição");
	}
	
	public void queEuTenhoUmUsuario(){
		usuario = new Usuario();
		usuario.setId(CODIGO_USUARIO);
		usuario.setNome("usuario");
		usuario.setEmail("usuario@gmail.com");
		usuario.setUsuarioAtivo(true);
	}
}