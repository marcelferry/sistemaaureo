package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.Pais;
import com.concafras.gestao.service.PaisService;

@Service
public class PaisServiceImpl implements PaisService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addPais(Pais pais) {
        em.persist(pais);
    }
    
    @Transactional
    public void updatePais(Pais pais) {
    	em.merge(pais);
    }

    @Transactional
    public List<Pais> listPais() {
        CriteriaQuery<Pais> c = em.getCriteriaBuilder().createQuery(Pais.class);
        c.from(Pais.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<Pais> listPais(String name) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Pais> c = cb.createQuery(Pais.class);
    	Root<Pais> emp = c.from(Pais.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removePais(Integer id) {
        Pais pais = em.find(Pais.class, id);
        if (null != pais) {
            em.remove(pais);
        }
    }

    @Transactional
	public Pais getPais(Integer id) {
		Pais pais = em.find(Pais.class, id);
		return pais;
	}
    
}
