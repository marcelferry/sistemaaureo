package com.concafras.gestao.form;

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Rodizio;

public class RelatorioForm {

  public Rodizio rodizio;
  
  public BaseInstituto instituto;

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
  
  
  
}
