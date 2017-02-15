package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @PersistenceContext
  EntityManager em;

  @Transactional
  public void save(Usuario usuario) {
    em.persist(usuario);
  }
  
  @Transactional
  public void update(Usuario usuario) {
    em.merge(usuario);
  }

  @Transactional
  public List<Usuario> findAll() {
    CriteriaQuery<Usuario> c = em.getCriteriaBuilder().createQuery(
        Usuario.class);
    c.from(Usuario.class);
   
    return em.createQuery(c).getResultList();
  }
  
  @Transactional
  public void remove(Integer id) {
    Usuario person = em.find(Usuario.class, id);
    if (null != person) {
      em.remove(person);
    }
  }

  @Transactional
  public Usuario findById(Integer id) {
    Usuario person = em.find(Usuario.class, id);
    return person;
  }

  @Transactional
  public Usuario findByPessoa(Pessoa pessoa) {
    try {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Usuario> c = cb.createQuery(Usuario.class);
      Root<Usuario> emp = c.from(Usuario.class);

      if (pessoa != null) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        criteria.add(cb.equal(emp.<Pessoa> get("pessoa"), pessoa));
        c.where(criteria.get(0));
      }

      return em.createQuery(c).getSingleResult();
    } catch (javax.persistence.NoResultException ex) {
      return null;
    }
  }

  @Transactional
  public Usuario findByUsername(String username) {
    try {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Usuario> c = cb.createQuery(Usuario.class);
      Root<Usuario> emp = c.from(Usuario.class);

      if (username != null && !username.trim().equals("")) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        criteria.add(cb.equal(cb.lower(emp.<String> get("username")), username
            .toLowerCase().trim()));
        c.where(criteria.get(0));
      }

      List<Usuario> usuarios = em.createQuery(c).getResultList();
      if (usuarios != null && usuarios.size() > 0) {
        Usuario usuario = usuarios.get(0);
        // loadListas(usuario);
        return usuario;
      } else {
        return null;
      }
    } catch (javax.persistence.NoResultException ex) {
      return null;
    }
  }

  private void loadListas(Usuario usuario) {
    Hibernate.initialize(usuario.getUserRoles());
  }
}
