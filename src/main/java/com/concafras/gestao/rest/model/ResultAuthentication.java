package com.concafras.gestao.rest.model;

import com.concafras.gestao.form.UsuarioVO;

public class ResultAuthentication extends ResultBase {
  private UsuarioVO usuario;

  public ResultAuthentication() {

  }

  public ResultAuthentication(ResultBase resultBase) {
    super(resultBase);
  }
  
  public UsuarioVO getUsuario() {
    return usuario;
  }
  
  public void setUsuario(UsuarioVO usuario) {
    this.usuario = usuario;      
  }

}