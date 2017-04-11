package com.concafras.gestao.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MetaEntidadeAnotacaoId implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3049199823278808454L;

  @ManyToOne
  private MetaEntidade metaEntidade;
  
  @ManyToOne
  private Anotacao anotacao;

  public MetaEntidade getMetaEntidade() {
    return metaEntidade;
  }

  public void setMetaEntidade(MetaEntidade metaEntidade) {
    this.metaEntidade = metaEntidade;
  }

  public Anotacao getAnotacao() {
    return anotacao;
  }

  public void setAnotacao(Anotacao anotacao) {
    this.anotacao = anotacao;
  }

  public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaEntidadeAnotacaoId that = (MetaEntidadeAnotacaoId) o;

        if (metaEntidade != null ? !metaEntidade.equals(that.metaEntidade) : that.metaEntidade != null) return false;
        if (anotacao != null ? !anotacao.equals(that.anotacao) : that.anotacao != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (metaEntidade != null ? metaEntidade.hashCode() : 0);
        result = 31 * result + (anotacao != null ? anotacao.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
      return "MetaEntidadeAnotacaoId [metaEntidade=" + metaEntidade
          + ", anotacao=" + anotacao + "]";
    }
    
    
    
}