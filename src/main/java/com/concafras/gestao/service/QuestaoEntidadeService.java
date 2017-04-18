package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.QuestaoEntidade;

public interface QuestaoEntidadeService {
    public void addQuestaoEntidade(QuestaoEntidade questaoEntidade);
    public void updateQuestaoEntidade(QuestaoEntidade questaoEntidade);
    public QuestaoEntidade findById(Integer id);
    public QuestaoEntidade getByQuestaoInstitutoIdAndEntidadeIdAndRodizioId(Integer idQuestaoInstituto, Integer idEntidade, Integer idRodizio);
    public List<QuestaoEntidade> findAll();
    public void removeQuestaoEntidade(Integer id);
	
}
