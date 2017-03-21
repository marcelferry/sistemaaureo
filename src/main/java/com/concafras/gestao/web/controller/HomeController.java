package com.concafras.gestao.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.DirigenteNacional;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.security.UsuarioAutenticado;
import com.concafras.gestao.service.DirigenteNacionalService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.RodizioService;

@Controller
@RequestMapping("/gestao/home")
public class HomeController {

  private static Map<String, String> rolesNames;

  private static Map<String, String> rolesRedirect;

  static {
    rolesRedirect = new HashMap<String, String>();
    rolesRedirect.put("ROLE_ADMIN", "/gestao/home/dashboard/");
    rolesRedirect.put("ROLE_METAS_FACILITADOR", "/gestao/planodemetas/");
    rolesRedirect.put("ROLE_METAS_SECRETARIA", "/gestao/home/dashboard/");
    rolesRedirect.put("ROLE_METAS_PRESIDENTE", "/gestao/home/presidente/");
    rolesRedirect.put("ROLE_METAS_COORDENADOR", "/gestao/home/coordenador/");
    rolesRedirect.put("ROLE_METAS_GRUPOFRATERNO", "/gestao/home/grupofraterno/");
    rolesRedirect.put("ROLE_METAS_DIRIGENTE", "/gestao/home/dirigente/");
    rolesRedirect.put("ROLE_METAS_CONSELHO", "/gestao/home/conselho/");

    rolesNames = new HashMap<String, String>();
    rolesNames.put("ROLE_ADMIN", "Administrador");
    rolesNames.put("ROLE_METAS_FACILITADOR", "Facilitador do Rodízio");
    rolesNames.put("ROLE_METAS_SECRETARIA", "Secretaria Nacional");
    rolesNames.put("ROLE_METAS_PRESIDENTE", "Presidente de Centro");
    rolesNames.put("ROLE_METAS_COORDENADOR", "Coordenador de Comissão Local");
    rolesNames.put("ROLE_METAS_GRUPOFRATERNO", "Dirigente de Grupo Fraterno");
    rolesNames.put("ROLE_METAS_DIRIGENTE", "Dirigente Nacional de Comissão");
    rolesNames.put("ROLE_METAS_CONSELHO", "Membro do Conselho");

  }

  @Autowired
  private EntidadeService entidadeService;
  
  @Autowired
  private RodizioService rodizioService;
  
  @Autowired
  private DirigenteNacionalService dirigenteNacionalService;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView login(
      @RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout,
      HttpServletRequest request,
      Authentication authentication) {

    if(authentication != null && authentication.isAuthenticated()){
      
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();

      System.out.println("User has authorities: " + userDetails.getAuthorities());

      request.getSession().removeAttribute("ROLE_CONTROLE");
      request.getSession().removeAttribute("INSTITUICAO_CONTROLE");
      
      List<String> listaRoles = new ArrayList<String>();

      for (GrantedAuthority auth : userDetails.getAuthorities()) {
        listaRoles.add(auth.getAuthority());
      }

      if (listaRoles.size() == 1) {
        if (rolesRedirect.containsKey(listaRoles.get(0))) {

          String retorno = rolesRedirect.get(listaRoles.get(0));

          request.getSession().setAttribute("ROLE_CONTROLE", listaRoles.get(0));

          ModelAndView redirectLogin = new ModelAndView();

          redirectLogin.setViewName("redirect:" + retorno);

          return redirectLogin;
        }
      }
      
      ModelAndView selecionaPerfil = new ModelAndView();
      
      selecionaPerfil.addObject("mapRoles", rolesNames);

      selecionaPerfil.addObject("listaRoles", listaRoles);

      selecionaPerfil.setViewName("selecionaperfil");

      return selecionaPerfil;
    }
    
    ModelAndView model = new ModelAndView();
    if (error != null) {
      model.addObject("error", "Usuário e/ou senha inválidos!");
    }

    if (logout != null) {
      model.addObject("msg", "Você saiu do sistema.");
    }
    model.setViewName("login");

    return model;
  }

