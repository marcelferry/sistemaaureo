package com.concafras.gestao.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.security.Usuario;

public interface EmailService {
	
    public void addEmail(ContatoInternet email);
    public void updateEmail(ContatoInternet email);
    public ContatoInternet getEmail(Integer id);
    public List<ContatoInternet> listEmail();
    public List<ContatoInternet> listEmail(String query);
    public void removeEmail(Integer id);
    public void sendConfirmationEmail(Usuario usuario, String novaSenha);
    public void sendInviteEmail(Pessoa pessoa, Entidade entidade);
    public void sendLembreteEmail(Pessoa pessoa, Entidade entidade, List vencidas, List avencer, String mesAtual, String mesAnterior);
    public void sendInviteEmail(Pessoa pessoa, String descricao);
    public void sendEmail(Pessoa pessoa, Entidade entidade, String assunto, String mensagem);
    public void sendEmail(HttpServletRequest request, Exception ex);
	
}
