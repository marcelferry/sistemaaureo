package com.concafras.gestao.form;
import java.util.List;

import com.concafras.gestao.model.Atividade;

public class AtividadeForm {

	public AtividadeForm() {
	}
	
	public AtividadeForm(Integer idInstituto) {
		this.idInstituto = idInstituto;
	}
	
	private Integer idInstituto;
	
	private String organizar;
	
	private List<Atividade> atividades;

	public Integer getIdInstituto() {
		return idInstituto;
	}
	
	public void setIdInstituto(Integer idInstituto) {
		this.idInstituto = idInstituto;
	}
	
	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	
	public String getOrganizar() {
		return organizar;
	}
	
	public void setOrganizar(String organizar) {
		this.organizar = organizar;
	}

  @Override
  public String toString() {
    return "AtividadeForm [idInstituto=" + idInstituto + ", organizar="
        + organizar + ", atividades=" + atividades + "]";
  }
	
	
}