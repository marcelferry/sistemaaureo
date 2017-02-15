package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Telefone;

public interface TelefoneService {
	
    public void addTelefone(Telefone telefone);
    public void updateTelefone(Telefone telefone);
    public Telefone getTelefone(Integer id);
    public List<Telefone> listTelefone();
    public List<Telefone> listTelefone(String query);
    public void removeTelefone(Integer id);
	
}
