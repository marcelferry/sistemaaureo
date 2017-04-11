package com.concafras.gestao.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.Cidade;
import com.concafras.gestao.model.Estado;
import com.concafras.gestao.service.CidadeService;
import com.concafras.gestao.service.EstadoService;

@Service
public class CidadeServiceImpl implements CidadeService {

  @PersistenceContext
  EntityManager em;

  @Autowired
  EstadoService estadoService;

  @Transactional
  public void addCidade(Cidade cidade) {
    Estado i = em.find(Estado.class, cidade.getEstado().getId());
    cidade.setEstado(i);
    em.persist(cidade);
  }

  @Transactional
  public void updateCidade(Cidade cidade) {
    Estado i = em.find(Estado.class, cidade.getEstado().getId());
    cidade.setEstado(i);
    em.merge(cidade);
  }

  @Transactional
  public List<Cidade> listCidade() {
    CriteriaQuery<Cidade> c = em.getCriteriaBuilder().createQuery(Cidade.class);
    c.from(Cidade.class);
    return em.createQuery(c).getResultList();
  }

  @Transactional
  public List<Cidade> listCidade(String name, int maxRows) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Cidade> c = cb.createQuery(Cidade.class);
    Root<Cidade> emp = c.from(Cidade.class);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.like(
        cb.function("UNACCENT", String.class,
            cb.lower(emp.<String> get("nome"))), ""
            + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    c.where(criteria.get(0));

    c.orderBy(cb.asc(emp.get("nome")));

    Query q = em.createQuery(c);

    if (maxRows > 0) {
      q.setMaxResults(maxRows);
    }

    return q.getResultList();
  }

  @Transactional
  public List<Cidade> listCidadeResumo(String name, int maxRows) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Cidade> c = cb.createQuery(Cidade.class);
    Root<Cidade> root = c.from(Cidade.class);
    Join estado = root.join("estado", JoinType.LEFT);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.like(
        cb.function("UNACCENT", String.class,
            cb.lower(root.<String> get("nome"))), ""
            + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    c.where(criteria.get(0));

    c.orderBy(cb.asc(root.get("nome")));

    c.multiselect(root.get("id"), root.get("nome"), estado.get("sigla"));

    Query q = em.createQuery(c);

    if (maxRows > 0) {
      q.setMaxResults(maxRows);
    }

    return q.getResultList();
  }
  
  @Transactional
  public List<Cidade> listCidadeResumo(String name, int maxRows, boolean rodizio) {
    
    if(rodizio){ 
      Query query = em.createNativeQuery(
          " select tc.id, tc.nome, te.sigla, count(*) " + 
          " from cidades tc inner join estados te on tc.idestado = te.id  " + 
          " inner join enderecos tend on tend.idcidade = tc.id " + 
          " inner join entidades tent on tent.idendereco = tend.id " + 
          " where UNACCENT(lower(tc.nome)) like lower('" + name.toLowerCase().trim().replaceAll(" ", "%") + "%') " +
          " group by tc.id, tc.nome, te.sigla order by tc.nome");
                
      List<Object[]> result2 = query.getResultList();
      
      List<Cidade> result = new ArrayList<Cidade>();
      
      for (Object[] resultElement : result2) {
        Integer id = (Integer)resultElement[0];
        String nome = (String)resultElement[1];
        String sigla = (String)resultElement[2];
        Integer entidades = ((BigInteger)resultElement[3]).intValue();
        Cidade op = new Cidade(id, nome, sigla, entidades);
        result.add(op);
        op = null;
      }
      
      return result;
    } else {
      return listCidadeResumo(name, maxRows);
    }
  
  }

  @Transactional
  public void removeCidade(Integer id) {
    Cidade cidade = em.find(Cidade.class, id);
    if (null != cidade) {
      em.remove(cidade);
    }
  }

  @Transactional
  public Cidade getCidade(Integer id) {
    Cidade cidade = em.find(Cidade.class, id);
    return cidade;
  }

  @Transactional
  public Cidade getCidade(Integer id, String cidade, String uf) {
    Cidade cid;
    if (id != null && id > 0) {
      cid = em.find(Cidade.class, id);
      return cid;
    } else {
      if (cidade != null && cidade.length() > 2 && uf != null
          && uf.length() == 2) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cidade> c = cb.createQuery(Cidade.class);
        Root<Cidade> emp = c.from(Cidade.class);

        List<Predicate> criteria = new ArrayList<Predicate>();
        criteria.add(cb.equal(cb.lower(emp.<String> get("nome")), cidade
            .toLowerCase().trim()));
        criteria.add(cb.equal(cb.lower(emp.<Estado> get("estado").<String> get(
            "sigla")), uf.toLowerCase().trim()));
        c.where(criteria.get(0));

        List<Cidade> cidades = em.createQuery(c).getResultList();

        if (cidades.size() == 1) {
          return cidades.get(0);
        }
      }
    }
    return null;
  }

  @Transactional
  public List<Cidade> listRangeCidade(String name, String sortCol,
      String sortDir, int startRange, int maxRows) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Cidade> c = cb.createQuery(Cidade.class);
    Root<Cidade> root = c.from(Cidade.class);
    Join estado = root.join("estado", JoinType.LEFT);

    if (name != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(cb.or(
          cb.like(
              cb.function("UNACCENT", String.class,
                  cb.lower(root.<String> get("nome"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%"),
          cb.like(
              cb.function("UNACCENT", String.class,
                  cb.lower(estado.get("sigla"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%")));

      c.where(criteria.toArray(new Predicate[] {}));
    }

    c.multiselect(root.get("id"), root.get("nome"), estado.get("sigla"));

    Expression exp = null;

    if (sortCol == null) {
      exp = root.<String> get("nome");
    } else if (sortCol.equals("id")) {
      exp = root.<String> get("id");
    } else if (sortCol.equals("nome")) {
      exp = root.<String> get("nome");
    } else if (sortCol.equals("sigla")) {
      exp = estado.get("sigla");
    }

    if (sortDir.equals("desc")) {
      c.orderBy(cb.desc(exp));
    } else {
      c.orderBy(cb.asc(exp));
    }

    Query q = em.createQuery(c);
    q.setFirstResult(startRange);
    q.setMaxResults(maxRows);

    List<Cidade> retorno = q.getResultList();

    return retorno;
  }

  @Transactional
  public Long countListRangeCidade(String name) {

    CriteriaBuilder qb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    Root<Cidade> root = cq.from(Cidade.class);
    Join estado = root.join("estado", JoinType.LEFT);
    cq.select(qb.count(root));

    if (name != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(qb.or(
          qb.like(
              qb.function("UNACCENT", String.class,
                  qb.lower(root.<String> get("nome"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%"),
          qb.like(
              qb.function("UNACCENT", String.class,
                  qb.lower(estado.get("sigla"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%")));

      cq.where(criteria.toArray(new Predicate[] {}));
    }

    return em.createQuery(cq).getSingleResult();

  }

}
