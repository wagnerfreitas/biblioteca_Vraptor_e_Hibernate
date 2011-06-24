package com.br.biblioteca.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.interceptor.download.Download;

import com.br.biblioteca.dao.LivroDAO;
import com.br.biblioteca.entitades.Livro;
	
public class LivroReportController {
	
	private final JasperMaker jasperMaker;
	private final LivroDAO livroDAO;
	
	public LivroReportController(JasperMaker jasperMaker, LivroDAO livroDAO) {
		this.jasperMaker = jasperMaker;
		this.livroDAO = livroDAO;
	}
	
	@Post
	@Path("relatorio/livros/{nome}")
	public Download relatoriosDeLivro(String nome) {
		List<Livro> livros = livroDAO.listaDeLivro(nome);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nome", nome);
		return jasperMaker.makePdf("", livros, "Livro.pdf", true, parametros);
	} 
}
