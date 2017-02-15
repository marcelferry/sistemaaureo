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

import com.concafras.gestao.model.Telefone;
import com.concafras.gestao.service.EstadoService;
import com.concafras.gestao.service.TelefoneService;

@Service
public class TelefoneServiceImpl implements TelefoneService {

    @PersistenceContext
    EntityManager em;
    
    @Autowired
    EstadoService estadoService;
        
    @Transactional
    public void addTelefone(Telefone telefone) {
        em.persist(telefone);
    }
    
    @Transactional
    public void updateTelefone(Telefone telefone) {
    	em.merge(telefone);
    }

    @Transactional
    public List<Telefone> listTelefone() {
        CriteriaQuery<Telefone> c = em.getCriteriaBuilder().createQuery(Telefone.class);
        c.from(Telefone.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<Telefone> listTelefone(String name) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Telefone> c = cb.createQuery(Telefone.class);
    	Root<Telefone> emp = c.from(Telefone.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeTelefone(Integer id) {
        Telefone telefone = em.find(Telefone.class, id);
        if (null != telefone) {
            em.remove(telefone);
        }
    }

    @Transactional
	public Telefone getTelefone(Integer id) {
		Telefone telefone = em.find(Telefone.class, id);
		return telefone;
	}
        
}
