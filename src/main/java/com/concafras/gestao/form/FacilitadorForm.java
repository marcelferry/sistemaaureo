package com.concafras.gestao.form;

import java.util.List;

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Rodizio;

public class FacilitadorForm {
	
	private int id;
	
	private int fase;
	
	private Rodizio rodizio;
	
	private BaseInstituto instituto;
	
	private Pessoa trabalhador;
	
	private List<FacilitadorForm> facilitadores;
	
	public FacilitadorForm() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFase() {
		return fase;
	}
	
	public void setFase(int fase) {
		this.fase = fase;
	}
	
	public Rodizio getRodizio() {
		return rodizio;
	}

	public void setRodizio(Rodizio rodizio) {
		this.rodizio = rodizio;
	}

	public BaseInstituto getInstituto() {
		return instituto;
	}

	public void setInstituto(BaseInstituto instituto) {
		this.instituto = instituto;
	}

	public List<FacilitadorForm> getFacilitadores() {
		return facilitadores;
	}
	
	public void setFacilitadores(List<FacilitadorForm> facilitadores) {
		this.facilitadores = facilitadores;
	}
	
	public Pessoa getTrabalhador() {
		return trabalhador;
	}
	
	public void setTrabalhador(Pessoa trabalhador) {
		this.trabalhador = trabalhador;
	}

  @Override
  public String toString() {
    return "FacilitadorForm [id=" + id + ", fase=" + fase + ", rodizio="
        + rodizio + ", instituto=" + instituto + ", trabalhador=" + trabalhador
        + ", facilitadores=" + facilitadores + "]";
  }
	
	
}
