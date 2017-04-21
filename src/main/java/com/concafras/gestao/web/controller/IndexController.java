package com.concafras.gestao.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController {
  
  private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

  @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,
      RequestMethod.DELETE, RequestMethod.PUT })
  public String redirect() {
    return "redirect:/gestao/home/roles/false";
  }

  @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,
      RequestMethod.DELETE, RequestMethod.PUT }, produces = "application/json")
  public ResponseEntity<?> doGetAjax() {
    return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
  }

}
