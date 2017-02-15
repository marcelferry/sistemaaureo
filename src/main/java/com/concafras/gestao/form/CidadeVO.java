package com.concafras.gestao.form;

import com.concafras.gestao.model.Cidade;

public class CidadeVO {

  private Integer id;

  private String nome;

  private EstadoVO estado;

  public CidadeVO() {
    // TODO Auto-generated constructor stub
  }

  public CidadeVO(Integer id, String nome, String sigla) {
    this.id = id;
    this.nome = nome;
    this.estado = new EstadoVO();
    this.estado.setSigla(sigla);
  }

  public CidadeVO(Cidade cidade) {
    this.id = cidade.getId();
    this.nome = cidade.getNome();
    this.estado = new EstadoVO(cidade.getEstado());
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

  public EstadoVO getEstado() {
    return estado;
  }

  public void setEstado(EstadoVO estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "CidadeVO [id=" + id + ", nome=" + nome + ", estado=" + estado + "]";
  }
  
  
}
