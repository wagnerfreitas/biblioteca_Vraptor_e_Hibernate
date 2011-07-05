package br.com.biblioteca;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mock;

import br.com.biblioteca.controller.UsuarioController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entitades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class UsuarioControllerTest {

	private Result result;
	private UsuarioController usuarioController;
	@Mock private AdminSession adminSession;
	@Mock private BibliotecaUtil bibliotecaUtil;
	@Mock private UsuarioDAO usuarioDAO;
	@Mock private EmprestimoDAO emprestimoDAO;
	
	public UsuarioControllerTest() {
		this.result = new MockResult();
		this.adminSession = new AdminSession();
		this.bibliotecaUtil = new BibliotecaUtil();
		this.emprestimoDAO = new EmprestimoDAO(bibliotecaUtil);
		this.usuarioDAO = new UsuarioDAO(bibliotecaUtil);
		this.usuarioController = new UsuarioController(result, usuarioDAO, adminSession, emprestimoDAO);
	}
	
	@Test
	public void adicionaUsuario(){
		Usuario usuario = criaUsuario();
		usuarioController.novo(usuario);
	}
	
	@Test
	public void listaUsuarios(){
		when(usuarioDAO.pesquisa("QualquerCoisa")).thenReturn(new ArrayList<Usuario>());
		usuarioController.index("QualquerCoisa");
		assertTrue(result.included().containsKey("usuarios"));
	}
	
	public Usuario criaUsuario(){
		Usuario usuario = new Usuario();
		usuario.setNome("Wagner");
		usuario.setEmail("wagner@gmail.com");
		usuario.setUsuarioAtivo(true);
		return usuario;
	}
}