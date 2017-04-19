package com.concafras.gestao.model.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import com.concafras.gestao.model.Pessoa;

@Entity
@Table(name = "USUARIOS")
public class Usuario implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5440504272126246939L;

  @Id
  @SequenceGenerator(name = "userprofile_id_seq_name", sequenceName = "userprofile_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "userprofile_id_seq_name", strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String username;

  private String password;

  @Transient
  private String confirmPassword;

  @OneToOne
  @JoinColumn(name = "idPessoa", referencedColumnName = "id")
  @XmlElement
  private Pessoa pessoa;

  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(name = "USUARIO_ALCADAS", 
    joinColumns = { @JoinColumn(name = "IdUser", referencedColumnName = "id") },
    inverseJoinColumns = { @JoinColumn(name = "IdRole", referencedColumnName = "id") 
  })
  @XmlElement
  private Set<AlcadaUsuario> userRoles;
  
  @Transient
  private List<Integer> rolesIds;

  
  private boolean passwordExpired;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLogin;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
  private Set<LoginHistory> loginHistory;
  
  public Usuario() {}

  public Usuario(Integer id, String username) {
    this.id = id;
    this.username = username;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public Pessoa getPessoa() {
    return pessoa;
  }

  public void setPessoa(Pessoa pessoa) {
    this.pessoa = pessoa;
  }

  public Set<AlcadaUsuario> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set<AlcadaUsuario> userRoles) {
    this.userRoles = userRoles;
  }

  public boolean isPasswordExpired() {
    return passwordExpired;
  }

  public void setPasswordExpired(boolean passwordExpired) {
    this.passwordExpired = passwordExpired;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public Set<LoginHistory> getLoginHistory() {
    return loginHistory;
  }

  public void setLoginHistory(Set<LoginHistory> loginHistory) {
    this.loginHistory = loginHistory;
  }

  @Override
  public String toString() {
    return "Usuario [id=" + id + ", username=" + username + ", password="
        + password + ", confirmPassword=" + confirmPassword + ", pessoa="
        + pessoa + ", userRoles=" + userRoles + ", passwordExpired="
        + passwordExpired + ", lastLogin=" + lastLogin + "]";
  }

  public void setRolesIds(List<Integer> ids) {
    this.rolesIds = ids;
  }
  
  public List<Integer> getRolesIds() {
    return rolesIds;
  }
  
  

}
