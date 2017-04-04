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
import javax.xml.bind.annotation.XmlElement;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="CICLOS_AVALIACAO")
@Access(AccessType.FIELD)
public class Rodizio extends ObjetoGerenciado {
	
	/**
   * Generated SerialVerionUID
   */
  private static final long serialVersionUID = 3860081919517744686L;

  @Id  @SequenceGenerator(name = "rodizio_id_seq_name", sequenceName = "rodizio_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "rodizio_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	/**
	 * Ciclo
	 */
	private String ciclo;
	
	/**
	 * Inicio do período de ajustes
	 */
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date inicioAjustes;
	
	/**
	 * Termino do período de ajustes
	 */
	@Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern="dd/MM/yyyy")
	private Date terminoAjustes;
	
	/** 
	 * Data em que o rodizio será realizado.
	 */
	@Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataAprovacao;
	
	@Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern="dd/MM/yyyy")
	private Date inicio;
	
	@Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern="dd/MM/yyyy")
	private Date termino;

	private boolean ativo;
	
	@ManyToOne
  @JoinColumn(name = "ciclo_anterior", referencedColumnName = "id")
  @XmlElement
	private Rodizio cicloAnterior;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ano) {
		this.ciclo = ano;
	}

	public Date getInicioAjustes() {
		return inicioAjustes;
	}

	public void setInicioAjustes(Date inicioAjustes) {
		this.inicioAjustes = inicioAjustes;
	}

	public Date getTerminoAjustes() {
		return terminoAjustes;
	}

	public void setTerminoAjustes(Date terminoAjustes) {
		this.terminoAjustes = terminoAjustes;
	}

	public Date getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Date dataRodizio) {
		this.dataAprovacao = dataRodizio;
	}
	
	public Date getInicio() {
		return inicio;
	}
	
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	
	public Date getTermino() {
		return termino;
	}
	
	public void setTermino(Date termino) {
		this.termino = termino;
	}
	
	public boolean isAtivo() {
    return ativo;
  }
	
	public void setAtivo(boolean ativo) {
    this.ativo = ativo;
  }
	
	public Rodizio getCicloAnterior() {
    return cicloAnterior;
  }
	
	public void setCicloAnterior(Rodizio cicloAnterior) {
    this.cicloAnterior = cicloAnterior;
  }

  @Override
  public String toString() {
    return "Rodizio [id=" + id + ", ciclo=" + ciclo + ", ativo=" + ativo + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Rodizio other = (Rodizio) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
	
	
	
}
