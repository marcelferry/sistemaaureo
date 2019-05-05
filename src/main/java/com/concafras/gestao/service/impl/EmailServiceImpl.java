package com.concafras.gestao.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl;
import org.apache.velocity.tools.generic.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.exception.JqueryBussinessException;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.service.EmailService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.EstadoService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.PlanoMetasService;
import com.concafras.gestao.web.controller.EmailController;
import com.concafras.gestao.web.controller.EntidadeController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @PersistenceContext
    EntityManager em;
    
    @Autowired
    EstadoService estadoService;
    
    @Autowired
    EntidadeService entidadeService;
    
    @Autowired
    PessoaService pessoaService;
    
    @Autowired
    PlanoMetasService planoMetasService;
    
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
        
    @Transactional
    public void addEmail(ContatoInternet email) {
        em.persist(email);
    }
    
    @Transactional
    public void updateEmail(ContatoInternet email) {
    	em.merge(email);
    }

    @Transactional
    public List<ContatoInternet> listEmail() {
        CriteriaQuery<ContatoInternet> c = em.getCriteriaBuilder().createQuery(ContatoInternet.class);
        c.from(ContatoInternet.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<ContatoInternet> listEmail(String name) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<ContatoInternet> c = cb.createQuery(ContatoInternet.class);
    	Root<ContatoInternet> emp = c.from(ContatoInternet.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeEmail(Integer id) {
    	ContatoInternet email = em.find(ContatoInternet.class, id);
        if (null != email) {
            em.remove(email);
        }
    }

    @Transactional
	public ContatoInternet getEmail(Integer id) {
    	ContatoInternet email = em.find(ContatoInternet.class, id);
		return email;
	}
    
    
    public void sendInviteEmail(final Pessoa pessoa, final Entidade entidade) {
      MimeMessagePreparator preparator = new MimeMessagePreparator() {
         public void prepare(MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setSubject(pessoa.getPrimeiroNome() + ", você está recebendo um convite muito especial.");
            message.setTo(  new InternetAddress( pessoa.getPrimeiroEmail() , pessoa.getNome() ));
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
    
    public void sendLembreteTodos(Rodizio ciclo) {
    	logger.debug("Action 'enviarLembrete'");
        
        List<Entidade> entidades = entidadeService.listEntidade();
        for (Entidade entidade : entidades) {
          if(entidade.getPresidente() != null && entidade.getPresidente().getPessoa() != null){
            Integer pessoaId = entidade.getPresidente().getPessoa().getId();
            Pessoa pessoa = pessoaService.getPessoa(pessoaId);
            if(pessoa.getEmails() == null || pessoa.getEmails().size() == 0){
              continue;
            }
            try{
              logger.debug("============================================");
              logger.debug("E:" + entidade.getRazaoSocial());
              logger.debug("P:" + pessoa.getNome());
              sendLembrete(ciclo, entidade, pessoa);
              logger.debug("OK:");
            }catch(Exception e){
              e.printStackTrace();
              logger.debug("Erro:" + e.getMessage());
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
    
    public void sendLembrete(Rodizio ciclo, Entidade entidade, Pessoa pessoa) {
    	
    	logger.debug("Action 'enviarLembrete'");
        
        Calendar calendar = Calendar.getInstance();
        Date mesAtual = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date mesAnterior = calendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String mesAtualtexto = sdf.format(mesAtual);
        String mesAnteriortexto = sdf.format(mesAnterior);
        
        
        Pessoa pessoaLoaded = pessoaService.getPessoa(pessoa.getId());
        Entidade entidadeLoaded = entidadeService.findById(entidade.getId());
        
        List<ResumoMetaEntidade> vencidas = null;
        vencidas = planoMetasService.getListaContratadoGeralData(ciclo.getId(), null,
            entidade.getId(), null, "ATRASADO");
        
        List<ResumoMetaEntidade> vencer = null;
        vencer = planoMetasService.getListaContratadoGeralData(ciclo.getId(), null,
            entidade.getId(), null, "NO PRAZO");
        
        if(pessoaLoaded.getEmails() == null || pessoaLoaded.getEmails().size() == 0){
          throw new JqueryBussinessException("Pessoa não possui email cadastrado.");
        }
        
        if( 
            (vencidas != null && !vencidas.isEmpty()) || 
            (vencer != null && !vencer.isEmpty()) ) {
          sendLembreteEmail(pessoaLoaded, entidadeLoaded, vencidas, vencer, mesAtualtexto, mesAnteriortexto);
        }
        
    }
    
    private void sendLembreteEmail(final Pessoa pessoa, final Entidade entidade, final List vencidas, final List avencer, final String mesAtual, final String mesAnterior) {
      MimeMessagePreparator preparator = new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws Exception {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
          message.setSubject(pessoa.getPrimeiroNome() + ", vamos manter nossas metas atualizadas?.");
          message.setTo(  new InternetAddress( pessoa.getPrimeiroEmail() , pessoa.getNome() ));
          message.setFrom( new InternetAddress("Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>") );
          
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("presidente" , pessoa );
          model.put("entidade"   , entidade );
          model.put("email"      , pessoa.getPrimeiroEmail());
          model.put("listaVencidas", vencidas);
          model.put("listaVencer"  , avencer);
          model.put("mesAtual"  , mesAtual);
          model.put("mesAnterior"  , mesAnterior);
          model.put("date", new DateTool());
          
          String text = VelocityEngineUtils.mergeTemplateIntoString(
              velocityEngine, "com/concafras/gestao/email/template/lembrete-presidente.vm", "UTF-8", model);
          message.setText(text, true);
        }
      };
      sendMessage(this.mailSender, preparator);
    }

    
    public void sendEmail(final Pessoa pessoa, final Entidade entidade, final String assunto, final String mensagem) {
      
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
    
    
    private void sendRememberEmail(final Pessoa pessoa, final Entidade entidade, final List<ResumoMetaEntidade> metas) {
      MimeMessagePreparator preparator = new MimeMessagePreparator() {
         public void prepare(MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setSubject(pessoa.getPrimeiroNome() + ", você está recebendo um lembrete.");
            message.setTo(  new InternetAddress( pessoa.getPrimeiroEmail() , pessoa.getNome() ));
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
    
    public void sendConfirmationEmail(final Usuario user, final String senha) {
      MimeMessagePreparator preparator = new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws Exception {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
          message
              .setSubject(user.getPessoa().getPrimeiroNome()
                  + ", sua nova senha de acesso ao sistema de gestão de metas chegou.");
          message.setTo(new InternetAddress(user.getUsername(), user.getPessoa()
              .getNome()));
          message
              .setFrom(new InternetAddress(
                  "Gestão de Metas - Concafras-PSE <sistemadegestaodemetas@gmail.com>"));
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("usuario", user);
          model.put("senha", senha);
          String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/concafras/gestao/email/template/confirmacao-registro.vm", model);
          message.setText(text, true);
        }
      };
      sendMessage(this.mailSender, preparator);
    }

    
    
    //public void sendMessage(JavaMailSender mailSender, MimeMessagePreparator preparator) {
    //		mailSender.send(preparator);
    //}
    
    public void sendMessage(JavaMailSender mailSender, MimeMessagePreparator preparator) {
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
    
    
    public void sendInviteEmail(final Pessoa pessoa, final String instituto) {
      MimeMessagePreparator preparator = new MimeMessagePreparator() {
         public void prepare(MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setSubject(pessoa.getPrimeiroNome() + ", você está recebendo um convite muito especial.");
            message.setTo(new InternetAddress( pessoa.getPrimeiroEmail() , pessoa.getNome() ));
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
    
    public void sendEmail(HttpServletRequest request, Exception exception) {
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
    
        sb.append("<b>Parametros de Requisição</b><br/>");
    
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
        
        sb.append("<b>Variaveis de Requisição</b><br/>");
        
        Enumeration<String> rParameters = request.getAttributeNames();
    
        while (rParameters.hasMoreElements()) {
          String key = rParameters.nextElement();
          sb.append(key);
          sb.append("=");
          sb.append(request.getAttribute(key));
          sb.append("<br/>");
        }
        
        sb.append("<br/>");
        
        sb.append("<b>Exceção</b><br/>");
    
        sb.append(exception.getMessage());
        
        sb.append("<br/>");
        
        sb.append(exception.getCause());
        
        sb.append("<br/>");
        
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
        
        sendMessage(this.mailSender, preparator);
        
      } catch (Exception e) {
        // TODO: handle exception
      }
      

    }

        
}
