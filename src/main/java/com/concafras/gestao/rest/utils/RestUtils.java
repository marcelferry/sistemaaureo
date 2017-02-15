package com.concafras.gestao.rest.utils;

import java.util.List;

import com.concafras.gestao.rest.model.DatatableResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestUtils<T> {

  public String createDatatableResponse(List<T> aaData) {
    DatatableResponse<T> response = new DatatableResponse<T>();
    response.setiTotalDisplayRecords(aaData.size());
    response.setiTotalRecords(aaData.size());
    response.setAaData(aaData);
    response.setSuccess(true);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(response);
    
    return json2;
  }

  public String createDatatablePaginateResponse( List<T> aaData, int iTotalDisplayRecords, int iTotalRecords) {
    
    DatatableResponse<T> response = new DatatableResponse<T>();
    response.setiTotalDisplayRecords(iTotalDisplayRecords);
    response.setiTotalRecords(iTotalRecords);
    response.setAaData(aaData);
    response.setSuccess(true);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(response);
    
    return json2;
  }

}
