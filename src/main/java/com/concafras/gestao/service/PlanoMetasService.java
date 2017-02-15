package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.model.view.StatusAtualInstitutoGraphicData;

public interface PlanoMetasService {
    
    void save(PlanoMetas planoMetas);
    void update(PlanoMetas planoMetas);
    void remove(Integer id);
    PlanoMetas findById(Integer id);
    PlanoMetas findByInstitutoIdAndRodizioId(Integer idInstituto, Integer idRodizio);
    PlanoMetas findByEntidadeIdAndInstitutoIdAndRodizioId(Integer idEntidade, Integer idInstituto, Integer idRodizio);
    List<PlanoMetas> findAll();
    List<PlanoMetas> findByInstitutoId(Integer id);
    List<PlanoMetas> findByRodizioId(Integer id);
    List<PlanoMetas> findByEntidadeId(Integer id);
    List<PlanoMetas> findByEntidadeIdAndInstitutoId(Integer idEntidade, Integer idInstituto);
    List<PlanoMetas> findByEntidadeIdAndRodizioId(Integer idEntidade, Integer idRodizio);
    
    List<StatusAtualInstitutoGraphicData> getStatusAtualGraphicData(Integer ciclo, Integer entidade);
    List<StatusAtualInstitutoGraphicData> getStatusPlanejadoGraphicData(Integer ciclo, Integer entidade);
    List<EntidadeOptionForm> getEntidadesParticipantes(Integer ciclo);
    List<EntidadeOptionForm> getEntidadesStatus(Integer ciclo);
    List<InstitutoOptionForm> getInstitutosPreechidos(Integer ciclo, Integer entidade);
    List<InstitutoOptionForm> getInstitutosContratados(Integer ciclo, Integer entidade);
    List<InstitutoOptionForm> getInstitutosStatus(Integer ciclo, Integer entidade);
    
    List<StatusAtualInstitutoGraphicData> getStatusContratadoGeralData(Integer ciclo, Integer instituto);
    List<StatusAtualInstitutoGraphicData> getStatusContratadoGraphicData(Integer ciclo, Integer entidade);
    List<StatusAtualInstitutoGraphicData> getStatusValidacaoGeralData(Integer ciclo);
    List<StatusAtualInstitutoGraphicData> getStatusContratadoPorRegiaoData(Integer ciclo, Integer instituto);
    List<StatusAtualInstitutoGraphicData> getStatusContratadoPorInstitutoData(Integer ciclo, Integer entidade);
    List<ResumoMetaEntidade> getListaContratadoGeralData(Integer ciclo, Integer regiao, Integer entidade, Integer instituto, String status);
    
    List<EntidadeOptionForm> listRangeEntidadesCiclo(Integer ciclo, String name, String sortCol, String sortDir, int startRange, int maxRows);
    
    Long countListRangeEntidadesCiclo(Integer ciclo, String name);
    
}
