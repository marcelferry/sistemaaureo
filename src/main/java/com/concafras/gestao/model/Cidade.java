package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "CIDADES")
@Access(AccessType.FIELD)
public class Cidade extends ObjetoGerenciado {

  /**
   * 
   */
  private static final long serialVersionUID = -1115552756351076823L;

  @Id
  @SequenceGenerator(name = "cidade_id_seq_name", sequenceName = "cidade_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "cidade_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String nome;

  @OneToOne
  @JoinColumn(name = "idEstado", referencedColumnName = "id")
  @XmlElement
  private Estado estado;
  
  @Transient
  private Integer entidades;

  public Cidade() {
    // TODO Auto-generated constructor stub
  }

  public Cidade(Integer id, String nome, String sigla) {
    super();
    this.id = id;
    this.nome = nome;
    this.estado = new Estado();
    this.estado.setSigla(sigla);
  }
  
  public Cidade(Integer id, String nome, String sigla, int entidades) {
    super();
    this.id = id;
    this.nome = nome;
    this.estado = new Estado();
    this.estado.setSigla(sigla);
    this.entidades = entidades;
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

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }
  
  public Integer getEntidades() {
    return entidades;
  }
  
  public void setEntidades(Integer entidades) {
    this.entidades = entidades;
  }
  
  @Override
  public String toString() {
    return this.nome + (this.estado != null? "/" + this.estado.getSigla() : "") + (this.entidades != null ? " - (" + this.entidades + ")": "");
  }

}
