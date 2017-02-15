package com.concafras.gestao.model.view;

import java.io.Serializable;

public class EntidadeExcel implements Serializable{
  
  boolean excluir;
  Integer codigo;
  String nome; 
  String cnpj  ;
  String logradouro;  
  String numero;
  String complemento;
  String bairro; 
  String cidade;  
  String estado; 
  String fone1;  
  String fone2; 
  String dirigente; 
  String email;
  
  
  
  public EntidadeExcel(boolean excluir, Integer codigo, String nome,
      String cnpj, String logradouro, String numero, String complemento,
      String bairro, String cidade, String estado, String fone1, String fone2,
      String dirigente, String email) {
    super();
    this.excluir = excluir;
    this.codigo = codigo;
    this.nome = nome;
    this.cnpj = cnpj;
    this.logradouro = logradouro;
    this.numero = numero;
    this.complemento = complemento;
    this.bairro = bairro;
    this.cidade = cidade;
    this.estado = estado;
    this.fone1 = fone1;
    this.fone2 = fone2;
    this.dirigente = dirigente;
    this.email = email;
  }
  public boolean isExcluir() {
    return excluir;
  }
  public void setExcluir(boolean excluir) {
    this.excluir = excluir;
  }
  public Integer getCodigo() {
    return codigo;
  }
  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getCnpj() {
    return cnpj;
  }
  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }
  public String getLogradouro() {
    return logradouro;
  }
  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }
  public String getNumero() {
    return numero;
  }
  public void setNumero(String numero) {
    this.numero = numero;
  }
  public String getComplemento() {
    return complemento;
  }
  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }
  public String getBairro() {
    return bairro;
  }
  public void setBairro(String bairro) {
    this.bairro = bairro;
  }
  public String getCidade() {
    return cidade;
  }
  public void setCidade(String cidade) {
    this.cidade = cidade;
  }
  public String getEstado() {
    return estado;
  }
  public void setEstado(String estado) {
    this.estado = estado;
  }
  public String getFone1() {
    return fone1;
  }
  public void setFone1(String fone1) {
    this.fone1 = fone1;
  }
  public String getFone2() {
    return fone2;
  }
  public void setFone2(String fone2) {
    this.fone2 = fone2;
  }
  public String getDirigente() {
    return dirigente;
  }
  public void setDirigente(String dirigente) {
    this.dirigente = dirigente;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  
  

}
