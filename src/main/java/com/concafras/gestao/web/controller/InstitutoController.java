package com.concafras.gestao.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.form.PessoaOptionForm;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Instituto;
import com.concafras.gestao.model.exception.JqueryBussinessException;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.InstitutoService;

@Controller
@RequestMapping("/gestao/instituto")
public class InstitutoController {

  private static final Logger logger = LoggerFactory.getLogger(InstitutoController.class);

  @Autowired
  private InstitutoService institutoService;

  @Autowired
  private BaseInstitutoService baseInstitutoService;

  @RequestMapping("/")
  public String listInstituto(Map<String, Object> map) {

    map.put("institutoList", institutoService.findAll());
    map.put("basicos", true);
    return "instituto.listar";
  }

  @RequestMapping("/listAll")
  @ResponseBody
  public Map<String, List<Instituto>> listInstituto() {

    Map<String, List<Instituto>> retorno = new HashMap<String, List<Instituto>>();

    retorno.put("institutos", institutoService.findAll());

    return retorno;
  }

  @RequestMapping("/list")
  public @ResponseBody List<InstitutoOptionForm> listInstituto(
      @RequestParam String query, @RequestParam int maxRows) {
    try {
      System.out.println(query);
      List<InstitutoOptionForm> retorno = new ArrayList<InstitutoOptionForm>();
      List<Instituto> lista = institutoService.findByDescricaoContains(query);
      for (Instituto instituto : lista) {
        InstitutoOptionForm op = new InstitutoOptionForm();
        op.setId(instituto.getId());
        op.setDescricao(instituto.getDescricao());
        op.setDirigenteNacional(new PessoaOptionForm());
        if (instituto.getDirigenteNacional() != null) {
          op.getDirigenteNacional().setId(
              instituto.getDirigenteNacional().getId());
          op.getDirigenteNacional().setNome(
              instituto.getDirigenteNacional().getNome());
        }
        retorno.add(op);
      }
      return retorno;
    } catch (Throwable ex) {
      logger.error(ex.getMessage(), ex);
      throw new JqueryBussinessException(ex.getMessage());
    }
  }

  @RequestMapping("/listBase")
  public @ResponseBody List<InstitutoOptionForm> listBaseInstituto(
      @RequestParam String query, @RequestParam int maxRows) {
    try {
      List<InstitutoOptionForm> retorno = new ArrayList<InstitutoOptionForm>();
      List<BaseInstituto> lista = baseInstitutoService
          .findByDescricaoLike(query);
      for (BaseInstituto instituto : lista) {
        InstitutoOptionForm op = new InstitutoOptionForm();
        op.setId(instituto.getId());
        op.setDescricao(instituto.getDescricao());
        op.setDirigenteNacional(new PessoaOptionForm());
        if (instituto.getDirigenteNacional() != null) {
          op.getDirigenteNacional().setId(
              instituto.getDirigenteNacional().getId());
          op.getDirigenteNacional().setNome(
              instituto.getDirigenteNacional().getNome());
        }
        retorno.add(op);
      }
      return retorno;
    } catch (Throwable ex) {
      logger.error(ex.getMessage(), ex);
      throw new JqueryBussinessException(ex.getMessage());
    }
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String addInstituto(Map<String, Object> map) {

    map.put("instituto", new Instituto());
    map.put("basicos", true);
    return "instituto.novo";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addInstituto(@ModelAttribute("instituto") Instituto instituto,
      BindingResult result) {

    if (instituto.getDirigenteNacional().getId() == null
        || instituto.getDirigenteNacional().getId() <= 0) {
      instituto.setDirigenteNacional(null);
    }

    institutoService.save(instituto);

    return "redirect:/gestao/instituto/";
  }

  @RequestMapping(value = "/edit/{institutoId}", method = RequestMethod.POST)
  public String editInstituto(Map<String, Object> map,
      @PathVariable("institutoId") Integer institutoId) {

    map.put("instituto", institutoService.findById(institutoId));
    map.put("basicos", true);
    return "instituto.editar";
  }

  @RequestMapping(value = "/edit/save/{institutoId}", method = RequestMethod.POST)
  public String editInstituto(@ModelAttribute("instituto") Instituto instituto,
      @PathVariable("institutoId") Integer institutoId) {

    if (instituto.getDirigenteNacional().getId() == null
        || instituto.getDirigenteNacional().getId() <= 0) {
      instituto.setDirigenteNacional(null);
    }

    institutoService.update(instituto);

    return "redirect:/gestao/instituto/";
  }

  @RequestMapping("/delete/{institutoId}")
  public String deleteInstituto(@PathVariable("institutoId") Integer institutoId) {

    institutoService.remove(institutoId);

    return "redirect:/gestao/instituto/";
  }
}
