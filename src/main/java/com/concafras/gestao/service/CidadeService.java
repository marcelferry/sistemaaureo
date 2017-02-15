package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Cidade;

public interface CidadeService {
	
    public void addCidade(Cidade cidade);
    public void updateCidade(Cidade cidade);
    public Cidade getCidade(Integer id);
    public Cidade getCidade(Integer id, String cidade, String uf);
    public List<Cidade> listCidade();
    public List<Cidade> listCidade(String query, int maxRows);
    public List<Cidade> listCidadeResumo(String query, int maxRows, boolean rodizio);
    public void removeCidade(Integer id);
    public List<Cidade> listRangeCidade(String name, String sortCol, String sortDir, int startRange, int maxRows);
    public Long countListRangeCidade(String name);
	
}
