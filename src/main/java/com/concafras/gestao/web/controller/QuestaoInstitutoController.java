package com.concafras.gestao.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.concafras.gestao.enums.DiaSemana;
import com.concafras.gestao.enums.TipoFrequencia;
import com.concafras.gestao.form.PlanoModeloForm;
import com.concafras.gestao.form.QuestionarioInstitutoForm;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.QuestaoInstituto;
import com.concafras.gestao.model.view.ItemPlanoModeloWrapper;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.QuestaoInstitutoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/gestao/questaoInstituto")
public class QuestaoInstitutoController {

  /**
   * The Constant PATTERN_YYYY_MM_DD.
   */
  private static final String PATTERN_HH_MM = "hh:mm";

  FormulaEvaluator evaluator;

  @Autowired
  private QuestaoInstitutoService questaoInstitutoService;

  @Autowired
  private BaseInstitutoService baseInstitutoService;

  @RequestMapping("/listar")
  public ModelAndView listQuestaoInstituto(
      @ModelAttribute("questaoInstitutoForm") QuestionarioInstitutoForm form, HttpServletRequest request) {
      
      BaseInstituto instituto = null;
      
      if(form.getInstituto() == null || form.getInstituto().getId() == null){
        if("ROLE_METAS_DIRIGENTE".equals( request.getSession().getAttribute("ROLE_CONTROLE") ) ){
          instituto = (BaseInstituto) request.getSession().getAttribute("INSTITUTO_CONTROLE");
        }
      } else {
        instituto = baseInstitutoService.findById(form
            .getInstituto().getId());
      }
    

    List<QuestaoInstituto> questaoInstitutos = questaoInstitutoService
        .findByInstitutoId(instituto.getId());

    form = new QuestionarioInstitutoForm(instituto.getId());
    form.setInstituto(instituto);
    
    if (questaoInstitutos != null) {
      cleanPlanoModeloList(questaoInstitutos);
      form.setItens(questaoInstitutos);
    } else {
      form.setItens(new ArrayList<QuestaoInstituto>());
    }
    ModelAndView model = null;
    
    if("ROLE_METAS_DIRIGENTE".equals( request.getSession().getAttribute("ROLE_CONTROLE") ) ){
      model = new ModelAndView("questaoInstituto.dirigente.listar", "questaoInstitutoForm", form);      
    } else {
      model = new ModelAndView("questaoInstituto.listar",  "questaoInstitutoForm", form);
    }
    model.addObject("instituto", instituto);
    model.addObject("rodizio", Boolean.TRUE);
    return model;
  }

  @RequestMapping("/")
  public ModelAndView listBaseInstituto(Map<String, Object> map) {

    PlanoModeloForm form2 = new PlanoModeloForm();

    ModelAndView model = new ModelAndView("questaoInstituto.selecionarInstituto",
        "questaoInstitutoForm", form2);

    model
        .addObject("questaoInstitutoList", baseInstitutoService.findByRodizio(true));

    model.addObject("rodizio", true);

    return model;
  }

  @RequestMapping("/editar")
  public ModelAndView editarPlanoModelos(
      @ModelAttribute("questaoInstitutoForm") QuestionarioInstitutoForm form) {

    List<QuestaoInstituto> questaoInstitutos = questaoInstitutoService
        .findByInstitutoId( form.getInstituto().getId() );

    if (questaoInstitutos != null) {
      cleanPlanoModeloList(questaoInstitutos);
      form.setItens(questaoInstitutos);
    } else {
      form.setItens(new ArrayList<QuestaoInstituto>());
    }

    if (form.getItens().size() == 0)
      form.getItens().add(new QuestaoInstituto());

    BaseInstituto instituto = baseInstitutoService.findById(form
        .getInstituto().getId());

    ModelAndView model = new ModelAndView("questaoInstituto.editarLista",
        "questaoInstitutoForm", form);

    List<TipoFrequencia> frequenciaList = new ArrayList<TipoFrequencia>(
        Arrays.asList(TipoFrequencia.values()));
    model.addObject("frequenciaList", frequenciaList);

    List<DiaSemana> semanaList = new ArrayList<DiaSemana>(
        Arrays.asList(DiaSemana.values()));
    model.addObject("semanaList", semanaList);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;

  }

