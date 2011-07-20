package br.com.biblioteca.entidades;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;


@SuppressWarnings("serial")
@Entity
@RevisionEntity(MyRevisionListener.class)
public class MyRevisionEntity extends DefaultRevisionEntity{
	
	@ManyToOne
	private String user;

	public void setUser(final String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}
}