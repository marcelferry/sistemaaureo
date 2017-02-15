package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name="FACILITADORES")
@Access(AccessType.FIELD)
public class Facilitador extends ObjetoGerenciado {

	/**
   * 
   */
  private static final long serialVersionUID = -922596429639786696L;

  @Id @SequenceGenerator(name = "facilitador_id_seq_name", sequenceName = "facilitador_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "facilitador_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idRodizio", referencedColumnName = "id")
	@XmlElement
	private Rodizio rodizio;
	
	@ManyToOne
	@JoinColumn(name = "idInstituto", referencedColumnName = "id")
	@XmlElement
	private BaseInstituto instituto;
	
	@ManyToOne
	@JoinColumn(name = "idTrabalhador", referencedColumnName = "id")
	@XmlElement
	private Pessoa trabalhador;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Rodizio getRodizio() {
		return rodizio;
	}

	public void setRodizio(Rodizio rodizio) {
		this.rodizio = rodizio;
	}

	public BaseInstituto getInstituto() {
		return instituto;
	}

	public void setInstituto(BaseInstituto instituto) {
		this.instituto = instituto;
	}

	public Pessoa getTrabalhador() {
		return trabalhador;
	}

	public void setTrabalhador(Pessoa trabalhador) {
		this.trabalhador = trabalhador;
	}

  @Override
  public String toString() {
    return "Facilitador [id=" + id + ", rodizio=" + rodizio + ", instituto="
        + instituto + ", trabalhador=" + trabalhador + "]";
  }
	
	
}