  @RequestMapping("/organizar")
  public ModelAndView organizarPlanoModelos(
      @ModelAttribute("questaoInstitutoForm") QuestionarioInstitutoForm form) {

    List<QuestaoInstituto> questaoInstitutos = questaoInstitutoService
        .findByInstitutoId( form.getInstituto().getId() );

    if (questaoInstitutos != null) {
      cleanPlanoModeloList(questaoInstitutos);
      form.setItens(questaoInstitutos);
    } else {
      form.setItens(new ArrayList<QuestaoInstituto>());
    }

    if (form.getItens().size() == 0)
      form.getItens().add(new QuestaoInstituto());

    BaseInstituto instituto = baseInstitutoService.findById(form
        .getInstituto().getId());

    ModelAndView model = new ModelAndView("questaoInstituto.organizar",
        "questaoInstitutoForm", form);

    List<TipoFrequencia> frequenciaList = new ArrayList<TipoFrequencia>(
        Arrays.asList(TipoFrequencia.values()));
    model.addObject("frequenciaList", frequenciaList);

    List<DiaSemana> semanaList = new ArrayList<DiaSemana>(
        Arrays.asList(DiaSemana.values()));
    model.addObject("semanaList", semanaList);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;

  }

  @RequestMapping(value = "/process", method = RequestMethod.POST)
  public ModelAndView save(
      @ModelAttribute("questaoInstitutoForm") QuestionarioInstitutoForm questaoInstitutoForm) {

    BaseInstituto instituto = baseInstitutoService.findById(questaoInstitutoForm.getInstituto().getId());

    List<QuestaoInstituto> questaoInstitutos = questaoInstitutoForm.getItens();

    ModelAndView model = new ModelAndView("questaoInstituto.listar", "questaoInstitutoForm", questaoInstitutoForm);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }

