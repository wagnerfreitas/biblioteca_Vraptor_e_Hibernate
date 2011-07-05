package br.com.biblioteca;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.controller.UsuarioController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entitades.Administrador;
import br.com.biblioteca.entitades.Usuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class UsuarioControllerTest {

	private Result result;
	private UsuarioController usuarioController;
	@Mock 
	private AdminSession adminSession;
	@Mock 
	private UsuarioDAO usuarioDAO;
	@Mock 
	private EmprestimoDAO emprestimoDAO;
	
	public UsuarioControllerTest() {
		MockitoAnnotations.initMocks(this);
		this.result = new MockResult();
		this.usuarioController = new UsuarioController(result, usuarioDAO, adminSession, emprestimoDAO);
	}
	
	@Test
	public void testAdicionaUsuarioMaiorQueUm()	{
		Usuario usuario = criaUsuario();
		Usuario usuario2 = criaUsuario();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario);
		usuarios.add(usuario2);
		
		when(usuarioDAO.pesquisa(usuario.getNome())).thenReturn(usuarios);
		usuarioController.novo(usuario);
		
		assertEquals("\"Wagner\" já está cadastrado",result.included().get("message"));
	}
	
	@Test
	public void testListaUsuarios() {
		Administrador adm = new Administrador();
		adm.setNome("nome");
		
		when(adminSession.getAdministrador()).thenReturn(adm);
		when(usuarioDAO.pesquisa("wagner")).thenReturn(new ArrayList<Usuario>());

		usuarioController.index("wagner");
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