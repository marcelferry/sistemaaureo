package com.concafras.gestao.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.enums.TipoEndereco;
import com.concafras.gestao.enums.TipoLogradouro;
import com.concafras.gestao.model.ObjetoGerenciado;

public class EnderecoVO extends ObjetoGerenciado {

  private Integer id;

  private Integer remove;

  private String cep;

  private boolean buscaCep;

  private boolean cepUnico;

  private TipoEndereco tipoEndereco;

  private TipoLogradouro tipo;

  private String logradouro;

  private String numero;

  private String complemento;

  private String bairro;

  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date desde;

  private boolean atual;

  private CidadeVO cidade;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public TipoEndereco getTipoEndereco() {
    return tipoEndereco;
  }

  public void setTipoEndereco(TipoEndereco tipoEndereco) {
    this.tipoEndereco = tipoEndereco;
  }

  public TipoLogradouro getTipo() {
    return tipo;
  }

  public void setTipo(TipoLogradouro tipo) {
    this.tipo = tipo;
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

  public CidadeVO getCidade() {
    return cidade;
  }

  public void setCidade(CidadeVO cidade) {
    this.cidade = cidade;
  }

  public Date getDesde() {
    return desde;
  }

  public void setDesde(Date desde) {
    this.desde = desde;
  }

  public boolean isBuscaCep() {
    return buscaCep;
  }

  public void setBuscaCep(boolean buscaCep) {
    this.buscaCep = buscaCep;
  }

  public boolean isCepUnico() {
    return cepUnico;
  }

  public void setCepUnico(boolean cepUnico) {
    this.cepUnico = cepUnico;
  }

  public boolean isAtual() {
    return atual;
  }

  public void setAtual(boolean atual) {
    this.atual = atual;
  }

  public Integer getRemove() {
    return remove;
  }

  public void setRemove(Integer remove) {
    this.remove = remove;
  }

  public String getEnderecoFormatado() {
    return this.logradouro + ", " + this.numero;
  }

  @Override
  public String toString() {
    return "EnderecoVO [id=" + id + ", cep=" + cep + ", logradouro="
        + logradouro + ", numero=" + numero + ", complemento=" + complemento
        + ", bairro=" + bairro + ", desde=" + desde + ", cidade=" + cidade
        + "]";
  }
  
  

}
