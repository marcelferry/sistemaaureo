package com.concafras.gestao.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.view.StatusAtualInstitutoGraphicData;
import com.concafras.gestao.service.PlanoMetasService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/gestao/graphicData")
public class GraphicDataController {
	
	@Autowired
	PlanoMetasService planoMetasService;
	
	@RequestMapping(value = "/statusContratadoGeralGraphicData/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusContratadoGeralAtual(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo)
      throws IOException {
    List<StatusAtualInstitutoGraphicData> retorno = null;
    
    retorno = planoMetasService.getStatusContratadoGeralData(ciclo, null);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(retorno);

    return json2;
  
  }
	
	@RequestMapping(value = "/statusContratadoGeralGraphicData/{ciclo}/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	String statusContratadoGeralAtual(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo, @PathVariable("instituto") Integer instituto)
	    throws IOException {
	  List<StatusAtualInstitutoGraphicData> retorno = null;
	  
	  retorno = planoMetasService.getStatusContratadoGeralData(ciclo, instituto);
	  
	  Gson gson = new GsonBuilder().setPrettyPrinting().create();
	  
	  String json2 = gson.toJson(retorno);
	  
	  return json2;
	  
	}
	
	 @RequestMapping(value = "/statusContratadoPorRegiaoGraphicData/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	  public @ResponseBody
	  String statusContratadoPorRegiaoAtual(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo)
	      throws IOException {
	    List<StatusAtualInstitutoGraphicData> retorno = null;
	    
	    retorno = planoMetasService.getStatusContratadoPorRegiaoData(ciclo, null);
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    
	    String json2 = gson.toJson(retorno);

	    return json2;
	  
	  }
	 
	 @RequestMapping(value = "/statusContratadoPorRegiaoGraphicData/{ciclo}/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	 public @ResponseBody
	 String statusContratadoPorRegiaoAtual(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo, @PathVariable("instituto") Integer instituto)
	     throws IOException {
	   List<StatusAtualInstitutoGraphicData> retorno = null;
	   
	   retorno = planoMetasService.getStatusContratadoPorRegiaoData(ciclo, instituto);
	   
	   Gson gson = new GsonBuilder().setPrettyPrinting().create();
	   
	   String json2 = gson.toJson(retorno);
	   
	   return json2;
	   
	 }
	
	@RequestMapping(value = "/statusContratadoPorInstitutoGraphicData/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusContratadoPorInstitutoAtual(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo)
      throws IOException {
    List<StatusAtualInstitutoGraphicData> retorno = null;
    
    retorno = planoMetasService.getStatusContratadoPorInstitutoData(ciclo, null);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(retorno);

    return json2;
  
  }
	
	@RequestMapping(value = "/statusContratadoPorInstitutoEntidadeGraphicData/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusContratadoPorInstitutoEntidadeAtual(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo)
      throws IOException {
    List<StatusAtualInstitutoGraphicData> retorno = null;
    
    BaseEntidade base = (BaseEntidade)request.getSession().getAttribute("INSTITUICAO_CONTROLE");
    
    retorno = planoMetasService.getStatusContratadoPorInstitutoData(ciclo, base.getId());
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(retorno);

    return json2;
  
  }
	
	@RequestMapping(value = "/statusContratadoInstitutoGraphicData/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusContratado(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
      throws IOException {
    List<StatusAtualInstitutoGraphicData> retorno = null;
    
    retorno = planoMetasService.getStatusContratadoGraphicData(ciclo, entidade);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(retorno);
    
    return json2;
    
  }

	@RequestMapping(value = "/statusAtualInstitutoGraphicData/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	String statusAtual(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
			throws IOException {
		List<StatusAtualInstitutoGraphicData> retorno = null;
		
		retorno = planoMetasService.getStatusAtualGraphicData(ciclo, entidade);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String json2 = gson.toJson(retorno);

		return json2;
	
	}
	
	@RequestMapping(value = "/statusAtualInstitutoGraphicData", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	String statusAtual(HttpServletRequest request)
	    throws IOException {
	  List<StatusAtualInstitutoGraphicData> retorno = null;
	  
	  BaseEntidade base = (BaseEntidade)request.getSession().getAttribute("INSTITUICAO_CONTROLE");
	  Rodizio ciclo = (Rodizio)request.getSession().getAttribute("CICLO_CONTROLE");
	  
	  retorno = planoMetasService.getStatusAtualGraphicData(ciclo.getId(), base.getId());
	  
	  Gson gson = new GsonBuilder().setPrettyPrinting().create();
	  
	  String json2 = gson.toJson(retorno);
	  
	  return json2;
	  
	}
	
	
	
	@RequestMapping(value = "/statusPropostoInstitutoGraphicData/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusProposto(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
      throws IOException {
    List<StatusAtualInstitutoGraphicData> retorno = null;
    
    retorno = planoMetasService.getStatusPlanejadoGraphicData(ciclo, entidade);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(retorno);

    return json2;
  
  }
	
	
	 @RequestMapping(value = "/statusPropostoInstitutoGraphicData", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	  public @ResponseBody
	  String statusProposto(HttpServletRequest request)
	      throws IOException {
	    List<StatusAtualInstitutoGraphicData> retorno = null;
	    
	    BaseEntidade base = (BaseEntidade)request.getSession().getAttribute("INSTITUICAO_CONTROLE");
	    
	    Rodizio ciclo = (Rodizio)request.getSession().getAttribute("CICLO_CONTROLE");
	    
	    retorno = planoMetasService.getStatusPlanejadoGraphicData(ciclo.getId(), base.getId());
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    
	    String json2 = gson.toJson(retorno);

	    return json2;
	  
	  }
	 
	 
	 /**
	  * Retorna a lista de status para exibição das informacoes de validação 
	  * @param request
	  * @param ciclo
	  * @return
	  * @throws IOException
	  */
	 @RequestMapping(value = "/statusValidadoGeralGraphicData/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	  public @ResponseBody
	  String statusValidadoGeralAtual(HttpServletRequest request,  @PathVariable("ciclo") Integer ciclo)
	      throws IOException {
	    List<StatusAtualInstitutoGraphicData> retorno = null;
	    
	    retorno = planoMetasService.getStatusValidacaoGeralData(ciclo);
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    
	    String json2 = gson.toJson(retorno);
	    
	    return json2;
	    
	  }
	
	
}
