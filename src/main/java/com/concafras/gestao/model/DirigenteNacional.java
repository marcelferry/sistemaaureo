package com.concafras.gestao.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name="INSTITUTO_DIRIGENTE")
@Access(AccessType.FIELD)
public class DirigenteNacional implements Serializable {
	
	/**
   * 
   */
  private static final long serialVersionUID = 227668058312475753L;

  @Id
	@SequenceGenerator(name = "instituto_dirigente_id_seq_name", sequenceName = "instituto_dirigente_id_seq_name", allocationSize=1, initialValue=1)
	@GeneratedValue(generator = "instituto_dirigente_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Transient
	private Integer remove;
	
	@ManyToOne
	@JoinColumn(name = "idDirigente", referencedColumnName = "id")
	@XmlElement
	private Pessoa dirigente;
	
	@ManyToOne
	@JoinColumn(name = "idInstituto", referencedColumnName = "id")
	@XmlElement
	private BaseInstituto instituto;
	
	@Temporal(TemporalType.DATE)
	private Date dataInclusao;
	
	private boolean ativo;
	
	public DirigenteNacional() {
		// TODO Auto-generated constructor stub
	}

	public DirigenteNacional(Integer idDirigenteNacional, String dirigenteNacional) {
		this.dirigente = new Pessoa(idDirigenteNacional, dirigenteNacional);
	}
	
	public DirigenteNacional(Integer idDirigenteNacional, Integer idTrabalhador, String dirigenteNacional, Integer idInstituto, String descricaoInstituto) {
		this.id = idDirigenteNacional;
		this.dirigente = new Pessoa(idTrabalhador, dirigenteNacional);
		this.instituto = new BaseInstituto(idInstituto, descricaoInstituto);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getDirigente() {
		return dirigente;
	}

	public void setDirigente(Pessoa dirigente) {
		this.dirigente = dirigente;
	}

	public BaseInstituto getInstituto() {
		return instituto;
	}

	public void setInstituto(BaseInstituto instituto) {
		this.instituto = instituto;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Integer getRemove() {
		return remove;
	}
	
	public void setRemove(Integer remove) {
		this.remove = remove;
	}

  @Override
  public String toString() {
    return "DirigenteNacional [id=" + id + ", dirigente=" + dirigente
        + ", instituto=" + instituto + ", ativo=" + ativo + "]";
  }
	
	
	
	
}
