package com.concafras.gestao.form;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.concafras.gestao.enums.DiaSemana;
import com.concafras.gestao.enums.TipoFrequencia;

public class AtividadeVO  {

  private Integer id;

  @Transient
  private Integer remove;

  public Integer getRemove() {
    return remove;
  }

  public void setRemove(Integer remove) {
    this.remove = remove;
  }

  private InstitutoOptionForm instituto;

  private AtividadeVO pai;

  private List<AtividadeVO> subAtividades;

  private String descricao;

  private TipoFrequencia frequencia;

  private DiaSemana diaSemana;

  private Date horaInicio;

  private Date horaTermino;

  private int viewOrder;

  public AtividadeVO() {
    // TODO Auto-generated constructor stub
  }

  public AtividadeVO(String descricao, TipoFrequencia frequencia) {
    this.descricao = descricao;
    this.frequencia = frequencia;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public AtividadeVO getPai() {
    return pai;
  }

  public void setPai(AtividadeVO pai) {
    this.pai = pai;
  }

  public List<AtividadeVO> getSubAtividades() {
    return subAtividades;
  }

  public void setSubAtividades(List<AtividadeVO> subAtividades) {
    this.subAtividades = subAtividades;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public TipoFrequencia getFrequencia() {
    return frequencia;
  }

  public void setFrequencia(TipoFrequencia frequencia) {
    this.frequencia = frequencia;
  }

  public DiaSemana getDiaSemana() {
    return diaSemana;
  }

  public void setDiaSemana(DiaSemana diaSemana) {
    this.diaSemana = diaSemana;
  }

  public Date getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(Date horaInicio) {
    this.horaInicio = horaInicio;
  }

  public Date getHoraTermino() {
    return horaTermino;
  }

  public void setHoraTermino(Date horaTermino) {
    this.horaTermino = horaTermino;
  }

  public InstitutoOptionForm getInstituto() {
    return instituto;
  }

  public void setInstituto(InstitutoOptionForm instituto) {
    this.instituto = instituto;
  }

  public int getViewOrder() {
    return viewOrder;
  }

  public void setViewOrder(int viewOrder) {
    this.viewOrder = viewOrder;
  }

  @Override
  public String toString() {
    return "AtividadeVO [id=" + id + ", instituto=" + instituto + ", pai=" + pai
        + ", descricao=" + descricao + "]";
  }
  
  
}
