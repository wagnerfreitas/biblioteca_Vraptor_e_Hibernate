package br.com.biblioteca.entidades;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class GrupoDePerfil {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@Column(name="Acoes")
	@OneToMany
	private List<Acao> idAcao;
	
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
	public List<Acao> getIdAcao() {
		return idAcao;
	}
	public void setIdAcao(List<Acao> idAcao) {
		this.idAcao = idAcao;
	}
}