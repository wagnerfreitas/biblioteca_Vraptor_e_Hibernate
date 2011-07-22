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
	private String usuarioLogado;
	private String acao;
	private String entidadeUsuario;
	private String entidadeLivro;
	private Date data;
	
	public String getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
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
		return data;
	}
	public void setDate(Date date) {
		this.data = date;
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