package com.concafras.gestao.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.form.RelatorioForm;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.rest.utils.RestUtils;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.PlanoMetasService;
import com.concafras.gestao.service.RodizioService;

@Controller
@RequestMapping("/gestao/relatorio")
public class RelatorioController {
  
  @Autowired
  PlanoMetasService planoMetasService;
  
  @Autowired
  private RodizioService rodizioService;
  
  @Autowired
  private BaseInstitutoService baseInstitutoService;
  
  
  @RequestMapping("/statusEntidadePreenchimentoCiclo")
  public ModelAndView statusEntidadePreenchimentoCiclo(Map<String, Object> map) {
    
    RelatorioForm form = new RelatorioForm();
    
    ModelAndView model = new ModelAndView("relatorio.preenchimentoPrevio", "relatorioForm", form);
    
    model.addObject("rodizioList", rodizioService.findAll());
    
    model.addObject("action", "statusEntidadePreenchimentoCiclo");
    
    model.addObject("relatorio", true);
    
    return model;
  }

  @RequestMapping(value = "/statusEntidadePreenchimentoCiclo/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusEntidadePreenchimentoCiclo(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
      throws IOException {
    
    List<EntidadeOptionForm> retorno = null;
    
    retorno = planoMetasService.getEntidadesParticipantes(ciclo);
    
    return new RestUtils<EntidadeOptionForm>().createDatatableResponse(retorno);
  
  }
  
  @RequestMapping(value = "/statusEntidadePreenchimentoCiclo/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusEntidadePreenchimentoCiclo(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
      throws IOException {
    
    List<InstitutoOptionForm> retorno = null;
    
    retorno = planoMetasService.getInstitutosPreechidos(ciclo, entidade);
      
    return new RestUtils<InstitutoOptionForm>().createDatatableResponse(retorno);
    
  }
  
  @RequestMapping(value = "/metascontratadasporentidade/ciclos")
  public ModelAndView metasContratadasPorEntidade(Map<String, Object> map) {
    
    RelatorioForm form = new RelatorioForm();
    ModelAndView model = new ModelAndView("relatorio.metascontratadas.entidade", "relatorioForm", form);
    
    model.addObject("rodizioList", rodizioService.findAll());
    model.addObject("relatorio", true);
    
    return model;
  }
  
  @RequestMapping(value = "/metascontratadasporentidade/ciclo/{ciclo}/entidades", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String metasContratadasPorEntidade(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
      throws IOException {
    
    List<EntidadeOptionForm> retorno = null;
    
    retorno = planoMetasService.getEntidadesParticipantes(ciclo);
    
    return new RestUtils<EntidadeOptionForm>().createDatatableResponse(retorno);
    
  }
  
  @RequestMapping(value = "/metascontratadasporentidade/ciclo/{ciclo}/entidade/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusEntidadeContratacaoCiclo(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
      throws IOException {
    
    List<InstitutoOptionForm> retorno = null;
    
    retorno = planoMetasService.getInstitutosContratados(ciclo, entidade);
    
    return new RestUtils<InstitutoOptionForm>().createDatatableResponse(retorno);
    
  }
  @RequestMapping("/statusInstitutoContratacaoCiclo")
  public ModelAndView statusInstitutoContratacaoCiclo(Map<String, Object> map) {
    
    RelatorioForm form = new RelatorioForm();
    
    ModelAndView model = new ModelAndView("relatorio.contratacoes.instituto", "relatorioForm", form);
    
    model.addObject("rodizioList", rodizioService.findAll());
    
    model.addObject("action", "statusInstitutoContratacaoCiclo");
    
    model.addObject("relatorio", true);
    
    return model;
  }
  
  @RequestMapping(value = "/statusInstitutoContratacaoCiclo/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusInstitutoContratacaoCiclo(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
      throws IOException {
    
    List<InstitutoOptionForm> retorno = new ArrayList<InstitutoOptionForm>();
    
    List<BaseInstituto> lista = baseInstitutoService.findByRodizio(true);
    
    for (BaseInstituto baseInstituto : lista) {
      InstitutoOptionForm item = new InstitutoOptionForm();
      item.setId(baseInstituto.getId());
      item.setDescricao(baseInstituto.getDescricao());
      retorno.add(item);
    }
    
    return new RestUtils<InstitutoOptionForm>().createDatatableResponse(retorno);

    
  }
  
  @RequestMapping(value = "/statusInstitutoContratacaoCiclo/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusInstitutoContratacaoCiclo(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
      throws IOException {
    
    List<InstitutoOptionForm> retorno = null;
    
    retorno = planoMetasService.getInstitutosContratados(ciclo, entidade);
    
    return new RestUtils<InstitutoOptionForm>().createDatatableResponse(retorno);
    
  }
  
  @RequestMapping("/fichasembranco")
  public ModelAndView fichasEmBranco(Map<String, Object> map) {
    
    RelatorioForm form = new RelatorioForm();
    
    ModelAndView model = new ModelAndView("relatorio.geral.fichasembranco", "relatorioForm", form);
    
    model.addObject("institutoList", baseInstitutoService.findByRodizioOverview(true));
    
    model.addObject("relatorio", true);
    
    return model;
  }
  
  @RequestMapping("/entidade/fichasimpressao/{ciclo}")
  public ModelAndView fichasImpressao(Map<String, Object> map, @PathVariable("ciclo") Integer ciclo) {
    
    RelatorioForm form = new RelatorioForm();
    
    ModelAndView model = new ModelAndView("relatorio.entidade.fichasimpressao", "relatorioForm", form);
    
    model.addObject("relatorio", true);
    
    Rodizio rodizio = rodizioService.findById(ciclo);
    
    map.put("CICLO_CONTROLE", rodizio);
    
    return model;
  }
  
}
