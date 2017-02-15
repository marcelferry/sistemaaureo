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

import com.concafras.gestao.model.Estado;
import com.concafras.gestao.model.Pais;
import com.concafras.gestao.service.EstadoService;

@Service
public class EstadoServiceImpl implements EstadoService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addEstado(Estado estado) {
    	Pais i = em.find(Pais.class, estado.getPais().getId());
    	estado.setPais(i);
        em.persist(estado);
    }
    
    @Transactional
    public void updateEstado(Estado estado) {
    	Pais i = em.find(Pais.class, estado.getPais().getId());
    	estado.setPais(i);
    	em.merge(estado);
    }

    @Transactional
    public List<Estado> listEstado() {
        CriteriaQuery<Estado> c = em.getCriteriaBuilder().createQuery(Estado.class);
        c.from(Estado.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<Estado> listEstado(String name) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Estado> c = cb.createQuery(Estado.class);
    	Root<Estado> emp = c.from(Estado.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.like( cb.lower( emp.<String>get("nome") ), "%" + name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeEstado(Integer id) {
        Estado estado = em.find(Estado.class, id);
        if (null != estado) {
            em.remove(estado);
        }
    }

    @Transactional
	public Estado getEstado(Integer id) {
		Estado estado = em.find(Estado.class, id);
		return estado;
	}
    @Transactional
    public Estado getEstadoBySigla(String sigla) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Estado> c = cb.createQuery(Estado.class);
    	Root<Estado> emp = c.from(Estado.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.equal( cb.lower( emp.<String>get("sigla") ), sigla.toLowerCase() ));
    	c.where(criteria.get(0));
    	
    	List<Estado> estados = em.createQuery(c).getResultList();
    	
    	if(estados.size() == 1){
    		return estados.get(0);
    	} else {
    		return null;
    	}
    }
    
}
