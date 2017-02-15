package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Dirigente;

public interface DirigenteService {
	
    public void addDirigente(Dirigente dirigente);
    public void updateDirigente(Dirigente dirigente);
    public Dirigente getDirigente(Integer id);
    public List<Dirigente> listDirigente();
    public List<Dirigente> listDirigente(String query);
    public void removeDirigente(Integer id);
	
}
