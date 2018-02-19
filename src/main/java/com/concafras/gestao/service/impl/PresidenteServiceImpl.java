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

import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Presidente;
import com.concafras.gestao.service.PresidenteService;

@Service
public class PresidenteServiceImpl implements PresidenteService {

    @PersistenceContext
    EntityManager em;
    
    @Transactional
    public void addPresidente(Presidente presidente) {
        em.persist(presidente);
    }
    
    @Transactional
    public void updatePresidente(Presidente presidente) {
    	em.merge(presidente);
    }

    @Transactional
    public List<Presidente> listPresidente() {
        CriteriaQuery<Presidente> c = em.getCriteriaBuilder().createQuery(Presidente.class);
        c.from(Presidente.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<Presidente> listPresidente(String name) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Presidente> c = cb.createQuery(Presidente.class);
    	Root<Presidente> emp = c.from(Presidente.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removePresidente(Integer id) {
        Presidente presidente = em.find(Presidente.class, id);
        if (null != presidente) {
            em.remove(presidente);
        }
    }

    @Transactional
	public Presidente getPresidente(Integer id) {
		Presidente presidente = em.find(Presidente.class, id);
		return presidente;
	}
    
    @Transactional
	public List<Presidente> getPresidente(Pessoa pessoa) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Presidente> c = cb.createQuery(Presidente.class);
		Root<Presidente> emp = c.from(Presidente.class);

		if (pessoa != null) {
			List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.equal(emp.<Pessoa> get("pessoa"), pessoa ));
			c.where(criteria.get(0));
		}

        return em.createQuery(c).getResultList();
	}
    
}
