package br.com.biblioteca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
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
import br.com.biblioteca.entidades.Administrador;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class LivroControllerTest {

	private static final long CODIGO_LIVRO = 1L;
	private Result result;
	private LivroController livroController;
	private Administrador administrador;
	private Livro livro;
	private Usuario usuario;
	private Emprestimo emprestimo;
	private Date dataDeEmprestimo;
	private Date dataDeDevolucao;
	private ArrayList<Long> livros;
	
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
	public void erroAoListarLivros() {
//		dado
		queEuTenhoUmAdministrador();
		
//		quando
		when(adminSession.getAdministrador()).thenReturn(administrador);
		doThrow(new RuntimeException("Erro ao pesquisar livro")).when(livroDAO).pesquisa("Livro");
		livroController.index("Livro");
		
//		então
		assertEquals("Erro ao pesquisar livro", result.included().get("error"));
	}
	
	@Test
	public void listaLivros() {
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
	public void nomeDoLivroNuloAoAdicionarLivro() {
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
	public void nomeDoLivroVazioAoAdicionarLivro() {
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
	public void autorNuloAoAdicionarLivro() {
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
	public void autorVazioAoAdicionarLivro() {
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
	public void edicaoNulaAoAdicionarLivro() {
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
	public void edicaoVaziaAoAdicionarLivro() {
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
	public void adicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		
//		quando
		livroController.novo(livro);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" adicionado com sucesso", result.included().get("message"));
	}
	
	@Test
	public void idDoUsuarioNuloAoEmprestarLivro() {
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
	public void idDoLivroNuloAoEmprestarLivro() {
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
	public void dataDeEmprestimoNuloAoAdicionarLivro() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		
//		quando
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Data nula", result.included().get("message"));
	}
	
	@Test
	public void pesquisarUsuarioAoEmprestarLivro() {
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
	public void pesquisarLivroAoEmprestarLivro() {
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
	public void emprestarLivro() {
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
	
	@Test
	public void nomeDoLivroNuloAoAtualizarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setNome(null);
		
//		quando
		livroController.atualiza(livro);
		
//		então
		assertEquals("Nome do livro nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmLivro();
		livro.setNome("");
		
//		quando
		livroController.atualiza(livro);
		
//		então
		assertEquals("Nome do livro nulo", result.included().get("message"));
	}
	
	@Test
	public void autorNuloAoAtualizaLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setAutor(null);
		
//		quando
		livroController.atualiza(livro);
		
//		então
		assertEquals("Autor nulo", result.included().get("message"));
	}
	
	@Test
	public void autorVazioAoAtulizarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setAutor("");
		
//		quando
		livroController.atualiza(livro);
		
//		então
		assertEquals("Autor nulo", result.included().get("message"));
	}
	
	@Test
	public void edicaoNulaAoAtualizarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setEdicao(null);
		
//		quando
		livroController.atualiza(livro);
		
//		então
		assertEquals("Edição nula", result.included().get("message"));
	}
	
	@Test
	public void edicaoVaziaAoAtualizarLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setEdicao("");
		
//		quando
		livroController.atualiza(livro);
		
//		então
		assertEquals("Edição nula", result.included().get("message"));
	}
	
	@Test
	public void erroAoAtualizarLivro() {
//		dado
		queEuTenhoUmLivro();
		
//		quando
		doThrow(new RuntimeException("Erro/Livro")).when(livroDAO).atualiza(livro);
		livroController.atualiza(livro);
		
//		então
		assertEquals("Erro/Livro", result.included().get("message"));
	}
	
	@Test
	public void atulizaLivro() {
//		dado
		queEuTenhoUmLivro();
		livro.setNome("Livro Atualizado");
		
//		quando
		livroController.atualiza(livro);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" atualizado com sucesso", result.included().get("message"));
	}
	
	@Test
	public void removerLivroQueEstarEmprestado() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaListaDeCodigosDeLivros();
		
//		quando
		when(livroDAO.pesquisarLivroPorId(CODIGO_LIVRO)).thenReturn(livro);
		livroController.remove(livros);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" está emprestado", result.included().get("message"));
	}
	
	@Test
	public void removerLivroQueNaoEstarEmprestado() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmaListaDeCodigosDeLivros();
		
//		quando
		when(livroDAO.pesquisarLivroPorId(CODIGO_LIVRO)).thenReturn(livro);
		livroController.remove(livros);
		
//		então
		assertEquals("Livro(s) deletado(s) com sucesso", result.included().get("message"));		
	}
	
	@Test
	public void erroAoPesquisarLivroAoTentarRemover() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmaListaDeCodigosDeLivros();
		
//		quando
		doThrow(new RuntimeException("Erro ao pesquisar")).when(livroDAO).pesquisarLivroPorId(CODIGO_LIVRO);
		livroController.remove(livros);
		
//		então
		assertEquals("Erro ao pesquisar", result.included().get("message"));
	}
	
	@Test
	public void erroAoTentarAtualizarLivroAoTentarRemover() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmaListaDeCodigosDeLivros();
		
//		quando
		when(livroDAO.pesquisarLivroPorId(CODIGO_LIVRO)).thenReturn(livro);
		doThrow(new RuntimeException("Erro/Livro")).when(livroDAO).atualiza(livro);
		livroController.remove(livros);
		
//		então
		assertEquals("Erro/Livro", result.included().get("message"));
	}
	
	@Test
	public void devolverLivroComIdNulo() {
//		dado
		queEuTenhoUmLivro();
		livro.setId(null);
		queEuTenhoUmaDataDeDevolucao();
		
//		quando
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Id no livro nulo", result.included().get("message"));
	}
	
	@Test
	public void devolverLivroComDataDeDevolucaoNula() {
//		dado
		queEuTenhoUmLivro();
		
//		quando
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Date de devolução nula", result.included().get("message"));
	}	
	
	@Test
	public void erroPesquisarLivroAoTentarDevolver() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		
