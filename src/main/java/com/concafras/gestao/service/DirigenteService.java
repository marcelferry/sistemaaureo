package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Dirigente;
import com.concafras.gestao.model.Pessoa;

public interface DirigenteService {
	
    public void save(Dirigente dirigente);
    public void update(Dirigente dirigente);
    public Dirigente findById(Integer id);
    public List<Dirigente> findAll();
    public void delete(Integer id);

    public List<Dirigente> findByNome(String query);
    public List<Dirigente> findByTrabalhador(Pessoa pessoa);
	
}
