package br.com.biblioteca.test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Any;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

import br.com.biblioteca.controller.IndexController;
import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.entitades.Administrador;

public class IndexControllerTest {

	private Result result;
	private AdminSession adminSession;
	private IndexController indexController;

	public IndexControllerTest() {
		this.result = new MockResult();
		this.adminSession = new AdminSession();
		indexController = new IndexController(result, adminSession);
	}

	@Test
	public void index() {
//		when(indexController.index());
		System.out.println(result.included());
	}

	@Test
	public void novo() {
		Administrador administrador = new Administrador();
		administrador.setNome("Wagner");
		administrador.setSenha("123");
	}
}
