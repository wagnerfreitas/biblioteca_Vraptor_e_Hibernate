package br.com.biblioteca.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Audited
@Entity
public class Livro {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String autor;
	private String edicao;
	@NotAudited
	private boolean emprestado;
	@NotAudited
	private boolean livroDeletado;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public boolean isEmprestado() {
		return emprestado;
	}
	public void setEmprestado(boolean emprestado) {
		this.emprestado = emprestado;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isLivroDeletado() {
		return livroDeletado;
	}
	public void setLivroDeletado(boolean livroDeletado) {
		this.livroDeletado = livroDeletado;
	}
	public String getEdicao() {
		return edicao;
	}
	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}
}
