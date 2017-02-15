package com.concafras.gestao.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import com.concafras.gestao.enums.TipoEndereco;
import com.concafras.gestao.enums.TipoLogradouro;

@Entity
@Table(name = "ENDERECOS")
@Access(AccessType.FIELD)
public class Endereco extends ObjetoGerenciado {

  /**
   * 
   */
  private static final long serialVersionUID = 8622498943289898555L;

  @Id
  @SequenceGenerator(name = "endereco_id_seq_name", sequenceName = "endereco_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "endereco_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Transient
  private Integer remove;

  private String cep;

  private boolean buscaCep;

  private boolean cepUnico;

  @Enumerated(EnumType.STRING)
  private TipoEndereco tipoEndereco;

  @Enumerated(EnumType.STRING)
  private TipoLogradouro tipo;

  private String logradouro;

  private String numero;

  private String complemento;

  private String bairro;

  @Temporal(TemporalType.DATE)
  private Date desde;

  private boolean atual;

  @ManyToOne
  @JoinColumn(name = "idCidade", referencedColumnName = "id")
  private Cidade cidade;

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

  public Cidade getCidade() {
    return cidade;
  }

  public void setCidade(Cidade cidade) {
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
    return "Endereco [id=" + id + ", cep=" + cep + ", logradouro=" + logradouro
        + ", numero=" + numero + ", bairro=" + bairro + ", cidade=" + cidade
        + "]";
  }
  
  

}
