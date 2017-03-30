package com.concafras.gestao.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.NivelAnotacao;
import com.concafras.gestao.enums.Sinalizador;
import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.form.AnotacaoVO;
import com.concafras.gestao.form.HistoricoMetaEntidadeVO;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.RodizioVO;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.service.MetaService;

public class MetasHelper {

  private MetaService metaService;
  
  public MetasHelper(MetaService metaService){
    this.metaService = metaService;
  }
  
  public List<MetaForm> processaMetaInstitutoToMetaForm(List<MetaInstituto> atividades, Pessoa facilitador, Pessoa contratante, EventoMeta evento, Rodizio ciclo) {
    
    List<MetaForm> metas = new ArrayList<MetaForm>();
    
    atividades.removeAll(Collections.singleton(null));
    
    for (MetaInstituto atividade : atividades) {
      MetaForm meta = new MetaForm();
    meta.setAtividade(atividade);
    meta.setSituacaoDesejada(new HistoricoMetaEntidadeVO());
    meta.setObservacoes(new ArrayList<AnotacaoVO>());
    
    if(evento == EventoMeta.PRERODIZIO){
      Anotacao anot = new Anotacao();
      anot.setNivel(NivelAnotacao.META_PRERODIZIO);
      if(contratante != null && contratante.getId() != null)
        anot.setResponsavel(contratante);
      anot.setSinalizador(Sinalizador.VERDE);
      anot.setData(new Date());
      meta.getObservacoes().add(new AnotacaoVO(anot, ciclo));
    }
    
    if(evento == EventoMeta.RODIZIO){
      Anotacao anot = new Anotacao();
      anot.setNivel(NivelAnotacao.META_RODIZIO);
      if(facilitador != null && facilitador.getId() != null)
        anot.setResponsavel(facilitador);
      anot.setSinalizador(Sinalizador.VERDE);
      anot.setData(new Date());
      meta.getObservacoes().add(new AnotacaoVO(anot, ciclo));
    }
    
    //Primeiro rodizio - Sem meta anterior
    meta.setSituacaoAnterior(null);
    
    //Primeiro rodizio - Situacao Atual - Inicial
    meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
    meta.getSituacaoAtual().setCiclo(new RodizioVO(ciclo));
    meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);
    
    List<MetaInstituto> subAtividades = atividade.getItens();
    if(subAtividades != null){
      List<MetaForm> metas1 = processaMetaInstitutoToMetaForm(subAtividades, facilitador, contratante, evento, ciclo);
      
      if(metas1.size() > 0){
        meta.setDependencias(metas1);
      }
    }
    
    metas.add(meta);
  }
    
