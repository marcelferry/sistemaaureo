package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "METAS_ANOTACOES", catalog = "")
@AssociationOverrides({
    @AssociationOverride(name = "pk.metaEntidade", 
      joinColumns = @JoinColumn(name = "IDMETA")),
    @AssociationOverride(name = "pk.anotacao", 
      joinColumns = @JoinColumn(name = "IDANOTACAO")) })
@Access(AccessType.FIELD)
public class MetaEntidadeAnotacao implements java.io.Serializable {

  @EmbeddedId
  private MetaEntidadeAnotacaoId pk = new MetaEntidadeAnotacaoId();
  
  @ManyToOne
  @JoinColumn(name = "idCiclo", referencedColumnName = "id")
  private Rodizio ciclo;

  public MetaEntidadeAnotacao() {
  }
  
  public MetaEntidadeAnotacao(MetaEntidade me, Rodizio ciclo, Anotacao anotacao){
    setMetaEntidade(me);
    setAnotacao(anotacao);
    setCiclo(ciclo);
  }

  public MetaEntidadeAnotacaoId getPk() {
    return pk;
  }

  public void setPk(MetaEntidadeAnotacaoId pk) {
    this.pk = pk;
  }

  @Transient
  public MetaEntidade getMetaEntidade() {
    return getPk().getMetaEntidade();
  }

  public void setMetaEntidade(MetaEntidade metaEntidade) {
    getPk().setMetaEntidade(metaEntidade);
  }

  
  public Anotacao getAnotacao() {
    return getPk().getAnotacao();
  }

  public void setAnotacao(Anotacao anotacao) {
    getPk().setAnotacao(anotacao);
  }

  
  public Rodizio getCiclo() {
    return this.ciclo;
  }

  public void setCiclo(Rodizio ciclo) {
    this.ciclo = ciclo;
  }

  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    MetaEntidadeAnotacao that = (MetaEntidadeAnotacao) o;

    if (getPk() != null ? !getPk().equals(that.getPk())
        : that.getPk() != null)
      return false;

    return true;
  }

  public int hashCode() {
    return (getPk() != null ? getPk().hashCode() : 0);
  }

  @Override
  public String toString() {
    return "MetaEntidadeAnotacao [pk=" + pk + ", ciclo=" + ciclo + "]";
  }
  
  
}