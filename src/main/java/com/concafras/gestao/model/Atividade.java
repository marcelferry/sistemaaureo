package com.concafras.gestao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.enums.DiaSemana;
import com.concafras.gestao.enums.TipoFrequencia;

@Entity
@Table(name = "ATIVIDADES")
@Access(AccessType.FIELD)
public class Atividade extends ObjetoGerenciado {

  /**
   * 
   */
  private static final long serialVersionUID = -4954866728368676804L;

  @Id
  @SequenceGenerator(name = "atividade_id_seq_name", sequenceName = "atividade_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "atividade_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Transient
  private Integer remove;

  public Integer getRemove() {
    return remove;
  }

  public void setRemove(Integer remove) {
    this.remove = remove;
  }

  @ManyToOne
  @JoinColumn(name = "idInstituto", referencedColumnName = "id")
  @XmlElement
  private BaseInstituto instituto;

  @ManyToOne
  @JoinColumn(name = "idAtividade", referencedColumnName = "id")
  @XmlElement
  private Atividade pai;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "pai")
  @OrderColumn(name = "viewOrder")
  private List<Atividade> subAtividades;

  private String descricao;

  @Enumerated(EnumType.STRING)
  private TipoFrequencia frequencia;

  @Enumerated(EnumType.STRING)
  private DiaSemana diaSemana;

  @DateTimeFormat(pattern = "HH:mm")
  @Temporal(TemporalType.TIME)
  private Date horaInicio;

  @DateTimeFormat(pattern = "HH:mm")
  @Temporal(TemporalType.TIME)
  private Date horaTermino;

  private int viewOrder;

  public Atividade() {
    // TODO Auto-generated constructor stub
  }

  public Atividade(String descricao, TipoFrequencia frequencia) {
    this.descricao = descricao;
    this.frequencia = frequencia;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Atividade getPai() {
    return pai;
  }

  public void setPai(Atividade pai) {
    this.pai = pai;
  }

  public List<Atividade> getSubAtividades() {
    return subAtividades;
  }

  public void setSubAtividades(List<Atividade> subAtividades) {
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

  public BaseInstituto getInstituto() {
    return instituto;
  }

  public void setInstituto(BaseInstituto instituto) {
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
    return "Atividade [id=" + id + ", instituto=" + instituto + ", pai=" + pai
        + ", descricao=" + descricao + "]";
  }
  
  
}
