package com.concafras.gestao.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.concafras.gestao.model.exception.ExceptionVO;
import com.concafras.gestao.model.exception.JqueryBussinessException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private JavaMailSender mailSender;

  private static final Logger logger = LoggerFactory
      .getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(SQLException.class)
  public ModelAndView handleSQLException(HttpServletRequest request,
      Exception ex) {
    logger.info("SQLException Occured:: URL=" + request.getRequestURL());

    sendEmail(request, ex);

    ex.printStackTrace();

    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", ex);
    mav.addObject("url", request.getRequestURL());
    mav.setViewName("erro_persistencia");

    return mav;
  }

  private void sendEmail(HttpServletRequest request, Exception exception) {
    try {

      final StringBuffer sb = new StringBuffer();
      
      String uri = request.getScheme() + "://" +   // "http" + "://
          request.getServerName() +       // "myhost"
          ":" + request.getServerPort() + // ":" + "8080"
          request.getRequestURI() +       // "/people"
         (request.getQueryString() != null ? "?" +
          request.getQueryString() : ""); // "?" + "lastname=Fox&age=30"
      
      sb.append("<b>Site URL</b><br/>");
      
      sb.append(uri);
      
      sb.append("<br/>");
  
      sb.append("<b>Variaveis de Sessão</b><br/>");
  
      Enumeration<String> sParameters = request.getSession().getAttributeNames();
  
      while (sParameters.hasMoreElements()) {
        String key = sParameters.nextElement();
        sb.append(key);
        sb.append("=");
        sb.append(request.getSession().getAttribute(key));
        sb.append("<br/>");
      }
  
      sb.append("<br/>");
  
      sb.append("<b>Variaveis de Requisição</b><br/>");
  
      Map<String, String[]> parameters = request.getParameterMap();
  
      for (String parameter : parameters.keySet()) {
        String[] values = parameters.get(parameter);
        sb.append(parameter);
        sb.append("=");
        for (String string : values) {
          sb.append(" | ");
          sb.append(string);
          sb.append(" | ");
        }
        sb.append("<br/>");
      }
  
      sb.append("<br/>");
      
      sb.append("<b>Variaveis de Sessão</b><br/>");
      
      Enumeration<String> rParameters = request.getSession().getAttributeNames();
  
      while (rParameters.hasMoreElements()) {
        String key = rParameters.nextElement();
        sb.append(key);
        sb.append("=");
        sb.append(request.getSession().getAttribute(key));
        sb.append("<br/>");
      }
      
      sb.append("<br/>");
      
      sb.append("<b>Exceção</b><br/>");
  
      
      StackTraceElement[] elements = exception.getStackTrace();
  
      for (StackTraceElement stackTraceElement : elements) {
        sb.append(stackTraceElement.toString());
        sb.append("<br/>");
      }
      
      MimeMessagePreparator preparator = new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws Exception {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
          message.setSubject("Houve um erro na aplicação!");
          message
              .setTo(new InternetAddress(
                  "Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>"));
  
          message.setText(sb.toString(), true);
        }
      };
      
      EmailController.sendMessage(this.mailSender, preparator);
      
    } catch (Exception e) {
      // TODO: handle exception
    }
    

  }

  @ExceptionHandler(PersistenceException.class)
  public ModelAndView handlePersistenceException(HttpServletRequest request,
      Exception ex) {
    logger
        .info("PersistenceException Occured:: URL=" + request.getRequestURL());


    sendEmail(request, ex);
    
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

    sendEmail(req, exception);
    
    exception.printStackTrace();

    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", exception);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName("erro_generico");

    return mav;
  }
}