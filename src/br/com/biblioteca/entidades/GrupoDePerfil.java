package br.com.biblioteca.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class GrupoDePerfil {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@ManyToMany
	private List<Acao> acao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Acao> getAcao() {
		return acao;
	}
	public void setAcao(List<Acao> acao) {
		this.acao = acao;
	}
}