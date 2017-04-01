package com.concafras.gestao.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.exception.JqueryBussinessException;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.FacilitadorService;
import com.concafras.gestao.service.PessoaService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/gestao/email")
public class EmailController {
  
  @Autowired
  private FacilitadorService facilitadorService;
  
  @Autowired
  private EntidadeService entidadeService;
  
  @Autowired
  private PessoaService pessoaService;
  
  @Autowired
  private JavaMailSender mailSender;
  
  @Autowired
  private VelocityEngine velocityEngine;

  private static String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
  private static String oauthClientId = "7956460239-ir1hiqggm2kric29mdferai6s6s4co29.apps.googleusercontent.com";
  private static String oauthSecret = "BPKfe09RPKqakLpw_qMOOe-k";
  private static String refreshToken = "1/ebcThq6IRgpncTNSjPm_-1t1uWqjJQbXJYh5myoK3a4";
  private static String accessToken = "ya29.GlsgBJToEx1fEBv-dOSaHCJf4AGq_-_dc4uwPkNdiKAJqwJpy5jgXwHkDGQOWCtJBuFvmnrJ-I8dRFGbdCb2ojRgfQf3PVW9J89Lmh2NEF8xaRmppmSkU6FWP04C";
  private static long tokenExpires = 1458168133864L;
  
  @RequestMapping("/entidade/envio")
  public String envioEmail(Map<String, Object> map) {
    map.put("basicos", true);
    return "entidade.enviar";
  }
  
