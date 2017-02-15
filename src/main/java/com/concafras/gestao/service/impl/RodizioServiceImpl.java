package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.service.EstadoService;
import com.concafras.gestao.service.RodizioService;

@Service
public class RodizioServiceImpl implements RodizioService {

    @PersistenceContext
    EntityManager em;
    
    @Autowired
    EstadoService estadoService;
        
    @Transactional
    public void save(Rodizio rodizio) {
        em.persist(rodizio);
    }
    
    @Transactional
    public void update(Rodizio rodizio) {
    	em.merge(rodizio);
    }

    @Transactional
    public List<Rodizio> findAll() {
        CriteriaQuery<Rodizio> c = em.getCriteriaBuilder().createQuery(Rodizio.class);
        c.from(Rodizio.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<Rodizio> findByCicloContains(String name) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Rodizio> c = cb.createQuery(Rodizio.class);
    	Root<Rodizio> emp = c.from(Rodizio.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getResultList();
    }

    @Transactional
    public void remove(Integer id) {
        Rodizio rodizio = em.find(Rodizio.class, id);
        if (null != rodizio) {
            em.remove(rodizio);
        }
    }

    @Transactional
	public Rodizio findById(Integer id) {
		Rodizio rodizio = em.find(Rodizio.class, id);
		return rodizio;
	}
    
    @Transactional
    public Rodizio findByAtivoTrue() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Rodizio> c = cb.createQuery(Rodizio.class);
      Root<Rodizio> emp = c.from(Rodizio.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal(emp.<Boolean>get("ativo"), true) );
      c.where(criteria.get(0));
      
      return em.createQuery(c).getSingleResult();
    }
    
}
