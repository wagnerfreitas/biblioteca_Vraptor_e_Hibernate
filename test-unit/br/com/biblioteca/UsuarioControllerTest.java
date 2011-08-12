package br.com.biblioteca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.UsuarioController;
import br.com.biblioteca.controller.helper.AuditoriaHelper;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.GrupoDePerfilDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Acao;
import br.com.biblioteca.entidades.Auditoria;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.GrupoDePerfil;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class UsuarioControllerTest{
	
	@Mock
	private GrupoDePerfilDAO grupoDePerfilDAO;
	@Mock 
	private UsuarioSession usuarioSession;
	@Mock 
	private UsuarioDAO usuarioDAO;
	@Mock 
	private EmprestimoDAO emprestimoDAO;
	@Mock
	private AuditoriaHelper auditoriaHelper;
	
	private static final long CODIGO_USUARIO = 1L;
	private Result result;
	private UsuarioController usuarioController;
	private Usuario usuario;
	private Livro livro;
	private Emprestimo emprestimo;
	private ArrayList<Emprestimo> emprestimos;
	private ArrayList<Long> usuarios;
	private Auditoria auditoria;
	private GrupoDePerfil grupoDeAcesso;
	private Acao acao;
	private ArrayList<Acao> grupoDeAcao;
	private ArrayList<GrupoDePerfil> grupos;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.usuarioController = new UsuarioController(result, usuarioDAO, usuarioSession, emprestimoDAO, auditoriaHelper, grupoDePerfilDAO);
	}
	
	@Test
	public void listaUsuarios() {
//		dado
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(usuarioDAO.pesquisa("nome")).thenReturn(new ArrayList<Usuario>());
		usuarioController.index("nome");

//		então
		assertTrue(result.included().containsKey("usuarios"));
	}
	
	@Test
	public void erroAoListarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Erro ao pesquisar usuário")).when(usuarioDAO).pesquisa("nome");
		usuarioController.index("nome");
		
//		então
		assertEquals("Erro ao pesquisar usuário", result.included().get("error"));
	}
	
	@Test
	public void listaUsuariosJson() {
//		dado
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(usuarioDAO.pesquisa("nome")).thenReturn(new ArrayList<Usuario>());
		
//		então
		usuarioController.list("nome");
		assertTrue(result.included().containsKey("usuarios"));
	}
	
	@Test 
	public void erroAoListarUsuariosJson() {
//		dado
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Erro ao pesquisar usuário")).when(usuarioDAO).pesquisa("nome");
		usuarioController.list("nome");
	}
	
	@Test
	public void erroAoBuscarOGrupoDoUsuario() {
//		dado
		queEuTenhoUmGrupoDePerfil();
		
//		quando
		doThrow(new RuntimeException("Erro ao pesquisar")).when(grupoDePerfilDAO).grupos();
		usuarioController.novo();
	}
	
	@Test
	public void buscaDoGrupoComSucesso() {
//		dado
		queEuTenhoUmGrupoDePerfil();
		queEuTenhoUmaListaDeGruposDeAcesso();
//		quando
		when(grupoDePerfilDAO.grupos()).thenReturn(grupos);
		usuarioController.novo();
		
//		entao
		assertTrue(result.included().containsKey("grupos"));
	}
	
	
	@Test
	public void nomeNuloAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmGrupoDePerfil();
		usuario.setNome(null);
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(grupoDePerfilDAO.pesquisaPorId(1L)).thenReturn(grupoDeAcesso);
		doThrow(new RuntimeException("Nome nulo")).when(usuarioDAO).adiciona(usuario);
		usuarioController.novo(1L,usuario);
		
//		entao
		assertEquals("Nome nulo", result.included().get("message"));
	}
	
	@Test
	public void emailNuloAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmGrupoDePerfil();
		usuario.setEmail(null);
		
//		quando
		when(grupoDePerfilDAO.pesquisaPorId(1L)).thenReturn(grupoDeAcesso);
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Email nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(1L, usuario);
		assertEquals("Email nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmGrupoDePerfil();
		usuario.setNome("");

//		quando
		when(grupoDePerfilDAO.pesquisaPorId(1L)).thenReturn(grupoDeAcesso);
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Nome nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(1L, usuario);
		assertEquals("Nome nulo", result.included().get("message"));
	}
	
	@Test
	public void emailVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmUsuario();
		queEuTenhoUmGrupoDePerfil();
		usuario.setEmail("");
		
//		quando
		when(grupoDePerfilDAO.pesquisaPorId(1L)).thenReturn(grupoDeAcesso);
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Email nulo")).when(usuarioDAO).adiciona(usuario);
		
//		entao
		usuarioController.novo(1L, usuario);
		assertEquals("Email nulo", result.included().get("message"));
	}
	
	@Test
	public void adicionaUsuarioQueJaExiste() {
//		dado 
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmGrupoDePerfil();
		queEuTenhoUmUsuario();
		
//		quando
		when(grupoDePerfilDAO.pesquisaPorId(1L)).thenReturn(grupoDeAcesso);
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("\"" + usuario.getNome() + "\" já está cadastrado")).when(usuarioDAO).adiciona(usuario);
		usuarioController.novo(1L, usuario);
		
