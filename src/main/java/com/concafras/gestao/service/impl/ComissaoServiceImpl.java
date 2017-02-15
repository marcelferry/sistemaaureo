package com.concafras.gestao.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.Comissao;
import com.concafras.gestao.model.Instituto;
import com.concafras.gestao.service.ComissaoService;

@Service
public class ComissaoServiceImpl implements ComissaoService {

  @PersistenceContext
  EntityManager em;

  @Transactional
  public void save(Comissao comissao) {
    if (comissao.getInstituto() != null
        && comissao.getInstituto().getId() != null) {
      Instituto i = em.find(Instituto.class, comissao.getInstituto().getId());
      comissao.setInstituto(i);
    } else {
      comissao.setInstituto(null);
    }
    em.persist(comissao);
  }

  @Transactional
  public void update(Comissao comissao) {
    if (comissao.getInstituto() != null
        && comissao.getInstituto().getId() != null) {
      Instituto i = em.find(Instituto.class, comissao.getInstituto().getId());
      comissao.setInstituto(i);
    } else {
      comissao.setInstituto(null);
    }
    em.merge(comissao);
  }

  @Transactional
  public List<Comissao> findAll() {
    CriteriaQuery<Comissao> c = em.getCriteriaBuilder().createQuery(
        Comissao.class);
    c.from(Comissao.class);
    return em.createQuery(c).getResultList();
  }

  @Transactional
  public void remove(Integer id) {
    Comissao entidade = em.find(Comissao.class, id);
    if (null != entidade) {
      em.remove(entidade);
    }
  }

  @Override
  public Comissao findById(Integer id) {
    return em.find(Comissao.class, id);
  }
}
