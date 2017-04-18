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

import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.QuestaoEntidade;
import com.concafras.gestao.model.QuestaoInstituto;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.service.QuestaoEntidadeService;

@Service
public class QuestaoEntidadeServiceImpl implements QuestaoEntidadeService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addQuestaoEntidade(QuestaoEntidade questaoEntidade) {
        em.persist(questaoEntidade);
    }
    
    @Transactional
    public void updateQuestaoEntidade(QuestaoEntidade questaoEntidade) {
    	em.merge(questaoEntidade);
    }

    @Transactional
    public List<QuestaoEntidade> findAll() {
        CriteriaQuery<QuestaoEntidade> c = em.getCriteriaBuilder().createQuery(QuestaoEntidade.class);
        c.from(QuestaoEntidade.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public QuestaoEntidade getByQuestaoInstitutoIdAndEntidadeIdAndRodizioId(Integer idQuestaoInstituto, Integer idEntidade, Integer idRodizio){
      
      QuestaoInstituto questao = em.find(QuestaoInstituto.class, idQuestaoInstituto);
      BaseEntidade entidade  = em.find(BaseEntidade.class, idEntidade);
      Rodizio rodizio  = em.find(Rodizio.class, idRodizio);
      
      
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<QuestaoEntidade> c = cb.createQuery(QuestaoEntidade.class);
    	Root<QuestaoEntidade> emp = c.from(QuestaoEntidade.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	criteria.add( cb.equal( emp.<QuestaoInstituto>get("questao") , questao ));
      criteria.add( cb.equal( emp.<BaseEntidade>get("entidade") , entidade ));
      criteria.add( cb.equal( emp.<Rodizio>get("rodizio") , rodizio ));
    	c.where(criteria.get(0));
    	
    	return em.createQuery(c).getSingleResult();
    }

    @Transactional
    public void removeQuestaoEntidade(Integer id) {
        QuestaoEntidade questaoEntidade = em.find(QuestaoEntidade.class, id);
        if (null != questaoEntidade) {
            em.remove(questaoEntidade);
        }
    }

    @Transactional
	public QuestaoEntidade findById(Integer id) {
		QuestaoEntidade questaoEntidade = em.find(QuestaoEntidade.class, id);
		return questaoEntidade;
	}
    
}
