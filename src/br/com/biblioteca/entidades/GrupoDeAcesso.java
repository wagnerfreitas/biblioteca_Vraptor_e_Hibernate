package br.com.biblioteca.entidades;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.caelum.vraptor.resource.ResourceMethod;

@Entity
public class GrupoDeAcesso {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@OneToMany
	private Collection<String> acoes;
	
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
	public Collection<String> getAcoes() {
		return acoes;
	}
	public void setAcoes(Collection<String> acoes) {
		this.acoes = acoes;
	}
}