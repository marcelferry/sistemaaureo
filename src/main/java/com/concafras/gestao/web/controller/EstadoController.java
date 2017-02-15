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

import com.concafras.gestao.model.Estado;
import com.concafras.gestao.service.EstadoService;

@Controller
@RequestMapping("/gestao/estado")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @RequestMapping("/")
    public String listEstado(Map<String, Object> map) {

        map.put("estadoList", estadoService.listEstado());
        map.put("aureo", true);
        return "estado.listar";
    }
    
    @RequestMapping("/list")
    public @ResponseBody List<Estado> listInstituto(@RequestParam String query, @RequestParam int maxRows) {
    	return estadoService.listEstado(query);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addEstado(Map<String, Object> map) {
    	
    	map.put("estado", new Estado());
        map.put("auxiliares", true); 
    	return "estado.novo";
    }
    

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addEstado(@ModelAttribute("estado") Estado estado, BindingResult result) {

    	estadoService.addEstado(estado);

        return "redirect:/gestao/estado/";
    }
    
    @RequestMapping(value="/edit/{estadoId}", method=RequestMethod.POST)
    public String editEstado(Map<String, Object> map, @PathVariable("estadoId") Integer estadoId) {

    	
        map.put("estado", estadoService.getEstado(estadoId));
        map.put("auxiliares", true);
        return "estado.editar";
    }
    
    @RequestMapping(value="/edit/save/{estadoId}", method=RequestMethod.POST)
    public String editEstado(@ModelAttribute("estado") Estado estado, @PathVariable("estadoId") Integer estadoId) {
    	
    	estadoService.updateEstado(estado);

        return "redirect:/gestao/estado/";
    }

    @RequestMapping("/delete/{estadoId}")
    public String deleteEstado(@PathVariable("estadoId") Integer estadoId) {

    	estadoService.removeEstado(estadoId);

        return "redirect:/gestao/estado/";
    }
}
