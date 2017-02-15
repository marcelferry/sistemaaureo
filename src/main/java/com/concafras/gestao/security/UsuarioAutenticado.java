package com.concafras.gestao.security;

import java.util.Collection;

import org.springframework.security.core.userdetails.User;

import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.security.Usuario;

public class UsuarioAutenticado extends User {

	public static final long serialVersionUID = -3531439484732724601L;
	
	private final Integer id;

	private final Pessoa pessoa;
	
	private final boolean passwordExpired;

	public UsuarioAutenticado(Pessoa pessoa, Integer id, String username, String password,  boolean passwordExpired, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection authorities) {

		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);

		this.id = id;
		this.pessoa = pessoa;
		this.passwordExpired = passwordExpired;

	}
	
	public Usuario getUsuario(){
	  return new Usuario(id, getUsername());
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public boolean isPasswordExpired() {
		return passwordExpired;
	}

}