package com.concafras.gestao.rest.model;

import java.util.List;

import com.concafras.gestao.form.UsuarioVO;

public class ResultAuthentication extends ResultBase {
  private UsuarioVO usuario;
  private List<String> authorities;

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

  public void setAuthorities(List<String> listaRoles) {
	this.authorities = listaRoles;
  }
  
  public List<String> getAuthorities() {
	return authorities;
  }

}