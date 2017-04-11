package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.security.UsuarioAutenticado;
import com.concafras.gestao.service.MetasInstitutoService;

@Service
public class MetasInstitutoServiceImpl implements MetasInstitutoService {

    @PersistenceContext
    EntityManager em;
    
    @Transactional
    public void addMetasInstituto(MetaInstituto itemPlanoModelo) {
      em.persist(itemPlanoModelo);
    }
    
    @Transactional
    public void updateMetasInstituto(MetaInstituto entidade) {
      em.merge(entidade);
    }
    
    @Transactional
    public void removeMetaInstituto(Integer id) {
      MetaInstituto entidade = em.find(MetaInstituto.class, id);
      if (null != entidade) {
        em.remove(entidade);
      }
    }
    
    @Transactional
    public void disableMetaInstituto(Integer id) {
      MetaInstituto meta = em.find(MetaInstituto.class, id);
      if (null != meta) {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        if (userDetails instanceof UsuarioAutenticado) {
          UsuarioAutenticado usuario = (UsuarioAutenticado) userDetails;
          Usuario usuarioLogado = usuario.getUsuario();
          if (usuarioLogado != null) {
            meta.setUsuarioExclusao(usuarioLogado);
          }
        }
        meta.setDataExclusao(new Date());
        em.merge(meta);
      }
    }
    
    @Transactional
    public void enableMetaInstituto(Integer id) {
      MetaInstituto meta = em.find(MetaInstituto.class, id);
      if (null != meta) {
        meta.setDataExclusao(null);
        meta.setUsuarioExclusao(null);
        em.merge(meta);
      }
    }
    
    @Transactional
    public void priorityMetaInstituto(Integer id, Integer priority) {
      MetaInstituto meta = em.find(MetaInstituto.class, id);
      if (null != meta) {
        meta.setPrioridade(priority);
        em.merge(meta);
      }
    }
    
    @Transactional
    public MetaInstituto getMetasInstituto(Integer id) {
      return em.find(MetaInstituto.class, id);
    }

    @Transactional
  public List<MetaInstituto> listMetaInstituto() {
    
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<MetaInstituto> c = cb.createQuery(MetaInstituto.class);
      Root<MetaInstituto> emp = c.from(MetaInstituto.class);
      
      CriteriaQuery<MetaInstituto> select = c.select(emp);
      
      select.orderBy(cb.asc(emp.get("viewOrder")));
      
      List<MetaInstituto> retorno = em.createQuery(select).getResultList();
      
      for(MetaInstituto a : retorno){
        Hibernate.initialize(a.getItens());
      }
      
      return retorno;
  }

    @Transactional
  public List<MetaInstituto> listMetaInstitutoByInstituto(Integer id, boolean removeExcluidos) {
    BaseInstituto base = em.find(BaseInstituto.class, id);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<MetaInstituto> c = cb.createQuery(MetaInstituto.class);
      Root<MetaInstituto> emp = c.from(MetaInstituto.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , base ));
      criteria.add( cb.isNull( emp.<MetaInstituto>get("pai")  ));
      if(removeExcluidos){
        criteria.add( cb.isNull( emp.<Date>get("dataExclusao")  ));
      }
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<MetaInstituto> select = c.select(emp);
      
      select.orderBy(cb.asc(emp.get("viewOrder")));
      
      List<MetaInstituto> retorno = em.createQuery(select).getResultList();
      
      for(MetaInstituto a : retorno){
        Hibernate.initialize(a.getItens());
        for(MetaInstituto b : a.getItens()){
          if(b != null){
            Hibernate.initialize(b.getItens());
            for(MetaInstituto c1 : b.getItens()){
                if(c1 != null){
                  Hibernate.initialize(c1.getItens());
                  for(MetaInstituto d : c1.getItens()){
                    if(d != null){
                      Hibernate.initialize(d.getItens());
                      for(MetaInstituto e : d.getItens()){
                        if(e != null)
                          Hibernate.initialize(e.getItens());
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
    public List<MetaInstituto> listMetaInstitutoByInstitutoResumo(Integer id) {
      BaseInstituto base = em.find(BaseInstituto.class, id);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<MetaInstituto> c = cb.createQuery(MetaInstituto.class);
      Root<MetaInstituto> root = c.from(MetaInstituto.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( root.<BaseInstituto>get("instituto") , base ));
      criteria.add( cb.isNull( root.<MetaInstituto>get("pai")  ));
      criteria.add( cb.isNull( root.<Date>get("dataExclusao")  ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      
      c.multiselect(
          root.get("id"), 
          root.get("descricao"), 
          root.get("tipoMeta"), 
          root.get("prioridade"), 
          root.get("viewOrder")
      );
      
      
      c.orderBy(cb.asc(root.get("viewOrder")));
      
      List<MetaInstituto> retorno = em.createQuery(c).getResultList();
      
      for(MetaInstituto a : retorno){
         List<MetaInstituto> lista = listMetaInstitutoByInstitutoResumo(a);
         if(lista != null){
           a.setItens(lista);
         }
      }
      
      return retorno;
    }
    
    @Transactional
    public List<MetaInstituto> listMetaInstitutoByInstitutoResumo(MetaInstituto pai) {
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<MetaInstituto> c = cb.createQuery(MetaInstituto.class);
      Root<MetaInstituto> root = c.from(MetaInstituto.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( root.<MetaInstituto>get("pai"), pai  ));
      criteria.add( cb.isNull( root.<Date>get("dataExclusao")  ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      
      /*
       * private Integer id;
  
  private String descricao;
  
  private boolean grupo;
  
  private boolean acao;
  
  private boolean numerica;
  
  private int viewOrder;
       * 
       */
      c.multiselect(
          root.get("id"), 
          root.get("descricao"), 
          root.get("tipoMeta"), 
          root.get("prioridade"), 
          root.get("viewOrder")
          );
      
      
      c.orderBy(cb.asc(root.get("viewOrder")));
      
      List<MetaInstituto> retorno = em.createQuery(c).getResultList();
      
      for(MetaInstituto a : retorno){
        List<MetaInstituto> lista = listMetaInstitutoByInstitutoResumo(a);
         if(lista != null){
           a.setItens(lista);
         }
      }
      
      return retorno;
    }

    @Transactional
  public List<MetaInstituto> listMetaInstitutoByInstitutoRodizio(
      Integer idInstituto, Integer idRodizio) {
    
    BaseInstituto instituto = em.find(BaseInstituto.class, idInstituto);
      Rodizio rodizio = em.find(Rodizio.class, idRodizio);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<MetaInstituto> c = cb.createQuery(MetaInstituto.class);
      Root<MetaInstituto> emp = c.from(MetaInstituto.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , instituto ));
      criteria.add( cb.equal( emp.<BaseInstituto>get("rodizio") , rodizio ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<MetaInstituto> select = c.select(emp);
      
      select.orderBy(cb.asc(emp.get("viewOrder")));
      
      List<MetaInstituto> retorno = em.createQuery(select).getResultList();
      
      for(MetaInstituto a : retorno){
        Hibernate.initialize(a.getItens());
      }
      
      return retorno;
  }
}
