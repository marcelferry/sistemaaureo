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

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.QuestaoInstituto;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.service.QuestaoInstitutoService;

@Service
public class QuestaoInstitutoServiceImpl implements QuestaoInstitutoService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addQuestaoInstituto(QuestaoInstituto questaoInstituto) {
        em.persist(questaoInstituto);
    }
    
    @Transactional
    public void updateQuestaoInstituto(QuestaoInstituto questaoInstituto) {
    	em.merge(questaoInstituto);
    }

    @Transactional
    public List<QuestaoInstituto> findAll() {
        CriteriaQuery<QuestaoInstituto> c = em.getCriteriaBuilder().createQuery(QuestaoInstituto.class);
        c.from(QuestaoInstituto.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public QuestaoInstituto getByInstitutoIdAndRodizioId(Integer idInstituto, Integer idRodizio){
      
      BaseInstituto instituto = em.find(BaseInstituto.class, idInstituto);
      Rodizio rodizio  = em.find(Rodizio.class, idRodizio);
      
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<QuestaoInstituto> c = cb.createQuery(QuestaoInstituto.class);
    	Root<QuestaoInstituto> emp = c.from(QuestaoInstituto.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.equal( emp.<BaseInstituto>get("questao") , instituto ));
      criteria.add( cb.equal( emp.<Rodizio>get("rodizio") , rodizio ));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getSingleResult();
    }
    
    @Transactional
    public List<QuestaoInstituto> findByInstitutoId(Integer idInstituto){
      
      BaseInstituto instituto = em.find(BaseInstituto.class, idInstituto);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<QuestaoInstituto> c = cb.createQuery(QuestaoInstituto.class);
      Root<QuestaoInstituto> emp = c.from(QuestaoInstituto.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("questao") , instituto ));
      c.where(criteria.get(0));
      
      return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeQuestaoInstituto(Integer id) {
        QuestaoInstituto questaoInstituto = em.find(QuestaoInstituto.class, id);
        if (null != questaoInstituto) {
            em.remove(questaoInstituto);
        }
    }

    @Transactional
	public QuestaoInstituto findById(Integer id) {
		QuestaoInstituto questaoInstituto = em.find(QuestaoInstituto.class, id);
		return questaoInstituto;
	}

    @Override
    public void enableQuestaoInstituto(Integer questaoInstitutoId) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void disableQuestaoInstituto(Integer questaoInstitutoId) {
      // TODO Auto-generated method stub
      
    }
    
}
