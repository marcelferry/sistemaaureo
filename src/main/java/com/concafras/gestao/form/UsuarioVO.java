package com.concafras.gestao.form;

import java.util.ArrayList;
import java.util.List;

import com.concafras.gestao.form.validator.PasswordMatches;
import com.concafras.gestao.model.security.AlcadaUsuario;
import com.concafras.gestao.model.security.Usuario;

@PasswordMatches
public class UsuarioVO {
	
  private Integer id;

	private String username;
	
	private String password;
	
	private String confirmPassword;
	
	private PessoaOptionForm pessoa;
	
	private List<AlcadaUsuario> userRoles;
	
	private boolean passwordExpired;
	
	private String token;
	
	public UsuarioVO() {
    // TODO Auto-generated constructor stub
  }

	public UsuarioVO(Usuario usuario) {
	  
    this.id = usuario.getId();
    this.username = usuario.getUsername();
    this.password = usuario.getPassword();
    this.passwordExpired = usuario.isPasswordExpired();
    this.userRoles = new ArrayList<AlcadaUsuario>();
    
    for(AlcadaUsuario alcada : usuario.getUserRoles()){
      userRoles.add(alcada);
    }
    
    this.pessoa = new PessoaOptionForm(usuario.getPessoa());
    
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

	public PessoaOptionForm getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaOptionForm pessoa) {
		this.pessoa = pessoa;
	}

	public List<AlcadaUsuario> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<AlcadaUsuario> userRoles) {
		this.userRoles = userRoles;
	}
	
	public boolean isPasswordExpired() {
		return passwordExpired;
	}
	
	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}
	
	public String getToken() {
    return token;
  }
	
	public void setToken(String token) {
    this.token = token;
  }
	

}
