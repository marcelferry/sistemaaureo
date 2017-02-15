package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.Cidade;
import com.concafras.gestao.model.Dirigente;
import com.concafras.gestao.model.Endereco;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Presidente;
import com.concafras.gestao.service.CidadeService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.InstitutoService;
import com.concafras.gestao.service.PessoaService;

@Service
public class EntidadeServiceImpl implements EntidadeService {

  @PersistenceContext
  EntityManager em;

  @Autowired
  PessoaService pessoaService;

  @Autowired
  CidadeService cidadeService;

  @Autowired
  InstitutoService institutoService;

  @Transactional
  public void save(Entidade entidade) {
    if (entidade.getPresidentes() != null)
      for (Presidente presidente : entidade.getPresidentes()) {
        Pessoa p = pessoaService.getPessoa(presidente.getPessoa().getId());
        presidente.setPessoa(p);
      }
    if (entidade.getEndereco() != null
        && entidade.getEndereco().getCidade() != null
        && entidade.getEndereco().getCidade().getEstado() != null) {
      Integer id = entidade.getEndereco().getCidade().getId();
      Cidade c = cidadeService.getCidade(id);
      entidade.getEndereco().setCidade(c);
    }
    em.persist(entidade);
  }

  @Transactional
  public void update(Entidade entidade) {
    if (entidade.getPresidentes() != null)
      for (Presidente presidente : entidade.getPresidentes()) {
        Pessoa p = pessoaService.getPessoa(presidente.getPessoa().getId());
        presidente.setPessoa(p);
      }
    if (entidade.getEndereco() != null
        && entidade.getEndereco().getCidade() != null
        && entidade.getEndereco().getCidade().getEstado() != null) {
      Integer id = entidade.getEndereco().getCidade().getId();
      Cidade c = cidadeService.getCidade(id);
      entidade.getEndereco().setCidade(c);
    }
    em.merge(entidade);
  }

  @Transactional
  public List<Entidade> listEntidade() {
    CriteriaQuery<Entidade> c = em.getCriteriaBuilder().createQuery(
        Entidade.class);
    c.from(Entidade.class);
    return em.createQuery(c).getResultList();
  }

  @Transactional
  public List<Entidade> listEntidade(String name, Integer cidade, int maxRows) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Entidade> c = cb.createQuery(Entidade.class);
    Root<Entidade> emp = c.from(Entidade.class);
    Join<Entidade, Endereco> joinEndereco = emp.join("endereco");
    Join<Endereco, Cidade> joinCidade = joinEndereco.join("cidade");

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.like(
        cb.function("UNACCENT", String.class,
            cb.lower(emp.<String> get("razaoSocial"))), "%"
            + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    if (cidade != null) {
      criteria.add(cb.equal(joinCidade.<Integer> get("id"), cidade));
    }

    c.where(criteria.toArray(new Predicate[] {}));

    c.orderBy(cb.asc(emp.<String> get("razaoSocial")));

    List<Entidade> retorno = em.createQuery(c).setMaxResults(maxRows)
        .getResultList();

    for (Entidade entidade : retorno) {
      loadListas(entidade);
    }

    return retorno;
  }

  @Transactional
  public List<Entidade> listRangeEntidade(String name, String sortCol,
      String sortDir, int startRange, int maxRows) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Entidade> c = cb.createQuery(Entidade.class);
    Root<Entidade> root = c.from(Entidade.class);
    Join endereco = root.join("endereco", JoinType.LEFT);
    Join cidade = endereco.join("cidade", JoinType.LEFT);
    Join estado = cidade.join("estado", JoinType.LEFT);
    Join presidente = root.join("presidente", JoinType.LEFT);
    Join pessoa = presidente.join("pessoa", JoinType.LEFT);

