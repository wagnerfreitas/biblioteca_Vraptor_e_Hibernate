package com.br.biblioteca.entitades;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Emprestimo {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne	
	private Usuario usuario;
	@OneToOne
	private Livro livro;
	@Temporal(TemporalType.DATE)
	private Calendar dataDeEmprestimo;
	@Temporal(TemporalType.DATE)
	private Calendar dataDeDevolucao;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public Calendar getDataDeEmprestimo() {
		return dataDeEmprestimo;
	}
	public void setDataDeEmprestimo(Calendar dataDeEmprestimo) {
		this.dataDeEmprestimo = dataDeEmprestimo;
	}
	public Calendar getDataDeDevolucao() {
		return dataDeDevolucao;
	}
	public void setDataDeDevolucao(Calendar dataDeDevolucao) {
		this.dataDeDevolucao = dataDeDevolucao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
