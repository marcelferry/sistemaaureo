package com.concafras.gestao.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.LoginHistoryVO;
import com.concafras.gestao.form.UsuarioVO;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.security.LoginHistory;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.rest.model.DatatableResponse;
import com.concafras.gestao.rest.model.ResultAuthentication;
import com.concafras.gestao.security.rest.TokenTransfer;
import com.concafras.gestao.security.rest.TokenUtils;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.LoginHistoryService;
import com.concafras.gestao.service.UsuarioService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class UsuarioRestController {

  @Autowired
  UsuarioService usuarioService;
  
  @Autowired
  EntidadeService entidadeService;
  
  @Autowired
  LoginHistoryService loginHistoryService;

  @Autowired
  @Qualifier("customUserDetailsService")
  private UserDetailsService userDetaislService;

  @Autowired
  @Qualifier("authenticationManager")
  private AuthenticationManager authManager;

  /**
   * Authenticates a user and creates an authentication token.
   *
   * @param username
   *          The name of the user.
   * @param password
   *          The password of the user.
   * @return A transfer containing the authentication token.
   */
  @RequestMapping(value = "/api/v1/usuarios/authenticate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
  public ResponseEntity<?>  authenticate(@RequestParam("username") String username,
      @RequestParam("password") String password) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        username, password);
    
    try{
      Authentication authentication = authManager
          .authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
  
      /*
       * Reload user as password of authentication principal will be null
       * after authorization and password is needed for token generation
       */
      UserDetails userDetails = userDetaislService.loadUserByUsername(username);
      
      TokenTransfer token = new TokenTransfer(TokenUtils.createToken(userDetails));
      
      Usuario usuario = usuarioService.findByUsername(username);
      
      List<String> listaRoles = new ArrayList<String>();

      for (GrantedAuthority auth : userDetails.getAuthorities()) {
        listaRoles.add(auth.getAuthority());
      }
      
      List<EntidadeOptionForm> entidades = new ArrayList<EntidadeOptionForm>();
      if(listaRoles.contains("ROLE_METAS_PRESIDENTE")) {
          Pessoa pessoa = usuario.getPessoa();
          List<Entidade> entidadesLoaded = entidadeService.getEntidade(pessoa);
          for (Entidade entidade : entidadesLoaded) {
			entidades.add(new EntidadeOptionForm(entidade));
		}
      }
      
      ResultAuthentication result = new ResultAuthentication();
      result.setAuthorities(listaRoles);
      result.setEntidades(entidades);
      result.setSuccess(true);
      result.setUsuario(new UsuarioVO(usuario));
      result.getUsuario().setToken(token.getToken());
  
      return new ResponseEntity<ResultAuthentication>(result , HttpStatus.OK );
    }catch(BadCredentialsException ex){
      ResultAuthentication result = new ResultAuthentication();
      result.setSuccess(false);
      result.setError("E-mail/Senha invalídos");
  
      return new ResponseEntity<ResultAuthentication>(result , HttpStatus.OK );
    }
  }

  @RequestMapping(value = "/api/v1/usuarios/loginhistory", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaHistoricoLogin(
      @RequestParam(value = "iDisplayStart", required = false) int iDisplayStart, 
      @RequestParam(value = "sSearch", required = false) String searchParameter,
      @RequestParam(value = "iSortCol_0", required = false) String sortCol,
      @RequestParam(value = "sSortDir_0", required = false) String sortDir,
      @RequestParam(value = "iDisplayLength", required = false) int pageDisplayLength,
      HttpServletRequest request)
      throws IOException {
    
    int pageNumber = 1;
    if (iDisplayStart > 0 )
      pageNumber = (iDisplayStart / pageDisplayLength) + 1;


    List<LoginHistory> retorno = null;
    
    int startRange = 0 + (pageDisplayLength * (pageNumber - 1));
    
    if(sortCol.equals("0")){
      sortCol = "loggedIn";
    } else if(sortCol.equals("1")){
      sortCol = "nome";
    } else if(sortCol.equals("2")){
      sortCol = "username";
    } else {
      sortCol = null;
    }

    Long totalDisplayRecords = loginHistoryService.countListRangeLoginHistory(searchParameter);
    
    retorno = loginHistoryService.listRangeLoginHistory(searchParameter, sortCol, sortDir, startRange, pageDisplayLength);

    List<LoginHistoryVO> data = new ArrayList<LoginHistoryVO>();

    for (LoginHistory loginHistory : retorno) {
      LoginHistoryVO vo = new LoginHistoryVO();
      vo.setId(loginHistory.getId());
      vo.setLoggedIn(loginHistory.getLoggedIn());
      vo.setUserIp(loginHistory.getUserIp());
      vo.setStatus(loginHistory.getStatus());
      UsuarioVO usuario = new UsuarioVO(loginHistory.getUser());
      vo.setUser(usuario);
      data.add(vo);
    }

    DatatableResponse<LoginHistoryVO> result = new DatatableResponse<LoginHistoryVO>();
    result.setiTotalDisplayRecords(data.size());
    result.setiTotalRecords(totalDisplayRecords.intValue());
    result.setAaData(data);
    result.setSuccess(true);
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    String json2 = gson.toJson(result);
    
    return json2;
    
    
  }
}
