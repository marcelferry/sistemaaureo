package com.concafras.gestao.web.controller;

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
import com.concafras.gestao.model.exception.JqueryBussinessException;
import com.concafras.gestao.service.EmailService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.FacilitadorService;
import com.concafras.gestao.service.PessoaService;

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
