package com.concafras.gestao.form;

import java.math.BigInteger;

import com.concafras.gestao.model.BaseInstituto;

public class InstitutoOptionForm {
	
	private Integer id;
	private String descricao;
	private String nome;
	private PessoaOptionForm dirigenteNacional;
	private Integer idPlano;
	private String status;
	private BigInteger count;
	
	public InstitutoOptionForm() {
    // TODO Auto-generated constructor stub
  }
	
	public InstitutoOptionForm(BaseInstituto instituto) {
	  this.id = instituto.getId();
	  this.descricao = instituto.getDescricao();
	  this.nome = instituto.getNome();
	  this.dirigenteNacional = new PessoaOptionForm( instituto.getDirigenteNacional());
  }

  public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getNome() {
    return nome;
  }
	
	public void setNome(String nome) {
    this.nome = nome;
  }
	
	public PessoaOptionForm getDirigenteNacional() {
		return dirigenteNacional;
	}
	
	public void setDirigenteNacional(PessoaOptionForm dirigenteNacional) {
		this.dirigenteNacional = dirigenteNacional;
	}
	
	public Integer getIdPlano() {
    return idPlano;
  }
	
	public void setIdPlano(Integer idPlano) {
    this.idPlano = idPlano;
  }
	
	public String getStatus() {
    return status;
  }
	
	public void setStatus(String status) {
    this.status = status;
  }
	
	public void setCount(BigInteger count) {
    this.count = count;
  }
	
	public BigInteger getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "InstitutoOptionForm [id=" + id + ", descricao=" + descricao + "]";
  }
	
}
