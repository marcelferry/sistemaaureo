package com.concafras.gestao.form;

import com.concafras.gestao.model.Presidente;

public class PresidenteOptionForm {
	
	private Integer id;
	private String nome;
	
	public PresidenteOptionForm(Integer id, String nomeCompleto) {
		this.id = id;
		this.nome = nomeCompleto;
	}
	public PresidenteOptionForm(Presidente presidente) {
	  if(presidente != null){
      this.id = presidente.getId();
      this.nome = presidente.getPessoa().getNomeCompleto();
	  }
  }
  public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
  @Override
  public String toString() {
    return "PresidenteOptionForm [id=" + id + ", nome=" + nome + "]";
  }
	
	
	
}
