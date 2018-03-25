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

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.service.BaseInstitutoService;

@Service
public class BaseInstitutoServiceImpl implements BaseInstitutoService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void save(BaseInstituto entidade) {
		em.persist(entidade);
	}

	@Transactional
	public void update(BaseInstituto entidade) {
		em.merge(entidade);
	}

	@Transactional
	public List<BaseInstituto> findAll() {
	  CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<BaseInstituto> c = cb.createQuery(BaseInstituto.class);
    Root<BaseInstituto> root = c.from(BaseInstituto.class);
    
    Expression exp = root.get("descricao");
    
    c.orderBy(cb.asc(exp));
    
		return em.createQuery(c).getResultList();
	}
	
	@Transactional
	public List<BaseInstituto> findByDescricaoLike(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BaseInstituto> c = cb.createQuery(BaseInstituto.class);
		Root<BaseInstituto> emp = c.from(BaseInstituto.class);

		List<Predicate> criteria = new ArrayList<Predicate>();
		criteria.add(cb.like(cb.lower(emp.<String> get("descricao")), "%"
				+ name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
		c.where(criteria.get(0));
		
		Expression exp = emp.get("descricao");
    
    c.orderBy(cb.asc(exp));

		return em.createQuery(c).getResultList();
	}

	@Transactional
	public void remove(Integer id) {
	  BaseInstituto entidade = em.find(BaseInstituto.class, id);
		if (null != entidade) {
			em.remove(entidade);
		}
	}

	@Transactional
	public BaseInstituto findById(Integer id) {
		return em.find(BaseInstituto.class, id);
	}

	@Transactional
	public List<BaseInstituto> findByRodizio(boolean rodizio) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BaseInstituto> c = cb.createQuery(BaseInstituto.class);
		Root<BaseInstituto> emp = c.from(BaseInstituto.class);
		if (rodizio) {
			List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.equal(emp.<String> get("rodizio"), true));
			c.where(criteria.get(0));
		}
		
	  Expression exp = emp.get("descricao");
    
    c.orderBy(cb.asc(exp));

		return em.createQuery(c).getResultList();
	}

	@Override
	public BaseInstituto findByIdOverview(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<BaseInstituto> c = cb.createQuery(BaseInstituto.class);
    	Root<BaseInstituto> root = c.from(BaseInstituto.class);
    	Join pessoa = root.join("dirigenteNacional", JoinType.LEFT);
    	
	    List<Predicate> criteria = new ArrayList<Predicate>();
	    criteria.add(cb.equal( root.<Integer>get("id"),  id));
	    	
	    c.where(criteria.toArray(new Predicate[]{}));
    	
    	c.multiselect(
    			root.get("id"), 
    			root.get("nome"), 
    			root.get("descricao"), 
    			pessoa.get("id"),
    			pessoa.get("nome")
    			);
    	
    	Expression exp = root.get("descricao");
    	
    	c.orderBy(cb.asc(exp));
    	
    	Query q = em.createQuery(c);
    	
    	List<BaseInstituto> retorno = q.getResultList();
    	if(retorno != null && retorno.size() == 1){
    		return retorno.get(0);
    	}
    	
    	return null;
	}

	@Override
	public List<BaseInstituto> findAllOverview() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<BaseInstituto> c = cb.createQuery(BaseInstituto.class);
    	Root<BaseInstituto> root = c.from(BaseInstituto.class);
    	Join pessoa = root.join("dirigenteNacional", JoinType.LEFT);
    	
    	c.multiselect(
    			root.get("id"), 
    			root.get("nome"), 
    			root.get("descricao"), 
    			pessoa.get("id"),
    			pessoa.get("nome")
    			);
    	
    	Expression exp = root.get("descricao");
    	
    	c.orderBy(cb.asc(exp));
    	
    	Query q = em.createQuery(c);
    	
    	List<BaseInstituto> retorno = q.getResultList();
    	
    	return retorno;
	}
	
	@Override
	public List<BaseInstituto> findByRodizioOverview(boolean rodizio) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BaseInstituto> c = cb.createQuery(BaseInstituto.class);
		Root<BaseInstituto> root = c.from(BaseInstituto.class);
		Join pessoa = root.join("dirigenteNacional", JoinType.LEFT);
		
		if (rodizio) {
			List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.equal(root.<String> get("rodizio"), true));
			c.where(criteria.get(0));
		}
		
		c.multiselect(
				root.get("id"), 
				root.get("nome"), 
				root.get("descricao"), 
				pessoa.get("id"),
				pessoa.get("nome")
				);
		
		Expression exp = root.get("descricao");
		
		c.orderBy(cb.asc(exp));
		
		Query q = em.createQuery(c);
		
		List<BaseInstituto> retorno = q.getResultList();
		
		return retorno;
	}
	
	@Override
	public List<BaseInstituto> findByDescricaoLikeOverview(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BaseInstituto> c = cb.createQuery(BaseInstituto.class);
		Root<BaseInstituto> root = c.from(BaseInstituto.class);
		Join pessoa = root.join("dirigenteNacional", JoinType.LEFT);
		
		if (name != null && !name.trim().equals("")) {
			List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.like(cb.lower(root.<String> get("descricao")), "%"
					+ name.toLowerCase().trim().replaceAll(" ", "%") + "%"));
			c.where(criteria.get(0));
		}
		
		c.multiselect(
				root.get("id"), 
				root.get("nome"), 
				root.get("descricao"), 
				pessoa.get("id"),
				pessoa.get("nome")
				);
		
		Expression<String> exp = root.get("descricao");
		
		c.orderBy(cb.asc(exp));
		
		Query q = em.createQuery(c);
		
		List<BaseInstituto> retorno = q.getResultList();
		
		return retorno;
	}
	
	
	public List<BaseInstituto> findByDirigenteNacional(Pessoa dirigente){
	  CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<BaseInstituto> c = cb.createQuery(BaseInstituto.class);
    Root<BaseInstituto> emp = c.from(BaseInstituto.class);
    Join pessoa = emp.join("dirigenteNacional", JoinType.LEFT);
    
    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.equal(pessoa.get("id"), dirigente.getId()));
    
    c.where(criteria.get(0));

    return em.createQuery(c).getResultList();
	}
}
