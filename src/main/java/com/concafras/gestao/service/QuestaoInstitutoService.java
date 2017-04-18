package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.QuestaoInstituto;

public interface QuestaoInstitutoService {
    public void addQuestaoInstituto(QuestaoInstituto questaoInstituto);
    public void updateQuestaoInstituto(QuestaoInstituto questaoInstituto);
    public QuestaoInstituto findById(Integer id);
    public QuestaoInstituto getByInstitutoIdAndRodizioId(Integer idInstituto, Integer idRodizio);
    public List<QuestaoInstituto> findAll();
    public List<QuestaoInstituto> findByInstitutoId(Integer idInstituto);
    public void removeQuestaoInstituto(Integer id);
    public void enableQuestaoInstituto(Integer questaoInstitutoId);
    public void disableQuestaoInstituto(Integer questaoInstitutoId);
	
}
