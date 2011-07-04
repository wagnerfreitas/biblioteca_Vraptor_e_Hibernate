package br.com.biblioteca.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.biblioteca.entitades.Administrador;
import br.com.biblioteca.entitades.Emprestimo;
import br.com.biblioteca.entitades.Livro;
import br.com.biblioteca.entitades.Usuario;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class BibliotecaUtil {
	private static SessionFactory factory;
	static{
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.addAnnotatedClass(Usuario.class);	
		cfg.addAnnotatedClass(Emprestimo.class);
		cfg.addAnnotatedClass(Livro.class);
		cfg.addAnnotatedClass(Administrador.class);
		factory = cfg.buildSessionFactory(); 
	}
	public Session getSession(){
		return factory.openSession();
	}
}