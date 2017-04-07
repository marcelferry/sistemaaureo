package com.concafras.gestao.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.model.view.StatusAtualInstitutoGraphicData;
import com.concafras.gestao.model.view.StatusValor;
import com.concafras.gestao.service.PlanoMetasService;

@Service
public class PlanoMetasServiceImpl implements PlanoMetasService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public PlanoMetas save(PlanoMetas planoMetas) {
        BaseInstituto i = em.find(BaseInstituto.class, planoMetas.getInstituto().getId());
        planoMetas.setInstituto(i);
        em.persist(planoMetas);
        return planoMetas;
    }
    
    @Transactional
    public PlanoMetas update(PlanoMetas entidade) {
      return em.merge(entidade);
    }

    @Transactional
    public List<PlanoMetas> findAll() {
        CriteriaQuery<PlanoMetas> c = em.getCriteriaBuilder().createQuery(PlanoMetas.class);
        c.from(PlanoMetas.class);
        List<PlanoMetas> retorno = em.createQuery(c).getResultList();
        
        for(PlanoMetas a : retorno){
          loadListas(a);
      }
        
        return retorno;
    }
    
    @Transactional
    public List<PlanoMetas> findByInstitutoId(Integer id) {
      
      BaseInstituto base = em.find(BaseInstituto.class, id);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<PlanoMetas> c = cb.createQuery(PlanoMetas.class);
      Root<PlanoMetas> emp = c.from(PlanoMetas.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , base ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<PlanoMetas> select = c.select(emp);
      
      List<PlanoMetas> retorno = em.createQuery(select).getResultList();
      
      for(PlanoMetas a : retorno){
        loadListas(a);
      }
      
      return retorno;
    }
    
    @Transactional
    public List<PlanoMetas> findByEntidadeId(Integer id) {
      
      BaseEntidade base = em.find(BaseEntidade.class, id);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<PlanoMetas> c = cb.createQuery(PlanoMetas.class);
      Root<PlanoMetas> emp = c.from(PlanoMetas.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseEntidade>get("entidade") , base ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<PlanoMetas> select = c.select(emp);
      
      List<PlanoMetas> retorno = em.createQuery(select).getResultList();
      
      for(PlanoMetas a : retorno){
        loadListas(a);
      }
      
      return retorno;
    }
    
    private void loadListas(PlanoMetas a) {
      Hibernate.initialize(a.getAnotacoes());
    }

  @Transactional
    public List<PlanoMetas> findByRodizioId(Integer idRodizio) {
      
      Rodizio rodizio = em.find(Rodizio.class, idRodizio);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<PlanoMetas> c = cb.createQuery(PlanoMetas.class);
      Root<PlanoMetas> emp = c.from(PlanoMetas.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<Rodizio>get("rodizio") , rodizio ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<PlanoMetas> select = c.select(emp);
      
      List<PlanoMetas> retorno = em.createQuery(select).getResultList();
      
      for(PlanoMetas a : retorno){
        loadListas(a);
      }
      
      return retorno;
    }
    
    @Transactional
    public PlanoMetas findByInstitutoIdAndRodizioId(Integer idInstituto, Integer idRodizio) {
      
      BaseInstituto instituto = em.find(BaseInstituto.class, idInstituto);
      Rodizio rodizio = em.find(Rodizio.class, idRodizio);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<PlanoMetas> c = cb.createQuery(PlanoMetas.class);
      Root<PlanoMetas> emp = c.from(PlanoMetas.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , instituto ));
      criteria.add( cb.equal( emp.<BaseInstituto>get("rodizio") , rodizio ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<PlanoMetas> select = c.select(emp);
      
      List<PlanoMetas> retorno = em.createQuery(select).getResultList();
      
      for(PlanoMetas a : retorno){
        loadListas(a);
      }
      
      return retorno.get(0);
    }
    
    @Transactional
    public List<PlanoMetas> findByEntidadeIdAndInstitutoId(Integer idEntidade, Integer idInstituto) {
      
      BaseInstituto base = em.find(BaseInstituto.class, idInstituto);
      BaseEntidade entidade  = em.find(BaseEntidade.class, idEntidade);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<PlanoMetas> c = cb.createQuery(PlanoMetas.class);
      Root<PlanoMetas> emp = c.from(PlanoMetas.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , base ));
      criteria.add( cb.equal( emp.<BaseEntidade>get("entidade") , entidade ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<PlanoMetas> select = c.select(emp);
      
      List<PlanoMetas> retorno = em.createQuery(select).getResultList();
      
      for(PlanoMetas a : retorno){
        loadListas(a);
      }
      
      return retorno;
    }
    
    @Transactional
    public List<PlanoMetas> findByEntidadeIdAndRodizioId(Integer idEntidade, Integer idRodizio) {
      
      Rodizio rodizio = em.find(Rodizio.class, idRodizio);
      BaseEntidade entidade  = em.find(BaseEntidade.class, idEntidade);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<PlanoMetas> c = cb.createQuery(PlanoMetas.class);
      Root<PlanoMetas> emp = c.from(PlanoMetas.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<Rodizio>get("rodizio") , rodizio ));
      criteria.add( cb.equal( emp.<BaseEntidade>get("entidade") , entidade ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<PlanoMetas> select = c.select(emp);
      
      List<PlanoMetas> retorno = em.createQuery(select).getResultList();
      
      for(PlanoMetas a : retorno){
        loadListas(a);
      }
      
      return retorno;
    }
    
    @Transactional
    public PlanoMetas findByEntidadeIdAndInstitutoIdAndRodizioId(Integer idEntidade, Integer idInstituto, Integer idRodizio) {
      
      BaseInstituto instituto = em.find(BaseInstituto.class, idInstituto);
      BaseEntidade entidade  = em.find(BaseEntidade.class, idEntidade);
      Rodizio rodizio = em.find(Rodizio.class, idRodizio);
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<PlanoMetas> c = cb.createQuery(PlanoMetas.class);
      Root<PlanoMetas> emp = c.from(PlanoMetas.class);
      
      List<Predicate> criteria = new ArrayList<Predicate>();
      criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , instituto ));
      criteria.add( cb.equal( emp.<Rodizio>get("rodizio") , rodizio ));
      criteria.add( cb.equal( emp.<BaseEntidade>get("entidade") , entidade ));
      c.where(criteria.toArray(new Predicate[]{}));
      
      CriteriaQuery<PlanoMetas> select = c.select(emp);
      
      List<PlanoMetas> retorno = em.createQuery(select).getResultList();
      
      for(PlanoMetas a : retorno){
        loadListas(a);
      }
      
      if(retorno != null && retorno.size() > 0){
        return retorno.get(0);
      }
      return null;
    }
    
    @Transactional
    public void remove(Integer id) {
      PlanoMetas entidade = em.find(PlanoMetas.class, id);
        if (null != entidade) {
            em.remove(entidade);
        }
    }
    
    @Transactional
    public PlanoMetas findById(Integer id) {
      PlanoMetas plano = em.find(PlanoMetas.class, id);
      
      loadListas(plano);
  
      return plano;
    }
    
    
    @Transactional
    public List<StatusAtualInstitutoGraphicData> getStatusAtualGraphicData(Integer ciclo, Integer entidade){
      Query query2 = em.createNativeQuery(
        "select id, descricao, situacao, total from ( (select i.id, i.descricao, coalesce(hme.situacao, " +
        " CASE WHEN hme.realizado > 0 THEN 'IMPLANTADA' " +
        "            ELSE NULL " +
        "       END, 'NAOINFORMADA') as situacao, count(*) as total " +
        "         from institutos i " +
        "         inner join metas_instituto mi on i.id = mi.idinstituto " + 
        "         left outer join metas_entidade me on me.idmetasinstituto = mi.id " +
        "         left outer join historico_metas_entidade hme on me.id = hme.idmeta " +
        "         where  " +
        "         mi.tipo_meta != 'GRUPO_METAS' and " +
        "         (hme.tipo_situacao = 0 or hme.tipo_situacao is null) and " +
        "         me.identidade = " + entidade + " and hme.idrodizio = " + ciclo + " " +
        "         group by i.id, i.descricao, coalesce(hme.situacao, " +
        " CASE WHEN hme.realizado > 0 THEN 'IMPLANTADA' " +
        "            ELSE NULL " +
        "       END, 'NAOINFORMADA') " +
        "order by i.descricao,  situacao) " +
        "UNION " +
        "(select i.id, i.descricao, 'NAOINFORMADA' as situacao, count(*) as total " +
        "         from institutos i " +
        "         inner join metas_instituto mi on i.id = mi.idinstituto " +
        "         where mi.tipo_meta != 'GRUPO_METAS' and " +
        "     i.id not in (select idinstituto from contrato_metas pme " +
        "     where pme.identidade = " + entidade + " and pme.idrodizio = " + ciclo + ") " +
        "     group by i.id, i.descricao " +
        "order by i.descricao,  situacao) " +
        ") x " +
        "order by descricao,  situacao; ");
      
      StoredProcedureQuery query = em.createStoredProcedureQuery("sp_status_atual_metas");
      query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
      query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
      query.setParameter(2, entidade);
      query.setParameter(3, ciclo);
      //query.execute();
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();

      if( query.hasMoreResults() ){
      
        List<Object[]> result2 = query.getResultList();
        
        StatusAtualInstitutoGraphicData institutoAtual = null;
        Integer idInstituto = null;
        
        for (Object[] resultElement : result2) {
          Integer idAux = (Integer)resultElement[0];
          if( idAux != idInstituto ){
            if(institutoAtual != null)
              result.add(institutoAtual);
            institutoAtual = new StatusAtualInstitutoGraphicData( (Integer)resultElement[0] , (String)resultElement[1] );
            idInstituto = idAux;
            institutoAtual.setStatusValor(new ArrayList<StatusValor>());
          }
          institutoAtual.getStatusValor().add(new StatusValor( (String)resultElement[2],  ((BigInteger)resultElement[3] ).intValue()));
          idAux = null;
        }
        
        if(institutoAtual != null) {
          result.add(institutoAtual);
        }
      }
      
      
      return result;
    }
    
    @Transactional
    public List<StatusAtualInstitutoGraphicData> getStatusPlanejadoGraphicData(Integer ciclo, Integer entidade){
      StoredProcedureQuery query = em.createStoredProcedureQuery("sp_status_planejado");
      query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
      query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
      query.setParameter(2, entidade);
      query.setParameter(3, ciclo);
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();
      
      if( query.hasMoreResults() ){
      
        List<Object[]> result2 = query.getResultList();
        
        StatusAtualInstitutoGraphicData institutoAtual = null;

        Integer idInstituto = null;
        
        for (Object[] resultElement : result2) {
          Integer idAux = (Integer)resultElement[0];
          if( idAux != idInstituto ){
            if(institutoAtual != null)
              result.add(institutoAtual);
            institutoAtual = new StatusAtualInstitutoGraphicData( (Integer)resultElement[0] , (String)resultElement[1] );
            idInstituto = idAux;
            institutoAtual.setStatusValor(new ArrayList<StatusValor>());
          }
          institutoAtual.getStatusValor().add(new StatusValor( (String)resultElement[3],  ((BigInteger)resultElement[4] ).intValue()));
          
          idAux = null;
        }
        if(institutoAtual != null) {
          result.add(institutoAtual);
        }
      
      }
      
      
      return result;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    public List<StatusAtualInstitutoGraphicData> getStatusContratadoGraphicData(Integer ciclo, Integer entidade){
      StoredProcedureQuery query = em.createStoredProcedureQuery("sp_status_contratado");
      query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
      query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
      query.setParameter(2, entidade);
      query.setParameter(3, ciclo);
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();

      if( query.hasMoreResults() ){
        
        List<Object[]> result2 = query.getResultList();
        
        StatusAtualInstitutoGraphicData institutoAtual = null;
        Integer idInstituto = null;
        
        for (Object[] resultElement : result2) {
          Integer idAux = (Integer)resultElement[0];
          if( idAux != idInstituto ){
            if(institutoAtual != null)
              result.add(institutoAtual);
            institutoAtual = new StatusAtualInstitutoGraphicData( (Integer)resultElement[0] , (String)resultElement[1] );
            idInstituto = idAux;
            institutoAtual.setStatusValor(new ArrayList<StatusValor>());
          }
          institutoAtual.getStatusValor().add(new StatusValor( (String)resultElement[2],  ((BigInteger)resultElement[3] ).intValue()));
          
          idAux = null;
        }
        if(institutoAtual != null){
          result.add(institutoAtual);
        }
      
      }
      
      return result;
    }
    
    public List<EntidadeOptionForm> getEntidadesParticipantes(Integer ciclo){
      Query query = em.createNativeQuery(
" select e.id, e.razaoSocial, c.nome, uf.sigla, count(*) " +
"    from entidades e  " +
"    inner join enderecos ende on e.idendereco = ende.id " +
"    inner join cidades c on ende.idcidade = c.id " +
"    inner join estados uf on c.idestado = uf.id " +
"    inner join contrato_metas pme on pme.identidade = e.id " +
"    inner join ciclos_avaliacao ca on ca.id = pme.idrodizio " +
"    where ca.id = "+ ciclo + " " + 
"    group by e.id, e.razaoSocial, c.nome, uf.sigla");
      List<Object[]> result2 = query.getResultList();
      
      List<EntidadeOptionForm> result = new ArrayList<EntidadeOptionForm>();
      
      
      for (Object[] resultElement : result2) {
        EntidadeOptionForm op = new EntidadeOptionForm();
        op.setId((Integer)resultElement[0]);
        op.setRazaoSocial((String) resultElement[1]);
        op.setCidade( (String) resultElement[2]);
        op.setUf( (String) resultElement[3]);
        op.setCount((BigInteger)resultElement[4]);
        result.add(op);
        op = null;
      }
      
      return result;
    }
    
    public List<EntidadeOptionForm> getEntidadesStatus(Integer ciclo){
      Query query = em.createNativeQuery(
          " select e.id, e.razaoSocial, c.nome, uf.sigla,  " +
          "  case " +
          "  when count(*) = 1 and p.id is null then 0 " +
          "  else count(*) " +
          "  end as countador " +
          "     from entidades e   " +
          "     inner join enderecos ende on e.idendereco = ende.id  " +
          "     inner join cidades c on ende.idcidade = c.id  " +
          "     inner join estados uf on c.idestado = uf.id " +
          "     left outer join (  " +
          "       select e.id from entidades e  " +
          "       inner join contrato_metas pme on pme.identidade = e.id  " +
          "       inner join ciclos_avaliacao ca on ca.id = pme.idrodizio  " +
          "       where ca.id = "+ ciclo + "  " +
          "     ) p on p.id = e.id " +
          "     group by e.id, e.razaoSocial, c.nome, uf.sigla, p.id");
      List<Object[]> result2 = query.getResultList();
      
      List<EntidadeOptionForm> result = new ArrayList<EntidadeOptionForm>();
      
      
      for (Object[] resultElement : result2) {
        EntidadeOptionForm op = new EntidadeOptionForm();
        op.setId((Integer)resultElement[0]);
        op.setRazaoSocial((String) resultElement[1]);
        op.setCidade( (String) resultElement[2]);
        op.setUf( (String) resultElement[3]);
        op.setCount((BigInteger)resultElement[4]);
        result.add(op);
        op = null;
      }
      
      return result;
    }
    
    public List<InstitutoOptionForm> getInstitutosContratados(Integer ciclo, Integer entidade){
      Query query = em.createNativeQuery(
"    select  i.id, i.descricao " +
"    from entidades e  " +
"    inner join contrato_metas pme on pme.identidade = e.id " +
"    inner join ciclos_avaliacao ca on ca.id = pme.idrodizio " +
"    inner join institutos i on pme.idinstituto = i.id " +
"    where ca.id = "+ ciclo + " and " + 
"    e.id = "+ entidade + " " + 
"    group by i.id, i.descricao order by i.descricao");
      
      List<Object[]> result2 = query.getResultList();
      
      List<InstitutoOptionForm> result = new ArrayList<InstitutoOptionForm>();
      
      for (Object[] resultElement : result2) {
        InstitutoOptionForm op = new InstitutoOptionForm();
        op.setId((Integer)resultElement[0]);
        op.setDescricao((String) resultElement[1]);
        result.add(op);
        op = null;
      }
      
      return result;
    }
    
    public List<InstitutoOptionForm> getInstitutosPreechidos(Integer ciclo, Integer entidade){
      Query query = em.createNativeQuery(
          "    select  i.id, i.descricao " +
              "    from entidades e  " +
              "    inner join contrato_metas pme on pme.identidade = e.id " +
              "    inner join ciclos_avaliacao ca on ca.id = pme.idrodizio " +
              "    inner join institutos i on pme.idinstituto = i.id " +
              "    where ca.id = "+ ciclo + " and " + 
              "    e.id = "+ entidade + " " + 
          "    group by i.id, i.descricao order by i.descricao");
      
      List<Object[]> result2 = query.getResultList();
      
      List<InstitutoOptionForm> result = new ArrayList<InstitutoOptionForm>();
      
      for (Object[] resultElement : result2) {
        InstitutoOptionForm op = new InstitutoOptionForm();
        op.setId((Integer)resultElement[0]);
        op.setDescricao((String) resultElement[1]);
        result.add(op);
        op = null;
      }
      
      return result;
    }
    
    public List<InstitutoOptionForm> getInstitutosStatus(Integer ciclo, Integer entidade){
      Query query = em.createNativeQuery(
          "    select  i.id, i.descricao, a.pme_id, " + 
          "        case " + 
          "       when a.pme_id is null then 'NAO CONTRATADO' " + 
          "        when a.pme_id is not null and a.validado = false then  'NAO VALIDADO' " + 
          "        when a.pme_id is not null and a.validado = true then  'VALIDADO' " + 
          "        end as status " + 
          "        from institutos i  " + 
          "        left outer join (  " + 
          "          select  i.id, i.descricao, pme.id as pme_id, pme.validado " + 
          "          from entidades e  " + 
          "          inner join contrato_metas pme on pme.identidade = e.id " + 
          "          inner join ciclos_avaliacao ca on ca.id = pme.idrodizio " + 
          "          inner join institutos i on pme.idinstituto = i.id " + 
          "    where ca.id = "+ ciclo + " and " + 
          "    e.id = "+ entidade + " " + 
          "         group by i.id, i.descricao, pme.id " + 
          "        ) a on i.id = a.id "
          + " order by descricao");
      
      List<Object[]> result2 = query.getResultList();
      
      List<InstitutoOptionForm> result = new ArrayList<InstitutoOptionForm>();
      
      for (Object[] resultElement : result2) {
        InstitutoOptionForm op = new InstitutoOptionForm();
        op.setId((Integer)resultElement[0]);
        op.setDescricao((String) resultElement[1]);
        op.setIdPlano((Integer)resultElement[2]);
        op.setStatus((String)resultElement[3]);
        result.add(op);
        op = null;
      }
      
      return result;
    }
    
    @Transactional
    public List<StatusAtualInstitutoGraphicData> getStatusContratadoGeralData(Integer ciclo, Integer instituto){
      StoredProcedureQuery query = em.createStoredProcedureQuery("sp_status_contratado_geral");
      query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
      query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
      if(instituto != null)
        query.setParameter(2, instituto);
      else 
        query.setParameter(2, 0);
        
      if(ciclo != null) 
        query.setParameter(3, ciclo);
      else 
        query.setParameter(3, 0);
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();

      if( query.hasMoreResults() ){
      
        List<Object[]> result2 = query.getResultList();
        
        
        StatusAtualInstitutoGraphicData institutoAtual = new StatusAtualInstitutoGraphicData(0, "Todos");
        institutoAtual.setStatusValor(new ArrayList<StatusValor>());
        
        for (Object[] resultElement : result2) {
          institutoAtual.getStatusValor().add(new StatusValor( (String)resultElement[0],  ((BigInteger)resultElement[1] ).intValue()));
        }
        
        result.add(institutoAtual);
      
      }
      
      return result;
    }
    
    @Transactional
    public List<ResumoMetaEntidade> getListaContratadoGeralData(Integer ciclo, Integer regiao, Integer entidade, Integer instituto, String status){
      StoredProcedureQuery query = em.createStoredProcedureQuery("sp_status_contratado_geral_lista");
      query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
      query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);
      
      if(entidade != null)
        query.setParameter(2, entidade);
      else 
        query.setParameter(2, 0);

      if(regiao != null)
        query.setParameter(3, regiao);
      else 
        query.setParameter(3, 0);
        
      if(instituto != null) 
        query.setParameter(4, instituto);
      else 
        query.setParameter(4, 0);
      
      if(status != null) 
        query.setParameter(5, status);
      else 
        query.setParameter(5, "");
      
      if(ciclo != null) 
        query.setParameter(6, ciclo);
      else 
        query.setParameter(6, 0);
      
      List<ResumoMetaEntidade> result = new ArrayList<ResumoMetaEntidade>();

      if( query.hasMoreResults() ){
              
        List<Object[]> result2 = query.getResultList();
        
        ResumoMetaEntidade institutoAtual = null;
        
        for (Object[] resultElement : result2) {
          institutoAtual = new ResumoMetaEntidade();
          institutoAtual.setIdEntidade((Integer)resultElement[0]);
          institutoAtual.setEntidade((String)resultElement[1]);
          institutoAtual.setCidade((String)resultElement[2]);
          institutoAtual.setUf((String)resultElement[3]);
          institutoAtual.setIdCiclo((Integer)resultElement[4]);
          institutoAtual.setCiclo((String)resultElement[5]);
          institutoAtual.setIdInstituto((Integer)resultElement[6]);
          institutoAtual.setInstituto((String)resultElement[7]);
          institutoAtual.setIdMeta((Integer)resultElement[8]);
          institutoAtual.setMeta((String)resultElement[9]);
          institutoAtual.setAcao(TipoSituacaoMeta.values()[(Integer)resultElement[10]].toString());
          institutoAtual.setPrevisao((Date)resultElement[11]);
          institutoAtual.setConclusao((Date)resultElement[12]);
          BigDecimal previsto = (BigDecimal)resultElement[13];
          if(previsto != null){
            institutoAtual.setPrevisto(previsto.intValue());
          }
          BigDecimal realizado = (BigDecimal)resultElement[14];
          if(realizado != null){
            institutoAtual.setRealizado(realizado.intValue());
          }
          institutoAtual.setSituacao((String)resultElement[15]);
          institutoAtual.setIdRegiao((Integer)resultElement[16]);
          institutoAtual.setRegiao((String)resultElement[17]);
          institutoAtual.setStatus((String)resultElement[18]);
          institutoAtual.setTipoMeta((String)resultElement[19]);
          
          result.add(institutoAtual);
        }
      
      }
      
      return result;
    }
    
    @Transactional
    public List<StatusAtualInstitutoGraphicData> getStatusContratadoPorRegiaoData(Integer ciclo, Integer instituto ){
      StoredProcedureQuery query = em.createStoredProcedureQuery("sp_status_contratado_regiao");
      query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
      query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
      
      if(instituto != null)
        query.setParameter(2, instituto);
      else 
        query.setParameter(2, 0);
        
      if(ciclo != null) 
        query.setParameter(3, ciclo);
      else 
        query.setParameter(3, 0);
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();
      
      if( query.hasMoreResults() ){
        
        List<Object[]> result2 = query.getResultList();
        
        StatusAtualInstitutoGraphicData institutoAtual = null;
        Integer idInstituto = null;
        
        for (Object[] resultElement : result2) {
          Integer idAux = (Integer)resultElement[0];
          if( idAux != idInstituto ){
            if(institutoAtual != null)
              result.add(institutoAtual);
            institutoAtual = new StatusAtualInstitutoGraphicData( (Integer)resultElement[0] , (String)resultElement[1] );
            idInstituto = idAux;
            institutoAtual.setStatusValor(new ArrayList<StatusValor>());
          }
          institutoAtual.getStatusValor().add(new StatusValor( (String)resultElement[2],  ((BigInteger)resultElement[3] ).intValue()));
          
          idAux = null;
        }
        if(institutoAtual != null){
          result.add(institutoAtual);
        }
      
      }
      
      
      return result;
    }
    
    @Transactional
    public List<StatusAtualInstitutoGraphicData> getStatusContratadoPorInstitutoData(Integer ciclo, Integer entidade){
      StoredProcedureQuery query = em.createStoredProcedureQuery("sp_status_contratado_instituto");
      query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
      query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
      query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
      if(entidade != null)
        query.setParameter(2, entidade);
      else 
        query.setParameter(2, 0);
        
      if(ciclo != null) 
        query.setParameter(3, ciclo);
      else 
        query.setParameter(3, 0);
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();

      if( query.hasMoreResults() ){
      
        List<Object[]> result2 = query.getResultList();
        
        StatusAtualInstitutoGraphicData institutoAtual = null;

        Integer idInstituto = null;
        
        for (Object[] resultElement : result2) {
          Integer idAux = (Integer)resultElement[0];
          if( idAux != idInstituto ){
            if(institutoAtual != null)
              result.add(institutoAtual);
            institutoAtual = new StatusAtualInstitutoGraphicData( (Integer)resultElement[0] , (String)resultElement[1] );
            idInstituto = idAux;
            institutoAtual.setStatusValor(new ArrayList<StatusValor>());
          }
          institutoAtual.getStatusValor().add(new StatusValor( (String)resultElement[2],  ((BigInteger)resultElement[3] ).intValue()));
          
          idAux = null;
        }
        if(institutoAtual != null)
          result.add(institutoAtual);
      }
      
      return result;
    }
    
    public List<StatusAtualInstitutoGraphicData> getStatusValidacaoGeralData(Integer ciclo){
      Query query = em.createNativeQuery("select status, count(*) " + 
          "from "
          + "( select  i.id, i.descricao, a.pme_id,    "
          + "case "
          + " when a.pme_id is null then 'NAO PREENCHIDOS' "
          + " when a.pme_id is not null and a.validado = false then  'NAO VALIDADO' "
          + " when a.pme_id is not null and a.validado = true then  'VALIDADO' "
          + "end as status from ( "
          + "   select e.id, e.razaoSocial, i.id as iid, i.descricao "
          + "     from entidades e, institutos i, entidade_ciclo ce, ciclos_avaliacao ca "
          + "     where e.id = ce.identidade  "
          + "     and ce.idciclo = ca.id "
          + "     and ca.ciclo = " + ciclo  + " " 
          + " ) i left outer join ( "
          + "   select e.id, e.razaoSocial, i.id as iid, i.descricao, pme.id as pme_id, pme.validado "
          + "     from entidades e "
          + "       inner join contrato_metas pme on pme.identidade = e.id "
          + "       inner join ciclos_avaliacao ca on ca.id = pme.idrodizio "
          + "       inner join institutos i on pme.idinstituto = i.id "
          + "     where ca.id = " + ciclo  + " " 
          + "     group by e.id, e.razaoSocial, i.id, i.descricao, pme.id "
          + ") a on i.id = a.id and i.iid = a.iid  order by id, descricao ) g "
          + "group by status");
      
      List<Object[]> result2 = query.getResultList();
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();
      
      StatusAtualInstitutoGraphicData institutoAtual = new StatusAtualInstitutoGraphicData(0, "Todos");
      institutoAtual.setStatusValor(new ArrayList<StatusValor>());
      
      for (Object[] resultElement : result2) {
        institutoAtual.getStatusValor().add(new StatusValor( (String)resultElement[0],  ((BigInteger)resultElement[1] ).intValue()));
      }
      
      result.add(institutoAtual);
      
      return result;
    }
    
    @Transactional
    public List<EntidadeOptionForm> listRangeEntidadesCiclo(Integer ciclo,
        String name, String sortCol,
        String sortDir, int startRange, int maxRows){
      
      String sql =  " select e.id, e.razaoSocial, c.nome, uf.sigla, count(*) " +
          "    from entidades e  " +
          "    inner join enderecos ende on e.idendereco = ende.id " +
          "    inner join cidades c on ende.idcidade = c.id " +
          "    inner join estados uf on c.idestado = uf.id " +
          "    inner join contrato_metas pme on pme.identidade = e.id " +
          "    inner join ciclos_avaliacao ca on ca.id = pme.idrodizio " +
          "    where ca.id = "+ ciclo + " ";
      if(name != null && name.length() > 0){
        sql += " and lower(e.razaoSocial) like lower('%" + name + "%') ";
      }
      sql +=    " group by e.id, e.razaoSocial, c.nome, uf.sigla";
      
      if(sortCol == null){
        sql +=    " order by e.razaoSocial " + sortDir;
      } else if(sortCol.equals("nome")){
        sql +=    " order by e.razaoSocial " + sortDir;
      } else if(sortCol.equals("cidade")){
          sql +=    " order by c.nome " + sortDir;
      } else if(sortCol.equals("sigla")){
        sql +=    " order by uf.sigla " + sortDir;
      }
      
      Query query = em.createNativeQuery(sql);
      
      query.setFirstResult(startRange);
      query.setMaxResults(maxRows);
      
      List<Object[]> result2 = query.getResultList();
      
      List<EntidadeOptionForm> result = new ArrayList<EntidadeOptionForm>();
      
      
      for (Object[] resultElement : result2) {
        EntidadeOptionForm op = new EntidadeOptionForm();
        op.setId((Integer)resultElement[0]);
        op.setRazaoSocial((String) resultElement[1]);
        op.setCidade( (String) resultElement[2]);
        op.setUf( (String) resultElement[3]);
        op.setCount((BigInteger)resultElement[4]);
        result.add(op);
        op = null;
      }
      
      return result;
    }
    
    
    @Transactional
    public Long countListRangeEntidadesCiclo(Integer ciclo, String name) {
      String sql =  "select count(*) from ( select e.id, e.razaoSocial, c.nome, uf.sigla, count(*) " +
          "    from entidades e  " +
          "    inner join enderecos ende on e.idendereco = ende.id " +
          "    inner join cidades c on ende.idcidade = c.id " +
          "    inner join estados uf on c.idestado = uf.id " +
          "    inner join contrato_metas pme on pme.identidade = e.id " +
          "    inner join ciclos_avaliacao ca on ca.id = pme.idrodizio " +
          "    where ca.id = "+ ciclo + " ";
      if(name != null && name.length() > 0){
        sql += " and lower(e.razaoSocial) like lower('%" + name + "%') ";
      }
      sql +=    " group by e.id, e.razaoSocial, c.nome, uf.sigla) x";
      
      Query query = em.createNativeQuery(sql);
      
      BigInteger result = (BigInteger) query.getSingleResult();
     
      return result.longValue();
          
    }
    
}