//		quando
		doThrow(new RuntimeException("Erro ao pesquisar livro emprestado")).when(emprestimoDAO).procuraPorIdLivro(livro.getId());
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao pesquisar livro emprestado", result.included().get("message"));
	}
	
	@Test
	public void erroAtualizarEmprestimoAoTentarDevolverLivro() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		queEuTenhoUmaDataDeEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		queEuTenhoUmEmprestimo();
		emprestimo.setDataDeDevolucao(dataDeDevolucao);
		
//		quando
		when(emprestimoDAO.procuraPorIdLivro(livro.getId())).thenReturn(emprestimo);
		doThrow(new RuntimeException("Erro ao devolver livro")).when(emprestimoDAO).atualiza(emprestimo);
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao devolver livro", result.included().get("message"));
	}
	
	@Test
	public void devolverLivro() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		queEuTenhoUmaDataDeEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		queEuTenhoUmEmprestimo();
		emprestimo.setDataDeDevolucao(dataDeDevolucao);

//		quando
		when(emprestimoDAO.procuraPorIdLivro(livro.getId())).thenReturn(emprestimo);
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" devolvido com sucesso", result.included().get("message"));
	}
	
	@Test
	public void erroAtualizarLivroAoTentarDevolver() {
//		dado
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		queEuTenhoUmaDataDeDevolucao();
		queEuTenhoUmEmprestimo();
		
//		quando
		when(emprestimoDAO.procuraPorIdLivro(livro.getId())).thenReturn(emprestimo);
		doThrow(new RuntimeException("Erro/Livro")).when(livroDAO).atualiza(livro);
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro/Livro", result.included().get("message"));
	}
	
	public void queEuTenhoUmaListaDeCodigosDeLivros() {
		livros = new ArrayList<Long>();
		livros.add(CODIGO_LIVRO);
	}
		
	public void queEuTenhoUmEmprestimo() {
		emprestimo = new Emprestimo();
		emprestimo.setId(1L);
		emprestimo.setUsuario(usuario);
		livro.setEmprestado(true);
		emprestimo.setLivro(livro);
		emprestimo.setDataDeEmprestimo(dataDeEmprestimo);
	}
	
	public void queEuTenhoUmaDataDeEmprestimo() {
		dataDeEmprestimo = new Date();
	}
	
	public void queEuTenhoUmaDataDeDevolucao() {
		dataDeDevolucao = new Date();
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