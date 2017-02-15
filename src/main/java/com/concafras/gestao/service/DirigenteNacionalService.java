package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.model.DirigenteNacional;
import com.concafras.gestao.model.Pessoa;

public interface DirigenteNacionalService {

  void save(DirigenteNacional dirigente);

  void update(DirigenteNacional dirigente);

  void remove(Integer id);

  DirigenteNacional findById(Integer id);

  List<DirigenteNacional> findAll();

  List<DirigenteNacional> findByInstitutoId(Integer id);
  
  List<DirigenteNacional> findByDirigenteNomeCompleto(String query);

  List<DirigenteNacional> findByDirigente(Pessoa pessoa);
  
}
