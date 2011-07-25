package br.com.biblioteca.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.biblioteca.dao.AuditoriaDAO;
import br.com.biblioteca.entidades.Auditoria;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.interceptor.download.Download;

@Resource
public class AuditoriaReport {
	
	private JasperMaker jasperMaker;
	private AuditoriaDAO auditoriaDAO;
	
	public AuditoriaReport(JasperMaker jasperMaker, AuditoriaDAO auditoriaDAO) {
		this.jasperMaker = jasperMaker;
		this.auditoriaDAO = auditoriaDAO;
	}
	
	@Post
	@Path("relatorio/auditoria")
	public Download relatoriosAuditoria(Date dataIninio, Date dataFim) {
		try {
			List<Auditoria> listaDeAuditoria = auditoriaDAO.list(dataIninio, dataFim);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("TITLE", "Listagem de auditoria");
			parametros.put("DATA_INICIO", dataIninio);
			parametros.put("DATA_FIM", dataFim);
			
			return jasperMaker.makePdf("Auditoria.jrxml", listaDeAuditoria, "Auditoria.pdf", true, parametros);
		} catch (Exception e) {
			throw new RuntimeException("Erro");
		}
	}
}