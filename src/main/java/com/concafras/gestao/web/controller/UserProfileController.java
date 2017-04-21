package com.concafras.gestao.web.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.concafras.gestao.form.UsuarioVO;
import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.security.AlcadaUsuario;
import com.concafras.gestao.model.security.LoginHistory;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.rest.utils.RestUtils;
import com.concafras.gestao.service.AlcadaUsuarioService;
import com.concafras.gestao.service.EmailService;
import com.concafras.gestao.service.LoginHistoryService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.RodizioService;
import com.concafras.gestao.service.UsuarioService;
import com.concafras.gestao.util.CpfValidator;

@Controller
@RequestMapping("/gestao/userprofile")
public class UserProfileController {
  
  private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

  @Autowired
  private RodizioService rodizioService;
  
  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private AlcadaUsuarioService alcadaUsuarioService;
  
  @Autowired
  private LoginHistoryService loginHistoryService;

  @Autowired
  private PessoaService pessoaService;
  
  @Autowired
  private EmailService emailService;
  
  @RequestMapping("/")
  public String listUserProfile(Map<String, Object> map) {

    map.put("userprofileList", usuarioService.findAll());
    map.put("admin", true);
    return "userprofile.listar";
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String addUserProfile(Map<String, Object> map) {

    List<AlcadaUsuario> lista = alcadaUsuarioService.findAll();
    map.put("alcadas", lista);
    map.put("userprofile", new Usuario());
    map.put("admin", true);
    return "userprofile.novo";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addUserProfile(
      @ModelAttribute("userprofile") Usuario userprofile, BindingResult result) {

    usuarioService.save(userprofile);

    return "redirect:/gestao/userprofile/";
  }

  @RequestMapping(value = "/edit/{userprofileId}", method = RequestMethod.POST)
  public String editUserProfile(Map<String, Object> map,
      @PathVariable("userprofileId") Integer userprofileId,
      HttpServletRequest request) {
    
    Rodizio ciclo = (Rodizio)request.getSession().getAttribute("CICLO_CONTROLE");
    
    if(ciclo == null){
      ciclo =  rodizioService.findByAtivoTrue();
    }

    List<AlcadaUsuario> lista = alcadaUsuarioService.findAll();
    map.put("alcadas", lista);
    
    Usuario usuario = usuarioService.findById(userprofileId);
    
    if(usuario.getPessoa() != null){
      if (usuarioService.isPresidente(usuario.getPessoa())) {
        usuario.getRolesIds().add(7); // PRESIDENTE
      }
      
      if (usuarioService.isDirigente(usuario.getPessoa())) {
        usuario.getRolesIds().add(5); // DIRIGENTE
      }
      
      if (usuarioService.isFacilitador(usuario.getPessoa(), ciclo)) {
        usuario.getRolesIds().add(3); // FACILITADOR
      }
    }
    
    map.put("userprofile", usuario);
    map.put("admin", true);
    return "userprofile.editar";
  }

  @RequestMapping(value = "/edit/save/{userprofileId}", method = RequestMethod.POST)
  public String editUserProfile(
      @ModelAttribute("userprofile") Usuario userprofile,
      @PathVariable("userprofileId") Integer userprofileId) {

    usuarioService.update(userprofile);

    return "redirect:/gestao/userprofile/";
  }

  @RequestMapping("/delete/{userprofileId}")
  public String deleteUserProfile(
      @PathVariable("userprofileId") Integer userprofileId) {

    usuarioService.remove(userprofileId);

    return "redirect:/gestao/userprofile/";
  }

  @RequestMapping(value = "/password/change", method = RequestMethod.GET)
  public String loadUserProfilePasswordChange(Map<String, Object> map,
      Authentication authentication) {

    return "trocarsenha";
  }

  @RequestMapping(value = "/trocarsenha", method = RequestMethod.GET)
  public ModelAndView abretrocarsenha(Authentication authentication) {

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    UsuarioVO usuarioform = new UsuarioVO();

    usuarioform.setUsername(userDetails.getUsername());

    ModelAndView model = new ModelAndView("trocarsenha", "usuarioform", usuarioform);

    return model;
  }
  
  @RequestMapping(value = "/trocarsenha/{userprofileId}", method = RequestMethod.POST)
  public ModelAndView abretrocarsenha(
        @PathVariable("userprofileId") Integer userprofileId 
      ) {

    Usuario usuario = usuarioService.findById(userprofileId);

    UsuarioVO usuarioform = new UsuarioVO();

    usuarioform.setUsername(usuario.getUsername());

    ModelAndView model = new ModelAndView("trocarsenha", "usuarioform", usuarioform);

    return model;
  }

  @RequestMapping(value = "/trocarsenha", method = RequestMethod.POST)
  public ModelAndView trocarsenha(HttpServletRequest request,
      Authentication authentication,
      @ModelAttribute("usuarioform") @Validated UsuarioVO usuarioform,
      BindingResult result) {

    if (result.hasErrors()) {

      ModelAndView model = new ModelAndView("trocarsenha", "usuarioform",
          usuarioform);

      return model;
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    boolean userChange = false;
    
    if(usuarioform.getUsername().equals(userDetails.getUsername())){
      userChange = true;
    }
    
    System.out.println("User has authorities: " + userDetails.getAuthorities());

    Usuario usuario = usuarioService.findByUsername(usuarioform.getUsername());

    try {
      usuario.setPassword(criptografa(usuarioform.getPassword()));
      usuario.setConfirmPassword(criptografa(usuarioform.getConfirmPassword()));
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    usuario.setPasswordExpired(false);

    usuarioService.update(usuario);
    
    try{
      String ip = request.getHeader("X-FORWARDED-FOR");  
      if (ip == null) {  
        ip = request.getRemoteAddr();  
      }

      LoginHistory userLoginHistory = new LoginHistory();
      userLoginHistory.setUser(usuario);
      userLoginHistory.setLoggedIn(new Date());
      userLoginHistory.setUserIp(ip);
      if(userChange) {
        userLoginHistory.setStatus("CHANGEPASSWORD");
      } else {
        userLoginHistory.setStatus("CHNGPASSBYADMIN");
      }
      loginHistoryService.save(userLoginHistory);
    }catch(Exception ex){
      //
    }

    ModelAndView model = new ModelAndView();

    if(userChange){
      model.setViewName("redirect:/gestao/home/roles/true");
    } else {
      model.setViewName("redirect:/gestao/userprofile/");
    }

    return model;
  }

  @RequestMapping(value = "/recuperarsenha", method = RequestMethod.GET)
  public ModelAndView abrerecuperarsenha() {

    ModelAndView model = new ModelAndView();

    model.setViewName("recuperarsenha");

    return model;
  }

  @RequestMapping(value = "/recuperarsenha", method = RequestMethod.POST)
  public ModelAndView recuperarsenha(
      @RequestParam(value = "cpf", required = false) String cpf,
      HttpServletRequest request) {

    ModelAndView model = new ModelAndView();
    // Nova senha limpa sem cripgrafia
    String novaSenha = "123456";//gerarSenhaNumerica();
    String senha = "";
    boolean filtroCpf = false;

    model.setViewName("recuperarsenha");

    if (cpf == null) {
      model.addObject("error", "CPF/Email Inválido.");
      return model;
    }

    if (cpf.contains("@")) {
      // validar E-mail
    } else if (CpfValidator.validaCpf(cpf)) {
      cpf = CpfValidator.format(cpf);
      filtroCpf = true;
    }

    try {
      // Nova senha criptografada
      senha = criptografa(novaSenha);
    } catch (Exception e) {
      throw new RuntimeException("Não foi possivel criptografar a senha.");
    }

    List<Pessoa> retorno = pessoaService.getPessoa(cpf);

    if (retorno == null || retorno.size() == 0) {
      model.addObject("error", "Cadastro não localizado.");
      return model;
    } else if (retorno.size() > 1) {
      model
          .addObject(
              "error",
              "Há mais de um cadastro para o valor informado.<br>Entrar em contato pelo e-mail: <a href=\"mailto:sistemadegestaodemetas@gmail.com\">sistemadegestaodemetas@gmail.com</a>");
      return model;
    }

    Pessoa visitante = retorno.get(0);

    if (visitante != null) {

      // testar se já tem usuario
      // apenas geramos uma nova senha

      Usuario usuario = usuarioService.findByPessoa(visitante);

      if (usuario != null) {
        // gera nova senha
        usuario.setPassword(senha);
        usuario.setPasswordExpired(true);
        usuarioService.update(usuario);
        
        try{
          String ip = request.getHeader("X-FORWARDED-FOR");  
          if (ip == null) {  
            ip = request.getRemoteAddr();  
          }

          LoginHistory userLoginHistory = new LoginHistory();
          userLoginHistory.setUser(usuario);
          userLoginHistory.setLoggedIn(new Date());
          userLoginHistory.setUserIp(ip);
          userLoginHistory.setStatus("RESETPASSWORD");
          loginHistoryService.save(userLoginHistory);
        }catch(Exception ex){
          //
        }
        
        emailService.sendConfirmationEmail(usuario, novaSenha);

        model.addObject("msg", "Sua nova senha será enviada por email.");
      } else {
        // Testar niveis

        List<String> alcadas = new ArrayList<String>();

        Rodizio ciclo =  rodizioService.findByAtivoTrue();
        
        if (usuarioService.isPresidente(visitante)) {
          alcadas.add("METAS_PRESIDENTE");
        }
        
        if (usuarioService.isDirigente(visitante)) {
          alcadas.add("METAS_DIRIGENTE");
        }
        
        if (usuarioService.isFacilitador(visitante, ciclo)) {
          alcadas.add("METAS_FACILITADOR");
        }

        if (alcadas.size() > 0) {

          //if (alcadas.size() == 1 && alcadas.contains("METAS_FACILITADOR")) {
          //  model
          //      .addObject(
          //          "error",
          //          "O acesso aos novos facilitadores foi bloqueado.<br>Contate-nos pelo e-mail: <a href=\"mailto:sistemadegestaodemetas@gmail.com\">sistemadegestaodemetas@gmail.com</a>");
          //  return model;
          //}

          List<ContatoInternet> emails = visitante.getEmails();

          boolean possuiEmail = false;
          String emailPessoal = null;

          for (ContatoInternet contato : emails) {
            if (contato.getContato() != null
                && !(contato.getContato().trim().length() == 0)) {

              if (filtroCpf && !contato.getContato().equals(cpf)) {
                continue;
              }

              possuiEmail = true;
              emailPessoal = contato.getContato();
              break;
            }
          }

          if (!possuiEmail) {
            model
                .addObject(
                    "error",
                    "Seu email não está cadastrado.<br>Contate-nos pelo e-mail: <a href=\"mailto:sistemadegestaodemetas@gmail.com\">sistemadegestaodemetas@gmail.com</a>");
            return model;
          }

          usuario = new Usuario();
          usuario.setPessoa(visitante);
          usuario.setUsername(emailPessoal);
          usuario.setPassword(senha);
          usuario.setPasswordExpired(true);
          usuarioService.save(usuario);
         
          try{
            String ip = request.getHeader("X-FORWARDED-FOR");  
            if (ip == null) {  
              ip = request.getRemoteAddr();  
            }

            LoginHistory userLoginHistory = new LoginHistory();
            userLoginHistory.setUser(usuario);
            userLoginHistory.setLoggedIn(new Date());
            userLoginHistory.setUserIp(ip);
            userLoginHistory.setStatus("REQUESTPASSWORD");
            loginHistoryService.save(userLoginHistory);
          }catch(Exception ex){
            //
          }
          
          // enviar email pessoa
          emailService.sendConfirmationEmail(usuario, novaSenha);

          
          model.addObject("msg", "Sua senha será enviada por email.");

        } else {
          model
              .addObject(
                  "error",
                  "No momento não há nenhum tipo de acesso cadastrado pra você. <br>Dúvidas pelo e-mail: <a href=\"mailto:sistemadegestaodemetas@gmail.com\">sistemadegestaodemetas@gmail.com</a>");
        }
      }

    }

    return model;
  }

  public String criptografa(String original) throws NoSuchAlgorithmException,
      UnsupportedEncodingException {
    MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
    byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

    StringBuilder hexString = new StringBuilder();
    for (byte b : messageDigest) {
      hexString.append(String.format("%02X", 0xFF & b));
    }
    String senha = hexString.toString();
    return senha.toLowerCase();
  }

  public String gerarSenha() {

    String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
        "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
        "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C",
        "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
        "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    String senha = "";

    for (int x = 0; x < 10; x++) {
      int j = (int) (Math.random() * carct.length);
      senha += carct[j];

    }

    return senha;
  }
  
  public String gerarSenhaNumerica() {
    
    String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    
    String senha = "";
    
    for (int x = 0; x < 4; x++) {
      int j = (int) (Math.random() * carct.length);
      senha += carct[j];
      
    }
    
    return senha;
  }

  /**
  public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
      if (emailExist(accountDto.getEmail())) {  
          throw new EmailExistsException("There is an account with that email adress: " + 
            accountDto.getEmail());
      }
      
      // the rest of the registration operation
  }
  
  private boolean emailExist(String email) {
      User user = repository.findByEmail(email);
      if (user != null) {
          return true;
      }
      return false;
  }*/

  @RequestMapping("/redefinirSenha/{pessoaId}/{notificar}")
  public @ResponseBody boolean redefinirSenha(
      @PathVariable("pessoaId") Integer pessoaId,
      @PathVariable("notificar") boolean notificar,
      HttpServletRequest request) {

    String novaSenha = "123456";
    String senha = "";

    try {
      // Nova senha criptografada
      senha = criptografa(novaSenha);
    } catch (Exception e) {
      throw new RuntimeException("Não foi possivel criptografar a senha.");
    }

    Pessoa visitante = pessoaService.getPessoa(pessoaId);

    if (visitante != null) {

      // testar se já tem usuario
      // apenas geramos uma nova senha

      Usuario usuario = usuarioService.findByPessoa(visitante);

      if (usuario != null) {
        // gera nova senha
        usuario.setPassword(senha);
        usuario.setPasswordExpired(false);
        usuarioService.update(usuario);
        
        try{
          String ip = request.getHeader("X-FORWARDED-FOR");  
          if (ip == null) {  
            ip = request.getRemoteAddr();  
          }

          LoginHistory userLoginHistory = new LoginHistory();
          userLoginHistory.setUser(usuario);
          userLoginHistory.setLoggedIn(new Date());
          userLoginHistory.setUserIp(ip);
          userLoginHistory.setStatus("RESETPASSBYADMIN");
          loginHistoryService.save(userLoginHistory);
        }catch(Exception ex){
          //
        }
        
        if (notificar)
          emailService.sendConfirmationEmail(usuario, novaSenha);

      } else {
        // Testar niveis

        List<String> alcadas = new ArrayList<String>();

        Rodizio ciclo =  rodizioService.findByAtivoTrue();
        
        if (usuarioService.isPresidente(visitante)) {
          alcadas.add("METAS_PRESIDENTE");
        }
        
        if (usuarioService.isDirigente(visitante)) {
          alcadas.add("METAS_DIRIGENTE");
        }
        
        if (usuarioService.isFacilitador(visitante, ciclo)) {
          alcadas.add("METAS_FACILITADOR");
        }

        if (alcadas.size() > 0) {

          String emailPessoal = visitante.getPrimeiroEmail();

          usuario = new Usuario();
          usuario.setPessoa(visitante);
          usuario.setUsername(emailPessoal);
          usuario.setPassword(senha);
          usuario.setPasswordExpired(true);
          usuarioService.save(usuario);
          
          try{
            String ip = request.getHeader("X-FORWARDED-FOR");  
            if (ip == null) {  
              ip = request.getRemoteAddr();  
            }

            LoginHistory userLoginHistory = new LoginHistory();
            userLoginHistory.setUser(usuario);
            userLoginHistory.setLoggedIn(new Date());
            userLoginHistory.setUserIp(ip);
            userLoginHistory.setStatus("REQUESTPASSBYADMIN");
            loginHistoryService.save(userLoginHistory);
          }catch(Exception ex){
            //
          }
          // enviar email pessoa
          if (notificar)
            emailService.sendConfirmationEmail(usuario, novaSenha);

        } else {
          return false;
        }
      }

    }
    return true;
  }
  
  @RequestMapping("/listaHistorico")
  public String listHistorico(Map<String, Object> map) {
    map.put("admin", true);
    return "userprofile.historico";
  }

  @RequestMapping(value = "/listaHistoricoLogin", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody
  String listaHistoricoLogin(HttpServletRequest request)
      throws IOException {
    
    List<LoginHistory> retorno = null;
    
    retorno = loginHistoryService.findByUsuario(null);
    
    List<LoginHistory> data = new ArrayList<LoginHistory>();
    
    for (LoginHistory loginHistory : retorno) {
      LoginHistory vo = new LoginHistory();
      vo.setId(loginHistory.getId());
      vo.setLoggedIn(loginHistory.getLoggedIn());
      vo.setUserIp(loginHistory.getUserIp());
      vo.setStatus(loginHistory.getStatus());
      Usuario usuario = new Usuario();
      usuario.setId(loginHistory.getUser().getId());
      usuario.setUsername(loginHistory.getUser().getUsername());
      usuario.setPessoa(new Pessoa());
      usuario.getPessoa().setNome(loginHistory.getUser().getPessoa().getNome());
      vo.setUser(usuario);
      data.add(vo);
    }
    
    return new RestUtils<LoginHistory>().createDatatableResponse(data);
  }
  
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String value) {
        try {
          Date parsedDate = dateFormat.parse(value);
          setValue(parsedDate);
        } catch (ParseException e) {
          setValue(null);
        }
      }

      @Override
      public String getAsText() {
        try {
          String parsedDate = dateFormat.format((Date) getValue());
          return parsedDate;
        } catch (Exception e) {
          return "";
        }
      }
    });
  }

}
