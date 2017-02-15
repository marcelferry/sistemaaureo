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
@Table(name="ENTIDADE_DIRIGENTE")
@Access(AccessType.FIELD)
public class Dirigente implements Serializable {
	
	@Override
  public String toString() {
    return "Dirigente [id=" + id + ", trabalhador=" + trabalhador
        + ", entidade=" + entidade + ", instituto=" + instituto + ", ativo="
        + ativo + "]";
  }

  /**
   * 
   */
  private static final long serialVersionUID = 227668058312475753L;

  @Id
	@SequenceGenerator(name = "dirigente_id_seq_name", sequenceName = "dirigente_id_seq_name", allocationSize=1, initialValue=1)
	@GeneratedValue(generator = "dirigente_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Transient
	private Integer remove;
	
	@ManyToOne
	@JoinColumn(name = "idTrabalhador", referencedColumnName = "id")
	@XmlElement
	private Pessoa trabalhador;
	
	@ManyToOne
	@JoinColumn(name = "idEntidade", referencedColumnName = "id")
	@XmlElement
	private BaseEntidade entidade;
	
	@ManyToOne
	@JoinColumn(name = "idInstituto", referencedColumnName = "id")
	@XmlElement
	private BaseInstituto instituto;
	
	@Temporal(TemporalType.DATE)
	private Date inicioMandato;
	
	@Temporal(TemporalType.DATE)
	private Date terminoMandato;
	
	@Temporal(TemporalType.DATE)
	private Date dataInclusao;
	
	private boolean ativo;
	
	public Dirigente() {
		// TODO Auto-generated constructor stub
	}

	public Dirigente(Integer idDirigenteNacional, String dirigenteNacional) {
		this.trabalhador = new Pessoa(idDirigenteNacional, dirigenteNacional);
	}
	
	public Dirigente(Integer idDirigenteNacional, Integer idTrabalhador, String dirigenteNacional, Integer idInstituto, String descricaoInstituto) {
		this.id = idDirigenteNacional;
		this.trabalhador = new Pessoa(idTrabalhador, dirigenteNacional);
		this.instituto = new BaseInstituto(idInstituto, descricaoInstituto);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getTrabalhador() {
		return trabalhador;
	}

	public void setTrabalhador(Pessoa trabalhador) {
		this.trabalhador = trabalhador;
	}

	public BaseEntidade getEntidade() {
		return entidade;
	}

	public void setEntidade(BaseEntidade entidade) {
		this.entidade = entidade;
	}

	public BaseInstituto getInstituto() {
		return instituto;
	}

	public void setInstituto(BaseInstituto instituto) {
		this.instituto = instituto;
	}

	public Date getInicioMandato() {
		return inicioMandato;
	}

	public void setInicioMandato(Date inicioMandato) {
		this.inicioMandato = inicioMandato;
	}

	public Date getTerminoMandato() {
		return terminoMandato;
	}

	public void setTerminoMandato(Date terminoMandato) {
		this.terminoMandato = terminoMandato;
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
	
	
	
	
}
