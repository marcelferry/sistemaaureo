package com.concafras.gestao.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.concafras.gestao.form.FacilitadorForm;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.Instituto;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.FacilitadorService;
import com.concafras.gestao.service.RodizioService;

@Controller
@RequestMapping("/gestao/facilitador")
public class FacilitadorController {
	
    @Autowired
    private FacilitadorService facilitadorService;
    
    @Autowired
    private BaseInstitutoService baseInstitutoService;

    @Autowired
    private RodizioService rodizioService;
    
    @Autowired
    @Qualifier("facilitadorFormValidator")
    private Validator validator;
 
    @InitBinder("facilitadorForm")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
    
   
    @RequestMapping("/")
    public ModelAndView listRodizio(Map<String, Object> map) {
      
    	FacilitadorForm form = new FacilitadorForm();
    	
    	ModelAndView model = new ModelAndView("facilitador.selecionar", "facilitadorForm", form);
    	model.addObject("rodizioList", rodizioService.findAll());
    	model.addObject("rodizio", true);
    	model.addObject("fase", 1);
    	return model;
    }
    
    @RequestMapping("/institutos")
    public ModelAndView listBaseInstituto(Map<String, Object> map, @Valid @ModelAttribute("facilitadorForm") FacilitadorForm form, BindingResult result, HttpServletRequest request) {
    	
    	if (result.hasErrors()) {
    		map.put("rodizioList", rodizioService.findAll());
			  return new ModelAndView("facilitador.selecionar","facilitadorForm", form);
		  }
    	
    	Rodizio rodizio = rodizioService.findById(form.getRodizio().getId());
    	form.setRodizio(rodizio);
    	form.setFase(2);
    	
    	String role = (String) request.getSession().getAttribute("ROLE_CONTROLE");
      BaseInstituto instituto = (BaseInstituto) request.getSession().getAttribute("INSTITUTO_CONTROLE");
      
      if(role != null && role.equals("ROLE_METAS_DIRIGENTE")){
        form.setInstituto(new Instituto());
        form.getInstituto().setId(instituto.getId());
        return listFacilitador(map, form, result);
      }
    	
    	ModelAndView model = new ModelAndView("facilitador.selecionarInstituto", "facilitadorForm", form);
    	model.addObject("institutoList", baseInstitutoService.findByRodizio(true));
    	model.addObject("rodizio", true);
    	return model;
    }
    
  @RequestMapping("/listar")
  public ModelAndView listFacilitador(Map<String, Object> map, @Valid @ModelAttribute("facilitadorForm") FacilitadorForm form, BindingResult result) {

    if (result.hasErrors()) {
      ModelAndView model = new ModelAndView("facilitador.selecionarInstituto", "facilitadorForm", form);
      model.addObject("institutoList", baseInstitutoService.findByRodizio(true));
      model.addObject("rodizio", true);
      return model;
    }

    BaseInstituto instituto = baseInstitutoService.findById(form.getInstituto().getId());
    Rodizio rodizio = rodizioService.findById(form.getRodizio().getId());

    List<Facilitador> facilitadores = facilitadorService .listFacilitadorByInstitutoRodizio(
            form.getInstituto().getId(), 
            form.getRodizio().getId());

    form.setInstituto(instituto);
    form.setRodizio(rodizio);

    if (facilitadores != null) {
      form.setFacilitadores(new ArrayList<FacilitadorForm>());
      for (Facilitador facilitador : facilitadores) {
        FacilitadorForm facilitadorForm = new FacilitadorForm();
        facilitadorForm.setId(facilitador.getId());
        facilitadorForm.setRodizio(facilitador.getRodizio());
        facilitadorForm.setInstituto(facilitador.getInstituto());
        facilitadorForm.setTrabalhador(facilitador.getTrabalhador());
        form.getFacilitadores().add(facilitadorForm);
      }
    }

    form.setFase(2);

    ModelAndView model = new ModelAndView("facilitador.listar", "facilitadorForm", form);
    model.addObject("rodizio", Boolean.TRUE);
    return model;
  }
    
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public ModelAndView addFacilitador(Map<String, Object> map,
      @ModelAttribute("facilitadorForm") FacilitadorForm form) {

    Rodizio rodizio = rodizioService.findById(form.getRodizio().getId());
    BaseInstituto baseInstituto = baseInstitutoService.findById(form.getInstituto().getId());

    form.setRodizio(rodizio);
    form.setInstituto(baseInstituto);

    ModelAndView model = new ModelAndView("facilitador.novo", "facilitadorForm", form);

    List<Rodizio> listaRodizio = rodizioService.findAll();

    Map<String, String> rodizioList = new HashMap<String, String>();
    for (Rodizio rod : listaRodizio) {
      rodizioList.put(String.valueOf(rod.getId()),
          String.valueOf(rod.getCiclo()));
    }

    model.addObject("rodizioList", rodizioList);
    model.addObject("rodizio", true);
    return model;
  }
    
    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("facilitadorForm") FacilitadorForm facilitadorForm) {
    	
    	BaseInstituto instituto = baseInstitutoService.findById(facilitadorForm.getInstituto().getId());

      ModelAndView model = new ModelAndView("facilitador.listar", "facilitadorForm", facilitadorForm);
      model.addObject("instituto", instituto);
    	model.addObject("rodizio", Boolean.TRUE);
        
      return model;
    }
  
    
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addFacilitador(Map<String, Object> map,
      @ModelAttribute("facilitador") Facilitador facilitador,
      BindingResult result, RedirectAttributes facilitadorForm) {

    FacilitadorForm form = new FacilitadorForm();

    form.setRodizio(new Rodizio());
    form.setInstituto(new BaseInstituto());

    form.getRodizio().setId(facilitador.getRodizio().getId());
    form.getInstituto().setId(facilitador.getInstituto().getId());
    form.getInstituto().setDescricao(facilitador.getInstituto().getDescricao());

    if (facilitador.getTrabalhador() != null
        && facilitador.getTrabalhador().getId() == null) {
      facilitador.setTrabalhador(null);
      ObjectError error = new ObjectError("trabalhador.id",
          "Trabalhador não localizado.");

      result.addError(error);

      List<Rodizio> listaRodizio = rodizioService.findAll();

      Map<String, String> rodizioList = new HashMap<String, String>();
      for (Rodizio rod : listaRodizio) {
        rodizioList.put(String.valueOf(rod.getId()),
            String.valueOf(rod.getCiclo()));
      }
      
      map.put("facilitadorForm", form);
      map.put("rodizioList", rodizioList);
      map.put("rodizio", true);
      return "facilitador.novo";

    }

    facilitadorService.addFacilitador(facilitador);

    facilitadorForm.addFlashAttribute("facilitadorForm", form);

    return "redirect:/gestao/facilitador/listar";
  }

  @RequestMapping("/delete/{facilitadorId}")
  public String deleteFacilitador(
      @PathVariable("facilitadorId") Integer facilitadorId,
      RedirectAttributes facilitadorForm) {

    FacilitadorForm form = new FacilitadorForm();

    form.setRodizio(new Rodizio());
    form.setInstituto(new BaseInstituto());

    Facilitador facilitador = facilitadorService.getFacilitador(facilitadorId);

    form.getRodizio().setId(facilitador.getRodizio().getId());
    form.getInstituto().setId(facilitador.getInstituto().getId());

    facilitadorService.removeFacilitador(facilitadorId);

    facilitadorForm.addFlashAttribute("facilitadorForm", form);

    return "redirect:/gestao/facilitador/listar";
  }
    
}
