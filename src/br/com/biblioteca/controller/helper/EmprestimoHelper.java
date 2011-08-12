package br.com.biblioteca.controller.helper;

import java.util.Date;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.entidades.Emprestimo;
import br.com.biblioteca.entidades.Livro;
import br.com.biblioteca.entidades.Usuario;
import br.com.caelum.vraptor.Resource;

@Resource
public class EmprestimoHelper {
	
	private LivroDAO livroDAO;
	private UsuarioDAO usuarioDAO;
	private EmprestimoDAO emprestimoDAO;
	private AuditoriaHelper auditoriaHelper;
	private Emprestimo emprestimo;
	private Livro livro;
	private Usuario usuario;
	
	public EmprestimoHelper(AuditoriaHelper auditoriaHelper, UsuarioDAO usuarioDAO, LivroDAO livroDAO, EmprestimoDAO emprestimoDAO) {
		this.livroDAO = livroDAO;
		this.usuarioDAO = usuarioDAO;
		this.emprestimoDAO = emprestimoDAO;
		this.auditoriaHelper = auditoriaHelper;
	}
	
	public boolean novoEmprestimo(Long iDUsuario, Long idLivro, Date dataDeEmprestimo) {
		try {
			emprestimo = new Emprestimo();
			usuario = usuarioDAO.pesquisarUsuarioPorId(iDUsuario);
			livro = livroDAO.pesquisarLivroPorId(idLivro);
			livro.setEmprestado(true);
			
			emprestimo.setUsuario(usuario);
			emprestimo.setLivro(livro);
			emprestimo.setDataDeEmprestimo(dataDeEmprestimo);
			
			livroDAO.atualiza(livro);
			emprestimoDAO.empresta(emprestimo);
			auditoriaHelper.auditoria(livro.getNome() + " - " + emprestimo.getUsuario().getNome(), "EMPRESTOU", dataDeEmprestimo);
			
			return true;
		} catch (Exception e) {
			return false;
		}
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