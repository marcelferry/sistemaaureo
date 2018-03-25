package com.concafras.gestao.form;

import java.util.ArrayList;
import java.util.List;

public class ContatoBasico {

  private String tipo;
  private String nome;
  private List<String> telefones;
  private List<String> emails;
  
  public ContatoBasico() {
    // TODO Auto-generated constructor stub
  }
  
  public ContatoBasico(String tipo) {
    this.tipo = tipo;
  }
  
  public String getTipo() {
    return tipo;
  }
  
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  
  public String getNome() {
    return nome;
  }
  
  public void setNome(String nome) {
    this.nome = nome;
  }
  
  public List<String> getTelefones() {
    return telefones;
  }
  
  public void setTelefones(List<String> telefones) {
    this.telefones = telefones;
  }
  
  public void addTelefone(String telefone){
    if(this.telefones == null){
      this.telefones = new ArrayList<String>();
    }
    this.telefones.add(telefone);
  }
  
  public List<String> getEmails() {
    return emails;
  }
  
  public void setEmails(List<String> emails) {
    this.emails = emails;
  }
  
  public void addEmail(String email){
    if(this.emails == null){
      this.emails = new ArrayList<String>();
    }
    this.emails.add(email);
  }

  @Override
  public String toString() {
    return "ContatoBasico [tipo=" + tipo + ", nome=" + nome
        + ", telefones=" + telefones + ", emails=" + emails + "]";
  }
  
  

}
