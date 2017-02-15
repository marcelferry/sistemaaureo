package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.AtividadeImplantada;

public interface AtividadeImplantadaService {
    
    public void addAtividadeImplantada(AtividadeImplantada atividade);
    public void updateAtividadeImplantada(AtividadeImplantada atividade);
    public AtividadeImplantada getAtividadeImplantada(Integer id);
    public List<AtividadeImplantada> listAtividadeImplantada();
    public List<AtividadeImplantada> listAtividadeImplantadaByInstituto(Integer id);
    public List<AtividadeImplantada> listAtividadeImplantadaByInstitutoEntidade(Integer idInstituto, Integer idEntidade);
    public void removeAtividadeImplantada(Integer id);
    
}
