package br.com.biblioteca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.UsuarioController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entitades.Administrador;
import br.com.biblioteca.entitades.Emprestimo;
import br.com.biblioteca.entitades.Livro;
import br.com.biblioteca.entitades.Usuario;
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
	
	public UsuarioControllerTest() {
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
		queEuTenhoUmAdministrador();
		
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(usuarioDAO.pesquisa("nome")).thenReturn(new ArrayList<Usuario>());
		
		usuarioController.index("nome");
		assertTrue(result.included().containsKey("usuarios"));
	}
	
	@Test
	public void testNomeNuloAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome(null);
		usuario.setEmail("email");
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Nome ou email nulos", result.included().get("message"));
	}
	
	@Test
	public void testEmailNuloAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome("Nome");
		usuario.setEmail(null);
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Nome ou email nulos", result.included().get("message"));
	}
	
	@Test
	public void testNomeVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome("");
		usuario.setEmail("Email");
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Nome ou email nulos", result.included().get("message"));
	}
	
	@Test
	public void testEmailVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome("Nome");
		usuario.setEmail("");
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Nome ou email nulos", result.included().get("message"));
	}
	
	@Test
	public void testAdicionaUsuarioQueJaExiste() {
//		dado 
		queEuTenhoUmUsuario();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario);
		
//		quando
		when(usuarioDAO.pesquisa(usuario.getNome())).thenReturn(usuarios);
		usuarioController.novo(usuario);
		
//		entao
		assertEquals("\"Wagner\" já está cadastrado",result.included().get("message"));
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
		assertEquals("\"Wagner\" adicionado com sucesso", result.included().get("message"));
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
		usuario.setNome("Wagner");
		usuario.setEmail("wagner@gmail.com");
		usuario.setUsuarioAtivo(true);
	}
}