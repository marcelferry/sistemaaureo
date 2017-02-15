package com.concafras.gestao.model;

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
@Table(name="PRESIDENTES_ENTIDADE")
@Access(AccessType.FIELD)
public class Presidente extends ObjetoGerenciado {
	
	/**
   * Generated SerialVerionUID
   */
  private static final long serialVersionUID = 7024012775193993136L;

  @Id
	@SequenceGenerator(name = "presidente_id_seq_name", sequenceName = "presidente_id_seq_name", allocationSize=1, initialValue=1)
	@GeneratedValue(generator = "presidente_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Transient
	private Integer remove;
	
	@ManyToOne
	@JoinColumn(name = "idTrabalhador", referencedColumnName = "id")
	@XmlElement
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "idEntidade", referencedColumnName = "id")
	@XmlElement
	private BaseEntidade entidade;
	
	@Temporal(TemporalType.DATE)
	private Date inicioMandato;
	
	@Temporal(TemporalType.DATE)
	private Date terminoMandato;
	
	@Temporal(TemporalType.DATE)
	private Date dataInclusao;
	
	private boolean ativo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa trabalhador) {
		this.pessoa = trabalhador;
	}

	public BaseEntidade getEntidade() {
		return entidade;
	}

	public void setEntidade(BaseEntidade entidade) {
		this.entidade = entidade;
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

  @Override
  public String toString() {
    return "Presidente [id=" + id + ", pessoa=" + pessoa + ", entidade="
        + entidade + ", ativo=" + ativo + "]";
  }
	
	
	
}
