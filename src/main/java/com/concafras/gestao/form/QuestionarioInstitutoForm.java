package com.concafras.gestao.form;

import java.util.List;

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.QuestaoInstituto;

public class QuestionarioInstitutoForm {
	
	//private int id;
	
	private Integer rodizioId;
	
	private Integer institutoId;
	
//	private Rodizio rodizio;
	
	private BaseInstituto instituto;
	
	private List<QuestaoInstituto> itens;
	
	private String organizar;
	
	public QuestionarioInstitutoForm() {
		// TODO Auto-generated constructor stub
	}
	
	public QuestionarioInstitutoForm(Integer id) {
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

	public List<QuestaoInstituto> getItens() {
		return itens;
	}

	public void setItens(List<QuestaoInstituto> itens) {
		this.itens = itens;
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
