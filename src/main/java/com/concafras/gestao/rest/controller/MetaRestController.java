package com.concafras.gestao.rest.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.rest.model.DatatableResponse;
import com.concafras.gestao.service.PlanoMetasService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class MetaRestController {
  
  @Autowired
  PlanoMetasService planoMetasService;

  @RequestMapping(value = "/api/v1/metas/ciclo/{ciclo}/entidades/", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String metasContratadasPorEntidade(
      HttpServletRequest request, 
      @PathVariable("ciclo") Integer ciclo,
      @RequestParam(value = "iDisplayStart", required = false) int iDisplayStart, 
      @RequestParam(value = "sSearch", required = false) String searchParameter,
      @RequestParam(value = "iSortCol_0", required = false) String sortCol,
      @RequestParam(value = "sSortDir_0", required = false) String sortDir,
      @RequestParam(value = "iDisplayLength", required = false) int pageDisplayLength)
      throws IOException {
    
    
    int pageNumber = 1;
    if (iDisplayStart > 0 )
      pageNumber = (iDisplayStart / pageDisplayLength) + 1;
    
    int startRange = 0 + (pageDisplayLength * (pageNumber - 1));
    
    List<EntidadeOptionForm> retorno = null;
    
    if(sortCol.equals("0")){
      sortCol = "nome";
    } else if(sortCol.equals("1")){
      sortCol = "cidade";
    } else if(sortCol.equals("2")){
      sortCol = "sigla";
    } else {
      sortCol = null;
    }
    
    Long totalDisplayRecords = planoMetasService.countListRangeEntidadesCiclo(ciclo, searchParameter);
    
    retorno = planoMetasService.listRangeEntidadesCiclo(ciclo, searchParameter, sortCol, sortDir, startRange, pageDisplayLength);
    
    DatatableResponse<EntidadeOptionForm> result = new DatatableResponse<EntidadeOptionForm>();
    result.setiTotalDisplayRecords(retorno.size());
    result.setiTotalRecords(totalDisplayRecords.intValue());
    result.setAaData(retorno);
    result.setSuccess(true);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(result);
    
    return json2;
    
  }
  
  @RequestMapping(value = "/api/v1/metas/ciclo/{ciclo}/entidade/{entidade}/institutos", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String statusEntidadeContratacaoCiclo(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
      throws IOException {
    
    List<InstitutoOptionForm> retorno = null;
    
    retorno = planoMetasService.getInstitutosContratados(ciclo, entidade);
    
    DatatableResponse<InstitutoOptionForm> result = new DatatableResponse<InstitutoOptionForm>();
    result.setiTotalDisplayRecords(retorno.size());
    result.setiTotalRecords(retorno.size());
    result.setAaData(retorno);
    result.setSuccess(true);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(result);
    
    return json2;
    
  }
  
  @RequestMapping(value = "/api/v1/metas/ciclo/{ciclo}/entidade/{entidade}/instituto/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String listaContratadoEntidadeInstitutoData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade, @PathVariable("instituto") Integer instituto)
      throws IOException {
    return listaContratadoEntidadeInstitutoData(request, ciclo, entidade, instituto, null);
  }
  
  @RequestMapping(value = "/api/v1/metas/ciclo/{ciclo}/entidade/{entidade}/instituto/{instituto}/status/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String listaContratadoEntidadeInstitutoData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade, @PathVariable("instituto") Integer instituto, @PathVariable("status") String status)
      throws IOException {
    
    List<ResumoMetaEntidade> retorno = null;
    
    retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, entidade, instituto, status);
    
    DatatableResponse<ResumoMetaEntidade> result = new DatatableResponse<ResumoMetaEntidade>();
    result.setiTotalDisplayRecords(retorno.size());
    result.setiTotalRecords(retorno.size());
    result.setAaData(retorno);
    result.setSuccess(true);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(result);
    
    return json2;

  }
  
  
}
