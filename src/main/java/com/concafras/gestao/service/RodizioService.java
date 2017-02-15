package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.model.Rodizio;

public interface RodizioService {

  // ********** BASE METHODS ****************//
  public void save(Rodizio rodizio);

  public void update(Rodizio rodizio);

  public void remove(Integer id);

  public Rodizio findById(Integer id);

  public List<Rodizio> findAll();

  public List<Rodizio> findByCicloContains(String query);
  
  public Rodizio findByAtivoTrue();

}
