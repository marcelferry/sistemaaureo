package com.concafras.gestao.service;

import java.util.List;

import com.concafras.gestao.model.security.LoginHistory;
import com.concafras.gestao.model.security.Usuario;

public interface LoginHistoryService {
  
  public void save(LoginHistory loginHistory);

  public List<LoginHistory> findAll();
  
  public List<LoginHistory> findByUsuario(Usuario usuario);
  
  public List<LoginHistory> listRangeLoginHistory(String name, String sortCol, String sortDir, int startRange, int maxRows);
  
  public Long countListRangeLoginHistory(String name);
  
}
