package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.DirigenteNacional;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.service.DirigenteNacionalService;
import com.concafras.gestao.service.EstadoService;

@Service
public class DirigenteNacionalServiceImpl implements DirigenteNacionalService {

  @PersistenceContext
  EntityManager em;

  @Autowired
  EstadoService estadoService;

  @Transactional
  public void save(DirigenteNacional dirigente) {
    em.persist(dirigente);
  }

  @Transactional
  public void update(DirigenteNacional dirigente) {
    em.merge(dirigente);
  }

  @Transactional
  public List<DirigenteNacional> findAll() {
    CriteriaQuery<DirigenteNacional> c = em.getCriteriaBuilder().createQuery(
        DirigenteNacional.class);
    c.from(DirigenteNacional.class);
    return em.createQuery(c).getResultList();
  }

  @Transactional
  public List<DirigenteNacional> findByDirigenteNomeCompleto(String name) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<DirigenteNacional> c = cb
        .createQuery(DirigenteNacional.class);
    Root<DirigenteNacional> emp = c.from(DirigenteNacional.class);
    Join pessoa = emp.join("dirigente", JoinType.LEFT);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.like(cb.lower(pessoa.get("nomeCompleto")), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    c.where(criteria.get(0));

    return em.createQuery(c).getResultList();
  }

  @Transactional
  public List<DirigenteNacional> findByInstitutoId(Integer id) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<DirigenteNacional> c = cb
        .createQuery(DirigenteNacional.class);
    Root<DirigenteNacional> emp = c.from(DirigenteNacional.class);
    Join instituto = emp.join("instituto", JoinType.LEFT);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.equal(instituto.get("id"), id));
    c.where(criteria.get(0));

    return em.createQuery(c).getResultList();
  }

  @Transactional
  public List<DirigenteNacional> findByDirigente(Pessoa pessoa) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<DirigenteNacional> c = cb
        .createQuery(DirigenteNacional.class);
    Root<DirigenteNacional> emp = c.from(DirigenteNacional.class);

    if (pessoa != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(cb.equal(emp.<Pessoa> get("dirigente"), pessoa));
      c.where(criteria.get(0));
    }

    return em.createQuery(c).getResultList();
  }

  @Transactional
  public void remove(Integer id) {
    DirigenteNacional dirigente = em.find(DirigenteNacional.class, id);
    if (null != dirigente) {
      em.remove(dirigente);
    }
  }

  @Transactional
  public DirigenteNacional findById(Integer id) {
    DirigenteNacional dirigente = em.find(DirigenteNacional.class, id);
    return dirigente;
  }

}
