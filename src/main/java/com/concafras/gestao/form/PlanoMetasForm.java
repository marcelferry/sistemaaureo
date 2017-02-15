package com.concafras.gestao.form;

import java.util.List;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.TipoContratante;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Cidade;
import com.concafras.gestao.model.Dirigente;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;

public class PlanoMetasForm {

  private Integer id;

  private Rodizio rodizio;

  /*
   * 01 - Selecao Rodizio / Instituto 02 - Selecao Cidade / Entidade /
   * Contratante 03 - Preenchimento das Metas 04 - Validação Secretaria
   */
  private Integer fase;

  private Cidade cidade;

  private Pessoa facilitador;

  private BaseInstituto instituto;

  private Dirigente dirigenteInstituto;

  private BaseEntidade entidade;

  private Pessoa outro;

  private Pessoa presidente;

  private Pessoa coordenador;

  private Pessoa contratante;

  private TipoContratante tipoContratante;

  private List<MetaForm> dependencias;

  private List<Anotacao> anotacoes;

  private boolean finalizado;

  private boolean validado;

  private EventoMeta evento;

  private String nomePresidente;

  private String nomeCoordenador;

  private String nomeContratante;

  private String telefonePresidente;

  private String telefoneCoordenador;

  private String telefoneContratante;

  private String emailPresidente;

  private String emailCoordenador;

  private String emailContratante;

  public PlanoMetasForm() {
    // TODO Auto-generated constructor stub
  }

  public PlanoMetasForm(PlanoMetas plano) {
    
    this.id = plano.getId();
    this.rodizio = plano.getRodizio();
    this.entidade = plano.getEntidade();
    this.instituto = plano.getInstituto();
    this.facilitador = plano.getFacilitador();
    this.tipoContratante = plano.getTipoContratante();
    this.presidente = plano.getPresidente();
    this.coordenador = plano.getCoordenador();
    this.contratante = plano.getContratante();
    this.evento = plano.getEvento();
    
    this.nomePresidente = plano.getNomePresidente();
    this.nomeCoordenador = plano.getNomeCoordenador();
    this.nomeContratante = plano.getNomeContratante();
    this.telefonePresidente = plano.getTelefonePresidente();
    this.telefoneCoordenador = plano.getTelefoneCoordenador();
    this.telefoneContratante = plano.getTelefoneContratante();
    this.emailPresidente = plano.getEmailPresidente();
    this.emailCoordenador = plano.getEmailCoordenador();
    this.emailContratante = plano.getEmailContratante();
    
    
  }

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

  public Integer getFase() {
    return fase;
  }

  public void setFase(Integer fase) {
    this.fase = fase;
  }

  public Cidade getCidade() {
    return cidade;
  }

  public void setCidade(Cidade cidade) {
    this.cidade = cidade;
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

  public Pessoa getOutro() {
    return outro;
  }

  public void setOutro(Pessoa outro) {
    this.outro = outro;
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

  public List<MetaForm> getDependencias() {
    return dependencias;
  }

  public void setDependencias(List<MetaForm> metas) {
    this.dependencias = metas;
  }

  public List<Anotacao> getAnotacoes() {
    return anotacoes;
  }

  public void setAnotacoes(List<Anotacao> listaAnotacoes) {
    this.anotacoes = listaAnotacoes;
  }

  public Dirigente getDirigenteInstituto() {
    return dirigenteInstituto;
  }

  public void setDirigenteInstituto(Dirigente dirigenteInstituto) {
    this.dirigenteInstituto = dirigenteInstituto;
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

  public String getNomePresidente() {
    return nomePresidente;
  }

  public void setNomePresidente(String nomePresidente) {
    this.nomePresidente = nomePresidente;
  }

  public String getNomeCoordenador() {
    if (coordenador != null) {
      return coordenador.getNomeCompleto();
    } else {
      return nomeCoordenador;
    }
  }

  public void setNomeCoordenador(String nomeCoordenador) {
    this.nomeCoordenador = nomeCoordenador;
  }

  public String getNomeContratante() {
    if (contratante != null) {
      return contratante.getNomeCompleto();
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
      return coordenador.getPrimeiroEmail();
    } else {
      return emailCoordenador;
    }
  }

  public void setEmailCoordenador(String emailCoordenador) {
    this.emailCoordenador = emailCoordenador;
  }

  public String getEmailContratante() {
    if (contratante != null) {
      return contratante.getPrimeiroEmail();
    } else {
      return emailContratante;
    }
  }

  public void setEmailContratante(String emailContratante) {
    this.emailContratante = emailContratante;
  }

  @Override
  public String toString() {
    return "PlanoMetasForm [id=" + id + ", rodizio=" + rodizio + ", instituto="
        + instituto + ", entidade=" + entidade + "]";
  }
  
  

}