  @RequestMapping(value = "/roles/{passed}", method = RequestMethod.GET)
  public ModelAndView listaRoles(HttpServletRequest request,
      Authentication authentication,
      @PathVariable(value = "passed") boolean passed) {

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    System.out.println("User has authorities: " + userDetails.getAuthorities());

    request.getSession().removeAttribute("ROLE_CONTROLE");
    request.getSession().removeAttribute("INSTITUICAO_CONTROLE");
    request.getSession().removeAttribute("INSTITUTO_CONTROLE");

    if (userDetails instanceof UsuarioAutenticado) {
      UsuarioAutenticado portal = (UsuarioAutenticado) userDetails;
      if (portal.isPasswordExpired() && !passed) {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("redirect:/gestao/userprofile/trocarsenha");

        return mav;
      }
    }

    ModelAndView model = new ModelAndView();

    List<String> listaRoles = new ArrayList<String>();

    for (GrantedAuthority auth : userDetails.getAuthorities()) {
      listaRoles.add(auth.getAuthority());
    }

    if (listaRoles.size() == 1) {
      if (rolesRedirect.containsKey(listaRoles.get(0))) {

        String retorno = rolesRedirect.get(listaRoles.get(0));

        request.getSession().setAttribute("ROLE_CONTROLE", listaRoles.get(0));

        ModelAndView mav = new ModelAndView();

        mav.setViewName("redirect:" + retorno);

        return mav;
      }
    }

    model.addObject("mapRoles", rolesNames);

    model.addObject("listaRoles", listaRoles);

    model.setViewName("selecionaperfil");

    return model;
  }

  @RequestMapping(value = "/roles", method = RequestMethod.POST)
  public String selecionaRoles(HttpServletRequest request,
      Authentication authentication,
      @RequestParam(value = "role", required = false) String role) {

    if (rolesRedirect.containsKey(role)) {
      String retorno = rolesRedirect.get(role);

      request.getSession().setAttribute("ROLE_CONTROLE", role);

      return "redirect:" + retorno;
    }
    return "selecionaperfil";
  }

  // for 403 access denied page
  @RequestMapping(value = "/403")
  public ModelAndView accesssDenied() {

    ModelAndView model = new ModelAndView();

    // check if user is login
    Authentication auth = SecurityContextHolder.getContext()
        .getAuthentication();
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      UserDetails userDetail = (UserDetails) auth.getPrincipal();
      model.addObject("username", userDetail.getUsername());
    }