  @RequestMapping("/entidade/enviar")
  public @ResponseBody
  boolean enviarConvite(@RequestParam("entidadeId") Integer entidadeId, @RequestParam("assunto") String assunto, @RequestParam("mensagem") String mensagem ) {

      if(entidadeId != null){
        Entidade entidade = entidadeService.findById(entidadeId);
        if(entidade.getPresidente() != null && entidade.getPresidente().getPessoa() != null){
          Integer pessoaId = entidade.getPresidente().getPessoa().getId();
          Pessoa pessoa = pessoaService.getPessoa(pessoaId);
          if(pessoa.getEmails() == null || pessoa.getEmails().size() == 0){
            throw new JqueryBussinessException("Pessoa não possui email cadastrado.");
          }
          sendEmail(pessoa, entidade, assunto, mensagem);
        }
      } else {
        List<Entidade> entidades = entidadeService.listEntidade();
        for (Entidade entidade : entidades) {
          if(entidade.getPresidente() != null && entidade.getPresidente().getPessoa() != null){
            Integer pessoaId = entidade.getPresidente().getPessoa().getId();
            Pessoa pessoa = pessoaService.getPessoa(pessoaId);
            if(pessoa.getEmails() == null || pessoa.getEmails().size() == 0){
              continue;
            }
            try{
              sendEmail(pessoa, entidade, assunto, mensagem);
            }catch(Exception e){
              e.printStackTrace();
            }
            try {
              Thread.sleep(100);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
      }

      return true;
  }
  
  @RequestMapping("/entidade/sendConvite/{pessoaId}/{entidadeId}")
  public @ResponseBody
  boolean enviarConvite(@PathVariable("pessoaId") Integer pessoaId, @PathVariable("entidadeId") Integer entidadeId) {

      Pessoa pessoa = pessoaService.getPessoa(pessoaId);
      Entidade entidade = entidadeService.findById(entidadeId);
      
      if(pessoa.getEmails() == null || pessoa.getEmails().size() == 0){
        throw new JqueryBussinessException("Pessoa não possui email cadastrado.");
      }
      
      sendInviteEmail(pessoa, entidade);

      return true;
  }
  
  
  private void sendInviteEmail(final Pessoa pessoa, final Entidade entidade) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
       public void prepare(MimeMessage mimeMessage) throws Exception {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
          message.setSubject(pessoa.getPrimeiroNome() + ", você está recebendo um convite muito especial.");
          message.setTo(  new InternetAddress( pessoa.getPrimeiroEmail() , pessoa.getNome() ));
          message.setBcc(  new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>"));
          message.setFrom( new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>") );
          
          //ClassPathResource file = new ClassPathResource("com/concafras/gestao/email/attachment/manual_do_facilitador.pdf");
          //message.addAttachment(file.getFilename(), file);
          
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("presidente" , pessoa );
          model.put("entidade"   , entidade );
          model.put("email"      , pessoa.getPrimeiroEmail());
          
          String text = VelocityEngineUtils.mergeTemplateIntoString(
             velocityEngine, "com/concafras/gestao/email/template/convite-presidente.vm", "UTF-8", model);
          message.setText(text, true);
       }
    };
    sendMessage(this.mailSender, preparator);
 }

  
  private void sendEmail(final Pessoa pessoa, final Entidade entidade, final String assunto, final String mensagem) {
    
    final Properties config = new Properties(); 
    
    config.put("resource.loader", "string"); 
    config.put("string.resource.loader.description", "Velocity StringResource loader"); 
    config.put("string.resource.loader.class", StringResourceLoader.class.getName()); 
    config.put("string.resource.loader.repository.class", StringResourceRepositoryImpl.class.getName()); 
    config.put("string.resource.loader.repository.name", EntidadeController.class.getName()); 

    MimeMessagePreparator preparator = new MimeMessagePreparator() {
       public void prepare(MimeMessage mimeMessage) throws Exception {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
          message.setSubject(assunto);
          message.setTo(  new InternetAddress( pessoa.getPrimeiroEmail() , pessoa.getNome() ));
          //message.setBcc(  new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>"));
          message.setFrom( new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>") );
          
          //ClassPathResource file = new ClassPathResource("com/concafras/gestao/email/attachment/manual_do_facilitador.pdf");
          //message.addAttachment(file.getFilename(), file);
          
          VelocityContext model = new VelocityContext();
          model.put("presidente" , pessoa );
          model.put("entidade"   , entidade );
          model.put("email"      , pessoa.getPrimeiroEmail());
          
          final VelocityEngine ve = new VelocityEngine();
          ve.init(config);
          final String templText = mensagem; 
          StringResourceLoader.getRepository(EntidadeController.class.getName()).putStringResource("my_template", templText, "UTF-8"); 
          final Template t = ve.getTemplate("my_template"); 
          final StringWriter writer = new StringWriter(); 
          t.merge(model, writer); 
          String text = writer.toString(); 
          message.setText(text, true);
       }
    };
    sendMessage(this.mailSender, preparator);
 }
  
  @RequestMapping("/facilitador/sendConvite/{pessoaId}")
  public @ResponseBody
  boolean enviarConvite(@PathVariable("pessoaId") Integer pessoaId) {

      Pessoa pessoa = pessoaService.getPessoa(pessoaId);
      
      List<Facilitador> facilitadores = facilitadorService.getFacilitador(pessoa);
      for (Facilitador facilitador : facilitadores) {
        sendInviteEmail(pessoa, facilitador.getInstituto().getDescricao());
      }

      return true;
  }
  
  
  private void sendInviteEmail(final Pessoa pessoa, final String instituto) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
       public void prepare(MimeMessage mimeMessage) throws Exception {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
          message.setSubject(pessoa.getPrimeiroNome() + ", você está recebendo um convite muito especial.");
          message.setTo(new InternetAddress( pessoa.getPrimeiroEmail() , pessoa.getNome() ));
          message.setBcc(new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>"));
          message.setFrom(new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>") );
          
          ClassPathResource file = new ClassPathResource("com/concafras/gestao/email/attachment/manual_do_facilitador.pdf");
          message.addAttachment(file.getFilename(), file);
          
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("nome", pessoa.getPrimeiroNome());
          model.put("instituto", instituto);
          model.put("email", pessoa.getPrimeiroEmail());
          
          String text = VelocityEngineUtils.mergeTemplateIntoString(
             velocityEngine, "com/concafras/gestao/email/template/convite-facilitador.vm", model);
          message.setText(text, true);
       }
    };
    sendMessage(this.mailSender, preparator);
 }
  
  private void sendRememberEmail(final Pessoa pessoa, final Entidade entidade, final List<ResumoMetaEntidade> metas) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
       public void prepare(MimeMessage mimeMessage) throws Exception {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
          message.setSubject(pessoa.getPrimeiroNome() + ", você está recebendo um lembrete.");
          message.setTo(  new InternetAddress( pessoa.getPrimeiroEmail() , pessoa.getNome() ));
          message.setBcc(  new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>"));
          message.setFrom( new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>") );
          
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("presidente" , pessoa );
          model.put("entidade"   , entidade );
          model.put("metas"      , metas );
          model.put("email"      , pessoa.getPrimeiroEmail());
          
          String text = VelocityEngineUtils.mergeTemplateIntoString(
             velocityEngine, "com/concafras/gestao/email/template/lembrete-presidente.vm", "UTF-8", model);
          message.setText(text, true);
       }
    };
    
 }

  
  
  public static void sendMessage(JavaMailSender mailSender, MimeMessagePreparator preparator) {
      if(System.currentTimeMillis() > tokenExpires) {
          try {
              String request = "client_id="+URLEncoder.encode(oauthClientId, "UTF-8")
                      +"&client_secret="+URLEncoder.encode(oauthSecret, "UTF-8")
                      +"&refresh_token="+URLEncoder.encode(refreshToken, "UTF-8")
                      +"&grant_type=refresh_token";
              HttpURLConnection conn = (HttpURLConnection) new URL(TOKEN_URL).openConnection();
              conn.setDoOutput(true);
              conn.setRequestMethod("POST");
              PrintWriter out = new PrintWriter(conn.getOutputStream());
              out.print(request); // note: println causes error
              out.flush();
              out.close();
              conn.connect();
              try {
                  HashMap<String,Object> result;
                  result = new ObjectMapper().readValue(conn.getInputStream(), new TypeReference<HashMap<String,Object>>() {});
                  accessToken = (String) result.get("access_token");
                  tokenExpires = System.currentTimeMillis()+(((Number)result.get("expires_in")).intValue()*1000);
              } catch (IOException e) {
                  String line;
                  BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                  while((line = in.readLine()) != null) {
                      System.out.println(line);
                  }
                  System.out.flush();
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      ((JavaMailSenderImpl)mailSender).setPassword(accessToken);
      // Now send mail like normal
      mailSender.send(preparator);
  }
  
  
}
