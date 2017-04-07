package com.concafras.gestao.model;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.TipoContratante;

@Entity
@Table(name="CONTRATO_METAS")
@Access(AccessType.FIELD)
public class PlanoMetas implements Serializable{
	
	/**
   * Generated SerialVerionUID
   */
  private static final long serialVersionUID = -7657616728842158935L;

  @Id  
  @SequenceGenerator(name = "plano_modelo_id_seq_name", sequenceName = "plano_modelo_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "plano_modelo_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;
  
  /**
   * Rodizio do exercicio
   */
  @ManyToOne
  @JoinColumn(name = "idRodizio", referencedColumnName = "id")
  @XmlElement
  private Rodizio rodizio;
  
  /** 
   * Entidade do plano de metas
   */
  @ManyToOne
  @JoinColumn(name = "idEntidade", referencedColumnName = "id")
  @XmlElement
  private BaseEntidade entidade;
  
  /**
   * Instituto/Comissao do Plano de Metas
   */
  @ManyToOne
  @JoinColumn(name = "idInstituto", referencedColumnName = "id")
  @XmlElement
  private BaseInstituto instituto;
  
  /**
   * Facilitador
   */
  @ManyToOne
  @JoinColumn(name = "idFacilitador", referencedColumnName = "id")
  @XmlElement
  private Pessoa facilitador;
  
  /**
   * Responsável pela contratação das metas
   */
  @ManyToOne
  @JoinColumn(name = "idPresidente", referencedColumnName = "id")
  @XmlElement
  private Pessoa presidente;
  
  /**
   * Responsável pela contratação das metas
   */
  @ManyToOne
  @JoinColumn(name = "idCoordenador", referencedColumnName = "id")
  @XmlElement
  private Pessoa coordenador;
  
  /**
   * Responsável pela contratação das metas
   */
  @ManyToOne
  @JoinColumn(name = "idContratante", referencedColumnName = "id")
  @XmlElement
  private Pessoa contratante;

  private String nomePresidente;

  private String nomeCoordenador;

  private String nomeContratante;

  private String telefonePresidente;

  private String telefoneCoordenador;

  private String telefoneContratante;

  private String emailPresidente;

  private String emailCoordenador;

  private String emailContratante;
  
  private boolean validado;
  
  private boolean finalizado;
  
  @Transient
  private EventoMeta evento;
  
  /**
   *	Tipo contratante 
   */
  @Enumerated(EnumType.STRING)
  private TipoContratante tipoContratante;
  
  
  /**
   * Observações
   */
  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name="CONTRATO_METAS_ANOTACOES",
    joinColumns = @JoinColumn(name = "idPlanoMetas", 
                              referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "idAnotacao", 
                              referencedColumnName = "id"))
  @XmlElement
  private List<Anotacao> anotacoes;

  
  @Transient
  private List<MetaEntidade> metas;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Rodizio getRodizio() {
    return rodizio;
  }

  public void setRodizio(Rodizio rodizio) {
    this.rodizio = rodizio;
  }

  public Pessoa getFacilitador() {
    return facilitador;
  }

  public void setFacilitador(Pessoa facilitador) {
    this.facilitador = facilitador;
  }

  public BaseInstituto getInstituto() {
    return instituto;
  }

  public void setInstituto(BaseInstituto instituto) {
    this.instituto = instituto;
  }

  public BaseEntidade getEntidade() {
    return entidade;
  }

  public void setEntidade(BaseEntidade entidade) {
    this.entidade = entidade;
  }

  public Pessoa getPresidente() {
    return presidente;
  }

  public void setPresidente(Pessoa presidente) {
    this.presidente = presidente;
  }

  public Pessoa getCoordenador() {
    return coordenador;
  }

  public void setCoordenador(Pessoa coordenador) {
    this.coordenador = coordenador;
  }

  public Pessoa getContratante() {
    return contratante;
  }

  public void setContratante(Pessoa contratante) {
    this.contratante = contratante;
  }

  public TipoContratante getTipoContratante() {
    return tipoContratante;
  }

  public void setTipoContratante(TipoContratante tipoContratante) {
    this.tipoContratante = tipoContratante;
  }

  public List<MetaEntidade> getMetas() {
  	return metas;
  }

  public void setMetas(List<MetaEntidade> listaMetas) {
  	this.metas = listaMetas;
  }

  public List<Anotacao> getAnotacoes() {
    return anotacoes;
  }

  public void setAnotacoes(List<Anotacao> listaAnotacoes) {
    this.anotacoes = listaAnotacoes;
  }

  public String getNomePresidente() {
    return nomePresidente;
  }

  public void setNomePresidente(String nomePresidente) {
    this.nomePresidente = nomePresidente;
  }

  public String getNomeCoordenador() {
    return nomeCoordenador;
  }

  public void setNomeCoordenador(String nomeCoordenador) {
    this.nomeCoordenador = nomeCoordenador;
  }

  public String getNomeContratante() {
    return nomeContratante;
  }

  public void setNomeContratante(String nomeContratante) {
    this.nomeContratante = nomeContratante;
  }

  public String getTelefonePresidente() {
    return telefonePresidente;
  }

  public void setTelefonePresidente(String telefonePresidente) {
    this.telefonePresidente = telefonePresidente;
  }

  public String getTelefoneCoordenador() {
    return telefoneCoordenador;
  }

  public void setTelefoneCoordenador(String telefoneCoordenador) {
    this.telefoneCoordenador = telefoneCoordenador;
  }

  public String getTelefoneContratante() {
    return telefoneContratante;
  }

  public void setTelefoneContratante(String telefoneContratante) {
    this.telefoneContratante = telefoneContratante;
  }

  public String getEmailPresidente() {
    return emailPresidente;
  }

  public void setEmailPresidente(String emailPresidente) {
    this.emailPresidente = emailPresidente;
  }

  public String getEmailCoordenador() {
    return emailCoordenador;
  }

  public void setEmailCoordenador(String emailCoordenador) {
    this.emailCoordenador = emailCoordenador;
  }

  public String getEmailContratante() {
    return emailContratante;
  }

  public void setEmailContratante(String emailContratante) {
    this.emailContratante = emailContratante;
  }
  
  public boolean isFinalizado() {
    return finalizado;
  }

  public void setFinalizado(boolean finalizado) {
    this.finalizado = finalizado;
  }

  public boolean isValidado() {
    return validado;
  }

  public void setValidado(boolean validado) {
    this.validado = validado;
  }

  public EventoMeta getEvento() {
    return evento;
  }

  public void setEvento(EventoMeta evento) {
    this.evento = evento;
  }
  

  @Override
  public String toString() {
    return "PlanoMetas [id=" + id + ", rodizio=" + rodizio + ", entidade="
        + entidade + ", instituto=" + instituto + "]";
  }
  
}