    if (name != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(cb.or(
          cb.like(
              cb.function("UNACCENT", String.class,
                  cb.lower(root.<String> get("razaoSocial"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%"),
          cb.like(
              cb.function("UNACCENT", String.class,
                  cb.lower(pessoa.get("nomeCompleto"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%")));

      c.where(criteria.toArray(new Predicate[] {}));
    }

    c.multiselect(root.get("id"), root.get("razaoSocial"), root.get("cnpj"),
        endereco.get("logradouro"), endereco.get("numero"), cidade.get("nome"),
        estado.get("sigla"), pessoa.get("id"), pessoa.get("nomeCompleto"));

    Expression exp = null;

    if (sortCol == null) {
      exp = root.<String> get("razaoSocial");
    } else if (sortCol.equals("id")) {
      exp = root.<String> get("id");
    } else if (sortCol.equals("razaoSocial")) {
      exp = root.<String> get("razaoSocial");
    } else if (sortCol.equals("cidade")) {
      exp = cidade.get("nome");
    } else if (sortCol.equals("presidente")) {
      exp = pessoa.get("nomeCompleto");
    }

    if (sortDir.equals("desc")) {
      c.orderBy(cb.desc(exp));
    } else {
      c.orderBy(cb.asc(exp));
    }

    Query q = em.createQuery(c);
    q.setFirstResult(startRange);
    q.setMaxResults(maxRows);

    List<Entidade> retorno = q.getResultList();

    return retorno;
  }

  @Transactional
  public Long countListRangeEntidade(String name) {

    CriteriaBuilder qb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    Root<Entidade> root = cq.from(Entidade.class);
    Join presidente = root.join("presidente", JoinType.LEFT);
    Join pessoa = presidente.join("pessoa", JoinType.LEFT);
    cq.select(qb.count(root));

    if (name != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(qb.or(
          qb.like(
              qb.function("UNACCENT", String.class,
                  qb.lower(root.<String> get("razaoSocial"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%"),
          qb.like(
              qb.function("UNACCENT", String.class,
                  qb.lower(pessoa.get("nomeCompleto"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%")));

      cq.where(criteria.toArray(new Predicate[] {}));
    }

    return em.createQuery(cq).getSingleResult();

  }

  @Transactional
  public Entidade findById(Integer id) {
    Entidade entidade = em.find(Entidade.class, id);
    if (entidade != null) {
      Hibernate.initialize(entidade.getTrabalhadores());
      Hibernate.initialize(entidade.getTelefones());
      Hibernate.initialize(entidade.getEmails());
      Hibernate.initialize(entidade.getAnotacoes());
      Hibernate.initialize(entidade.getListaInstitutos());
      Hibernate.initialize(entidade.getDirigentes());
      Hibernate.initialize(entidade.getPresidentes());
    }
    return entidade;
  }

  @Transactional
  public Entidade findByIdOverview(Integer id) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Entidade> c = cb.createQuery(Entidade.class);
    Root<Entidade> root = c.from(Entidade.class);
    Join endereco = root.join("endereco", JoinType.LEFT);
    Join cidade = endereco.join("cidade", JoinType.LEFT);
    Join estado = cidade.join("estado", JoinType.LEFT);
    Join presidente = root.join("presidente", JoinType.LEFT);
    Join pessoa = presidente.join("pessoa", JoinType.LEFT);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.equal(root.<Integer> get("id"), id));

    c.where(criteria.toArray(new Predicate[] {}));

    c.multiselect(root.get("id"), root.get("razaoSocial"), root.get("cnpj"),
        endereco.get("logradouro"), endereco.get("numero"), cidade.get("nome"),
        estado.get("sigla"), pessoa.get("id"), pessoa.get("nomeCompleto"));

    Query q = em.createQuery(c);

    List<Entidade> retorno = q.getResultList();

    if (retorno != null && retorno.size() == 1) {
      List<Dirigente> historicoDirigentes = getDirigentes(retorno.get(0));
      retorno.get(0).setDirigentes(historicoDirigentes);
      return retorno.get(0);
    }

    return null;
  }

  @Transactional
  public List<Pessoa> listTrabalhadoresEntidade(Integer id) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pessoa> c = cb.createQuery(Pessoa.class);
    Root<Entidade> root = c.from(Entidade.class);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.equal(root.<Integer> get("id"), id));

    c.where(criteria.toArray(new Predicate[] {}));

    Join<Entidade, Pessoa> answers = root
        .<Entidade, Pessoa> join("trabalhadores");

    CriteriaQuery<Pessoa> cq = c.select(answers);
    TypedQuery<Pessoa> query = em.createQuery(cq);

    List<Pessoa> retorno = query.getResultList();

    for (Pessoa pessoa : retorno) {
      Hibernate.initialize(pessoa.getEmails());
      Hibernate.initialize(pessoa.getTelefones());
    }

    return retorno;
  }

  @Transactional
  public List<Entidade> getEntidade(String cnpj) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Entidade> c = cb.createQuery(Entidade.class);
    Root<Entidade> emp = c.from(Entidade.class);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.equal(cb.lower(emp.<String> get("cnpj")), cnpj.trim()));

    c.where(criteria.toArray(new Predicate[] {}));

    List<Entidade> retorno = em.createQuery(c).getResultList();

    for (Entidade entidade : retorno) {
      loadListas(entidade);
    }

    return retorno;
  }

  @Transactional
  public boolean existeEntidadeCnpj(String cnpj) {
    CriteriaBuilder qb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    Root<Entidade> emp = cq.from(Entidade.class);
    cq.select(qb.count(emp));

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(qb.equal(qb.lower(emp.<String> get("cnpj")), cnpj.trim()));

    cq.where(criteria.toArray(new Predicate[] {}));

    return em.createQuery(cq).getSingleResult() > 0;
  }

  @Transactional
  public List<Entidade> getEntidade(Pessoa pessoa) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Entidade> c = cb.createQuery(Entidade.class);
    Root<Entidade> emp = c.from(Entidade.class);
    Join<Entidade, Presidente> joinPresidente = emp.join("presidente");

    if (pessoa != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(cb.equal(joinPresidente.get("pessoa"), pessoa));
      c.where(criteria.toArray(new Predicate[] {}));
    }

    List<Entidade> retorno = em.createQuery(c).getResultList();

    for (Entidade entidade : retorno) {
      loadListas(entidade);
    }

    return retorno;
  }

  private void loadListas(Entidade entidade) {
    loadListas(entidade, true);
  }

  private void loadListas(Entidade entidade, boolean full) {
    Hibernate.initialize(entidade.getTelefones());
    Hibernate.initialize(entidade.getEmails());
    if (full) {
      Hibernate.initialize(entidade.getAnotacoes());
      // Hibernate.initialize(entidade.getListaInstitutos());
      Hibernate.initialize(entidade.getDirigentes());
      // Hibernate.initialize(entidade.getPresidentes());
    }
  }

  @Transactional
  public void removeEntidade(Integer id) {
    Entidade entidade = em.find(Entidade.class, id);
    if (null != entidade) {
      em.remove(entidade);
    }
  }

  private List<Dirigente> getDirigentes(Entidade pEntidade) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Dirigente> c = cb.createQuery(Dirigente.class);
    Root<Dirigente> root = c.from(Dirigente.class);
    Join pessoa = root.join("trabalhador", JoinType.LEFT);
    Join instituto = root.join("instituto", JoinType.LEFT);
    Join entidade = root.join("entidade", JoinType.LEFT);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.equal(entidade.get("id"), pEntidade.getId()));

    c.where(criteria.toArray(new Predicate[] {}));

    c.multiselect(root.get("id"), pessoa.get("id"), pessoa.get("nomeCompleto"),
        instituto.get("id"), instituto.get("descricao"));

    Query q = em.createQuery(c);

    List<Dirigente> retorno = q.getResultList();

    return retorno;
  }
}
