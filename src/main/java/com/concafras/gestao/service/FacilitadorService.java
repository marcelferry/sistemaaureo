package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Rodizio;

public interface FacilitadorService {
    public void addFacilitador(Facilitador facilitador);
    public void updateFacilitador(Facilitador facilitador);
    public Facilitador getFacilitador(Integer id);
    public List<Facilitador> getFacilitador(Pessoa pessoa);
    public List<Facilitador> getFacilitador(Pessoa pessoa, Rodizio rodizio);
    public List<Facilitador> listFacilitador();
    public List<Facilitador> listFacilitador(String name, int maxRows);
    public void removeFacilitador(Integer id);
	public List<Facilitador> listFacilitadorByRodizio(Integer id);
	public List<Facilitador> listFacilitadorByInstituto(Integer id);
	public List<Facilitador> listFacilitadorByInstitutoRodizio(Integer idInstituto, Integer idRodizio);
}
