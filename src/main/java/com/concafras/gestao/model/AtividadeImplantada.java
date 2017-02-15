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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ATIVIDADE_ENTIDADE")
@Access(AccessType.FIELD)
public class AtividadeImplantada implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6162417846590717354L;

  @Id
  @SequenceGenerator(name = "atividadeimplantada_id_seq_name", sequenceName = "atividadeimplantada_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "atividadeimplantada_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "idAtividade", referencedColumnName = "id")
  @XmlElement
  private Atividade atividade;

  @OneToOne
  @JoinColumn(name = "idEntidade", referencedColumnName = "id")
  @XmlElement
  private BaseEntidade entidade;

  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Temporal(TemporalType.DATE)
  private Date desde;

  @ManyToOne
  @JoinColumn(name = "idResponsavel", referencedColumnName = "id")
  @XmlElement
  private Pessoa responsavel;

  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Temporal(TemporalType.DATE)
  private Date ate;

  private boolean ativo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Atividade getAtividade() {
    return atividade;
  }

  public void setAtividade(Atividade atividade) {
    this.atividade = atividade;
  }

  public BaseEntidade getEntidade() {
    return entidade;
  }

  public void setEntidade(BaseEntidade entidade) {
    this.entidade = entidade;
  }

  public Date getDesde() {
    return desde;
  }

  public void setDesde(Date desde) {
    this.desde = desde;
  }

  public Pessoa getResponsavel() {
    return responsavel;
  }

  public void setResponsavel(Pessoa responsavel) {
    this.responsavel = responsavel;
  }

  public Date getAte() {
    return ate;
  }

  public void setAte(Date ate) {
    this.ate = ate;
  }

  public boolean isAtivo() {
    return ativo;
  }

  public void setAtivo(boolean ativo) {
    this.ativo = ativo;
  }

  @Override
  public String toString() {
    return "AtividadeImplantada [id=" + id + ", atividade=" + atividade
        + ", entidade=" + entidade + ", desde=" + desde + "]";
  }
  
  

}
