package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Presidente;

public interface PresidenteService {
	
    public void addPresidente(Presidente presidente);
    public void updatePresidente(Presidente presidente);
    public Presidente getPresidente(Integer id);
    public List<Presidente> getPresidente(Pessoa pessoa);
    public List<Presidente> listPresidente();
    public List<Presidente> listPresidente(String query);
    public void removePresidente(Integer id);
	
}
