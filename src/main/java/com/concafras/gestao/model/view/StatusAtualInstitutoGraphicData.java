package com.concafras.gestao.model.view;

import java.util.List;


public class StatusAtualInstitutoGraphicData {
	
	private Integer idinstituto;
	private String nomeInstituto;
	private String nomeCurto;
	private List<StatusValor> statusValor;
	
	public StatusAtualInstitutoGraphicData() {
		// TODO Auto-generated constructor stub
	}
	
	public StatusAtualInstitutoGraphicData(Integer idinstituto,
			String nomeInstituto, String nomeCurto) {
		super();
		this.idinstituto = idinstituto;
		this.nomeInstituto = nomeInstituto;
		this.nomeCurto = nomeCurto;
	}



	public Integer getIdinstituto() {
		return idinstituto;
	}
	
	public void setIdinstituto(Integer idinstituto) {
		this.idinstituto = idinstituto;
	}
	
	public String getNomeInstituto() {
		return nomeInstituto;
	}
	
	public void setNomeInstituto(String nomeInstituto) {
		this.nomeInstituto = nomeInstituto;
	}
	
	public String getNomeCurto() {
		return nomeCurto;
	}
	
	public void setNomeCurto(String nomeCurto) {
		this.nomeCurto = nomeCurto;
	}
	
	public List<StatusValor> getStatusValor() {
		return statusValor;
	}
	
	public void setStatusValor(List<StatusValor> statusValor) {
		this.statusValor = statusValor;
	}

}
