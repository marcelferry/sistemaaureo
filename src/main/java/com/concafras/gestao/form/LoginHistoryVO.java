package com.concafras.gestao.form;

import java.util.Date;

public class LoginHistoryVO {
  
  private Long id;
  
  private Date loggedIn;
  
  private String userIp;
  
  private String status;
  
  private UsuarioVO user;

  public Long getId() {
    return id;
  }

  public void setId(Long loginHistoryId) {
    this.id = loginHistoryId;
  }

 
  public Date getLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(Date loggedIn) {
    this.loggedIn = loggedIn;
  }

  
  public UsuarioVO getUser() {
    return user;
  }

  public void setUser(UsuarioVO user) {
    this.user = user;
  }
  
  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String ip) {
    this.userIp = ip;
  }
  
  public String getStatus() {
    return status;
  }

  public void setStatus(String string) {
    this.status = string;
  }
}