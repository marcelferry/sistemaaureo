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

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.service.FacilitadorService;

@Service
public class FacilitadorServiceImpl implements FacilitadorService {

    @PersistenceContext
    EntityManager em;
    
    @Transactional
    public void addFacilitador(Facilitador facilitador) {
        em.persist(facilitador);
    }
    
    @Transactional
    public void updateFacilitador(Facilitador facilitador) {
      em.merge(facilitador);
    }

    @Transactional
    public List<Facilitador> listFacilitador() {
        CriteriaQuery<Facilitador> c = em.getCriteriaBuilder().createQuery(Facilitador.class);
        c.from(Facilitador.class);
        List<Facilitador> retorno = em.createQuery(c).getResultList();
        return retorno;
    }
    
    @Transactional
    public List<Facilitador> listFacilitador(String name, int maxRows) {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Facilitador> c = cb.createQuery(Facilitador.class);
      Root<Facilitador> emp = c.from(Facilitador.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
      c.where(criteria.get(0));
      
      List<Facilitador> retorno = em.createQuery(c).setMaxResults(maxRows).getResultList();

      return retorno;
    }

    @Transactional
    public void removeFacilitador(Integer id) {
        Facilitador person = em.find(Facilitador.class, id);
        if (null != person) {
            em.remove(person);
        }
    }
    
    @Transactional
    public Facilitador getFacilitador(Integer id){
      Facilitador person = em.find(Facilitador.class, id);
      return person;
    }

    @Transactional
  public List<Facilitador> listFacilitadorByRodizio(Integer id) {
      Rodizio rodizio = em.find(Rodizio.class, id);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Facilitador> c = cb.createQuery(Facilitador.class);
      Root<Facilitador> emp = c.from(Facilitador.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("rodizio") , rodizio ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<Facilitador> select = c.select(emp);
      
      List<Facilitador> retorno = em.createQuery(select).getResultList();
      
      return retorno;
  }

  @Transactional
  public List<Facilitador> listFacilitadorByInstituto(Integer id) {
    BaseInstituto instituto = em.find(BaseInstituto.class, id);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Facilitador> c = cb.createQuery(Facilitador.class);
      Root<Facilitador> emp = c.from(Facilitador.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , instituto ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<Facilitador> select = c.select(emp);
      
      List<Facilitador> retorno = em.createQuery(select).getResultList();
      
      return retorno;
  }

  @Transactional
  public List<Facilitador> listFacilitadorByInstitutoRodizio(
      Integer idInstituto, Integer idRodizio) {
    BaseInstituto instituto = em.find(BaseInstituto.class, idInstituto);
      Rodizio rodizio = em.find(Rodizio.class, idRodizio);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Facilitador> c = cb.createQuery(Facilitador.class);
      Root<Facilitador> emp = c.from(Facilitador.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , instituto ));
      criteria.add( cb.equal( emp.<BaseInstituto>get("rodizio") , rodizio ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<Facilitador> select = c.select(emp);
      
      List<Facilitador> retorno = em.createQuery(select).getResultList();
      
      for (Facilitador facilitador : retorno) {
        Pessoa pessoa = facilitador.getTrabalhador();
        Hibernate.initialize(pessoa.getEmails());
      }
      
      return retorno;
  }
  
  @Transactional
  public List<Facilitador> getFacilitador(Pessoa pessoa){
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Facilitador> c = cb.createQuery(Facilitador.class);
    Root<Facilitador> emp = c.from(Facilitador.class);
  
    if (pessoa != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(cb.equal(emp.<Pessoa> get("trabalhador"), pessoa ));
      c.where(criteria.get(0));
    }
  
      return em.createQuery(c).getResultList();
  }
  
  @Transactional
  public List<Facilitador> getFacilitador(Pessoa pessoa, Rodizio rodizio){
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Facilitador> c = cb.createQuery(Facilitador.class);
    Root<Facilitador> emp = c.from(Facilitador.class);
    
    if (pessoa != null) {
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add(cb.equal(emp.<Pessoa> get("trabalhador"), pessoa ));
      criteria.add(cb.equal(emp.<Rodizio> get("rodizio"), rodizio ));
      c.where(criteria.get(0));
    }
    
    return em.createQuery(c).getResultList();
  }
    
}
