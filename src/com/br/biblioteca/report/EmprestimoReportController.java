package com.br.biblioteca.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.interceptor.download.Download;

import com.br.biblioteca.dao.EmprestimoDAO;
import com.br.biblioteca.entitades.Emprestimo;

public class EmprestimoReportController {
	
	private final JasperMaker jasperMaker;
	private final EmprestimoDAO emprestimoDAO;
	
	public EmprestimoReportController(JasperMaker jasperMaker, EmprestimoDAO emprestimoDAO) {
		this.emprestimoDAO = emprestimoDAO;
		this.jasperMaker = jasperMaker;
	}
	
	@Post
	@Path("relatorio/livros/{nome}")
	public Download relatoriosDeLivro(String nomeDoLivro) {
		List<Emprestimo> emprestimos = emprestimoDAO.pesquisarEmprestimo(nomeDoLivro);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nomeDoLivro", nomeDoLivro);
		return jasperMaker.makePdf("", emprestimos, "Lista_de_Emprestimos.pdf", true, parametros);
	} 
}