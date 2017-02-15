package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Estado;

public interface EstadoService {
    public void addEstado(Estado estado);
    public void updateEstado(Estado estado);
    public Estado getEstado(Integer id);
    public Estado getEstadoBySigla(String sigla);
    public List<Estado> listEstado();
    public List<Estado> listEstado(String query);
    public void removeEstado(Integer id);
	
}
