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
import br.com.biblioteca.controller.helper.AuditoriaHelper;
import br.com.biblioteca.controller.helper.EmprestimoHelper;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
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

public class LivroControllerTest {

	@Mock
	private LivroDAO livroDAO;
	@Mock
	private EmprestimoDAO emprestimoDAO;
	@Mock
	private UsuarioDAO usuarioDAO;
	@Mock
	private UsuarioSession usuarioSession;
	@Mock
	private AuditoriaHelper auditoriaHelper;
	@Mock
	private EmprestimoHelper emprestimoHelper;

	private static final long CODIGO_LIVRO = 1L;
	private Result result;
	private LivroController livroController;
	private Livro livro;
	private Usuario usuario;
	private Emprestimo emprestimo;
	private Date dataDeEmprestimo;
	private Date dataDeDevolucao;
	private ArrayList<Long> codigosLivros;
	private Auditoria auditoria;
	private ArrayList<Livro> livros;
	private GrupoDePerfil grupoDePerfil;
	private Acao acao;
	private ArrayList<Acao> acoes;
	
	public LivroControllerTest() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.livroController = new LivroController(result, livroDAO, emprestimoHelper, usuarioSession, auditoriaHelper);
	}
	
	@Test
	public void erroAoListarLivros() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Erro ao pesquisar livro")).when(livroDAO).pesquisa("Livro");
		livroController.index("Livro");
		
//		então
		assertEquals("Erro ao pesquisar livro", result.included().get("error"));
	}
	
	@Test
	public void listaLivros() {
//		dado
		queEuTenhoUmUsuario();
		queEuTenhoUmUsuario();
		queEuTenhoUmaListaDeLivros();

//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(livroDAO.pesquisa(livro.getNome())).thenReturn(livros);
		livroController.index(livro.getNome());
		
//		entao
		assertTrue(result.included().containsKey("livros"));
	}
	
	
	@Test
	public void nomeDoLivroNuloAoAdicionarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setNome(null);
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Nome do livro nulo")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Nome do livro nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeDoLivroVazioAoAdicionarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setNome("");
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Nome do livro nulo")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Nome do livro nulo", result.included().get("message"));
	}
	
	@Test
	public void autorNuloAoAdicionarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setAutor(null);
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Nome do autor nulo")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Nome do autor nulo", result.included().get("message"));
	}
	
	@Test
	public void autorVazioAoAdicionarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setAutor("");
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Nome do autor nulo")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Nome do autor nulo", result.included().get("message"));
	}
	
	@Test
	public void edicaoNulaAoAdicionarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setEdicao(null);
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Edição nula")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Edição nula", result.included().get("message"));
	}
	
	@Test
	public void edicaoVaziaAoAdicionarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setEdicao("");
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Edição nula")).when(livroDAO).adiciona(livro);
		livroController.novo(livro);
		
//		então
		assertEquals("Edição nula", result.included().get("message"));
	}
	
	@Test
	public void adicionarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		livroController.novo(livro);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" adicionado com sucesso", result.included().get("message"));
	}
	
	@Test
	public void idDoUsuarioNuloAoEmprestarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		usuario.setId(null);
		queEuTenhoUmaDataDeEmprestimo();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Erro ao emprestar livro", result.included().get("message"));
	}
	
	@Test
	public void idDoLivroNuloAoEmprestarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setId(null);
		queEuTenhoUmUsuario();
		queEuTenhoUmaDataDeEmprestimo();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Erro ao emprestar livro", result.included().get("message"));
	}
	
	@Test
	public void dataDeEmprestimoNuloAoAdicionarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Data nula", result.included().get("message"));
	}
	
	@Test
	public void pesquisarUsuarioAoEmprestarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaDataDeEmprestimo();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Erro na pesquisa")).when(usuarioDAO).pesquisarUsuarioPorId(usuario.getId());
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Erro ao emprestar livro", result.included().get("message"));
	}
	
	@Test
	public void pesquisarLivroAoEmprestarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaDataDeEmprestimo();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Erro ao pesquisar")).when(livroDAO).pesquisarLivroPorId(livro.getId());
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("Erro ao emprestar livro", result.included().get("message"));
	}
	
	@Test
	public void emprestarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaDataDeEmprestimo();
		queEuTenhoUmEmprestimo();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(emprestimoHelper.novoEmprestimo(usuario.getId(), livro.getId(), dataDeEmprestimo)).thenReturn(true);
		livroController.emprestar(usuario.getId(), livro.getId(), dataDeEmprestimo);
		
//		então
		assertEquals("\"Livro\" emprestado com sucesso", result.included().get("message"));
	}
	
	@Test
	public void nomeDoLivroNuloAoAtualizarLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setNome(null);
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		livroController.atualiza(livro);
		
//		então
		assertEquals("Nome do livro nulo", result.included().get("message"));
	}
	
	@Test
	public void nomeVazioAoAdicionarUsuario() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setNome("");
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
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
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Erro/Livro")).when(livroDAO).atualiza(livro);
		livroController.atualiza(livro);
		
//		então
		assertEquals("Erro/Livro", result.included().get("message"));
	}
	
	@Test
	public void atulizaLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setNome("Livro Atualizado");
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		livroController.atualiza(livro);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" atualizado com sucesso", result.included().get("message"));
	}
	
	@Test
	public void removerLivroQueEstarEmprestado() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaListaDeCodigosDeLivros();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(livroDAO.pesquisarLivroPorId(CODIGO_LIVRO)).thenReturn(livro);
		livroController.remove(codigosLivros);
		
