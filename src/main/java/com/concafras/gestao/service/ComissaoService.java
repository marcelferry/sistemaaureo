package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.model.Comissao;
import com.concafras.gestao.model.Instituto;

public interface ComissaoService {

  public void save(Comissao comissao);

  public void update(Comissao comissao);

  public void remove(Integer id);

  public Comissao findById(Integer id);

  public List<Comissao> findAll();

}
