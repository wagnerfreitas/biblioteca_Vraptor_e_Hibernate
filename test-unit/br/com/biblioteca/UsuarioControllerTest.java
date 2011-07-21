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
import br.com.biblioteca.dao.AuditoriaDAO;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Administrador;
import br.com.biblioteca.entidades.Auditoria;
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
	@Mock
	private AuditoriaDAO auditoriaDAO;
	
	private Usuario usuario;
	private Livro livro;
	private Emprestimo emprestimo;
	private ArrayList<Emprestimo> emprestimos;
	private ArrayList<Long> usuarios;
	private Administrador administrador;
	private Auditoria auditoria;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.usuarioController = new UsuarioController(result, usuarioDAO, adminSession, emprestimoDAO, auditoriaDAO);
	}
	
	@Test
	public void listaUsuarios() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(usuarioDAO.pesquisa("nome")).thenReturn(new ArrayList<Usuario>());
		usuarioController.index("nome");

//		então
		assertTrue(result.included().containsKey("usuarios"));
	}
	
	@Test
	public void erroAoListarUsuario() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Erro ao pesquisar usuário")).when(usuarioDAO).pesquisa("nome");
		usuarioController.index("nome");
		
//		então
		assertEquals("Erro ao pesquisar usuário", result.included().get("error"));
	}
	
	@Test
	public void listaUsuariosJson() {
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
	public void erroAoListarUsuariosJson() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Erro ao pesquisar usuário")).when(usuarioDAO).pesquisa("nome");
		usuarioController.list("nome");
		
//		então
		assertEquals("Erro ao pesquisar usuário", result.included().get("error"));
	}
	
	@Test
	public void nomeNuloAoAdicionarUsuario() {
//		dado
		queEuTenhoUmAdministrador();
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		usuario.setNome(null);
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Nome nulo")).when(usuarioDAO).adiciona(usuario);
		usuarioController.novo(usuario);
		
//		entao
		assertEquals("Nome nulo", result.included().get("message"));
	}
	
	@Test
	public void emailNuloAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmaAuditoria();
		queEuTenhoUmAdministrador();
		usuario.setEmail(null);
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Email nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Email nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmaAuditoria();
		queEuTenhoUmAdministrador();
		usuario.setNome("");

//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Nome nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Nome nulo", result.included().get("message"));
	}
	
	@Test
	public void emailVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmAdministrador();
		queEuTenhoUmUsuario();
		usuario.setEmail("");
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Email nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(usuario);
		assertEquals("Email nulo", result.included().get("message"));
	}
	
	@Test
	public void adicionaUsuarioQueJaExiste() {
//		dado 
		queEuTenhoUmaAuditoria();
		queEuTenhoUmAdministrador();
		queEuTenhoUmUsuario();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("\"" + usuario.getNome() + "\" já está cadastrado")).when(usuarioDAO).adiciona(usuario);
		usuarioController.novo(usuario);
		
//		entao
		assertEquals("\"" + usuario.getNome() + "\" já está cadastrado",result.included().get("message"));
	}
	
	@Test
	public void adicionaUsuarioQueNaoExiste() {
//		dado 
		queEuTenhoUmaAuditoria();
		queEuTenhoUmAdministrador();
		queEuTenhoUmUsuario();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(usuarioDAO.pesquisa(usuario.getNome())).thenReturn(usuarios);
		usuarioController.novo(usuario);
		
//		entao
		assertEquals("\""+ usuario.getNome() + "\" adicionado com sucesso!", result.included().get("message"));
	}
	
	@Test
	public void idNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setId(null);
		
//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Id do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome(null);

//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Nome do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void emailNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setEmail(null);
		
//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Email do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeVazioAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome("");
		
//		entao
		usuarioController.atualiza(usuario);
		assertEquals("Nome do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void emailVazioAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setEmail("");

//		quando
		usuarioController.atualiza(usuario);
		
//		entao
		assertEquals("Email do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void atualizarUsuario() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmAdministrador();
		queEuTenhoUmUsuario();
		usuario.setNome("Nome Atualizado");
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		usuarioController.atualiza(usuario);
		
//		então
		assertEquals("\"" + usuario.getNome() + "\" atualizado com sucesso", result.included().get("message"));
	}
	
	@Test
	public void erroAoAtualizarUsuario() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmAdministrador();
		queEuTenhoUmUsuario();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Erro/Usuario")).when(usuarioDAO).atualiza(usuario);
		usuarioController.atualiza(usuario);
		
//		então
		assertEquals("Erro/Usuario", result.included().get("message"));
	}
	
	@Test
	public void deleteUsuarioQuandoEsteTemEmprestimo() {
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
		assertEquals("\"" + usuario.getNome() + "\" com empréstimo ativo"+"\n", result.included().get("message"));
		
	}
	
	@Test 
	public void deleteUsuarioQuandoEsteNaoTemEmprestimo() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmaListaDeEmprestimoVazia();
		queEuTenhoUmaListaDeCodigosDeUsuario();
		queEuTenhoUmaAuditoria();
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		when(usuarioDAO.pesquisarUsuarioPorId(CODIGO_USUARIO)).thenReturn(usuario);
		when(emprestimoDAO.procuraPorIdUsuario(CODIGO_USUARIO)).thenReturn(emprestimos);
		
//		entao
		usuarioController.delete(usuarios);
		assertEquals("Usuario(s) deletado(s) com sucesso" + "\n", result.included().get("message"));
	}
	public void queEuTenhoUmaListaDeCodigosDeUsuario() {
		usuarios = new ArrayList<Long>();
		usuarios.add(1L);
	}
	
	public void queEuTenhoUmaAuditoria() {
		auditoria = new Auditoria();
		auditoria.setAdministrador("Admin");
		auditoria.setAcao("acao");
		auditoria.setDate(new Date());
		auditoria.setEntidadeLivro("livro");
		auditoria.setEntidadeUsuario("usuarios");
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