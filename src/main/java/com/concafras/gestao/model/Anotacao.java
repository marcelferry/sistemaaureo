package com.concafras.gestao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import com.concafras.gestao.enums.NivelAnotacao;
import com.concafras.gestao.enums.Sinalizador;
import com.concafras.gestao.rest.model.AnotacaoVO;

@Entity
@Table(name = "ANOTACOES")
@Access(AccessType.FIELD)
public class Anotacao implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7565333051243494716L;

  @Id
  @SequenceGenerator(name = "anotacao_id_seq_name", sequenceName = "anotacao_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "anotacao_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Transient
  private Integer remove;

  public Integer getRemove() {
    return remove;
  }

  public void setRemove(Integer remove) {
    this.remove = remove;
  }

  @Temporal(TemporalType.TIMESTAMP)
  private Date data;

  @Enumerated(EnumType.STRING)
  private NivelAnotacao nivel;

  @ManyToOne
  @JoinColumn(name = "idPessoa", referencedColumnName = "id")
  @XmlElement
  private Pessoa responsavel;

  private String texto;

  @Enumerated(EnumType.STRING)
  private Sinalizador sinalizador;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "idAnotacao", referencedColumnName = "id")
  @XmlElement
  private Anotacao emResposta;

  @OneToMany(mappedBy = "emResposta")
  private List<Anotacao> respostas;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public Pessoa getResponsavel() {
    return responsavel;
  }

  public void setResponsavel(Pessoa responsavel) {
    this.responsavel = responsavel;
  }

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public Sinalizador getSinalizador() {
    return sinalizador;
  }

  public void setSinalizador(Sinalizador sinalizador) {
    this.sinalizador = sinalizador;
  }

  public NivelAnotacao getNivel() {
    return nivel;
  }

  public void setNivel(NivelAnotacao nivel) {
    this.nivel = nivel;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass() && obj.getClass() != AnotacaoVO.class)
      return false;

    if(obj instanceof Anotacao) {
    		Anotacao other = (Anotacao) obj;
	    if (id == null) {
	      if (other.id != null)
	        return false;
	    } else if (!id.equals(other.id))
	      return false;
    } 
    
    if(obj instanceof AnotacaoVO) {
		AnotacaoVO other = (AnotacaoVO) obj;
    if (id == null) {
      if (other.getId() != null)
        return false;
    } else if (!id.equals(other.getId()))
      return false;
}
    return true;
  }

  @Override
  public String toString() {
    return "Anotacao [id=" + id + ", nivel=" + nivel + ", texto=" + texto + "]";
  }

  
  
}
