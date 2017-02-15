package com.concafras.gestao.helper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.NivelAnotacao;
import com.concafras.gestao.enums.Sinalizador;
import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.form.AnotacaoVO;
import com.concafras.gestao.form.HistoricoMetaEntidadeVO;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;

public class MetasHelper {

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
      metaForm.load(metaEntidade);
      //TODO: Remove Depois do Rodizio
      if(evento == null) evento = EventoMeta.RODIZIO;
      if(evento.equals(EventoMeta.PRERODIZIO) || evento.equals(EventoMeta.RODIZIO) ){
        if (metaForm.getSituacaoAnterior() == null) {
          metaForm.setSituacaoAtual( carregarSituacaoInicial(listaHistoricoAtual) );
        }
        metaForm.setSituacaoDesejada( carregarSituacaoDesejada(listaHistoricoAtual, evento));
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
  private HistoricoMetaEntidadeVO carregarSituacaoDesejada(
      List<HistoricoMetaEntidade> listaMetaEntidade, EventoMeta evento) {
    
    HistoricoMetaEntidadeVO situacaoDesejada = null;

    HistoricoMetaEntidade historico = null;

    if (evento == EventoMeta.PRERODIZIO) {
      historico = getUltimoHistorico(listaMetaEntidade,
          TipoSituacaoMeta.PRECONTRATAR);
    } else if (evento == EventoMeta.RODIZIO) {
      HistoricoMetaEntidade histAux = getUltimoHistorico(
          listaMetaEntidade, TipoSituacaoMeta.CONTRATAR);
      if (histAux != null) {
        historico = histAux;
      } else {
        histAux = getUltimoHistorico(listaMetaEntidade,
            TipoSituacaoMeta.PRECONTRATAR);
        if (histAux != null) {
          historico = histAux;
        }
      }
    } else if (evento == EventoMeta.POSRODIZIO) {
      HistoricoMetaEntidade histAux = getUltimoHistorico(
          listaMetaEntidade);
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
  private HistoricoMetaEntidadeVO carregarSituacaoInicial(List<HistoricoMetaEntidade> historicoMetaEntidade) {
    
    HistoricoMetaEntidadeVO situacaoAtual = null;
    
    HistoricoMetaEntidade statusInicial = getUltimoHistorico(
        historicoMetaEntidade, TipoSituacaoMeta.INICIAL);
    
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
  }

}
