package com.concafras.gestao.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.view.StatusAtualInstitutoGraphicData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/gestao/acompanhamento")
public class AcompanhamentoController {
  
  @RequestMapping(value = "/listarTodasMetas", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String listarTodasMetas(HttpServletRequest request)
      throws IOException {
    List<StatusAtualInstitutoGraphicData> retorno = null;
    
    BaseEntidade base = (BaseEntidade)request.getSession().getAttribute("INSTITUICAO_CONTROLE");
    
    //retorno = planoMetasService.getStatusAtualGraphicData("2015", base.getId());
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(retorno);
    
    return json2;
    
  }
  
  @RequestMapping(value = "/listarMetasVencidas", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String listarMetasVencidas(HttpServletRequest request)
      throws IOException {
    List<StatusAtualInstitutoGraphicData> retorno = null;
    
    BaseEntidade base = (BaseEntidade)request.getSession().getAttribute("INSTITUICAO_CONTROLE");
    
    //retorno = planoMetasService.getStatusAtualGraphicData("2015", base.getId());
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(retorno);
    
    return json2;
    
  }
  
  @RequestMapping(value = "/listarMetasAVencer", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String listarMetasAVencer(HttpServletRequest request)
      throws IOException {
    List<StatusAtualInstitutoGraphicData> retorno = null;
    
    BaseEntidade base = (BaseEntidade)request.getSession().getAttribute("INSTITUICAO_CONTROLE");
    
    //retorno = planoMetasService.getStatusAtualGraphicData("2015", base.getId());
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(retorno);
    
    return json2;
    
  }
  
}
