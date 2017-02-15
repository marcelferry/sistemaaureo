package com.concafras.gestao.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.concafras.gestao.model.Rodizio;

public class RodizioVO {
	
	@NotNull
	private Integer id;
	
	private String ciclo;
	
	/**
   * Inicio do período de ajustes
   */
  private Date inicioAjustes;
  
  /**
   * Termino do período de ajustes
   */
  private Date terminoAjustes;
  
  /** 
   * Data em que o rodizio será realizado.
   */
  private Date dataAprovacao;
  
  private Date inicio;
  
  private Date termino;
	
	public RodizioVO() {
    // TODO Auto-generated constructor stub
  }
	
	public RodizioVO(Rodizio rodizio) {
	  if(rodizio != null){
  	  this.id = rodizio.getId();
  	  this.ciclo = rodizio.getCiclo();
	  }
	}
	
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCiclo() {
    return ciclo;
  }

  public void setCiclo(String ano) {
    this.ciclo = ano;
  }

  public Date getInicioAjustes() {
    return inicioAjustes;
  }

  public void setInicioAjustes(Date inicioAjustes) {
    this.inicioAjustes = inicioAjustes;
  }

  public Date getTerminoAjustes() {
    return terminoAjustes;
  }

  public void setTerminoAjustes(Date terminoAjustes) {
    this.terminoAjustes = terminoAjustes;
  }

  public Date getDataAprovacao() {
    return dataAprovacao;
  }

  public void setDataAprovacao(Date dataRodizio) {
    this.dataAprovacao = dataRodizio;
  }
  
  public Date getInicio() {
    return inicio;
  }
  
  public void setInicio(Date inicio) {
    this.inicio = inicio;
  }
  
  public Date getTermino() {
    return termino;
  }
  
  public void setTermino(Date termino) {
    this.termino = termino;
  }

  @Override
  public String toString() {
    return "RodizioVO [id=" + id + ", ciclo=" + ciclo + "]";
  }
  
  
}
