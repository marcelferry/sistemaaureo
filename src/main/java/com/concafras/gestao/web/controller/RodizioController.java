package com.concafras.gestao.web.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.service.RodizioService;

@Controller
@RequestMapping("/gestao/rodizio")
public class RodizioController {
  
  private static final Logger logger = LoggerFactory.getLogger(RodizioController.class);

  @Autowired
  private RodizioService rodizioService;

  @RequestMapping("/")
  public String listRodizio(Map<String, Object> map) {
    map.put("rodizioList", rodizioService.findAll());
    map.put("rodizio", true);
    return "rodizio.listar";
  }

  @RequestMapping("/list")
  public @ResponseBody List<Rodizio> listInstituto(@RequestParam String query,
      @RequestParam int maxRows) {
    return rodizioService.findByCicloContains(query);
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String addRodizio(Map<String, Object> map) {
    map.put("rodizioForm", new Rodizio());
    map.put("rodizioList", rodizioService.findAll());
    map.put("rodizio", true);
    return "rodizio.novo";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addRodizio(@ModelAttribute("rodizio") Rodizio rodizio,
      BindingResult result) {
    rodizioService.save(rodizio);
    return "redirect:/gestao/rodizio/";
  }

  @RequestMapping(value = "/edit/{rodizioId}", method = RequestMethod.POST)
  public String editRodizio(Map<String, Object> map,
      @PathVariable("rodizioId") Integer rodizioId) {
    map.put("rodizioForm", rodizioService.findById(rodizioId));
    map.put("rodizioList", rodizioService.findAll());
    map.put("rodizio", true);
    return "rodizio.editar";
  }

  @RequestMapping(value = "/edit/save/{rodizioId}", method = RequestMethod.POST)
  public String editRodizio(@ModelAttribute("rodizio") Rodizio rodizio,
      @PathVariable("rodizioId") Integer rodizioId) {
    rodizioService.update(rodizio);
    return "redirect:/gestao/rodizio/";
  }

  @RequestMapping("/delete/{rodizioId}")
  public String deleteRodizio(@PathVariable("rodizioId") Integer rodizioId) {
    rodizioService.remove(rodizioId);
    return "redirect:/gestao/rodizio/";
  }
}
