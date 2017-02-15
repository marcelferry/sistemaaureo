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
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "ESTADOS")
@Access(AccessType.FIELD)
public class Estado extends ObjetoGerenciado {

  /**
   * 
   */
  private static final long serialVersionUID = -2888772130051035707L;

  @Id
  @SequenceGenerator(name = "estado_id_seq_name", sequenceName = "estado_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "estado_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String nome;

  private String sigla;

  private Integer idRegiao;

  @OneToOne
  @JoinColumn(name = "idPais", referencedColumnName = "id")
  @XmlElement
  private Pais pais;

  /**
   * 
   * @return
   */
  public Integer getId() {
    return id;
  }

  /**
   * 
   * @param id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * 
   * @return
   */
  public String getNome() {
    return nome;
  }

  /**
   * 
   * @param nome
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * 
   * @return
   */
  public String getSigla() {
    return sigla;
  }

  /**
   * 
   * @param sigla
   */
  public void setSigla(String sigla) {
    this.sigla = sigla;
  }
  
  /**
   * 
   * @return
   */
  public Integer getIdRegiao() {
    return idRegiao;
  }

  /**
   * 
   * @param idRegiao
   */
  public void setIdRegiao(Integer idRegiao) {
    this.idRegiao = idRegiao;
  }

  /**
   * 
   * @return
   */
  public Pais getPais() {
    return pais;
  }
  
  /**
   * 
   * @param pais
   */
  public void setPais(Pais pais) {
    this.pais = pais;
  }

}
