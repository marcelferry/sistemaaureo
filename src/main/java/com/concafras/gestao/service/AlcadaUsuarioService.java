package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.model.security.AlcadaUsuario;

public interface AlcadaUsuarioService {
  public void save(AlcadaUsuario pessoa);

  public void update(AlcadaUsuario pessoa);

  public void remove(Integer id);

  public AlcadaUsuario findById(Integer id);

  public List<AlcadaUsuario> findAll();
  
}
