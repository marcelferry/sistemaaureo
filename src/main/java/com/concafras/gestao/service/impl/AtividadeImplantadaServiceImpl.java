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

import com.concafras.gestao.model.AtividadeImplantada;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.service.AtividadeImplantadaService;

@Service
public class AtividadeImplantadaServiceImpl implements AtividadeImplantadaService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addAtividadeImplantada(AtividadeImplantada atividadeImplantada) {
        em.persist(atividadeImplantada);
    }
    
    @Transactional
    public void updateAtividadeImplantada(AtividadeImplantada entidade) {
    	em.merge(entidade);
    }

    @Transactional
    public List<AtividadeImplantada> listAtividadeImplantada() {
        CriteriaQuery<AtividadeImplantada> c = em.getCriteriaBuilder().createQuery(AtividadeImplantada.class);
        c.from(AtividadeImplantada.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<AtividadeImplantada> listAtividadeImplantadaByInstituto(Integer id) {
    	
    	BaseInstituto base = em.find(BaseInstituto.class, id);
    	
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<AtividadeImplantada> c = cb.createQuery(AtividadeImplantada.class);
    	Root<AtividadeImplantada> emp = c.from(AtividadeImplantada.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , base ));
    	criteria.add( cb.isNull( emp.<AtividadeImplantada>get("pai")  ));
    	c.where(criteria.toArray(new Predicate[]{}));
    	
    	CriteriaQuery<AtividadeImplantada> select = c.select(emp);
    	
    	select.orderBy(cb.asc(emp.get("viewOrder")));
    	
    	List<AtividadeImplantada> retorno = em.createQuery(select).getResultList();
    	
    	return retorno;
    }

    @Transactional
    public void removeAtividadeImplantada(Integer id) {
    	AtividadeImplantada entidade = em.find(AtividadeImplantada.class, id);
        if (null != entidade) {
            em.remove(entidade);
        }
    }
    
    @Override
	public AtividadeImplantada getAtividadeImplantada(Integer id) {
		return em.find(AtividadeImplantada.class, id);
	}

	@Override
	public List<AtividadeImplantada> listAtividadeImplantadaByInstitutoEntidade(
			Integer idInstituto, Integer idEntidade) {
		// TODO Auto-generated method stub
		return null;
	}
}
