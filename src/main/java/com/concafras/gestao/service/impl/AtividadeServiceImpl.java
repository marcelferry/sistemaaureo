package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.Atividade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.service.AtividadeService;

@Service
public class AtividadeServiceImpl implements AtividadeService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addAtividade(Atividade atividade) {
      BaseInstituto i = em.find(BaseInstituto.class, atividade.getInstituto().getId());
      atividade.setInstituto(i);
        em.persist(atividade);
    }
    
    @Transactional
    public void updateAtividade(Atividade entidade) {
      em.merge(entidade);
    }

    @Transactional
    public List<Atividade> listAtividade() {
        CriteriaQuery<Atividade> c = em.getCriteriaBuilder().createQuery(Atividade.class);
        c.from(Atividade.class);
        return em.createQuery(c).getResultList();
    }
    
    @Transactional
    public List<Atividade> listAtividadeByInstituto(Integer id) {
      
      BaseInstituto base = em.find(BaseInstituto.class, id);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Atividade> c = cb.createQuery(Atividade.class);
      Root<Atividade> emp = c.from(Atividade.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , base ));
      criteria.add( cb.isNull( emp.<Atividade>get("pai")  ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<Atividade> select = c.select(emp);
      
      select.orderBy(cb.asc(emp.get("viewOrder")));
      
      List<Atividade> retorno = em.createQuery(select).getResultList();
      
      for(Atividade a : retorno){
        Hibernate.initialize(a.getSubAtividades());
        for(Atividade b : a.getSubAtividades()){
          if(b != null){
            Hibernate.initialize(b.getSubAtividades());
            for(Atividade c1 : b.getSubAtividades()){
                if(c1 != null){
                  Hibernate.initialize(c1.getSubAtividades());
                  for(Atividade d : c1.getSubAtividades()){
                    if(d != null){
                      Hibernate.initialize(d.getSubAtividades());
                      for(Atividade e : d.getSubAtividades()){
                        if(e != null)
                          Hibernate.initialize(e.getSubAtividades());
                      }
                    }
                  }
                }
              }
          }
        }
      }
      
      return retorno;
    }

    @Transactional
    public void removeAtividade(Integer id) {
      Atividade entidade = em.find(Atividade.class, id);
        if (null != entidade) {
            em.remove(entidade);
        }
    }
    
    @Override
  public Atividade getAtividade(Integer id) {
    return em.find(Atividade.class, id);
  }
}
