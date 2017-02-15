package com.concafras.gestao.form;

import com.concafras.gestao.model.Estado;
import com.concafras.gestao.model.Pais;

public class EstadoVO {

  private Integer id;

  private String nome;

  private String sigla;

  private Integer idRegiao;

  private Pais pais;

  public EstadoVO() {
    // TODO Auto-generated constructor stub
  }
  
  public EstadoVO(Estado estado) {
    this.id = estado.getId();
    this.nome = estado.getNome();
    this.sigla = estado.getSigla();
    this.pais = estado.getPais();
  }

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
