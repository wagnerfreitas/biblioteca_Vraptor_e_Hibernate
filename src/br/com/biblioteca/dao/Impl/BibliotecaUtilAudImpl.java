package br.com.biblioteca.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.biblioteca.dao.BibliotecaUtilAud;
import br.com.biblioteca.entidades.Auditoria;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class BibliotecaUtilAudImpl implements BibliotecaUtilAud{

	private static SessionFactory factory;
	static {
		AnnotationConfiguration cfg = new AnnotationConfiguration().configure("hibernateAud.cfg.xml");
		cfg.addAnnotatedClass(Auditoria.class);
		factory = cfg.buildSessionFactory();
	}
	public Session getSession() {
		return factory.openSession();
	}
}