package com.concafras.gestao.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.exception.JqueryBussinessException;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.service.EmailService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.FacilitadorService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.PlanoMetasService;

@Controller
@RequestMapping("/gestao/email")
public class EmailController {
  
  private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
  
  @Autowired
  private FacilitadorService facilitadorService;
  
  @Autowired
  private EntidadeService entidadeService;
  
  @Autowired
  private PessoaService pessoaService;
  
  @Autowired
  private EmailService emailService;
  
  @Autowired
  private PlanoMetasService planoMetasService;


  
  @RequestMapping("/entidade/envio")
  public String envioEmail(Map<String, Object> map) {
    map.put("utilidades", true);
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
          emailService.sendEmail(pessoa, entidade, assunto, mensagem);
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
              emailService.sendEmail(pessoa, entidade, assunto, mensagem);
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
      
      emailService.sendInviteEmail(pessoa, entidade);

      return true;
  }
  
  @RequestMapping("/entidade/sendConviteTodos")
  public @ResponseBody
  boolean enviarConviteTodos() {
    logger.debug("Action 'enviarConviteTodos'");
    
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
          enviarConvite(pessoa.getId(), entidade.getId());
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
    
    return true;
  }
  
  @RequestMapping("/entidade/sendLembreteTodos/{ciclo}")
  public @ResponseBody
  boolean enviarLembreteTodos( @PathVariable("ciclo") Integer cicloId ) {
	Rodizio ciclo = new Rodizio();
	ciclo.setId(cicloId);
	//TODO: change to rodizio object from session
    emailService.sendLembreteTodos(ciclo);
    return true;
  }
  
  
  @RequestMapping("/entidade/sendLembrete/{ciclo}/{entidade}/{pessoa}")
  public @ResponseBody
  boolean enviarLembrete(@PathVariable("ciclo") Integer cicloId,  @PathVariable("entidade") Integer entidadeId, @PathVariable("pessoa") Integer pessoaId) {
	//TODO: change to rodizio object from session
	Rodizio ciclo = new Rodizio();
	ciclo.setId(cicloId);
	//TODO: Validade if exist entidade
	Entidade entidade = new Entidade();
	entidade.setId(entidadeId);
	//TODO: Validade if exist pessoa
	Pessoa pessoa = new Pessoa();
	pessoa.setId(pessoaId);
    emailService.sendLembrete(ciclo, entidade, pessoa);
    return true;
  }
  
  
  @RequestMapping("/facilitador/sendConvite/{pessoaId}")
  public @ResponseBody
  boolean enviarConvite(@PathVariable("pessoaId") Integer pessoaId) {

      Pessoa pessoa = pessoaService.getPessoa(pessoaId);
      
      List<Facilitador> facilitadores = facilitadorService.getFacilitador(pessoa);
      for (Facilitador facilitador : facilitadores) {
        emailService.sendInviteEmail(pessoa, facilitador.getInstituto().getDescricao());
      }

      return true;
  }

}
