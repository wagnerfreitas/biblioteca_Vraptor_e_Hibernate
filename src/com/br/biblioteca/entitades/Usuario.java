package com.br.biblioteca.entitades;

public class Usuario {
	private Long id;
	private String nome;
	private String email;
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
}
