package br.com.biblioteca.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.interceptor.download.Download;

@Resource
public class EmprestimoReportController {
	
	private final JasperMaker jasperMaker;
	private final EmprestimoDAO emprestimoDAO;
	
	public EmprestimoReportController(JasperMaker jasperMaker, EmprestimoDAO emprestimoDAO) {
		this.emprestimoDAO = emprestimoDAO;
		this.jasperMaker = jasperMaker;
	}
	
	@Post
	@Path("relatorio/emprestimos")
	public Download relatoriosDeLivro(String nomeDoLivro, String ordenarPor) {
		try {
			List<Emprestimo> emprestimos = emprestimoDAO.pesquisarEmprestimo(nomeDoLivro, ordenarPor);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("DATE", new Date());
			parametros.put("TITLE", "Lista de livros emprestados");
			return jasperMaker.makePdf("Emprestimo.jrxml", emprestimos, "Lista_de_Emprestimos.pdf", true, parametros);
		}
		catch (Exception e) {
			throw new RuntimeException("Erro ao tentar fazer relatório de empréstimos");
		}
	} 
}