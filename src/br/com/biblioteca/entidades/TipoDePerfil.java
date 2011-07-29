package br.com.biblioteca.entidades;

import java.util.ArrayList;
import java.util.Collection;

public enum TipoDePerfil {
	MEMBRO, MODERADOR, ADMINISTRADOR;
	
	public static Collection<TipoDePerfil> loadAll() {
		Collection<TipoDePerfil> perfis = new ArrayList<TipoDePerfil>();
		for(TipoDePerfil perfil : perfis) {
			perfis.add(perfil);
		}
		return perfis;
	}
}