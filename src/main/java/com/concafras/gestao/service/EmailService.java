package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.ContatoInternet;

public interface EmailService {
	
    public void addEmail(ContatoInternet email);
    public void updateEmail(ContatoInternet email);
    public ContatoInternet getEmail(Integer id);
    public List<ContatoInternet> listEmail();
    public List<ContatoInternet> listEmail(String query);
    public void removeEmail(Integer id);
	
}