    model.setViewName("403");
    return model;

  }

  @RequestMapping("/dashboard/")
  public String dashboard(Map<String, Object> map, HttpServletRequest request) {
    Rodizio rodizio = rodizioService.findByAtivoTrue();
    request.getSession().setAttribute("CICLO_CONTROLE", rodizio);
    map.put("dashboard", true);
    return "dashboard";
  }
  
  @RequestMapping("/dashboard/{ciclo}")
  public String dashboard(Map<String, Object> map, HttpServletRequest request, @PathVariable("ciclo") Integer ciclo) {
    Rodizio rodizio = rodizioService.findById(ciclo);
    request.getSession().setAttribute("CICLO_CONTROLE", rodizio);
    map.put("dashboard", true);
    return "dashboard";
  }
  
  @RequestMapping("/menu")
  public String loadMenu(Map<String, Object> map) {
    List<Rodizio> rodizios = rodizioService.findAll();
    map.put("rodiziolist", rodizios);
    return "menu";
  }
  
  @RequestMapping("/dashboard/listagem/{ciclo}")
  public String listMetas(Map<String, Object> map, HttpServletRequest request, @PathVariable("ciclo") Integer ciclo) {
    Rodizio rodizio = rodizioService.findById(ciclo);
    request.getSession().setAttribute("CICLO_CONTROLE", rodizio);
    map.put("dashboard", true);
    return "listagem";
  }

  @RequestMapping(value = "/presidente", method = RequestMethod.GET)
  public String listPresidente(Map<String, Object> map,
      HttpServletRequest request, Authentication authentication){
    Rodizio rodizio = rodizioService.findByAtivoTrue();
    return listPresidente(map, rodizio.getId(), request, authentication);
  }
  
  @RequestMapping(value = "/presidente/{ciclo}", method = RequestMethod.GET)
  public String listPresidente(Map<String, Object> map, @PathVariable("ciclo") Integer ciclo,
      HttpServletRequest request, Authentication authentication) {
    
    Rodizio rodizio = rodizioService.findById(ciclo);
    request.getSession().setAttribute("CICLO_CONTROLE", rodizio);
    
    Entidade controle = (Entidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
    if(controle != null){
      map.put("dashboard", true);
      return "dashboard";
    } else {

      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
  
      if (userDetails instanceof UsuarioAutenticado) {
        UsuarioAutenticado p = (UsuarioAutenticado) userDetails;
        Pessoa pessoa = p.getPessoa();
  
        List<Entidade> entidades = entidadeService.getEntidade(pessoa);
  
        if (entidades != null && entidades.size() == 1) {
  
          request.getSession().setAttribute("INSTITUICAO_CONTROLE", entidades.get(0));
  
          map.put("dashboard", true);
  
          return "dashboard";
        } 
  
        map.put("listaEntidades", entidades);
      }
  
      return "selecionaentidade";
    }
  }
  
  @RequestMapping(value = "/dirigente", method = RequestMethod.GET)
  public String listDirigente(Map<String, Object> map, 
      HttpServletRequest request, Authentication authentication){
    Rodizio rodizio = rodizioService.findByAtivoTrue();
    return listDirigente(map, rodizio.getId(), request, authentication);
  }
  
  @RequestMapping(value = "/dirigente/{ciclo}", method = RequestMethod.GET)
  public String listDirigente(Map<String, Object> map, @PathVariable("ciclo") Integer ciclo,
      HttpServletRequest request, Authentication authentication) {
    
    Rodizio rodizio = rodizioService.findById(ciclo);
    request.getSession().setAttribute("CICLO_CONTROLE", rodizio);
    
    BaseInstituto controle = (BaseInstituto) request.getSession().getAttribute("INSTITUTO_CONTROLE");
    if(controle != null){
      map.put("dashboard", true);
      return "dashboard";
    } else {
      
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      
      if (userDetails instanceof UsuarioAutenticado) {
        UsuarioAutenticado p = (UsuarioAutenticado) userDetails;
        
        Pessoa pessoa = p.getPessoa();
        
        List<DirigenteNacional> institutos = dirigenteNacionalService.findByDirigente(pessoa);
        
        if (institutos != null && institutos.size() == 1) {
          
          request.getSession().setAttribute("INSTITUTO_CONTROLE",
              institutos.get(0).getInstituto());
          map.put("dashboard", true);
          return "dashboard";
        } 
      }
      
      return null;
    }
    
  }
  
  @RequestMapping(value = "/presidente", method = RequestMethod.POST)
  public String selecionaEntidade(Map<String, Object> map,
    HttpServletRequest request, Authentication authentication, @RequestParam(value = "id", required = true) Integer id){
    Rodizio rodizio = rodizioService.findByAtivoTrue();
    return selecionaEntidade( request, authentication, map, rodizio.getId(), id );
  }

  @RequestMapping(value = "/presidente/{ciclo}", method = RequestMethod.POST)
  public String selecionaEntidade(HttpServletRequest request,
      Authentication authentication, Map<String, Object> map, @PathVariable("ciclo") Integer ciclo,
      @RequestParam(value = "id", required = true) Integer id) {

    Entidade entidade = entidadeService.findById(id);

    request.getSession().setAttribute("INSTITUICAO_CONTROLE", entidade);

    Rodizio rodizio = rodizioService.findById(ciclo);
    request.getSession().setAttribute("CICLO_CONTROLE", rodizio);
    
    map.put("dashboard", true);

    return "dashboard";
  }

}
