package com.concafras.gestao.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.concafras.gestao.enums.TipoContatoInternet;

@Entity
@Table(name="CONTATOS")
@Access(AccessType.FIELD)
public class ContatoInternet implements Serializable{

	/**
   * 
   */
  private static final long serialVersionUID = 4231205194217892238L;

  @Id
	@SequenceGenerator(name = "email_id_seq_name", sequenceName = "email_id_seq", allocationSize=1, initialValue=1)
	@GeneratedValue(generator = "email_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Transient
	private Integer remove;
	
	@Enumerated(EnumType.STRING)
	private TipoContatoInternet tipo;
	
	private String contato;
	
	private boolean principal;
	
	public ContatoInternet(){
	}
	
	public ContatoInternet(TipoContatoInternet tipo){
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoContatoInternet getTipo() {
		return tipo;
	}

	public void setTipo(TipoContatoInternet tipo) {
		this.tipo = tipo;
	}

	public String getContato() {
	  if(tipo == TipoContatoInternet.EMAILCOM || tipo == TipoContatoInternet.EMAILPES){
	    return contato != null ? contato.toLowerCase() : contato;
	  } else {
	    return contato;
	  }
	}

	public void setContato(String email) {
	  if(tipo == TipoContatoInternet.EMAILCOM || tipo == TipoContatoInternet.EMAILPES){
	    this.contato = email != null ? email.toLowerCase() : email;
	  }
		this.contato = email;
	}
	
	public boolean isPrincipal() {
	  return principal;
	}
	
	public void setPrincipal(boolean principal) {
	  this.principal = principal;
	}
	
	public Integer getRemove() {
		return remove;
	}
	
	public void setRemove(Integer remove) {
		this.remove = remove;
	}

  @Override
  public String toString() {
    return "ContatoInternet [id=" + id + ", tipo=" + tipo + ", contato="
        + contato + "]";
  }
	
	
	
}
