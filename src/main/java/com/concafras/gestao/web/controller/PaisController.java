package com.concafras.gestao.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.model.Pais;
import com.concafras.gestao.service.PaisService;

@Controller
@RequestMapping("/gestao/pais")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @RequestMapping("/")
    public String listPais(Map<String, Object> map) {

        map.put("paisList", paisService.listPais());
        map.put("auxiliares", true);
        return "pais.listar";
    }
    
    @RequestMapping("/list")
    public @ResponseBody List<Pais> listInstituto(@RequestParam String query, @RequestParam int maxRows) {
    	return paisService.listPais(query);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPais(Map<String, Object> map) {
    	
    	map.put("pais", new Pais());
        map.put("auxiliares", true); 
    	return "pais.novo";
    }
    

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPais(@ModelAttribute("pais") Pais pais, BindingResult result) {

    	paisService.addPais(pais);

        return "redirect:/gestao/pais/";
    }
    
    @RequestMapping(value="/edit/{paisId}", method=RequestMethod.POST)
    public String editPais(Map<String, Object> map, @PathVariable("paisId") Integer paisId) {

    	
        map.put("pais", paisService.getPais(paisId));
        map.put("auxiliares", true);
        return "pais.editar";
    }
    
    @RequestMapping(value="/edit/save/{paisId}", method=RequestMethod.POST)
    public String editPais(@ModelAttribute("pais") Pais pais, @PathVariable("paisId") Integer paisId) {
    	
    	paisService.updatePais(pais);

        return "redirect:/gestao/pais/";
    }

    @RequestMapping("/delete/{paisId}")
    public String deletePais(@PathVariable("paisId") Integer paisId) {

    	paisService.removePais(paisId);

        return "redirect:/gestao/pais/";
    }
}
