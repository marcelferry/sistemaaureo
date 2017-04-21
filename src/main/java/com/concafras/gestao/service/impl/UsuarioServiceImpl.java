package com.concafras.gestao.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.DirigenteNacional;
import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Presidente;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.Telefone;
import com.concafras.gestao.model.security.AlcadaUsuario;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.DirigenteNacionalService;
import com.concafras.gestao.service.FacilitadorService;
import com.concafras.gestao.service.PresidenteService;
import com.concafras.gestao.service.UsuarioService;
import javax.persistence.criteria.Expression;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @PersistenceContext
  EntityManager em;
  
  @Autowired
  private FacilitadorService facilitadorService;
  
  @Autowired
  private PresidenteService presidenteService;
  
  @Autowired
  private DirigenteNacionalService dirigenteNacionalService;

  @Transactional
  public void save(Usuario usuario) {
    em.persist(usuario);
  }
  
  @Transactional
  public void update(Usuario usuario) {
    Usuario usuarioEntity = em.find(Usuario.class, usuario.getId());
    
    if (null != usuarioEntity) {
      List<Integer> ids = usuario.getRolesIds();
      if(ids != null && !ids.isEmpty()){ 
        Set<AlcadaUsuario> alcadas = usuarioEntity.getUserRoles();
        alcadas.clear();
        for (Integer integer : ids) {
          if(integer.equals(1) || integer.equals(2) || integer.equals(4) || integer.equals(6)){
            AlcadaUsuario alcada = new AlcadaUsuario();
            alcada.setId(integer);
            alcadas.add(alcada);
          }
        }
      }
      if(usuario.getPassword() != null){
        if(usuario.getPassword().equals(usuario.getConfirmPassword())){
          usuarioEntity.setPassword(usuario.getPassword());
        }
      }
      usuarioEntity.setUsername(usuario.getUsername());
      usuarioEntity.setPessoa(usuario.getPessoa());
      usuarioEntity.setPasswordExpired(usuario.isPasswordExpired());
      usuarioEntity.setLastLogin(usuario.getLastLogin());
      em.merge(usuarioEntity);
    }
  }

  @Transactional
  public List<Usuario> findAll() {
    CriteriaQuery<Usuario> c = em.getCriteriaBuilder().createQuery(
        Usuario.class);
    c.from(Usuario.class);
   
    return em.createQuery(c).getResultList();
  }
  
  @Transactional
  public void remove(Integer id) {
    Usuario person = em.find(Usuario.class, id);
    if (null != person) {
      em.remove(person);
    }
  }

  @Transactional
  public Usuario findById(Integer id) {
    Usuario person = em.find(Usuario.class, id);
    
    List<Integer> ids = new ArrayList<Integer>();
    for (AlcadaUsuario usuarioEmpresaPermissao : person.getUserRoles()) {
      ids.add(usuarioEmpresaPermissao.getId());
    }
    person.setRolesIds(ids);
    
    return person;
  }

  @Transactional
  public Usuario findByPessoa(Pessoa pessoa) {
    try {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Usuario> c = cb.createQuery(Usuario.class);
      Root<Usuario> emp = c.from(Usuario.class);

      if (pessoa != null) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        criteria.add(cb.equal(emp.<Pessoa> get("pessoa"), pessoa));
        c.where(criteria.get(0));
      }

      Usuario person = em.createQuery(c).getSingleResult();
      
      List<Integer> ids = new ArrayList<Integer>();
      for (AlcadaUsuario usuarioEmpresaPermissao : person.getUserRoles()) {
        ids.add(usuarioEmpresaPermissao.getId());
      }
      person.setRolesIds(ids);
      
      return person;
      
    } catch (javax.persistence.NoResultException ex) {
      return null;
    }
  }

  @Transactional
  public Usuario findByUsername(String username) {
    try {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Usuario> c = cb.createQuery(Usuario.class);
      Root<Usuario> emp = c.from(Usuario.class);
      Join<Usuario, Pessoa> pessoa = emp.join("pessoa", JoinType.LEFT);
      Join<Pessoa, ContatoInternet> emails = pessoa.join("emails", JoinType.LEFT);
      Join<Pessoa, Telefone> telefones = pessoa.join("telefones", JoinType.LEFT);
      

      if (username != null && !username.trim().equals("")) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        
        Expression<String> campoTelefone = cb.concat(telefones.<String>get("ddd"), telefones.<String>get("numero"));
        
        criteria.add( cb.or( 
              cb.equal(cb.lower(emp.<String> get("username")), username.toLowerCase().trim()),
              cb.equal(cb.lower(pessoa.<String> get("cpf")), username.toLowerCase().trim()),
              cb.equal(cb.lower(emails.<String>get("contato")), username.toLowerCase().trim()),
              cb.equal(cb.lower(campoTelefone), username.toLowerCase().trim())
            )
        );
        c.where(criteria.get(0));
      }

      List<Usuario> usuarios = em.createQuery(c).getResultList();
      if (usuarios != null && usuarios.size() > 0) {
        Usuario person = usuarios.get(0);
        
        List<Integer> ids = new ArrayList<Integer>();
        for (AlcadaUsuario usuarioEmpresaPermissao : person.getUserRoles()) {
          ids.add(usuarioEmpresaPermissao.getId());
        }
        person.setRolesIds(ids);
        
        return person;
      } else {
        return null;
      }
    } catch (javax.persistence.NoResultException ex) {
      return null;
    }
  }

  @Override
  public boolean isFacilitador(Pessoa pessoa, Rodizio ciclo) {

    List<Facilitador> listaFacilitadores = facilitadorService.getFacilitador(pessoa, ciclo);

    if (listaFacilitadores != null && listaFacilitadores.size() > 0) {
      return true;
    }
    return false;
    
  }

  @Override
  public boolean isPresidente(Pessoa pessoa) {

    List<Presidente> listaPresidentes = presidenteService.getPresidente(pessoa);

    if (listaPresidentes != null && listaPresidentes.size() > 0) {
      boolean presidenteAtivo = false;
      List<Presidente> presidenciasAtuais = new ArrayList<Presidente>();

      for (Presidente presidente : listaPresidentes) {
        if (presidente.isAtivo()) {
          presidenteAtivo = true;
          presidenciasAtuais.add(presidente);
        }
      }

      if (presidenteAtivo) {
        return true;
      }
    }
    return false;
    
  }

  @Override
  public boolean isDirigente(Pessoa pessoa) {
  //DIRIGENTE?
    List<DirigenteNacional> listaDirigentes = dirigenteNacionalService.findByDirigente(pessoa);
    
    if(listaDirigentes != null && listaDirigentes.size() > 0) {
      return true;
    }
    return false;
  }
}
