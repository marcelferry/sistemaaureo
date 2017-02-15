package com.concafras.gestao.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.form.CidadeVO;
import com.concafras.gestao.model.Cidade;
import com.concafras.gestao.rest.utils.RestUtils;
import com.concafras.gestao.service.CidadeService;

@Controller
public class CidadeRestController {
  
  @Autowired
  private CidadeService cidadeService;
  
  @RequestMapping(value = "/api/v1/clientes/datatable/", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String springPaginationDataTables(HttpServletRequest request)
      throws IOException {

    // Fetch the page number from client
    Integer pageNumber = 0;
    if (null != request.getParameter("iDisplayStart"))
      pageNumber = (Integer
          .valueOf(request.getParameter("iDisplayStart")) / 10) + 1;

    // Fetch search parameter
    String searchParameter = request.getParameter("sSearch");
    
    // Sort parameter
    String sortCol = request.getParameter("iSortCol_0");
    
    // Sort parameter
    String sortDir = request.getParameter("sSortDir_0");

    // Fetch Page display length
    Integer pageDisplayLength = Integer.valueOf(request
        .getParameter("iDisplayLength"));

    Long totalDisplayRecords = cidadeService
        .countListRangeCidade(searchParameter);

    // Create page list data
    List<CidadeVO> personsList = createPaginationData(
        searchParameter, sortCol, sortDir, pageNumber, pageDisplayLength);

    return new RestUtils<CidadeVO>().createDatatablePaginateResponse(personsList, totalDisplayRecords.intValue(), totalDisplayRecords.intValue() );
    
  }
  
  
  private List<CidadeVO> createPaginationData(
      String searchParameter, String sortCol, String sortDir, Integer pageNumber,
      Integer pageDisplayLength) {

    int startRange = 0 + (pageDisplayLength * (pageNumber - 1));
    
    if(sortCol.equals("0")){
      sortCol = "id";
    } else if(sortCol.equals("1")){
      sortCol = "nome";
    } else if(sortCol.equals("2")){
      sortCol = "sigla";
    } else {
      sortCol = null;
    }

    List<Cidade> lista = cidadeService.listRangeCidade(
        searchParameter, sortCol, sortDir, startRange, pageDisplayLength);
    List<CidadeVO> retorno = new ArrayList<CidadeVO>();
    
    for (Cidade cidade : lista) {
      CidadeVO e = new CidadeVO(cidade);
      retorno.add(e);
    }
    return retorno;
  }
}
