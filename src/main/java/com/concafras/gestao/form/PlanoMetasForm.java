package com.concafras.gestao.form;

import java.util.List;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.TipoContratante;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;

public class PlanoMetasForm {

  private Integer id;

  private RodizioVO rodizio;

  private EntidadeOptionForm entidade;

  private InstitutoOptionForm instituto;

  private PessoaOptionForm facilitador;

  private PessoaOptionForm presidente;

  private PessoaOptionForm coordenador;

  private PessoaOptionForm contratante;

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
  
  private EventoMeta evento;
  
  private TipoContratante tipoContratante;
  
  private List<MetaForm> dependencias;

  private List<AnotacaoVO> anotacoes;
  
  /*
   * 01 - Selecao Rodizio / Instituto 02 - Selecao Cidade / Entidade /
   * Contratante 03 - Preenchimento das Metas 04 - Validação Secretaria
   */
  private Integer fase;

  private CidadeVO cidade;

  private DirigenteOptionForm dirigenteInstituto;

  private PessoaOptionForm outro;
  
  private Long prioridades;
  

  public PlanoMetasForm() {
    // TODO Auto-generated constructor stub
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public RodizioVO getRodizio() {
    return rodizio;
  }

  public void setRodizio(RodizioVO rodizio) {
    this.rodizio = rodizio;
  }

  public PessoaOptionForm getFacilitador() {
    return facilitador;
  }

  public void setFacilitador(PessoaOptionForm facilitador) {
    this.facilitador = facilitador;
  }

  public InstitutoOptionForm getInstituto() {
    return instituto;
  }

  public void setInstituto(InstitutoOptionForm instituto) {
    this.instituto = instituto;
  }

  public EntidadeOptionForm getEntidade() {
    return entidade;
  }

  public void setEntidade(EntidadeOptionForm entidade) {
    this.entidade = entidade;
  }

  public PessoaOptionForm getPresidente() {
    return presidente;
  }

  public void setPresidente(PessoaOptionForm presidente) {
    this.presidente = presidente;
  }

  public PessoaOptionForm getCoordenador() {
    return coordenador;
  }

  public void setCoordenador(PessoaOptionForm coordenador) {
    this.coordenador = coordenador;
  }

  public PessoaOptionForm getContratante() {
    return contratante;
  }

  public void setContratante(PessoaOptionForm contratante) {
    this.contratante = contratante;
  }

  public TipoContratante getTipoContratante() {
    return tipoContratante;
  }

  public void setTipoContratante(TipoContratante tipoContratante) {
    this.tipoContratante = tipoContratante;
  }

  public List<MetaForm> getDependencias() {
    return dependencias;
  }

  public void setDependencias(List<MetaForm> metas) {
    this.dependencias = metas;
  }

  public List<AnotacaoVO> getAnotacoes() {
    return anotacoes;
  }

  public void setAnotacoes(List<AnotacaoVO> listaAnotacoes) {
    this.anotacoes = listaAnotacoes;
  }

  public String getNomePresidente() {
    return nomePresidente;
  }

  public void setNomePresidente(String nomePresidente) {
    this.nomePresidente = nomePresidente;
  }

  public String getNomeCoordenador() {
    if (coordenador != null) {
      return coordenador.getNome();
    } else {
      return nomeCoordenador;
    }
  }

  public void setNomeCoordenador(String nomeCoordenador) {
    this.nomeCoordenador = nomeCoordenador;
  }

  public String getNomeContratante() {
    if (contratante != null) {
      return contratante.getNome();
    } else {
      return nomeContratante;
    }
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
    if (coordenador != null) {
      return coordenador.getEmail();
    } else {
      return emailCoordenador;
    }
  }

  public void setEmailCoordenador(String emailCoordenador) {
    this.emailCoordenador = emailCoordenador;
  }

  public String getEmailContratante() {
    if (contratante != null) {
      return contratante.getEmail();
    } else {
      return emailContratante;
    }
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

  public Integer getFase() {
    return fase;
  }

  public void setFase(Integer fase) {
    this.fase = fase;
  }

  public CidadeVO getCidade() {
    return cidade;
  }

  public void setCidade(CidadeVO cidade) {
    this.cidade = cidade;
  }
  
  public DirigenteOptionForm getDirigenteInstituto() {
    return dirigenteInstituto;
  }

  public void setDirigenteInstituto(DirigenteOptionForm dirigenteInstituto) {
    this.dirigenteInstituto = dirigenteInstituto;
  }

  public PessoaOptionForm getOutro() {
    return outro;
  }

  public void setOutro(PessoaOptionForm outro) {
    this.outro = outro;
  }

  public Long getPrioridades() {
    return prioridades;
  }
  
  public void setPrioridades(Long prioridades) {
    this.prioridades = prioridades;
  }

  @Override
  public String toString() {
    return "PlanoMetasForm [id=" + id + ", rodizio=" + rodizio + ", instituto="
        + instituto + ", entidade=" + entidade + "]";
  }
  
}
