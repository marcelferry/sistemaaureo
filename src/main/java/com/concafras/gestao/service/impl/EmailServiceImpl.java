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

import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.service.EmailService;
import com.concafras.gestao.service.EstadoService;

@Service
public class EmailServiceImpl implements EmailService {

    @PersistenceContext
    EntityManager em;
    
    @Autowired
    EstadoService estadoService;
        
    @Transactional
    public void addEmail(ContatoInternet email) {
        em.persist(email);
    }
    
    @Transactional
    public void updateEmail(ContatoInternet email) {
    	em.merge(email);
    }

    @Transactional
    public List<ContatoInternet> listEmail() {
        CriteriaQuery<ContatoInternet> c = em.getCriteriaBuilder().createQuery(ContatoInternet.class);
        c.from(ContatoInternet.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<ContatoInternet> listEmail(String name) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<ContatoInternet> c = cb.createQuery(ContatoInternet.class);
    	Root<ContatoInternet> emp = c.from(ContatoInternet.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeEmail(Integer id) {
    	ContatoInternet email = em.find(ContatoInternet.class, id);
        if (null != email) {
            em.remove(email);
        }
    }

    @Transactional
	public ContatoInternet getEmail(Integer id) {
    	ContatoInternet email = em.find(ContatoInternet.class, id);
		return email;
	}
        
}
