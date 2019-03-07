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

import com.concafras.gestao.model.Dirigente;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.service.DirigenteService;
import com.concafras.gestao.service.EstadoService;

@Service
public class DirigenteServiceImpl implements DirigenteService {

    @PersistenceContext
    EntityManager em;
    
    @Autowired
    EstadoService estadoService;
        
    @Transactional
    public void save(Dirigente dirigente) {
        em.persist(dirigente);
    }
    
    @Transactional
    public void update(Dirigente dirigente) {
    	em.merge(dirigente);
    }

    @Transactional
    public List<Dirigente> findAll() {
        CriteriaQuery<Dirigente> c = em.getCriteriaBuilder().createQuery(Dirigente.class);
        c.from(Dirigente.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<Dirigente> findByNome(String name) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Dirigente> c = cb.createQuery(Dirigente.class);
    	Root<Dirigente> emp = c.from(Dirigente.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<Dirigente> findByTrabalhador(Pessoa pessoa) {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Dirigente> c = cb
          .createQuery(Dirigente.class);
      Root<Dirigente> emp = c.from(Dirigente.class);

      if (pessoa != null) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        criteria.add(cb.equal(emp.<Pessoa> get("trabalhador"), pessoa));
        c.where(criteria.get(0));
      }

      return em.createQuery(c).getResultList();
    }

    @Transactional
    public void delete(Integer id) {
        Dirigente dirigente = em.find(Dirigente.class, id);
        if (null != dirigente) {
            em.remove(dirigente);
        }
    }

    @Transactional
	public Dirigente findById(Integer id) {
		Dirigente dirigente = em.find(Dirigente.class, id);
		return dirigente;
	}
        
}
