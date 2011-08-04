package br.com.biblioteca.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.biblioteca.dao.BibliotecaUtil;
import br.com.biblioteca.entidades.Acoes;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class BibliotecaUtilImpl implements BibliotecaUtil {
	private static SessionFactory factory;
	static {
		AnnotationConfiguration cfg = new AnnotationConfiguration().configure("hibernate.cfg.xml");
		cfg.addAnnotatedClass(Usuario.class);
		cfg.addAnnotatedClass(Emprestimo.class);
		cfg.addAnnotatedClass(Acoes.class);
		cfg.addAnnotatedClass(Livro.class);
		factory = cfg.buildSessionFactory();
	}
	
	public Session getSession() {
		return factory.openSession();
	}
}