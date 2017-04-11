package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.view.ResumoMetaEntidade;

public interface MetaService {

  void save(MetaEntidade meta);

  void update(MetaEntidade meta);

  void remove(Integer id);

  MetaEntidade findById(Integer id);
  
  MetaEntidade findByEntidadeIdAndMetaInstitutoId(Integer idEntidade, Integer idMetaInstituto);

  List<MetaEntidade> findByEntidadeIdAndInstitutoId(Integer entidade,
      Integer instituto);

  List<ResumoMetaEntidade> findByMetaInstitutoAndStatus(Integer atividade,
      SituacaoMeta status);

  List<HistoricoMetaEntidade> findByMetaEntidadeIdAndRodizioId(
      Integer metaentidade, Integer rodizio);

  HistoricoMetaEntidade findLastByMetaEntidadeIdAndRodizioId(
      Integer metaentidade, Integer rodizio, boolean atual);
  
  HistoricoMetaEntidade findLastByMetaEntidadeIdAndRodizioIdPreAvaliar(
      Integer metaentidade, Integer rodizio);
  
  HistoricoMetaEntidade findLastByMetaEntidadeIdAndRodizioIdAndTipoSituacao(
      Integer idmeta, Integer ciclo, Integer tipoSituacao, boolean atual);

  String getCaminhoMeta(Integer id);

  void saveAnotacao(Anotacao anotacao);

  void saveMetaAnotacao(MetaEntidadeAnotacao metaEntidadeAnotacao);

  MetaEntidade saveOrUpdate(MetaEntidade metaEntidade);

  

}
