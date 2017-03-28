package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "INSTITUTOS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
@Access(AccessType.FIELD)
public class BaseInstituto extends ObjetoGerenciado {

  /**
   * 
   */
  private static final long serialVersionUID = 742494263305279198L;

  /**
   * Identificador
   */
  @Id
  @SequenceGenerator(name = "instituto_id_seq_name", sequenceName = "instituto_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "instituto_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;

  /**
   * Nome
   */
  private String nome;

  /**
   * Descricao
   */
  private String descricao;

  /**
   * Objetivo
   */
  private String objetivo;

  /**
   * Tem sala no rodizio
   */
  private boolean rodizio;

  @ManyToOne
  @JoinColumn(name = "idDirigente", referencedColumnName = "id")
  @XmlElement
  private Pessoa dirigenteNacional;

  public BaseInstituto() {
    // TODO Auto-generated constructor stub
  }

  public BaseInstituto(Integer id, String nome, String descricao,
      Integer idDirigenteNacional, String dirigenteNacional) {
    super();
    this.id = id;
    this.nome = nome;
    this.descricao = descricao;
    this.dirigenteNacional = new Pessoa(idDirigenteNacional, dirigenteNacional);
  }

  public BaseInstituto(Integer id, String descricao) {
    super();
    this.id = id;
    this.descricao = descricao;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getNome() {
    return nome;
  }
  
  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }

  public boolean isRodizio() {
    return rodizio;
  }

  public void setRodizio(boolean rodizio) {
    this.rodizio = rodizio;
  }

  public Pessoa getDirigenteNacional() {
    return dirigenteNacional;
  }

  public void setDirigenteNacional(Pessoa dirigenteNacional) {
    this.dirigenteNacional = dirigenteNacional;
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
    if (!(obj instanceof BaseInstituto))
      return false;
    BaseInstituto other = (BaseInstituto) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "BaseInstituto [id=" + id + ", descricao=" + descricao + "]";
  }
  
  
  
  

}
