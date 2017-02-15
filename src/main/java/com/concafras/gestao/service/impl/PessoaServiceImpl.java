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
import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.Endereco;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.service.CidadeService;
import com.concafras.gestao.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

  @PersistenceContext
  EntityManager em;

  @Autowired
  CidadeService cidadeService;

  @Transactional
  public void addPessoa(Pessoa pessoa) {
    if (pessoa.getEnderecos() != null)
      for (Endereco end : pessoa.getEnderecos()) {
        Integer id = end.getCidade().getId();
        if(id != null){
          Cidade c = cidadeService.getCidade(id);
          end.setCidade(c);
        } else {
          end.setCidade(null);
        }
      }

    if (pessoa.getEndereco() != null
        && pessoa.getEndereco().getCidade() != null
        && pessoa.getEndereco().getCidade().getEstado() != null) {
      Integer id = pessoa.getEndereco().getCidade().getId();
      if(id != null){
        Cidade c = cidadeService.getCidade(id);
        pessoa.getEndereco().setCidade(c);
      } else {
        pessoa.getEndereco().setCidade(null);
      }
    }

    em.persist(pessoa);
  }

  @Transactional
  public void updatePessoa(Pessoa pessoa) {
    if (pessoa.getEnderecos() != null)
      for (Endereco end : pessoa.getEnderecos()) {
        Integer id = end.getCidade().getId();
        if(id != null){
          Cidade c = cidadeService.getCidade(id);
          end.setCidade(c);
        } else {
          end.setCidade(null);
        }
      }

    if (pessoa.getEndereco() != null
        && pessoa.getEndereco().getCidade() != null
        && pessoa.getEndereco().getCidade().getEstado() != null) {
      Integer id = pessoa.getEndereco().getCidade().getId();
      if(id != null){
        Cidade c = cidadeService.getCidade(id);
        pessoa.getEndereco().setCidade(c);
      } else {
        pessoa.getEndereco().setCidade(null);
      }
    }

    em.merge(pessoa);
  }

  @Transactional
  public List<Pessoa> listPessoa() {
    CriteriaQuery<Pessoa> c = em.getCriteriaBuilder().createQuery(Pessoa.class);
    c.from(Pessoa.class);
    List<Pessoa> retorno = em.createQuery(c).getResultList();
    for (Pessoa person : retorno) {
      listLoad(person);
    }
    return retorno;
  }

  @Transactional
  public List<Pessoa> listPessoaResumo() {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pessoa> c = cb.createQuery(Pessoa.class);
    Root<Pessoa> root = c.from(Pessoa.class);
    Join endereco = root.join("endereco", JoinType.LEFT);
    Join cidade = endereco.join("cidade", JoinType.LEFT);
    Join estado = cidade.join("estado", JoinType.LEFT);

    c.multiselect(root.get("id"), root.get("nomeCompleto"), root.get("cpf"),
        cidade.get("nome"), estado.get("sigla"));

    c.orderBy(cb.asc(root.<String> get("nomeCompleto")));

    List<Pessoa> retorno = em.createQuery(c).getResultList();

    for (Pessoa pessoa : retorno) {
      pessoa.setEmails(getContatos(pessoa));
    }

    return retorno;
  }

  private List<ContatoInternet> getContatos(Pessoa pPessoa) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<ContatoInternet> c = cb.createQuery(ContatoInternet.class);
    Root<Pessoa> root = c.from(Pessoa.class);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.equal(root.<Integer> get("id"), pPessoa.getId()));

    c.where(criteria.toArray(new Predicate[] {}));

    Join<Pessoa, ContatoInternet> answers = root
        .<Pessoa, ContatoInternet> join("emails");

    CriteriaQuery<ContatoInternet> cq = c.select(answers);
    TypedQuery<ContatoInternet> query = em.createQuery(cq);

    List<ContatoInternet> retorno = query.getResultList();

    return retorno;
  }

  @Transactional
  public List<Pessoa> listPessoa(String name, int maxRows) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pessoa> c = cb.createQuery(Pessoa.class);
    Root<Pessoa> emp = c.from(Pessoa.class);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.like( cb.function("UNACCENT", String.class,  cb.lower( emp.<String>get("nomeCompleto") ) ), "%"
        + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    c.where(criteria.get(0));

    c.orderBy(cb.asc(emp.<String> get("nomeCompleto")));

    List<Pessoa> retorno = em.createQuery(c).setMaxResults(maxRows)
        .getResultList();

    for (Pessoa person : retorno) {
      listLoad(person);
    }

    return retorno;
  }

  @Transactional
  public void removePessoa(Integer id) {
    Pessoa person = em.find(Pessoa.class, id);
    if (null != person) {
      em.remove(person);
    }
  }

  @Transactional
  public Pessoa getPessoa(Integer id) {
    Pessoa person = em.find(Pessoa.class, id);

    listLoad(person);

    return person;
  }

  @Transactional
  public List<Pessoa> getPessoa(String cpfEmail) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pessoa> c = cb.createQuery(Pessoa.class);
    Root<Pessoa> emp = c.from(Pessoa.class);
    Join emails = emp.join("emails", JoinType.LEFT);

    List<Predicate> criteria = new ArrayList<Predicate>();

    criteria.add(cb.or(
        cb.equal(cb.lower(emp.<String> get("cpf")), cpfEmail.trim().toLowerCase()),
        cb.equal(cb.lower(emails.get("contato")), cpfEmail.trim().toLowerCase())));

    c.where(criteria.toArray(new Predicate[] {}));

    c.orderBy(cb.asc(emp.<String> get("nomeCompleto")));

    List<Pessoa> pessoas = em.createQuery(c).getResultList();

    for (Pessoa person : pessoas) {
      listLoad(person);
    }

    return pessoas;

  }

  @Transactional
  public List<Pessoa> listRangePessoa(String name, String sortCol,
      String sortDir, int startRange, int maxRows) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pessoa> c = cb.createQuery(Pessoa.class);
    Root<Pessoa> root = c.from(Pessoa.class);
    Join endereco = root.join("endereco", JoinType.LEFT);
    Join cidade = endereco.join("cidade", JoinType.LEFT);
    Join estado = cidade.join("estado", JoinType.LEFT);
    Join emails = root.join("emails", JoinType.LEFT);

    if (name != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(cb.or(
          cb.like(
              cb.function("UNACCENT", String.class,
                  cb.lower(root.<String> get("nomeCompleto"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%"),
          cb.like(
              cb.function("UNACCENT", String.class,
                  cb.lower(emails.get("contato"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%")));
      criteria.add(
          cb.or(
              cb.equal(emails.get("principal"), true),
              cb.isNull(emails.get("principal"))
              )
          );

      c.where(criteria.toArray(new Predicate[] {}));
    }

    c.multiselect(root.get("id"), root.get("nomeCompleto"), root.get("cpf"),
        cidade.get("nome"), estado.get("sigla"), emails.get("contato"));

    Expression exp = null;

    if (sortCol == null) {
      exp = root.<String> get("nomeCompleto");
    } else if (sortCol.equals("id")) {
      exp = root.<String> get("id");
    } else if (sortCol.equals("nomeCompleto")) {
      exp = root.<String> get("nomeCompleto");
    } else if (sortCol.equals("cidade")) {
      exp = cidade.get("nome");
    }

    if (sortDir.equals("desc")) {
      c.orderBy(cb.desc(exp));
    } else {
      c.orderBy(cb.asc(exp));
    }

    Query q = em.createQuery(c);
    q.setFirstResult(startRange);
    q.setMaxResults(maxRows);

    List<Pessoa> retorno = q.getResultList();
    
    for (Pessoa person : retorno) {
        listLoad(person, false);
    }

    return retorno;
  }

  @Transactional
  public Long countListRangePessoa(String name) {

    CriteriaBuilder qb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    Root<Pessoa> root = cq.from(Pessoa.class);
    Join emails = root.join("emails", JoinType.LEFT);
    cq.select(qb.count(root));

    if (name != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(qb.or(
          qb.like(
              qb.function("UNACCENT", String.class,
                  qb.lower(root.<String> get("nomeCompleto"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%"),
          qb.like(
              qb.function("UNACCENT", String.class,
                  qb.lower(emails.get("contato"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%")));
      
      criteria.add(
          qb.or(
              qb.equal(emails.get("principal"), true),
              qb.isNull(emails.get("principal"))
              )
          );

      cq.where(criteria.toArray(new Predicate[] {}));
    }

    return em.createQuery(cq).getSingleResult();

  }

  private void listLoad(Pessoa person) {
    listLoad(person, true);
  }

  private void listLoad(Pessoa person, boolean full) {
    Hibernate.initialize(person.getTelefones());
    Hibernate.initialize(person.getEmails());
    if (full) {
      Hibernate.initialize(person.getEnderecos());
      Hibernate.initialize(person.getAnotacoes());
      Hibernate.initialize(person.getTrabalhadorPostos());
    }
  }

}
