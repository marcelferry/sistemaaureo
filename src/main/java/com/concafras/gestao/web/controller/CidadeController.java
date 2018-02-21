package com.concafras.gestao.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.concafras.gestao.model.Cidade;
import com.concafras.gestao.model.exception.JqueryBussinessException;
import com.concafras.gestao.rest.model.CidadeVO;
import com.concafras.gestao.rest.utils.RestUtils;
import com.concafras.gestao.service.CidadeService;

@Controller
@RequestMapping("/gestao/cidade")
public class CidadeController {
  
  private static final Logger logger = LoggerFactory.getLogger(CidadeController.class);

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping("/")
    public String listCidade(Map<String, Object> map) {

        map.put("cidadeList", cidadeService.listCidade());
        map.put("auxiliares", true);
        return "cidade.listar";
    }
    
    @RequestMapping("/list")
    public @ResponseBody List<Cidade> listInstituto(@RequestParam String query, @RequestParam int maxRows, @RequestParam(required=false) boolean rodizio) {
    	try {
	    	System.out.println(query);
	    	return cidadeService.listCidadeResumo(query, maxRows, rodizio);
    	} catch (Throwable ex){
    		throw new JqueryBussinessException(ex.getMessage());
    	}
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCidade(Map<String, Object> map) {
    	
    	map.put("cidade", new Cidade());
    	map.put("auxiliares", true); 
    	return "cidade.novo";
    }
    

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCidade(@ModelAttribute("cidade") Cidade cidade, BindingResult result) {

    	cidadeService.addCidade(cidade);

        return "redirect:/gestao/cidade/";
    }
    
    @RequestMapping(value="/edit/{cidadeId}", method=RequestMethod.POST)
    public String editCidade(Map<String, Object> map, @PathVariable("cidadeId") Integer cidadeId) {

    	
        map.put("cidade", cidadeService.getCidade(cidadeId));
        map.put("auxiliares", true);
        return "cidade.editar";
    }
    
    @RequestMapping(value="/edit/save/{cidadeId}", method=RequestMethod.POST)
    public String editCidade(@ModelAttribute("cidade") Cidade cidade, @PathVariable("cidadeId") Integer cidadeId) {
    	
    	cidadeService.updateCidade(cidade);

        return "redirect:/gestao/cidade/";
    }

    @RequestMapping("/delete/{cidadeId}")
    public String deleteCidade(@PathVariable("cidadeId") Integer cidadeId) {

    	cidadeService.removeCidade(cidadeId);

        return "redirect:/gestao/cidade/";
    }
    
    
    @RequestMapping(value = "/listPagination", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
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