//		entao
		assertEquals("\"" + usuario.getNome() + "\" já está cadastrado",result.included().get("message"));
	}
	
	@Test
	public void adicionaUsuarioQueNaoExiste() {
//		dado 
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmUsuario();
		queEuTenhoUmGrupoDePerfil();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
//		quando
		when(grupoDePerfilDAO.pesquisaPorId(1L)).thenReturn(grupoDeAcesso);
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(usuarioDAO.pesquisa(usuario.getNome())).thenReturn(usuarios);
		usuarioController.novo(1L, usuario);
		
//		entao
		assertEquals("\""+ usuario.getNome() + "\" adicionado com sucesso!", result.included().get("message"));
	}
	
	@Test
	public void idNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setId(null);
		
//		entao
		usuarioController.atualiza(usuario.getId(), usuario.getNome(), usuario.getEmail());
		assertEquals("Id do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome(null);

//		entao
		usuarioController.atualiza(usuario.getId(), usuario.getNome(), usuario.getEmail());
		assertEquals("Nome do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void emailNuloAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setEmail(null);
		
//		entao
		usuarioController.atualiza(usuario.getId(), usuario.getNome(), usuario.getEmail());
		assertEquals("Email do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeVazioAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setNome("");
		
//		entao
		usuarioController.atualiza(usuario.getId(), usuario.getNome(), usuario.getEmail());
		assertEquals("Nome do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void emailVazioAoAtualizarUsuario() {
//		dado
		queEuTenhoUmUsuario();
		usuario.setEmail("");

//		quando
		usuarioController.atualiza(usuario.getId(), usuario.getNome(), usuario.getEmail());
		
//		entao
		assertEquals("Email do usuário nulo", result.included().get("message"));
	}
	
	@Test
	public void atualizarUsuario() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		usuario.setNome("Nome Atualizado");
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(usuarioDAO.pesquisarUsuarioPorId(usuario.getId())).thenReturn(usuario);
		usuarioController.atualiza(usuario.getId(), usuario.getNome(), usuario.getEmail());
		
//		então
		assertEquals("\"" + usuario.getNome() + "\" atualizado com sucesso", result.included().get("message"));
	}
	
	@Test
	public void erroAoAtualizarUsuario() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(usuarioDAO.pesquisarUsuarioPorId(usuario.getId())).thenReturn(usuario);
		doThrow(new RuntimeException("Erro/Usuario")).when(usuarioDAO).atualiza(usuario);
		usuarioController.atualiza(usuario.getId(), usuario.getNome(), usuario.getEmail());
		
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
		usuarioController.delete(usuarios);
		
//		 entao
		assertEquals("\"" + usuario.getNome() + "\" com empréstimo ativo\n", result.included().get("message"));
		
	}
	
	@Test 
	public void deleteUsuarioQuandoEsteNaoTemEmprestimo() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmaListaDeEmprestimoVazia();
		queEuTenhoUmaListaDeCodigosDeUsuario();
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(usuarioDAO.pesquisarUsuarioPorId(CODIGO_USUARIO)).thenReturn(usuario);
		when(emprestimoDAO.procuraPorIdUsuario(CODIGO_USUARIO)).thenReturn(emprestimos);
		usuarioController.delete(usuarios);
		
//		entao
		assertEquals("Usuário(s) deletado(s) com sucesso", result.included().get("message"));
	}
	public void queEuTenhoUmaListaDeCodigosDeUsuario() {
		usuarios = new ArrayList<Long>();
		usuarios.add(1L);
	}
	
	public void queEuTenhoUmaListaDeGruposDeAcesso() {
		queEuTenhoUmGrupoDePerfil();
		grupos = new ArrayList<GrupoDePerfil>();
		grupos.add(grupoDeAcesso);
	}
	
	public void queEuTenhoUmListaDeAcoes() {
		queEuTenhoUmaAcao();
		grupoDeAcao = new ArrayList<Acao>();
		grupoDeAcao.add(acao);
	}
	
	public void queEuTenhoUmGrupoDePerfil() {
		queEuTenhoUmListaDeAcoes();
		grupoDeAcesso = new GrupoDePerfil();
		grupoDeAcesso.setAcoes(grupoDeAcao);
		grupoDeAcesso.setNome("Grupo");
		grupoDeAcesso.setId(1L);
	}
	
	public void queEuTenhoUmaAcao() {
		acao = new Acao();
		acao.setId(1L);
		acao.setNome("Acao");
	}
	
	public void queEuTenhoUmaAuditoria() {
		auditoria = new Auditoria();
		auditoria.setUsuarioLogado("Admin");
		auditoria.setAcao("acao");
		auditoria.setData(new Date());
		auditoria.setEntidade("Entidade");
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
		usuario.setSenha("123456");
		usuario.setEmail("usuario@gmail.com");
		usuario.setAtivo(true);
		usuario.setGrupoDePerfil(grupoDeAcesso);
	}
}