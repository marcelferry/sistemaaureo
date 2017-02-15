package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.model.Cidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.security.LoginHistory;
import com.concafras.gestao.model.security.Usuario;

public interface UsuarioService {
  public void save(Usuario pessoa);

  public void update(Usuario pessoa);

  public void remove(Integer id);

  public Usuario findById(Integer id);

  public List<Usuario> findAll();

  public Usuario findByPessoa(Pessoa pessoa);

  public Usuario findByUsername(String username);
  
}
