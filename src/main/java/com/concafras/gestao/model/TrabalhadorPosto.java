package com.concafras.gestao.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name="PESSOA_POSTO")
@Access(AccessType.FIELD)
public class TrabalhadorPosto implements Serializable {
	
	/**
   * Generated SerialVerionUID
   */
  private static final long serialVersionUID = 1167549868719006590L;

  @Id
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "idPosto", referencedColumnName = "id")
	@XmlElement
	private Posto posto;
	
	private String atividade;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Posto getPosto() {
		return posto;
	}

	public void setPosto(Posto posto) {
		this.posto = posto;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	
	
}
