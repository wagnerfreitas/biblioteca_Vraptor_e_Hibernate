package com.br.biblioteca.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.interceptor.download.Download;

import com.br.biblioteca.dao.LivroDAO;
import com.br.biblioteca.entitades.Livro;
	
@Resource
public class LivroReportController {
	
	private final JasperMaker jasperMaker;
	private final LivroDAO livroDAO;
	
	public LivroReportController(JasperMaker jasperMaker, LivroDAO livroDAO) {
		this.jasperMaker = jasperMaker;
		this.livroDAO = livroDAO;
	}
	
	@Post
	@Path("relatorio/livros")
	public Download relatoriosDeLivro(String filtro_relatorio) {
		List<Livro> livros = livroDAO.listaDeLivro(filtro_relatorio);
		Map<String, Object> parametros = new HashMap<String, Object>();
		Date data = new Date();
		parametros.put("TITLE", "Lista de livros");
		parametros.put("DATE", data);
		return jasperMaker.makePdf("Livro.jrxml", livros, "Livros.pdf", true, parametros);
	} 
}