    return metas;
  }


  public MetaForm processaMetasAnteriores(MetaForm meta, MetaInstituto metaInstituto, List<HistoricoMetaEntidade> historicoAnterior, PlanoMetas planometas) {
    if(planometas != null){
      List<MetaEntidade> metas = planometas.getListaMetas();
      MetaEntidade metaEntidade = searchMeta(metas, planometas.getEntidade(), planometas.getInstituto(), metaInstituto);
      
      if(metaEntidade != null){
        // Carregar avaliacao pre-salva
        HistoricoMetaEntidade situacaoAtualSalva = getUltimoHistorico( metaEntidade.getId(), planometas.getRodizio().getCicloAnterior().getId(), TipoSituacaoMeta.AVALIAR, true);
        if(situacaoAtualSalva != null)
          meta.setSituacaoAtual(new HistoricoMetaEntidadeVO(situacaoAtualSalva));
  
          return processaMetasAnteriores2(meta, metaEntidade, planometas, situacaoAtualSalva, true);         
      } 
    } 

    meta.setSituacaoAnterior(null);
    meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
    meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);

    return meta;                                                               
  }
  
  private MetaForm processaMetasAnteriores2(MetaForm meta, MetaEntidade metaEntidade, PlanoMetas planometas, HistoricoMetaEntidade situacaoAtualSalva, boolean atual) {
  //pegar ultimo historico diferente de avaliar
    HistoricoMetaEntidade historico = null; //getHistoricoPreAvaliacao( metaEntidade.getId(), planometas.getRodizio().getCicloAnterior().getId() );
    if(!atual){
      historico = getHistoricoPreAvaliacao( metaEntidade.getId(), planometas.getRodizio().getCicloAnterior().getId() );
      if(historico != null){
        HistoricoMetaEntidadeVO situacaoAnterior = new HistoricoMetaEntidadeVO(historico);
        meta.setSituacaoAnterior(situacaoAnterior);
        if(situacaoAtualSalva == null){
          HistoricoMetaEntidadeVO situacaoAtual = new HistoricoMetaEntidadeVO(historico);
          situacaoAtual.setId(null);
          meta.setSituacaoAtual(situacaoAtual);
          meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.AVALIAR);
          return meta;
        }
      }
    }
    if(historico == null){
      historico = getUltimoHistorico( metaEntidade.getId(), planometas.getRodizio().getCicloAnterior().getId(), TipoSituacaoMeta.CONTRATAR, atual);
    }
    if(historico == null){
      historico = getUltimoHistorico( metaEntidade.getId(), planometas.getRodizio().getCicloAnterior().getId(), TipoSituacaoMeta.PRECONTRATAR, atual);
    }
    
    if(historico != null && historico.getSituacao() != SituacaoMeta.NAOPLANEJADA){
      HistoricoMetaEntidadeVO situacaoAnterior = new HistoricoMetaEntidadeVO(historico);
      meta.setSituacaoAnterior(situacaoAnterior);
      if(situacaoAnterior.getSituacao() == SituacaoMeta.PLANEJADA) {
        if(situacaoAtualSalva == null){
          HistoricoMetaEntidade ultimoHistorico = getUltimoHistorico(metaEntidade.getId(), planometas.getRodizio().getCicloAnterior().getId(), atual );
          if(ultimoHistorico != null 
               && ultimoHistorico.getTipoSituacao() != TipoSituacaoMeta.INICIAL
               && ultimoHistorico.getTipoSituacao() != TipoSituacaoMeta.PRECONTRATAR
               && ultimoHistorico.getTipoSituacao() != TipoSituacaoMeta.CONTRATAR
               ){ 
            HistoricoMetaEntidadeVO situacaoAtual = new HistoricoMetaEntidadeVO(ultimoHistorico);
            situacaoAtual.setId(null);
            meta.setSituacaoAtual(situacaoAtual);
            meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.AVALIAR);
            return meta;
          } else if (ultimoHistorico != null) {
              HistoricoMetaEntidadeVO situacaoAtual = new HistoricoMetaEntidadeVO(ultimoHistorico);
              situacaoAtual.setId(null);
              meta.setSituacaoAtual(situacaoAtual);
              meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.AVALIAR);
              return meta;
          }
        }
      }
    }
    
    if(historico == null || historico.getSituacao() == SituacaoMeta.NAOPLANEJADA){
      // Pega Meta Inicial
      historico = getUltimoHistorico(metaEntidade.getId(), planometas.getRodizio().getCicloAnterior().getId(), TipoSituacaoMeta.INICIAL, atual);
      if(historico != null){
        HistoricoMetaEntidadeVO situacaoAnteriorNovo = new HistoricoMetaEntidadeVO(historico);
        meta.setSituacaoAnterior(situacaoAnteriorNovo);
        if(situacaoAtualSalva == null){
          meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
          meta.getSituacaoAtual().setCiclo(new RodizioVO( planometas.getRodizio().getCicloAnterior() ));
          meta.getSituacaoAtual().setSituacao( situacaoAnteriorNovo.getSituacao() );
          meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.AVALIAR);
        } 
        return meta;
      } else if(atual){
        Rodizio ciclo = planometas.getRodizio().getCicloAnterior();
        if(ciclo.getCicloAnterior() != null){
          historico = getUltimoHistorico(metaEntidade.getId(), ciclo.getCicloAnterior().getId(), TipoSituacaoMeta.AVALIAR, atual);
          if(historico != null){
            HistoricoMetaEntidadeVO situacaoAnteriorNovo = new HistoricoMetaEntidadeVO(historico);
            meta.setSituacaoAnterior(situacaoAnteriorNovo);
            if(situacaoAtualSalva == null){
              meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
              meta.getSituacaoAtual().setCiclo(new RodizioVO( planometas.getRodizio().getCicloAnterior() ));
              meta.getSituacaoAtual().setSituacao( situacaoAnteriorNovo.getSituacao() );
              meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.AVALIAR);
            }
            return meta;
          } else {
            if(atual){
              return processaMetasAnteriores2(meta, metaEntidade, planometas, situacaoAtualSalva, false);  
            }
          }
        }
      }
    }
    
    meta.setSituacaoAnterior(null);
    meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
    meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);
    
    return meta;
    
  }

  public List<MetaForm> processaMetaToMetaForm(List<MetaInstituto> metasInstituto, PlanoMetas planoMetasAtual) {
    
    List<MetaEntidade> metas = planoMetasAtual.getListaMetas();
    
    List<MetaForm> metasForm = new ArrayList<MetaForm>();
    
    metasInstituto.removeAll(Collections.singleton(null));
    
    for (MetaInstituto metaInstituto : metasInstituto) {
      MetaForm metaForm = new MetaForm();
      
      MetaEntidade metaEntidade = searchMeta(metas, planoMetasAtual.getEntidade(), planoMetasAtual.getInstituto(), metaInstituto);
      
      List<HistoricoMetaEntidade> historicoAtual = metaService.findByMetaEntidadeIdAndRodizioId(metaEntidade.getId(), planoMetasAtual.getRodizio().getId());
      List<HistoricoMetaEntidade> historicoAnterior = null;
      if(planoMetasAtual.getRodizio().getCicloAnterior() != null){
        historicoAnterior = metaService.findByMetaEntidadeIdAndRodizioId(metaEntidade.getId(), planoMetasAtual.getRodizio().getCicloAnterior().getId());
      } else {
        historicoAnterior = new ArrayList<HistoricoMetaEntidade>();
      }
      metaForm = processaMetasAnteriores(metaForm, metaInstituto, historicoAnterior, planoMetasAtual);
      
      metaForm = processaMetaEntidade(metaEntidade,  
          metaForm, 
          historicoAtual,
          historicoAnterior,
          planoMetasAtual.getContratante(), 
          planoMetasAtual.getFacilitador(), 
          planoMetasAtual.getEvento(), 
          planoMetasAtual.getRodizio(),
          true
      );
      
      metaForm.setAtividade(metaInstituto);
      List<MetaInstituto> subAtividades = metaInstituto.getItens();
      if(subAtividades != null && subAtividades.size() > 0) {
        List<MetaForm> metas1 = processaMetaToMetaForm(subAtividades, planoMetasAtual);
      
        if(metas1.size() > 0){
          metaForm.setDependencias(metas1);
        }
      }
      
      metasForm.add(metaForm);
    }
    
    return metasForm;
  }
  
  public MetaEntidade searchMeta(List<MetaEntidade> metasEntidade, BaseEntidade entidadeId, BaseInstituto institutoId, MetaInstituto metaId){
    MetaEntidade meta = new MetaEntidade();
    
    meta.setEntidade(entidadeId);
    
    meta.setInstituto(institutoId);
    
    meta.setMeta(metaId);
    
    if(metasEntidade.indexOf(meta) != -1){
      MetaEntidade retorno = metasEntidade.get(metasEntidade.indexOf(meta));
      return retorno;   
    } else {
      return null;
    }
  }

  public MetaForm processaMetaEntidade(MetaEntidade metaEntidade, 
      MetaForm metaForm, 
      List<HistoricoMetaEntidade> listaHistoricoAtual,
      List<HistoricoMetaEntidade> listaHistoricoAnterior,
      PlanoMetas planoMetasAtual, 
      boolean editMode) {
    return processaMetaEntidade(metaEntidade, metaForm, listaHistoricoAtual, listaHistoricoAnterior, planoMetasAtual.getContratante(), planoMetasAtual.getFacilitador(), planoMetasAtual.getEvento(), planoMetasAtual.getRodizio(), editMode);
  }
  
  public MetaForm processaMetaEntidade(MetaEntidade metaEntidade, 
        MetaForm metaForm,
        List<HistoricoMetaEntidade> listaHistoricoAtual,
        List<HistoricoMetaEntidade> listaHistoricoAnterior,
        Pessoa contratante, 
        Pessoa facilitador, 
        EventoMeta evento, 
        Rodizio ciclo,
        boolean editMode) {

    if (metaEntidade != null) {
      HistoricoMetaEntidade atual = getUltimoHistorico(metaEntidade.getId(), ciclo.getId(), false);
      metaForm.load(metaEntidade, atual);
      //TODO: Remove Depois do Rodizio
      if(evento == null) evento = EventoMeta.RODIZIO;
      if(evento.equals(EventoMeta.PRERODIZIO) || evento.equals(EventoMeta.RODIZIO) ){
        if (metaForm.getSituacaoAnterior() == null) {
          metaForm.setSituacaoAtual( carregarSituacaoInicial(metaEntidade.getId(), ciclo.getId()) );
        }
        metaForm.setSituacaoDesejada( carregarSituacaoDesejada( metaEntidade.getId(), ciclo.getId(), evento));
      } else if(evento.equals(EventoMeta.POSRODIZIO)){
        
      }

      if(editMode){ // Se nao for mode de visualizacao adicionar uma anotacao em branco para preenchimento
        if (evento == EventoMeta.PRERODIZIO) {
          Pessoa responsavel = contratante; // Presidente
          verificaValidaAnotacoes(ciclo, evento, metaEntidade.getAnotacoes(), metaForm.getObservacoes(), responsavel);
        }
  
        if (evento == EventoMeta.RODIZIO) {
          Pessoa responsavel = facilitador; // Facilitador
          verificaValidaAnotacoes(ciclo, evento, metaEntidade.getAnotacoes(), metaForm.getObservacoes(), responsavel);
        }
        
        if (evento == EventoMeta.POSRODIZIO) {
          //Pessoa responsavel = contratante; // Usuario do sistema
          //verificaValidaAnotacoes(evento, metaEntidade.getAnotacoes(), metaForm.getObservacoes(), responsavel);
        }
      }
    }

    return metaForm;
  }

  /**
   * Nos eventos PRERODIZIO e no RODIZIO
   * SituacaoAnterior é o contrato no ano anterior (TipoSituacao == CONTRATAR)
   * SituaçãoAtual é TipoSituacao == INICIAL || Resultado do Ano Anterior (Estado Atual da Meta)
   * SituacaoDesejada é o precontrato/contrato (TipoSituacao == PRECONTRATAR || TipoSituacao == CONTRATAR
   * @param metaEntidade 
   */
  private HistoricoMetaEntidadeVO carregarSituacaoDesejada(Integer metaentidade, Integer rodizio, EventoMeta evento) {
    
    HistoricoMetaEntidadeVO situacaoDesejada = null;

    HistoricoMetaEntidade historico = null;

    if (evento == EventoMeta.PRERODIZIO) {
      historico = getUltimoHistorico(metaentidade, rodizio, TipoSituacaoMeta.PRECONTRATAR, true);
    } else if (evento == EventoMeta.RODIZIO) {
      HistoricoMetaEntidade histAux = getUltimoHistorico(metaentidade, rodizio, TipoSituacaoMeta.CONTRATAR, true);
      if (histAux != null) {
        historico = histAux;
      } else {
        histAux = getUltimoHistorico(metaentidade, rodizio, TipoSituacaoMeta.PRECONTRATAR, true);
        if (histAux != null) {
          historico = histAux;
        }
      }
    } else if (evento == EventoMeta.POSRODIZIO) {
      HistoricoMetaEntidade histAux = getUltimoHistorico(metaentidade, rodizio, true);
      if (histAux != null) {
        historico = histAux;
      }
    }
    
    if (historico != null) {
      situacaoDesejada = new HistoricoMetaEntidadeVO(historico);
      if (evento == EventoMeta.PRERODIZIO ) {
        situacaoDesejada.setTipoSituacao(TipoSituacaoMeta.PRECONTRATAR);
      }
      if (evento == EventoMeta.RODIZIO ) {
        situacaoDesejada.setTipoSituacao(TipoSituacaoMeta.CONTRATAR);
      }
    } else {
      situacaoDesejada = new HistoricoMetaEntidadeVO();
    }

    return situacaoDesejada;
  }

  /**
   * Nos eventos PRERODIZIO e no RODIZIO
   * SituacaoAnterior é o contrato no ano anterior (TipoSituacao == CONTRATAR)
   * SituaçãoAtual é TipoSituacao == INICIAL || Resultado do Ano Anterior (Estado Atual da Meta)
   * SituacaoDesejada é o precontrato/contrato (TipoSituacao == PRECONTRATAR || TipoSituacao == CONTRATAR
   * @param metaEntidade 
   */
  private HistoricoMetaEntidadeVO carregarSituacaoInicial(Integer metaentidade, Integer rodizio) {
    
    HistoricoMetaEntidadeVO situacaoAtual = null;
    
    HistoricoMetaEntidade statusInicial = getUltimoHistorico(metaentidade, rodizio, TipoSituacaoMeta.INICIAL, true);
    
    if (statusInicial != null) {
      situacaoAtual = new HistoricoMetaEntidadeVO(statusInicial);
    } else {
      situacaoAtual = new HistoricoMetaEntidadeVO();
    }
    
    return situacaoAtual;
  }

  private void verificaValidaAnotacoes(Rodizio ciclo, EventoMeta evento,
      final List<MetaEntidadeAnotacao> anotacoes, final List<AnotacaoVO> observacoes, final Pessoa responsavel) {

    NivelAnotacao nivel = null;

    if (evento == EventoMeta.PRERODIZIO) {
      nivel = NivelAnotacao.META_PRERODIZIO;
    }
    if (evento == EventoMeta.RODIZIO) {
      nivel = NivelAnotacao.META_RODIZIO;
    }
    if (evento == EventoMeta.POSRODIZIO) {
      nivel = NivelAnotacao.META_POSRODIZIO;
    }

    boolean poosuiAnot = false;
    
    if(anotacoes != null){
      for (MetaEntidadeAnotacao an : anotacoes) {
        Anotacao anot = an.getAnotacao();
        if (anot.getNivel().equals(nivel) && an.getCiclo().equals(ciclo)) {
          poosuiAnot = true;
        }
      }
    }
    
    if (!poosuiAnot) {
      Anotacao anot = new Anotacao();
      anot.setNivel(nivel);
      if (responsavel != null && responsavel.getId() != null)
        anot.setResponsavel(responsavel);
      anot.setSinalizador(Sinalizador.VERDE);
      anot.setData(new Date());
      observacoes.add(new AnotacaoVO(anot, ciclo));
    }
  }
  
  public HistoricoMetaEntidade getUltimoHistorico(Integer metaentidade, Integer rodizio, TipoSituacaoMeta acao, boolean atual){
      return metaService.findLastByMetaEntidadeIdAndRodizioIdAndTipoSituacao(metaentidade, rodizio, acao.ordinal(), atual);
  }

  /*
  public HistoricoMetaEntidade getUltimoHistorico(List<HistoricoMetaEntidade> historico,
      TipoSituacaoMeta acao) {
    HistoricoMetaEntidade statusInicial = null;
    if (historico != null && historico.size() > 0) {
      for (HistoricoMetaEntidade historicoMetas : historico) {
        if (historicoMetas != null && historicoMetas.getTipoSituacao() == acao) {
          statusInicial = historicoMetas;
        }
      }
    }
    return statusInicial;
  }
  */

  
  /*public HistoricoMetaEntidade getHistoricoAvaliar(Integer metaentidade, Integer rodizio) {
    return getUltimoHistorico(metaentidade, rodizio, TipoSituacaoMeta.AVALIAR);
  }*/
  
  /*
  public HistoricoMetaEntidade getHistoricoAvaliar(List<HistoricoMetaEntidade> historico) {
  
    Collections.sort(historico);
    HistoricoMetaEntidade statusInicial = null;
    if (historico != null && historico.size() > 0) {
      for (HistoricoMetaEntidade historicoMetas : historico) {
        if (historicoMetas != null && historicoMetas.getTipoSituacao() == TipoSituacaoMeta.AVALIAR) {
          statusInicial = historicoMetas;
        }
      }
    }
    return statusInicial;
  }
  */
  
  public HistoricoMetaEntidade getHistoricoPreAvaliacao(Integer metaentidade, Integer rodizio) {
    return metaService.findLastByMetaEntidadeIdAndRodizioIdPreAvaliar(metaentidade, rodizio);
  }

  /*
  public HistoricoMetaEntidade getHistoricoPreAvaliacao(List<HistoricoMetaEntidade> historico) {
    Collections.sort(historico);
    HistoricoMetaEntidade statusInicial = null;
    if (historico != null && historico.size() > 0) {
      for (HistoricoMetaEntidade historicoMetas : historico) {
        if (historicoMetas != null && historicoMetas.getTipoSituacao() != TipoSituacaoMeta.AVALIAR) {
          statusInicial = historicoMetas;
        }
      }
    }
    return statusInicial;
  }
  */
  
  public HistoricoMetaEntidade getUltimoHistorico(Integer metaentidade, Integer rodizio, boolean atual) {
    return metaService.findLastByMetaEntidadeIdAndRodizioId(metaentidade, rodizio, atual);
  }

  /*
  public HistoricoMetaEntidade getUltimoHistorico(List<HistoricoMetaEntidade> historico) {
    Collections.sort(historico);
    HistoricoMetaEntidade statusInicial = null;
    if (historico != null && historico.size() > 0) {
      for (HistoricoMetaEntidade historicoMetas : historico) {
        if(historicoMetas != null)  
          statusInicial = historicoMetas;
      }
    }
    return statusInicial;
  }*/

}
