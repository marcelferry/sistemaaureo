package com.concafras.gestao.model.view;

import java.util.List;


public class StatusAtualInstitutoGraphicData {
	
	private Integer idinstituto;
	private String nomeInstituto;
	private List<StatusValor> statusValor;
	
	public StatusAtualInstitutoGraphicData() {
		// TODO Auto-generated constructor stub
	}
	
	public StatusAtualInstitutoGraphicData(Integer idinstituto,
			String nomeInstituto) {
		super();
		this.idinstituto = idinstituto;
		this.nomeInstituto = nomeInstituto;
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
	
	public List<StatusValor> getStatusValor() {
		return statusValor;
	}
	
	public void setStatusValor(List<StatusValor> statusValor) {
		this.statusValor = statusValor;
	}

}
