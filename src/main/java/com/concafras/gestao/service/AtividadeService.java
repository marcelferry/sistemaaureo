package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Atividade;

public interface AtividadeService {
    
    public void addAtividade(Atividade atividade);
    public void updateAtividade(Atividade atividade);
    public Atividade getAtividade(Integer id);
    public List<Atividade> listAtividade();
    public List<Atividade> listAtividadeByInstituto(Integer id);
    public void removeAtividade(Integer id);
    
}
