package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Instituto;
import com.concafras.gestao.model.Pessoa;

public interface BaseInstitutoService {
  
  //********** BASE METHODS ****************//
  public void save(BaseInstituto instituto);

  public void update(BaseInstituto instituto);

  public void remove(Integer id);

  public BaseInstituto findById(Integer id);
  
  public List<BaseInstituto> findAll();
  
  public List<BaseInstituto> findByDescricaoLike(String name);

  public List<BaseInstituto> findByRodizio(boolean rodizio);

  public BaseInstituto findByIdOverview(Integer id);

  public List<BaseInstituto> findAllOverview();

  public List<BaseInstituto> findByDescricaoLikeOverview(String name);

  public List<BaseInstituto> findByRodizioOverview(boolean rodizio);

  public List<BaseInstituto> findByDirigenteNacional(Pessoa dirigente);
}
