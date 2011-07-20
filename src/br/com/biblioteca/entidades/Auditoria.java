package br.com.biblioteca.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Auditoria {
	@Id
	@GeneratedValue
	private Long id;
	private String administrador;
	private String acao;
	private String entidadeUsuario;
	private String entidadeLivro;
	private Date date;
	
	public String getAdministrador() {
		return administrador;
	}
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getEntidadeUsuario() {
		return entidadeUsuario;
	}
	public void setEntidadeUsuario(String entidade) {
		this.entidadeUsuario = entidade;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEntidadeLivro() {
		return entidadeLivro;
	}
	public void setEntidadeLivro(String entidadeLivro) {
		this.entidadeLivro = entidadeLivro;
	}
}