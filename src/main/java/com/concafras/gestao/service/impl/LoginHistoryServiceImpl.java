package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.Date;
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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.security.LoginHistory;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.LoginHistoryService;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

  @PersistenceContext
  EntityManager em;

  @Transactional
  public void save(LoginHistory loginHistory){
    em.persist(loginHistory);
  }

  @Transactional
  public List<LoginHistory> findAll(){
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<LoginHistory> c = cb.createQuery(
        LoginHistory.class);
    Root<LoginHistory> root = c.from(LoginHistory.class);
    Expression exp =  root.<String>get("loggedIn");
    c.orderBy(cb.desc(exp));
    return em.createQuery(c).getResultList();
  }
  
  @Transactional
  public List<LoginHistory> findByUsuario(Usuario usuario){
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<LoginHistory> c = cb.createQuery(
        LoginHistory.class);
    Root<LoginHistory> root = c.from(LoginHistory.class);
    
    //fazer filtro por usuario
    
    Expression exp =  root.<String>get("loggedIn");
    
    c.orderBy(cb.desc(exp));
    
    return em.createQuery(c).getResultList();
  }

  @Transactional
  public List<LoginHistory> listRangeLoginHistory(String name, String sortCol,
      String sortDir, int startRange, int maxRows) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<LoginHistory> c = cb.createQuery(LoginHistory.class);
    Root<LoginHistory> root = c.from(LoginHistory.class);
    Join usuario = root.join("user", JoinType.LEFT);
    Join pessoa = usuario.join("pessoa", JoinType.LEFT);

    if (name != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(cb.or(
          cb.like(
              cb.function("UNACCENT", String.class,
                  cb.lower(usuario.get("username"))), "%"
                      + name.toLowerCase().trim().replaceAll(" ", "%") + "%"),
          cb.like(
              cb.function("UNACCENT", String.class,
                  cb.lower(pessoa.get("nomeCompleto"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%")));

      c.where(criteria.toArray(new Predicate[] {}));
    }

    Expression exp = null;

    if (sortCol == null) {
      exp = root.<Date> get("loggedIn");
    } else if (sortCol.equals("id")) {
      exp = root.<Long> get("id");
    } else if (sortCol.equals("loggedIn")) {
      exp = root.<Date> get("loggedIn");
    } else if (sortCol.equals("nome")) {
      exp = pessoa.get("nomeCompleto");
    } else if (sortCol.equals("username")) {
      exp = usuario.get("username");
    }

    if (sortDir.equals("desc")) {
      c.orderBy(cb.desc(exp));
    } else {
      c.orderBy(cb.asc(exp));
    }

    Query q = em.createQuery(c);
    q.setFirstResult(startRange);
    q.setMaxResults(maxRows);

    List<LoginHistory> retorno = q.getResultList();

    return retorno;
  }

  @Transactional
  public Long countListRangeLoginHistory(String name) {

    CriteriaBuilder qb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    Root<LoginHistory> root = cq.from(LoginHistory.class);
    Join usuario = root.join("user", JoinType.LEFT);
    Join pessoa = usuario.join("pessoa", JoinType.LEFT);
    cq.select(qb.count(root));

    if (name != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(qb.or(
          qb.like(
              qb.function("UNACCENT", String.class,
                  qb.lower(usuario.get("username"))), "%"
                      + name.toLowerCase().trim().replaceAll(" ", "%") + "%"),
          qb.like(
              qb.function("UNACCENT", String.class,
                  qb.lower(pessoa.get("nomeCompleto"))), "%"
                  + name.toLowerCase().trim().replaceAll(" ", "%") + "%")));
      cq.where(criteria.toArray(new Predicate[] {}));
    }

    return em.createQuery(cq).getSingleResult();

  }

}
