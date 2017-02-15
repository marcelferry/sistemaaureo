package com.concafras.gestao.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.concafras.gestao.model.security.LoginHistory;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.LoginHistoryService;
import com.concafras.gestao.service.UsuarioService;

public class CustomAuthenticationFailureHandler extends
    SimpleUrlAuthenticationFailureHandler {

  @Autowired
  private UsuarioService usuarioService;
  
  @Autowired
  private LoginHistoryService loginHistoryService;

  public CustomAuthenticationFailureHandler() {
    super();
  }

  public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
    super(defaultFailureUrl);
  }

  public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";

  private static Logger log = Logger
      .getLogger(AuthenticationFailureHandler.class);

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    String username = obtainUsername(request);
    log.info("login failed for user: " + username);

    // notify the processor (that updates the DB):

    Usuario user = usuarioService.findByUsername(username);

    if (user != null) {
      String ipAddress = request.getHeader("X-FORWARDED-FOR");
      if (ipAddress == null) {
        ipAddress = request.getRemoteAddr();
      }

      LoginHistory userLoginHistory = new LoginHistory();
      userLoginHistory.setUser(user);
      userLoginHistory.setLoggedIn(new Date());
      userLoginHistory.setUserIp(ipAddress);
      if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
        userLoginHistory.setStatus("BADCREDENTIALS");
      } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
        userLoginHistory.setStatus("DISABLED");
      }

      loginHistoryService.save(userLoginHistory);
    }

    super.onAuthenticationFailure(request, response, exception);
  }

  protected String obtainUsername(HttpServletRequest request) {
    return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
  }

}