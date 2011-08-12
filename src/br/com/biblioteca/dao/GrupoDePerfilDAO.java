package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entidades.GrupoDePerfil;

public interface GrupoDePerfilDAO {
	
	public void novo(GrupoDePerfil grupoDePerfil);
	public List<GrupoDePerfil> grupos();
	public GrupoDePerfil pesquisaPorId(Long id);
}