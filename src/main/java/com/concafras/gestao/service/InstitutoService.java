package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.model.Instituto;

public interface InstitutoService {
  
  //********** BASE METHODS ****************//
  public void save(Instituto instituto);

  public void update(Instituto instituto);

  public void remove(Integer id);

  public Instituto findById(Integer id);
  
  public List<Instituto> findAll();

  public List<Instituto> findByDescricaoContains(String name);

  public Instituto findByIdOverview(Integer id);
  
  public List<Instituto> findAllOverview();

}
