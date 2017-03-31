package com.concafras.gestao.model.view;

import com.concafras.gestao.model.BaseInstituto;

public class PreContrato {
  
  BaseInstituto instituto;
  boolean finalizadoRodizio;
  boolean inicializado;
  
  public BaseInstituto getInstituto() {
    return instituto;
  }
  
  public void setInstituto(BaseInstituto instituto) {
    this.instituto = instituto;
  }
  
  public boolean isFinalizadoRodizio() {
    return finalizadoRodizio;
  }
  
  public void setFinalizadoRodizio(boolean finalizadoRodizio) {
    this.finalizadoRodizio = finalizadoRodizio;
  }
  
  public boolean isInicializado() {
    return inicializado;
  }
  
  public void setInicializado(boolean inicializado) {
    this.inicializado = inicializado;
  }

}
