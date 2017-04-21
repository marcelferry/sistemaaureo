package com.concafras.gestao.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.concafras.gestao.model.exception.ExceptionVO;
import com.concafras.gestao.model.exception.JqueryBussinessException;
import com.concafras.gestao.service.EmailService;

@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private EmailService emailService;

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(SQLException.class)
  public ModelAndView handleSQLException(HttpServletRequest request,
      Exception ex) {
    logger.info("SQLException Occured:: URL=" + request.getRequestURL());

    emailService.sendEmail(request, ex);

    ex.printStackTrace();

    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", ex);
    mav.addObject("url", request.getRequestURL());
    mav.setViewName("erro_persistencia");

    return mav;
  }

  @ExceptionHandler(PersistenceException.class)
  public ModelAndView handlePersistenceException(HttpServletRequest request,
      Exception ex) {
    logger
        .info("PersistenceException Occured:: URL=" + request.getRequestURL());


    emailService.sendEmail(request, ex);
    
    ex.printStackTrace();

    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", ex);
    mav.addObject("url", request.getRequestURL());
    mav.setViewName("erro_persistencia");

    return mav;

  }

  @ExceptionHandler(JqueryBussinessException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ExceptionVO handleException5(JqueryBussinessException ex,
      HttpServletResponse response) throws IOException {
    ex.printStackTrace();
    ExceptionVO exceptionVO = new ExceptionVO("handleException5()",
        "ExceptionController", ex.getMessage());

    return exceptionVO;

  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
  @ExceptionHandler(IOException.class)
  public void handleIOException() {
    logger.error("IOException handler executed");
    // returning 404 error code
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView handleError(HttpServletRequest req, Exception exception) {
    logger.error("Request: " + req.getRequestURL() + " raised " + exception);

    emailService.sendEmail(req, exception);
    
    exception.printStackTrace();

    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", exception);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName("erro_generico");

    return mav;
  }
}