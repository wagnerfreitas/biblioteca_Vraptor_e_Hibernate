package com.br.biblioteca.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.br.biblioteca.entitades.Emprestimo;
import com.br.biblioteca.entitades.Livro;
import com.br.biblioteca.entitades.Usuario;


public class BibliotecaUtil {
	private static SessionFactory factory;
	static{
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.addAnnotatedClass(Usuario.class);	
		cfg.addAnnotatedClass(Emprestimo.class);
		cfg.addAnnotatedClass(Livro.class);
		factory = cfg.buildSessionFactory(); 
	}
	public Session getSession(){
		return factory.openSession();
	}
}