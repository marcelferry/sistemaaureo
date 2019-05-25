package com.concafras.gestao.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.security.Usuario;

public interface EmailService {

	public void addEmail(ContatoInternet email);

	public void updateEmail(ContatoInternet email);

	public ContatoInternet getEmail(Integer id);

	public List<ContatoInternet> listEmail();

	public List<ContatoInternet> listEmail(String query);

	public void removeEmail(Integer id);

	public void sendConfirmationEmail(Usuario usuario, String novaSenha);

	public void sendLembreteTodos(Rodizio ciclo);

	public void sendLembrete(Rodizio ciclo, Entidade entidade, Pessoa pessoa);

	public void sendConviteTodos();

	public void sendConvite(Pessoa pessoa, Entidade entidade);

	public void sendInviteEmail(Pessoa pessoa, String descricao);

	public void sendEmail(Pessoa pessoa, Entidade entidade, String assunto, String mensagem);

	public void sendEmail(HttpServletRequest request, Exception ex);

}
