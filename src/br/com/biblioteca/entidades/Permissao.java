package br.com.biblioteca.entidades;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Permissao {
	TipoDePerfil[] value();
}