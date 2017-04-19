package com.concafras.gestao.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.security.AlcadaUsuario;
import com.concafras.gestao.service.AlcadaUsuarioService;

@Service
public class AlcadaUsuarioServiceImpl implements AlcadaUsuarioService {

  @PersistenceContext
  EntityManager em;

  @Transactional
  public void save(AlcadaUsuario usuario) {
    em.persist(usuario);
  }
  
  @Transactional
  public void update(AlcadaUsuario usuario) {
    em.merge(usuario);
  }

  @Transactional
  public List<AlcadaUsuario> findAll() {
    CriteriaQuery<AlcadaUsuario> c = em.getCriteriaBuilder().createQuery(
        AlcadaUsuario.class);
    c.from(AlcadaUsuario.class);
   
    return em.createQuery(c).getResultList();
  }
  
  @Transactional
  public void remove(Integer id) {
    AlcadaUsuario person = em.find(AlcadaUsuario.class, id);
    if (null != person) {
      em.remove(person);
    }
  }

  @Transactional
  public AlcadaUsuario findById(Integer id) {
    AlcadaUsuario person = em.find(AlcadaUsuario.class, id);
    return person;
  }
}
