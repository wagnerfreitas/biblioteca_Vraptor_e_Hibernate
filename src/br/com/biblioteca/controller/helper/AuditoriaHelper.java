package br.com.biblioteca.controller.helper;

import java.util.Date;

import br.com.biblioteca.dao.AuditoriaDAO;
import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Auditoria;
import br.com.caelum.vraptor.Resource;

@Resource
public class AuditoriaHelper {
	
	private UsuarioSession usuarioSession;
	private AuditoriaDAO auditoriaDAO;
	private Auditoria auditoria;
	
	public AuditoriaHelper(AuditoriaDAO auditoriaDAO, UsuarioSession usuarioSession) {
		this.auditoriaDAO = auditoriaDAO;
		this.usuarioSession = usuarioSession;
	}
	
	public void auditoria(String entidade, String acao, Date data) {
		try {
			auditoria = new Auditoria();
			auditoria.setUsuarioLogado(usuarioSession.getUsuario().getNome());
			auditoria.setAcao(acao);
			auditoria.setData(data);
			auditoria.setEntidade(entidade);
			
			auditoriaDAO.salva(auditoria);
		} catch (Exception e) {
			throw new RuntimeException("Erro");
		}
	}
}