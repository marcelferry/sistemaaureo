package com.concafras.gestao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "METAS_ENTIDADE")
@Access(AccessType.FIELD)
public class MetaEntidade implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -2785795254154220058L;

  @Id
  @SequenceGenerator(name = "meta_id_seq_name", sequenceName = "meta_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "meta_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;
  
  /** 
   * Entidade do plano de metas
   */
  @ManyToOne
  @JoinColumn(name = "idEntidade", referencedColumnName = "id")
  @XmlElement
  private BaseEntidade entidade;

  /**
   * Instituto da meta
   */
  @ManyToOne
  @JoinColumn(name = "idInstituto", referencedColumnName = "id")
  @XmlElement
  private BaseInstituto instituto;
  
  /**
   * MetaEntidade sendo contratada
   */
  @ManyToOne
  @JoinColumn(name = "idMetasInstituto", referencedColumnName = "id")
  @XmlElement
  private MetaInstituto meta;
  
  @ManyToOne
  @JoinColumn(name = "idPai", referencedColumnName = "id")
  @XmlElement
  private MetaEntidade pai;
  
  //TODO: REMOVER CAMPO
  @ManyToOne
  @JoinColumn(name = "idAtividade", referencedColumnName = "id")
  @XmlElement
  private Atividade atividade;

  /**
   * Rodizio do exercicio
   */
  @ManyToOne
  @JoinColumn(name = "idRodizio", referencedColumnName = "id")
  @XmlElement
  private Rodizio primeiroRodizio;
  
  /**
   * Anotações Gerais
   */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.metaEntidade")
  @XmlElement
  private List<MetaEntidadeAnotacao> anotacoes;

  /**
   * Histórico das açoes realizadas sobre esta meta
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "meta")
  @OrderBy("idRodizio ASC, tipo_situacao ASC")
  private List<HistoricoMetaEntidade> historico;
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public MetaInstituto getMeta() {
    return meta;
  }

  public void setMeta(MetaInstituto atividade) {
    this.meta = atividade;
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

  public List<MetaEntidadeAnotacao> getAnotacoes() {
    return anotacoes;
  }

  public void setAnotacoes(List<MetaEntidadeAnotacao> anotacoes) {
    this.anotacoes = anotacoes;
  }
  
  public List<HistoricoMetaEntidade> getHistorico() {
    return historico;
  }

  public void setHistorico(List<HistoricoMetaEntidade> historico) {
    this.historico = historico;
  }
  
  public MetaEntidade getPai() {
    return pai;
  }
  
  public void setPai(MetaEntidade pai) {
    this.pai = pai;
  }
  
  public Rodizio getPrimeiroRodizio() {
    return primeiroRodizio;
  }
  
  public void setPrimeiroRodizio(Rodizio primeiroRodizio) {
    this.primeiroRodizio = primeiroRodizio;
  }

  
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if(id != null){
      result = prime * result + ((id == null) ? 0 : id.hashCode());
    } else {
      result = prime * result + ((entidade == null) ? 0 : entidade.hashCode());
      result = prime * result + ((instituto == null) ? 0 : instituto.hashCode());
      result = prime * result + ((meta == null) ? 0 : meta.hashCode());
    }
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
    MetaEntidade other = (MetaEntidade) obj;
    if(id != null && other.id != null){
      if (!id.equals(other.id))
        return false;
    } else {
      if (entidade == null) {
        if (other.entidade != null)
          return false;
      } else if (!entidade.equals(other.entidade))
        return false;
      if (instituto == null) {
        if (other.instituto != null)
          return false;
      } else if (!instituto.equals(other.instituto))
        return false;
      if (meta == null) {
        if (other.meta != null)
          return false;
      } else if (!meta.equals(other.meta))
        return false;
    }
    return true;
  }

  // ------------- ADICIONAIS ----------------//
  @Transient
  private Integer remove;

  public Integer getRemove() {
    return remove;
  }

  public void setRemove(Integer remove) {
    this.remove = remove;
  }

  @Override
  public String toString() {
    return "MetaEntidade [id=" + id + ", entidade=" + entidade + ", instituto="
        + instituto + ", meta=" + meta + "]";
  }
  
  

}
