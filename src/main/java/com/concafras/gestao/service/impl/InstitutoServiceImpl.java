package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.Instituto;
import com.concafras.gestao.service.InstitutoService;

@Service
public class InstitutoServiceImpl implements InstitutoService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void save(Instituto entidade) {
		em.persist(entidade);
	}

	@Transactional
	public void update(Instituto entidade) {
		em.merge(entidade);
	}

	@Transactional
	public List<Instituto> findAll() {
	  CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Instituto> c = cb.createQuery(Instituto.class);
    Root<Instituto> root = c.from(Instituto.class);
    
    Expression exp = root.get("descricao");
    
    c.orderBy(cb.asc(exp));
    
		return em.createQuery(c).getResultList();
	}
	
	@Transactional
	public List<Instituto> findByDescricaoContains(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Instituto> c = cb.createQuery(Instituto.class);
		Root<Instituto> emp = c.from(Instituto.class);

		List<Predicate> criteria = new ArrayList<Predicate>();
		criteria.add(cb.like(cb.lower(emp.<String> get("descricao")), "%"
				+ name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
		c.where(criteria.get(0));

		return em.createQuery(c).getResultList();
	}

	@Transactional
	public void remove(Integer id) {
		Instituto entidade = em.find(Instituto.class, id);
		if (null != entidade) {
			em.remove(entidade);
		}
	}

	@Transactional
	public Instituto findById(Integer id) {
		return em.find(Instituto.class, id);
	}

	@Override
	public Instituto findByIdOverview(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Instituto> c = cb.createQuery(Instituto.class);
    	Root<Instituto> root = c.from(Instituto.class);
    	Join pessoa = root.join("dirigenteNacional", JoinType.LEFT);
    	
	    List<Predicate> criteria = new ArrayList<Predicate>();
	    criteria.add(cb.equal( root.<Integer>get("id"),  id));
	    	
	    c.where(criteria.toArray(new Predicate[]{}));
    	
    	c.multiselect(
    			root.get("id"), 
    			root.get("descricao"), 
    			pessoa.get("id"),
    			pessoa.get("nome")
    			);
    	
    	Expression exp = root.get("descricao");
    	
    	c.orderBy(cb.asc(exp));
    	
    	Query q = em.createQuery(c);
    	
    	List<Instituto> retorno = q.getResultList();
    	if(retorno != null && retorno.size() == 1){
    		return retorno.get(0);
    	}
    	
    	return null;
	}

	@Override
	public List<Instituto> findAllOverview() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Instituto> c = cb.createQuery(Instituto.class);
    	Root<Instituto> root = c.from(Instituto.class);
    	Join pessoa = root.join("dirigenteNacional", JoinType.LEFT);
    	
    	c.multiselect(
    			root.get("id"), 
    			root.get("descricao"), 
    			pessoa.get("id"),
    			pessoa.get("nome")
    			);
    	
    	Expression exp = root.get("descricao");
    	
    	c.orderBy(cb.asc(exp));
    	
    	Query q = em.createQuery(c);
    	
    	List<Instituto> retorno = q.getResultList();
    	
    	return retorno;
	}
	
}
