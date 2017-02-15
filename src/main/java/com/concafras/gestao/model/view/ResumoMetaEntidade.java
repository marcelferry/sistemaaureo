package com.concafras.gestao.model.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class ResumoMetaEntidade {
  
  public static final SimpleDateFormat _formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");

  Integer idEntidade;
  String entidade;
  String cidade;
  String uf;
  Integer idCiclo;
  String ciclo;
  Integer idInstituto;
  String instituto;
  Integer idMeta;
  String meta;
  String tipoMeta;
  String acao;
  Date previsao;
  Date conclusao;
  Integer previsto;
  Integer realizado;
  String situacao;
  Integer idRegiao;
  String regiao;
  String status;
  
  
  public void setIdEntidade(Integer idEntidade) {
    this.idEntidade = idEntidade;
  }
  
  public Integer getIdEntidade() {
    return idEntidade;
  }

  public void setEntidade(String entidade) {
    this.entidade = entidade;
  }
  
  public String getEntidade() {
    return entidade;
  }
  
  public void setCidade(String cidade) {
    this.cidade = cidade;
  }
  
  public String getCidade() {
    return cidade;
  }
  
  public void setUf(String uf) {
    this.uf = uf;
  }
  
  public String getUf() {
    return uf;
  }
  
  public void setIdCiclo(Integer idCiclo) {
    this.idCiclo = idCiclo;
  }
  
  public Integer getIdCiclo() {
    return idCiclo;
  }
  
  public void setCiclo(String ciclo) {
    this.ciclo = ciclo;
  }
  
  public String getCiclo() {
    return ciclo;
  }
  
  public void setIdInstituto(Integer idInstituto) {
    this.idInstituto = idInstituto;
  }
  
  public Integer getIdInstituto() {
    return idInstituto;
  }
  
  public void setInstituto(String instituto) {
    this.instituto = instituto;
  }
  
  public String getInstituto() {
    return instituto;
  }
  
  public void setIdMeta(Integer idMeta) {
    this.idMeta = idMeta;
  }
  
  public Integer getIdMeta() {
    return idMeta;
  }
  
  public void setMeta(String meta) {
    this.meta = meta;
  }
  
  public String getMeta() {
    return meta;
  }
  
  public String getTipoMeta() {
    return tipoMeta;
  }
  
  public void setTipoMeta(String tipoMeta) {
    this.tipoMeta = tipoMeta;
  }
  
  public void setAcao(String acao) {
    this.acao = acao;
  }
  
  public String getAcao() {
    return acao;
  }
  
  public void setPrevisao(Date previsao) {
    this.previsao = previsao;
  }
  
  public Date getPrevisao() {
    return previsao;
  }
  
  public void setConclusao(Date conclusao) {
    this.conclusao = conclusao;
  }
  
  public Date getConclusao() {
    return conclusao;
  }
  
  public void setPrevisto(Integer previsto) {
    this.previsto = previsto;
  }
  
  public Integer getPrevisto() {
    return previsto;
  }
  
  public void setRealizado(Integer realizado) {
    this.realizado = realizado;
  }
  
  public Integer getRealizado() {
    return realizado;
  }
  
  public void setSituacao(String situacao) {
    this.situacao = situacao;
  }
  
  public String getSituacao() {
    return situacao;
  }
  
  public void setIdRegiao(Integer idRegiao) {
    this.idRegiao = idRegiao;
  }
  
  public Integer getIdRegiao() {
    return idRegiao;
  }
  
  public void setRegiao(String regiao) {
    this.regiao = regiao;
  }
  
  public String getRegiao() {
    return regiao;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getStatus() {
    return status;
  }  
  
}
