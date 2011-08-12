package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entidades.Acao;

public interface AcaoDAO {
	
	public void novo(Acao acao);
	public List<Acao> acoes();
	public Acao pesquisaAcoesPorId(Long id);
}