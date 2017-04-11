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
  
  private MetaForm preencheSituacaoAnteriorAtual(MetaForm meta, MetaEntidade metaEntidade, Rodizio cicloAtual) {
    if(metaEntidade != null){
      // Carregar avaliacao pre-salva
      HistoricoMetaEntidade situacaoAtualSalva = null;
      HistoricoMetaEntidade historico = null;
      if( cicloAtual.getCicloAnterior() != null ){
        situacaoAtualSalva = getUltimoHistorico( metaEntidade.getId(), cicloAtual.getCicloAnterior().getId(), TipoSituacaoMeta.AVALIAR, true);
        historico = getHistoricoPreAvaliacao( metaEntidade.getId(), cicloAtual.getCicloAnterior().getId() );
      }
      
      if(historico != null){
        System.out.println("\n========================================================");
        System.out.println(metaEntidade.getDescricao());
        System.out.println(cicloAtual.getCicloAnterior().getCiclo());
        System.out.println("========================================================");
        System.out.println("RECUPERADO: " + historico.getTipoSituacao() + " - "  + historico.getSituacao()  + " - "  + historico.getRodizio());
        System.out.println("========================================================");
        
        SituacaoMeta situacao = historico.getSituacao();
        Rodizio ciclo = historico.getRodizio();
        
        HistoricoMetaEntidadeVO situacaoAnterior = new HistoricoMetaEntidadeVO(historico);
        
        if( situacao == SituacaoMeta.NAOPLANEJADA ){
          HistoricoMetaEntidade preHistorico = getUltimoHistorico(metaEntidade.getId(), ciclo.getId(), TipoSituacaoMeta.INICIAL, true);
          if(preHistorico == null && ciclo.getCicloAnterior() != null){
            preHistorico = getUltimoHistorico(metaEntidade.getId(), ciclo.getCicloAnterior().getId(), TipoSituacaoMeta.AVALIAR, true);
          }
          
          if(preHistorico != null) {
            situacaoAnterior.setSituacao(preHistorico.getSituacao());
          }  
        }
        
        meta.setSituacaoAnterior(situacaoAnterior);
        System.out.println("ANTERIOR: " + situacaoAnterior.getTipoSituacao() + " - "  + situacaoAnterior.getSituacao() + " - "  + situacaoAnterior.getCiclo());
        System.out.println("========================================================");
        
        HistoricoMetaEntidadeVO situacaoAtual = null;
        if(situacaoAtualSalva == null){
          situacaoAtual = new HistoricoMetaEntidadeVO();
          situacaoAtual.setCiclo(new RodizioVO( cicloAtual.getCicloAnterior() ));
          situacaoAtual.setSituacao( situacaoAnterior.getSituacao() );
          situacaoAtual.setTipoSituacao(TipoSituacaoMeta.AVALIAR);
        } else {
          situacaoAtual = new HistoricoMetaEntidadeVO(situacaoAtualSalva);
        }
        
        System.out.println("ATUAL: " + situacaoAtual.getTipoSituacao() + " - "  + situacaoAtual.getSituacao() + " - "  + situacaoAtual.getCiclo());
        System.out.println("========================================================\n");
        
        meta.setSituacaoAtual(situacaoAtual);
        
        return meta;
      } 
      
      if(situacaoAtualSalva == null){
        meta.setSituacaoAnterior(null);
        meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
        meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);
      }
      return meta; 
    } else { 
      meta.setSituacaoAnterior(null);
      meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
      meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);
      return meta;                                                               
    }
  }

  public List<MetaForm> mapMetaEntidadeToMetaForm(List<MetaInstituto> metasInstituto, Pessoa facilitador, Pessoa contratante, EventoMeta evento, BaseEntidade entidade, Rodizio ciclo) {
    
    List<MetaForm> metasForm = new ArrayList<MetaForm>();
    
    metasInstituto.removeAll(Collections.singleton(null));
    
    for (MetaInstituto metaInstituto : metasInstituto) {
      MetaForm metaForm = mapMetaEntidadeToMetaForm(metaInstituto, facilitador, contratante, evento, entidade, ciclo);
      
      List<MetaInstituto> subAtividades = metaInstituto.getItens();
      if(subAtividades != null && subAtividades.size() > 0) {
        List<MetaForm> metas1 = mapMetaEntidadeToMetaForm(subAtividades, facilitador, contratante, evento, entidade, ciclo);
      
        if(metas1.size() > 0){
          metaForm.setDependencias(metas1);
        }
      }
      
      metasForm.add(metaForm);
    }
    
    return metasForm;
  }
  
  
  

  private MetaForm mapMetaEntidadeToMetaForm(MetaInstituto metaInstituto, Pessoa facilitador, Pessoa contratante, EventoMeta evento, BaseEntidade entidade, Rodizio ciclo) {
    MetaForm metaForm = new MetaForm();
    metaForm.setAtividade(metaInstituto);
    
    MetaEntidade metaEntidade = metaService.findByEntidadeIdAndMetaInstitutoId(entidade.getId(), metaInstituto.getId()); //searchMeta(metas, planoMetasAtual.getEntidade(), planoMetasAtual.getInstituto(), metaInstituto);
    
    String rota = metaService.getCaminhoMeta(metaInstituto.getId());
    metaForm.setDescricaoCompleta(rota);
    
    metaForm = preencheSituacaoAnteriorAtual(metaForm, metaEntidade, ciclo);
    
    metaForm = preencheSituacaoDesejada(metaEntidade,  
        metaForm,
        evento,
        ciclo
    );
    
    metaForm = preencheAnotacoes(metaEntidade,  
        metaForm, 
        contratante, 
        facilitador, 
        evento,
        ciclo,
        true
        );
    
    return metaForm; 
  }

  public List<MetaForm> mapMetaInstitutoToMetaForm(List<MetaInstituto> metasInstituto, Pessoa facilitador, Pessoa contratante, EventoMeta evento, Rodizio ciclo) {
    
    List<MetaForm> metas = new ArrayList<MetaForm>();
    
    metasInstituto.removeAll(Collections.singleton(null));
    
    for (MetaInstituto metaInstituto : metasInstituto) {
      MetaForm meta = new MetaForm();
      meta.setAtividade(metaInstituto);
      
      String rota = metaService.getCaminhoMeta(metaInstituto.getId());
      meta.setDescricaoCompleta(rota);
    
      //Primeiro rodizio - Sem meta anterior
      meta.setSituacaoAnterior(null);
      
      //Primeiro rodizio - Situacao Atual - Inicial
      meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
      meta.getSituacaoAtual().setCiclo(new RodizioVO(ciclo));
      meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);
    
      //Primeiro rodizio - Situacao Desejada vazia
      meta.setSituacaoDesejada(new HistoricoMetaEntidadeVO());

      //Primeiro Rodizio - Nova Anotacao em Branco
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

      List<MetaInstituto> subAtividades = metaInstituto.getItens();
      if(subAtividades != null){
        List<MetaForm> metas1 = mapMetaInstitutoToMetaForm(subAtividades, facilitador, contratante, evento, ciclo);
        
        if(metas1.size() > 0){
          meta.setDependencias(metas1);
        }
      }
    
      metas.add(meta);
    }
    
    return metas;
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
      EventoMeta evento,
      PlanoMetas planoMetasAtual, 
      boolean editMode) {
    metaForm = preencheSituacaoDesejada(metaEntidade, metaForm, evento, planoMetasAtual.getRodizio());
    return preencheAnotacoes(metaEntidade, metaForm, planoMetasAtual.getContratante(), planoMetasAtual.getFacilitador(), evento, planoMetasAtual.getRodizio(), editMode);
  }
  
  public MetaForm preencheSituacaoDesejada(MetaEntidade metaEntidade, 
        MetaForm metaForm,
        EventoMeta evento, 
        Rodizio ciclo) {

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
    }

    return metaForm;
  }
  
  public MetaForm preencheAnotacoes(MetaEntidade metaEntidade, 
      MetaForm metaForm,
      Pessoa contratante, 
      Pessoa facilitador, 
      EventoMeta evento, 
      Rodizio ciclo,
      boolean editMode) {
    
    if (metaEntidade != null) {
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
  
  public HistoricoMetaEntidade getHistoricoPreAvaliacao(Integer metaentidade, Integer rodizio) {
    return metaService.findLastByMetaEntidadeIdAndRodizioIdPreAvaliar(metaentidade, rodizio);
  }
  
  public HistoricoMetaEntidade getUltimoHistorico(Integer metaentidade, Integer rodizio, boolean atual) {
    return metaService.findLastByMetaEntidadeIdAndRodizioId(metaentidade, rodizio, atual);
  }
}
