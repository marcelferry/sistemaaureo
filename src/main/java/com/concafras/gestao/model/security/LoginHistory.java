package com.concafras.gestao.model.security;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LOGIN_HISTORY")
public class LoginHistory {
  
  @Id
  @SequenceGenerator(name = "login_history_id_seq_name", sequenceName = "login_history_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "login_history_id_seq_name", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "logged_in")
  private Date loggedIn;
  
  @Column(name = "user_ip")
  private String userIp;
  
  @Column(name = "status")
  private String status;
  
  @ManyToOne(optional = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private Usuario user;



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

  
  public Usuario getUser() {
    return user;
  }

  public void setUser(Usuario user) {
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