package com.concafras.gestao.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.rest.model.DatatableResponse;
import com.concafras.gestao.service.MetasInstitutoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class PlanoModeloRestController {

  @Autowired
  private MetasInstitutoService metasInstitutoService;
  
  @RequestMapping(value = "/api/v1/planomodelo/ciclo/{ciclo}/instituto/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listarMetasPorInstituto(
      HttpServletRequest request, 
      @PathVariable("ciclo") int ciclo,
      @PathVariable("instituto") int instituto){
    
    //TODO: Regra diferente 
    List<MetaInstituto> retorno = metasInstitutoService.listMetaInstitutoByInstitutoRodizio(instituto, ciclo);
    
    DatatableResponse<MetaInstituto> result = new DatatableResponse<MetaInstituto>();
    result.setiTotalDisplayRecords(retorno.size());
    result.setiTotalRecords(retorno.size());
    result.setAaData(retorno);
    result.setSuccess(true);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(result);
    
    return json2;
  }
      
  
}
