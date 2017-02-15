package com.concafras.gestao.service;


import java.util.List;

import com.concafras.gestao.model.Pessoa;

public interface PessoaService {
    public void addPessoa(Pessoa pessoa);
    public void updatePessoa(Pessoa pessoa);
    public Pessoa getPessoa(Integer id);
    public List<Pessoa> getPessoa(String cpfEmail);
    public List<Pessoa> listPessoa();
    public List<Pessoa> listPessoaResumo();
    public List<Pessoa> listPessoa(String name, int maxRows);
    public Long countListRangePessoa(String name);
    public List<Pessoa> listRangePessoa(String name, String sortCol, String sortDir, int startRange, int maxRows);
    public void removePessoa(Integer id);
}
