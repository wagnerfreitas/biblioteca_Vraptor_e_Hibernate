package br.com.biblioteca.dao;

import java.util.Date;
import java.util.List;

import br.com.biblioteca.entidades.Auditoria;

public interface AuditoriaDAO {
	
	public void salva(Auditoria auditoria);
	
	public List<Auditoria> list(Date dataInicio, Date dataFim);
}