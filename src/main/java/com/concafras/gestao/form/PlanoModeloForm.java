package com.concafras.gestao.form;

import java.util.List;

import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.Rodizio;

public class PlanoModeloForm {
	
	//private int id;
	
	private Integer rodizioId;
	
	private Integer institutoId;
	
//	private Rodizio rodizio;
	
	private BaseInstituto instituto;
	
	private List<MetaInstituto> itens;
	
	private List<Anotacao> anotacoes;
	
	private String organizar;
	
	public PlanoModeloForm() {
		// TODO Auto-generated constructor stub
	}
	
	public PlanoModeloForm(Integer id) {
		this.instituto = new BaseInstituto();
		this.instituto.setId(id);
	}

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

//	public Rodizio getRodizio() {
//		return rodizio;
//	}
//
//	public void setRodizio(Rodizio rodizio) {
//		this.rodizio = rodizio;
//	}

	public BaseInstituto getInstituto() {
		return instituto;
	}

	public void setInstituto(BaseInstituto instituto) {
		this.instituto = instituto;
	}

	public List<MetaInstituto> getItens() {
		return itens;
	}

	public void setItens(List<MetaInstituto> itens) {
		this.itens = itens;
	}

	public List<Anotacao> getAnotacoes() {
		return anotacoes;
	}

	public void setAnotacoes(List<Anotacao> anotacoes) {
		this.anotacoes = anotacoes;
	}
	
	public String getOrganizar() {
		return organizar;
	}
	
	public void setOrganizar(String organizar) {
		this.organizar = organizar;
	}
	
	public Integer getRodizioId() {
		return rodizioId;
	}
	
	public void setRodizioId(Integer rodizioId) {
		this.rodizioId = rodizioId;
	}
	
	public Integer getInstitutoId() {
		return institutoId;
	}
	
	public void setInstitutoId(Integer institutoId) {
		this.institutoId = institutoId;
	}
	

}