//		então
		assertEquals("\"" + livro.getNome() + "\" está emprestado", result.included().get("message"));
	}
	
	@Test
	public void removerLivroQueNaoEstarEmprestado() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaListaDeCodigosDeLivros();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(livroDAO.pesquisarLivroPorId(CODIGO_LIVRO)).thenReturn(livro);
		livroController.remove(codigosLivros);
		
//		então
		assertEquals("Livro(s) deletado(s) com sucesso", result.included().get("message"));		
	}
	
	@Test
	public void erroAoPesquisarLivroAoTentarRemover() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaListaDeCodigosDeLivros();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Erro ao pesquisar")).when(livroDAO).pesquisarLivroPorId(CODIGO_LIVRO);
		livroController.remove(codigosLivros);
		
//		então
		assertEquals("Erro ao pesquisar", result.included().get("message"));
	}
	
	@Test
	public void erroAoTentarAtualizarLivroAoTentarRemover() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmaListaDeCodigosDeLivros();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(livroDAO.pesquisarLivroPorId(CODIGO_LIVRO)).thenReturn(livro);
		doThrow(new RuntimeException("Erro/Livro")).when(livroDAO).atualiza(livro);
		livroController.remove(codigosLivros);
		
//		então
		assertEquals("Erro/Livro", result.included().get("message"));
	}
	
	@Test
	public void devolverLivroComIdNulo() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		livro.setId(null);
		queEuTenhoUmaDataDeDevolucao();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao deletar livro", result.included().get("message"));
	}
	
	@Test
	public void devolverLivroComDataDeDevolucaoNula() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao deletar livro", result.included().get("message"));
	}	
	
	@Test
	public void erroPesquisarLivroAoTentarDevolver() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		doThrow(new RuntimeException("Erro ao pesquisar livro emprestado")).when(emprestimoDAO).procuraPorIdLivro(livro.getId());
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao deletar livro", result.included().get("message"));
	}
	
	@Test
	public void erroAtualizarEmprestimoAoTentarDevolverLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		queEuTenhoUmaDataDeEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		queEuTenhoUmEmprestimo();
		emprestimo.setDataDeDevolucao(dataDeDevolucao);
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(emprestimoDAO.procuraPorIdLivro(livro.getId())).thenReturn(emprestimo);
		doThrow(new RuntimeException("Erro ao devolver livro")).when(emprestimoDAO).atualiza(emprestimo);
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao deletar livro", result.included().get("message"));
	}
	
	@Test
	public void devolverLivro() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		queEuTenhoUmaDataDeEmprestimo();
		queEuTenhoUmaDataDeDevolucao();
		queEuTenhoUmEmprestimo();
		emprestimo.setDataDeDevolucao(dataDeDevolucao);

//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(emprestimoDAO.procuraPorIdLivro(livro.getId())).thenReturn(emprestimo);
		when(emprestimoHelper.finalizarEmprestimo(livro.getId(), dataDeDevolucao)).thenReturn(true);
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("\"Livro\" devolvido com sucesso", result.included().get("message"));
	}
	
	@Test
	public void erroAtualizarLivroAoTentarDevolver() {
//		dado
		queEuTenhoUmaAuditoria();
		queEuTenhoUmUsuario();
		queEuTenhoUmLivro();
		queEuTenhoUmUsuario();
		queEuTenhoUmaDataDeDevolucao();
		queEuTenhoUmEmprestimo();
		
//		quando
		when(usuarioSession.getUsuario()).thenReturn(usuario);
		when(emprestimoDAO.procuraPorIdLivro(livro.getId())).thenReturn(emprestimo);
		doThrow(new RuntimeException("Erro/Livro")).when(livroDAO).atualiza(livro);
		livroController.devolve(livro.getId(), dataDeDevolucao);
		
//		então
		assertEquals("Erro ao deletar livro", result.included().get("message"));
	}
	
	public void queEuTenhoUmaListaDeLivros() {
		queEuTenhoUmLivro();
		livros = new ArrayList<Livro>();
		livros.add(livro);
	}
	
	public void queEuTenhoUmaListaDeCodigosDeLivros() {
		codigosLivros = new ArrayList<Long>();
		codigosLivros.add(CODIGO_LIVRO);
	}
	
	public void queEuTenhoUmaAuditoria() {
		auditoria = new Auditoria();
		auditoria.setUsuarioLogado("Admin");
		auditoria.setAcao("Ação");
		auditoria.setEntidade("Entidade");
		auditoria.setData(new Date());
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
	
	public void queEuTenhoUmaAcao() {
		acao = new Acao();
		acao.setId(1L);
		acao.setNome("Acao");
		acao.setDescricao("Descricao");
	}
	
	public void queEuTenhoUmaListaDeAcoes() {
		queEuTenhoUmaAcao();
		acoes = new ArrayList<Acao>();
		acoes.add(acao);
	}
	
	public void queEuTenhoUmGrupoDePerfil() {
		queEuTenhoUmaListaDeAcoes();
		grupoDePerfil = new GrupoDePerfil();
		grupoDePerfil.setId(1L);
		grupoDePerfil.setNome("Grupo");
		grupoDePerfil.setAcoes(acoes);
	}
	
	public void queEuTenhoUmUsuario() {
		queEuTenhoUmGrupoDePerfil();
		usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Usuario");
		usuario.setEmail("email@email.com");
		usuario.setAtivo(true);
		usuario.setGrupoDePerfil(grupoDePerfil);
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
	
}