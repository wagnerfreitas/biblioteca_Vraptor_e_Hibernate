package br.com.biblioteca.controller.helper;

import java.util.Date;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Resource;

@Resource
public class FinalizarEmprestimoHelper {
	
	private EmprestimoDAO emprestimoDAO;
	private LivroDAO livroDAO;
	private Emprestimo emprestimo;
	private Livro livro;
	private Usuario usuario;
	private AuditoriaHelper auditoriaHelper;
	
	public FinalizarEmprestimoHelper(EmprestimoDAO emprestimoDAO, LivroDAO livroDAO, AuditoriaHelper auditoriaHelper) {
		this.emprestimoDAO = emprestimoDAO;
		this.livroDAO = livroDAO;
		this.auditoriaHelper = auditoriaHelper;
	}
	
	public boolean finalizarEmprestimo(Long id, Date dataDeDevolucao) {
		try {
			emprestimo = emprestimoDAO.procuraPorIdLivro(id);
			livro = emprestimo.getLivro();
			usuario = emprestimo.getUsuario();
			
			emprestimo.setDataDeDevolucao(dataDeDevolucao);
			livro.setEmprestado(false);
			
			emprestimoDAO.atualiza(emprestimo);
			livroDAO.atualiza(livro);
			auditoriaHelper.auditoria(usuario.getNome() + " - " + livro.getNome(), "DEVOLVEU", dataDeDevolucao);
			
			return true; 
		} catch (Exception e) {
			return false;
		}
	}
}