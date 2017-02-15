package com.concafras.gestao.form;

import java.math.BigInteger;

import com.concafras.gestao.model.BaseEntidade;


public class EntidadeOptionForm {
	
	private Integer id;
	private String razaoSocial;
	private String endereco;
	private String telefone;
	private PresidenteOptionForm presidente;
	private DirigenteOptionForm dirigente;
	private DirigenteOptionForm outro;
	private String cidade;
	private String uf;
	private BigInteger count;
	
	public EntidadeOptionForm() {
    // TODO Auto-generated constructor stub
  }
	
	public EntidadeOptionForm(BaseEntidade entidade) {
    this.id = entidade.getId();
    this.razaoSocial = entidade.getRazaoSocial();
    this.endereco = entidade.getEndereco().getEnderecoFormatado();
    if(entidade.getEndereco() != null && entidade.getEndereco().getCidade() != null ){
      this.cidade = entidade.getEndereco().getCidade().getNome();
      this.uf = entidade.getEndereco().getCidade().getEstado().getSigla();
    }
    this.presidente = new PresidenteOptionForm(entidade.getPresidente());
    this.dirigente = new DirigenteOptionForm(entidade.getDirigentes());
  }
  public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereço) {
		this.endereco = endereço;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public PresidenteOptionForm getPresidente() {
		return presidente;
	}
	public void setPresidente(PresidenteOptionForm presidente) {
		this.presidente = presidente;
	}
	
	public DirigenteOptionForm getDirigente() {
		return dirigente;
	}
	
	public void setDirigente(DirigenteOptionForm dirigente) {
		this.dirigente = dirigente;
	}
	
	public DirigenteOptionForm getOutro() {
    return outro;
  }
	
	public void setOutro(DirigenteOptionForm outro) {
    this.outro = outro;
  }
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public BigInteger getCount() {
    return count;
  }
	
	public void setCount(BigInteger count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "EntidadeOptionForm [id=" + id + ", razaoSocial=" + razaoSocial
        + ", endereco=" + endereco + ", telefone=" + telefone + ", presidente="
        + presidente + ", dirigente=" + dirigente + ", outro=" + outro
        + ", cidade=" + cidade + ", uf=" + uf + ", count=" + count + "]";
  }
	
	
	
}