  @RequestMapping(value = "/sort", method = RequestMethod.POST)
  public ModelAndView sort(
      @ModelAttribute("questaoInstitutoForm") QuestionarioInstitutoForm questaoInstitutoForm) {

    List<QuestaoInstituto> questaoInstitutos = null;

    String organizar = questaoInstitutoForm.getOrganizar();

    try {
      ObjectMapper mapper = new ObjectMapper();

      ItemPlanoModeloWrapper[] novasPlanoModelos = mapper.readValue(organizar,
          ItemPlanoModeloWrapper[].class);

      int sortOrder = 1;

      for (ItemPlanoModeloWrapper questaoInstitutoWrapper : novasPlanoModelos) {
        updateGrupo(questaoInstitutoWrapper, sortOrder, null);
        sortOrder++;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    // questaoInstitutoService.updatePlanoModelo(questaoInstituto);

    questaoInstitutos = questaoInstitutoService.findByInstitutoId( questaoInstitutoForm.getInstituto().getId() );

    questaoInstitutoForm.setItens(questaoInstitutos);

    BaseInstituto instituto = baseInstitutoService
        .findByIdOverview(questaoInstitutoForm.getInstituto().getId());

    ModelAndView model = new ModelAndView("questaoInstituto.listar",
        "questaoInstitutoForm", questaoInstitutoForm);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String addPlanoModelo(Map<String, Object> map,
      @ModelAttribute("questaoInstitutoForm") PlanoModeloForm questaoInstitutoForm) {

    QuestaoInstituto questaoInstituto = new QuestaoInstituto();

    BaseInstituto base = baseInstitutoService
        .findByIdOverview(questaoInstitutoForm.getInstituto().getId());

    questaoInstituto.setInstituto(base);

    map.put("questaoInstituto", questaoInstituto);

    map.put("rodizio", true);

    return "questaoInstituto.novo";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addPlanoModelo(
      @ModelAttribute("questaoInstituto") QuestaoInstituto questaoInstituto,
      BindingResult result, RedirectAttributes questaoInstitutoForm) {
    
    if(questaoInstituto.getCiclo() == null || questaoInstituto.getCiclo().getId() == null){
      questaoInstituto.setCiclo(null);
    }
    
    if(questaoInstituto.getPai() == null || questaoInstituto.getPai().getId() == null){
      questaoInstituto.setPai(null);
    }

    questaoInstitutoService.addQuestaoInstituto(questaoInstituto);

    questaoInstitutoForm.addFlashAttribute("questaoInstitutoForm", new PlanoModeloForm(
        questaoInstituto.getInstituto().getId()));

    return "redirect:/gestao/questaoInstituto/listar";
  }

  @RequestMapping(value = "/edit/{questaoInstitutoId}", method = RequestMethod.POST)
  public String editPlanoModelo(Map<String, Object> map,
      @PathVariable("questaoInstitutoId") Integer questaoInstitutoId) {

    QuestaoInstituto questaoInstituto = questaoInstitutoService
        .findById(questaoInstitutoId);

    map.put("questaoInstituto", questaoInstituto);

    map.put("rodizio", true);
    
    return "questaoInstituto.editar";
  }

  @RequestMapping(value = "/edit/save/{questaoInstitutoId}", method = RequestMethod.POST)
  public String editPlanoModelo(
      @ModelAttribute("questaoInstituto") QuestaoInstituto questaoInstituto,
      @PathVariable("questaoInstitutoId") Integer questaoInstitutoId,
      RedirectAttributes questaoInstitutoForm) {
    
    if(questaoInstituto.getCiclo() == null || questaoInstituto.getCiclo().getId() == null){
      questaoInstituto.setCiclo(null);
    }
    
    if(questaoInstituto.getPai() == null || questaoInstituto.getPai().getId() == null){
      questaoInstituto.setPai(null);
    }

    questaoInstitutoService.updateQuestaoInstituto(questaoInstituto);

    questaoInstitutoForm.addFlashAttribute("questaoInstitutoForm", new PlanoModeloForm(
        questaoInstituto.getInstituto().getId()));

    return "redirect:/gestao/questaoInstituto/listar";
  }

  
  @RequestMapping("/enable/{questaoInstitutoId}")
  public ModelAndView enablePlanoModelo(
      HttpServletRequest request,
      @ModelAttribute("questaoInstitutoForm") QuestionarioInstitutoForm form, 
      @PathVariable("questaoInstitutoId") Integer questaoInstitutoId) {
    
    questaoInstitutoService.enableQuestaoInstituto(questaoInstitutoId);
    
    return listQuestaoInstituto(form, request);
  }
  
  @RequestMapping("/disable/{questaoInstitutoId}")
  public ModelAndView disablePlanoModelo(
      HttpServletRequest request,
      @ModelAttribute("questaoInstitutoForm") QuestionarioInstitutoForm form,
      @PathVariable("questaoInstitutoId") Integer questaoInstitutoId) {
    
    questaoInstitutoService.disableQuestaoInstituto(questaoInstitutoId);
    
    return listQuestaoInstituto(form, request);
  }
  
  @RequestMapping("/delete/{questaoInstitutoId}")
  public ModelAndView deletePlanoModelo(
      HttpServletRequest request,
      @ModelAttribute("questaoInstitutoForm") QuestionarioInstitutoForm form,
      @PathVariable("questaoInstitutoId") Integer questaoInstitutoId) {
    
    questaoInstitutoService.removeQuestaoInstituto(questaoInstitutoId);
    
    return listQuestaoInstituto(form, request);
  }

  public void updateGrupo(ItemPlanoModeloWrapper questaoInstitutoWrapper,
      Integer sortOrder, Integer idPai) {

    QuestaoInstituto questaoInstituto = questaoInstitutoService
        .findById(questaoInstitutoWrapper.getId());

    questaoInstituto.setViewOrder(sortOrder);

    if (idPai != null) {
      QuestaoInstituto pai = questaoInstitutoService.findById(idPai);
      questaoInstituto.setPai(pai);
    } else {
      questaoInstituto.setPai(null);
    }

    questaoInstitutoService.updateQuestaoInstituto(questaoInstituto);

    if (questaoInstitutoWrapper.getChildren() != null
        && questaoInstitutoWrapper.getChildren().length > 0) {
      int order = 1;
      for (ItemPlanoModeloWrapper questaoInstitutoWrapper2 : questaoInstitutoWrapper
          .getChildren()) {
        updateGrupo(questaoInstitutoWrapper2, order, questaoInstituto.getId());
        order++;
      }
    }
  }

  public void insertGrupo(List<QuestaoInstituto> atividades,
      BaseInstituto instituto, QuestaoInstituto pai) {
    for (QuestaoInstituto atividade : atividades) {
      if (!atividade.getDescricao().trim().equals("")) {
        atividade.setInstituto(instituto);
        atividade.setPai(pai);
        if (atividade.getId() == null || atividade.getId().equals(0)) {
          questaoInstitutoService.addQuestaoInstituto(atividade);
        } else {
          questaoInstitutoService.updateQuestaoInstituto(atividade);
        }
        if (atividade.getItens() != null) {
          insertGrupo(atividade.getItens(), instituto, atividade);
        }
      }
    }
  }

  /**
   * Pre inicializa controller
   * 
   * @param binder
   */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    bindDate(binder);
  }

  /**
   * Bind date.
   * 
   * @param binder
   *          the binder
   */
  protected void bindDate(WebDataBinder binder) {
    binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String value) {
        try {
          Date parsedDate = new SimpleDateFormat(PATTERN_HH_MM).parse(value);
          setValue(parsedDate);
        } catch (ParseException e) {
          setValue(null);
        }
      }
    });
  }
  
  protected void cleanPlanoModeloList(List<QuestaoInstituto> listaPlanos){
    if (listaPlanos != null) {
      listaPlanos.removeAll(Collections.singleton(null));
      for (QuestaoInstituto questaoInstituto : listaPlanos) {
        List<QuestaoInstituto> listaFilhos = questaoInstituto.getItens();
          cleanPlanoModeloList(listaFilhos);
        }
      }
  }
}
