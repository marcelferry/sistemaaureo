package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.MetaInstituto;

public interface MetasInstitutoService {
    
    public void addMetasInstituto(MetaInstituto planoModelo);
    public void updateMetasInstituto(MetaInstituto planoModelo);
    public void removeMetaInstituto(Integer id);
    public void disableMetaInstituto(Integer id);
    public void enableMetaInstituto(Integer id);
    public void priorityMetaInstituto(Integer id, Integer priority);
    public MetaInstituto getMetasInstituto(Integer id);
    
    public List<MetaInstituto> listMetaInstituto();
    public List<MetaInstituto> listMetaInstitutoByInstituto(Integer id, boolean removeExcluidos);
    public List<MetaInstituto> listMetaInstitutoByInstitutoResumo(Integer id);
    public List<MetaInstituto> listMetaInstitutoByInstitutoRodizio(Integer idInstituto, Integer idRodizio);
   
    
}
