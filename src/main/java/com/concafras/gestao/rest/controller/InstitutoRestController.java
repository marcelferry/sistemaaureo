package com.concafras.gestao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.service.BaseInstitutoService;


@Controller
public class InstitutoRestController {
  
  @Autowired
  private BaseInstitutoService baseInstitutoService;
  
  @RequestMapping(value="/api/v1/institutos", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public ResponseEntity<?> findAll(){
    
    List<BaseInstituto> institutos = baseInstitutoService.findByRodizio(true);
    
    return new ResponseEntity<List<BaseInstituto>>(institutos, HttpStatus.OK  );
  }
  
}
