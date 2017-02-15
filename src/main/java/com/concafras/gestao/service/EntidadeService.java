package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;

public interface EntidadeService {
    
    public void save(Entidade entidade);
    public void update(Entidade entidade);
    public Entidade findById(Integer id);
    public Entidade findByIdOverview(Integer id);
    public List<Entidade> getEntidade(String cnpj);
    public boolean existeEntidadeCnpj(String cnpj);
    public List<Entidade> getEntidade(Pessoa pessoa);
    public List<Entidade> listEntidade();
    public List<Entidade> listEntidade(String query, Integer idCidade, int maxRows);
    public void removeEntidade(Integer id);
    public Long countListRangeEntidade(String name);
    public List<Entidade> listRangeEntidade(String name, String sortCol, String sortDir, int startRange, int maxRows);
    public List<Pessoa> listTrabalhadoresEntidade(Integer id);
}
