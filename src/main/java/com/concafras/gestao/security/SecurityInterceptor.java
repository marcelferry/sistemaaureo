package com.concafras.gestao.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.concafras.gestao.model.DirigenteNacional;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.DirigenteNacionalService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.UsuarioService;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
  
  @Autowired
  private UsuarioService userService;
  
  @Autowired
  private EntidadeService entidadeService;
  
  @Autowired
  private DirigenteNacionalService dirigenteNacionalService;
  
  
  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if(auth != null){
      Usuario user = (Usuario) session.getAttribute("user");
      if (user == null) {
        String email = auth.getName();
        user = userService.findByUsername(email);
        session.setAttribute("user", user);
      }
  
      List<String> listaRoles = new ArrayList<String>();
      
      Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
      
      for (GrantedAuthority grantedauth : authorities) {
        listaRoles.add(grantedauth.getAuthority());
      }
      
      String roleDefault = (String)session.getAttribute("ROLE_CONTROLE");
      
      if( roleDefault == null || roleDefault.equals("ROLE_ANONYMOUS") ){
        if(authorities.size() == 1){
          if(!listaRoles.get(0).equals("ROLE_ANONYMOUS")){
            roleDefault = listaRoles.get(0);
            session.setAttribute("ROLE_CONTROLE", roleDefault);
          } else {
            response.sendRedirect(request.getContextPath() + "/gestao/home/roles/false");
          }
        } else {
          response.sendRedirect(request.getContextPath() + "/gestao/home/roles/false");
        }
      } 
      
      if(roleDefault != null){
        if(roleDefault.equals("ROLE_METAS_PRESIDENTE")){
          List<Entidade> entidades = entidadeService.getEntidade(user.getPessoa());
    
          if (entidades != null && entidades.size() == 1) {
    
            session.setAttribute("INSTITUICAO_CONTROLE",
                entidades.get(0));
          }
        }
        
        if(roleDefault.equals("ROLE_METAS_DIRIGENTE")){
          List<DirigenteNacional> institutos = dirigenteNacionalService.findByDirigente(user.getPessoa());
    
          if (institutos != null && institutos.size() == 1) {
    
            session.setAttribute("INSTITUTO_CONTROLE",
                institutos.get(0).getInstituto());
          }
        }
      }
      
      return super.preHandle(request, response, handler);
  
    }
    
    return super.preHandle(request, response, handler);
    
  }
  
  @Override
  public void postHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler, ModelAndView modelAndView)
      throws Exception {
    if (modelAndView != null) {
      ServletRequest req = (ServletRequest) request;
      ServletResponse resp = (ServletResponse) response;
      FilterInvocation filterInvocation = new FilterInvocation(req, resp,
          new FilterChain() {
            public void doFilter(ServletRequest request,
                ServletResponse response) throws IOException, ServletException {
              throw new UnsupportedOperationException();
            }
          });

      Authentication authentication = SecurityContextHolder.getContext()
          .getAuthentication();
      if (authentication != null) {
        WebSecurityExpressionRoot sec = new WebSecurityExpressionRoot(
            authentication, filterInvocation);
        sec.setTrustResolver(new AuthenticationTrustResolverImpl());
        modelAndView.getModel().put("sec", sec);
      }
    }
  }
}
