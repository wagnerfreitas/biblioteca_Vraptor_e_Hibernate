package com.br.biblioteca.entitades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Usuario implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private boolean emprestimoAtivo;
	private boolean usuarioAtivo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isUsuarioAtivo() {
		return usuarioAtivo;
	}
	public void setUsuarioAtivo(boolean usuarioAtivo) {
		this.usuarioAtivo = usuarioAtivo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isEmprestimoAtivo() {
		return emprestimoAtivo;
	}
	public void setEmprestimoAtivo(boolean emprestimoAtivo) {
		this.emprestimoAtivo = emprestimoAtivo;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}