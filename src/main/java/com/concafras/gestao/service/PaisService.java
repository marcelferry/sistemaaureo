package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Pais;

public interface PaisService {
    public void addPais(Pais pais);
    public void updatePais(Pais pais);
    public Pais getPais(Integer id);
    public List<Pais> listPais();
    public List<Pais> listPais(String query);
    public void removePais(Integer id);
}
