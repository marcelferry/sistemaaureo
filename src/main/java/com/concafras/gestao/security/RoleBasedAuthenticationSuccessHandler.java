package com.concafras.gestao.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.concafras.gestao.model.security.LoginHistory;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.LoginHistoryService;
import com.concafras.gestao.service.UsuarioService;

public class RoleBasedAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Autowired
  private UsuarioService usuarioService;
  
  @Autowired
  private LoginHistoryService loginHistoryService;

  protected Log logger = LogFactory.getLog(this.getClass());

  private String defaultRedirectUrl;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    
    UsuarioAutenticado customUserDetails = (UsuarioAutenticado) authentication.getPrincipal();
    Usuario user = usuarioService.findByUsername(customUserDetails.getUsername());

    WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
    String ip = details.getRemoteAddress();

    LoginHistory userLoginHistory = new LoginHistory();
    userLoginHistory.setUser(user);
    userLoginHistory.setLoggedIn(new Date());
    userLoginHistory.setUserIp(ip);
    userLoginHistory.setStatus("SUCCESS");
    loginHistoryService.save(userLoginHistory);
    
    super.onAuthenticationSuccess(request, response, authentication);

  }

  protected String determineTargetUrl(Authentication authentication) {
    return defaultRedirectUrl;
  }

  public void setDefaultRedirectUrl(String defaultRedirectUrl) {
    this.defaultRedirectUrl = defaultRedirectUrl;
  }
  
  public String getDefaultRedirectUrl() {
    return defaultRedirectUrl;
  }
}