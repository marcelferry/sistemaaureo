package com.concafras.gestao.web.controller;

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

import com.concafras.gestao.model.Comissao;
import com.concafras.gestao.model.Instituto;
import com.concafras.gestao.service.ComissaoService;
import com.concafras.gestao.service.InstitutoService;

@Controller
@RequestMapping("/gestao/comissao")
public class ComissaoController {
  
  private static final Logger logger = LoggerFactory.getLogger(ComissaoController.class);

    @Autowired
    private ComissaoService comissaoService;
    
    @Autowired
    private InstitutoService institutoService;

    @RequestMapping("/")
    public String listComissao(Map<String, Object> map) {

        map.put("comissaoList", comissaoService.findAll());
        map.put("basicos", true);
        return "comissao.listar";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addComissao(Map<String, Object> map) {
    	
    	map.put("comissao", new Comissao());
    	List<Instituto> lista = institutoService.findAll();
        Map<String, String> institutoList = new HashMap<String, String>();
        
        for (Instituto instituto : lista) {
			institutoList.put(String.valueOf(instituto.getId()), instituto.getDescricao());
		}
        
        map.put("institutoList",institutoList);
        map.put("basicos", true); 
    	return "comissao.novo";
    }
    

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addComissao(@ModelAttribute("comissao") Comissao comissao, BindingResult result) {

    	comissaoService.save(comissao);

        return "redirect:/gestao/comissao/";
    }
    
    @RequestMapping(value="/edit/{comissaoId}", method=RequestMethod.POST)
    public String editComissao(Map<String, Object> map, @PathVariable("comissaoId") Integer comissaoId) {

    	List<Instituto> lista = institutoService.findAll();
    	Map<String, String> institutoList = new HashMap<String, String>();
    	 
    	for (Instituto instituto : lista) {
			institutoList.put(String.valueOf(instituto.getId()), instituto.getDescricao());
		}
        
        map.put("institutoList",institutoList);
    	
        map.put("comissao", comissaoService.findById(comissaoId));
        map.put("basicos", true);
        return "comissao.editar";
    }
    
    @RequestMapping(value="/edit/save/{comissaoId}", method=RequestMethod.POST)
    public String editComissao(@ModelAttribute("comissao") Comissao comissao, @PathVariable("comissaoId") Integer comissaoId) {
    	
    	comissaoService.update(comissao);

        return "redirect:/gestao/comissao/";
    }

    @RequestMapping("/delete/{comissaoId}")
    public String deleteComissao(@PathVariable("comissaoId") Integer comissaoId) {

    	comissaoService.remove(comissaoId);

        return "redirect:/gestao/comissao/";
    }
}
