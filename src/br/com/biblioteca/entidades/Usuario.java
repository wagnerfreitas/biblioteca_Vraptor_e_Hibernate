package br.com.biblioteca.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Usuario {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String email;
	private String senha;
	@SuppressWarnings("unused")
	private boolean ativo;
	@OneToOne
	private GrupoDePerfil grupoDePerfil;

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
	public void setAtivo(boolean usuarioAtivo) {
		this.ativo = usuarioAtivo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public GrupoDePerfil getGrupoDePerfil() {
		return grupoDePerfil;
	}
	public void setGrupoDePerfil(GrupoDePerfil grupoDePerfil) {
		this.grupoDePerfil = grupoDePerfil;
	}
}