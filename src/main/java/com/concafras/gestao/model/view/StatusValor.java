package com.concafras.gestao.model.view;

public class StatusValor {
	
	
	private String situacao;
	private Integer quantidade;
	
	public StatusValor() {
		// TODO Auto-generated constructor stub
	}
	
	public StatusValor(String situacao, Integer quantidade) {
		super();
		this.situacao = situacao;
		this.quantidade = quantidade;
	}

	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
