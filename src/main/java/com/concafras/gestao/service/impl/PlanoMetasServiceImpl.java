package com.concafras.gestao.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public void save(PlanoMetas planoMetas) {
      BaseInstituto i = em.find(BaseInstituto.class, planoMetas.getInstituto().getId());
      planoMetas.setInstituto(i);
        em.persist(planoMetas);
    }
    
    @Transactional
    public void update(PlanoMetas entidade) {
      em.merge(entidade);
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
      Hibernate.initialize(a.getListaAnotacoes());
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
    
    
    
    public List<StatusAtualInstitutoGraphicData> getStatusAtualGraphicData(Integer ciclo, Integer entidade){
      Query query = em.createNativeQuery(
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
      List<Object[]> result2 = query.getResultList();
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();
      
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
      
      
      return result;
    }
    
    public List<StatusAtualInstitutoGraphicData> getStatusPlanejadoGraphicData(Integer ciclo, Integer entidade){
      Query query = em.createNativeQuery(
          "select i.id, i.descricao, " +
              "CASE " +
                "WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN " +
                " hme.situacao " +
                "ELSE " +
                  "CASE " +
                    "WHEN hme.tipo_situacao = 1 THEN " +
                    " hme.situacao " +
                    "ELSE " +
                    //"WHEN hme.tipo_situacao is null THEN " +
                    " 'NAOINFORMADA' " +
                    "END " +
                "END, count(*) as total " +
              "from institutos i  " +
              "inner join metas_entidade me on me.idinstituto = i.id  " +
              "inner join historico_metas_entidade hme on me.id = hme.idmeta " +
              "where me.tipo_meta != 'GRUPO_METAS' and " +
                "me.identidade = " + entidade + " and " +
                "hme.idrodizio = " + ciclo + " and " +
                "hme.tipo_situacao <> 2 " +
              "group by i.id, i.descricao, hme.tipo_situacao,  " +
                "CASE " +
                "WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN " +
                  " hme.situacao " +
                  "ELSE " +
                    "CASE " +
                      "WHEN hme.tipo_situacao = 1 THEN " +
                      " hme.situacao " +
                      "ELSE " +
                      //"WHEN hme.tipo_situacao is null THEN " +
                      " 'NAOINFORMADA' " +
                      "END " +
                "END " +
              "order by i.descricao;");
      List<Object[]> result2 = query.getResultList();
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();
      
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
      
      
      return result;
    }
    
    public List<StatusAtualInstitutoGraphicData> getStatusContratadoGraphicData(Integer ciclo, Integer entidade){
      Query query = em.createNativeQuery(
          "select i.id, i.descricao, " +
              "CASE " +
                "WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN " +
                " hme.situacao " +
                "ELSE " +
                  "CASE " +
                    "WHEN hme.tipo_situacao = 2 THEN " +
                    " hme.situacao " +
                    "ELSE " +
                    //"WHEN hme.tipo_situacao is null THEN " +
                    " 'NAOINFORMADA' " +
                    "END " +
                "END, count(*) as total " +
              "from historico_metas_entidade hme " +
              "inner join institutos i on hme.idinstituto = i.id  " +
              "inner join metas_entidade me on me.id = hme.idmeta " +
              "where me.tipo_meta != 'GRUPO_METAS' and " +
                "pme.identidade = " + entidade + " and " +
                "pme.idrodizio = " + ciclo + " and " +
                "hme.tipo_situacao <> 1 " +
              "group by i.id, i.descricao, hme.tipo_situacao,  " +
                "CASE " +
                "WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN " +
                  " hme.situacao " +
                  "ELSE " +
                    "CASE " +
                      "WHEN hme.tipo_situacao = 2 THEN " +
                      " hme.situacao " +
                      "ELSE " +
                      //"WHEN hme.tipo_situacao is null THEN " +
                      " 'NAOINFORMADA' " +
                      "END " +
                "END " +
              "order by i.descricao;");
      List<Object[]> result2 = query.getResultList();
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();
      
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
    
    public List<StatusAtualInstitutoGraphicData> getStatusContratadoGeralData(Integer ciclo, Integer instituto){
      Query query = em.createNativeQuery("select status, count(*) " + 
          "    from ( " + 
          "      select e.id, e.razaosocial, c.nome, uf.sigla, r.ciclo, i.descricao, m.id, m.descricao, " + 
          "      hme.tipo_situacao, hme.previsao, hme.conclusao, hme.previsto, hme.realizado, hme.situacao,  " + 
          "    case  " + 
          "      when uf.id >= 10 and uf.id < 20 then 10 " + 
          "      when uf.id >= 20 and uf.id < 30 then 20 " + 
          "      when uf.id >= 30 and uf.id < 40 then 30 " + 
          "      when uf.id >= 40 and uf.id < 50 then 40 " + 
          "      when uf.id >= 50 and uf.id < 60 then 50 " + 
          "    end as idregiao, " + 
          "    case  " + 
          "      when uf.id >= 10 and uf.id < 20 then 'NORTE' " + 
          "      when uf.id >= 20 and uf.id < 30 then 'NORDESTE' " + 
          "      when uf.id >= 30 and uf.id < 40 then 'SUDESTE' " + 
          "      when uf.id >= 40 and uf.id < 50 then 'SUL' " + 
          "      when uf.id >= 50 and uf.id < 60 then 'CENTRO-OESTE' " + 
          "    end as regiao, " + 
          "      case  " + 
          "      when hme.previsao is null then 'INDEFINIDO'  " + 
          "      when cast(hme.previsao as date) < cast(date_trunc('month', current_date) as date) then 'ATRASADO' " + 
          "      when cast(hme.previsao as date) > cast(date_trunc('month', current_date) as date) then 'A VENCER' " + 
          "      when cast(hme.previsao as date) = cast(date_trunc('month', current_date) as date) then 'NO PRAZO' " + 
          "      end as status " + 
          "      from entidades e " + 
          "      inner join enderecos ende on e.idendereco = ende.id " + 
          "      inner join cidades c on ende.idcidade = c.id " + 
          "      inner join estados uf on c.idestado = uf.id " + 
          "      inner join metas_entidade m on m.identidade = e.id " + 
          "      inner join ("
          + " select idmeta, idrodizio, max(tipo_situacao) as tipo_situacao from historico_metas_entidade "
          //+  ( ciclo != null ? " where idrodizio = " + ciclo : " " )
          + " group by idmeta, idrodizio "
          + ") hmelast on m.id = hmelast.idmeta " +
          "      inner join historico_metas_entidade hme on hmelast.idmeta = hme.idmeta and hmelast.idrodizio = hme.idrodizio and hmelast.tipo_situacao = hme.tipo_situacao" +
          "      inner join institutos i on m.idinstituto = i.id " + 
          "      inner join ciclos_avaliacao r on r.id = hme.idrodizio " + 
          "      where  " + 
          "      r.id = " + ciclo  + " and " +
          "      ( hme.tipo_situacao = 1 or hme.tipo_situacao = 2 or hme.tipo_situacao = 3 )  and " + 
          "      ( hme.situacao = 'PLANEJADA' or hme.situacao = 'REPLANEJADA' ) and " + 
          (instituto != null ? " i.id = " + instituto + " and " : "") + 
          //"    and   m.tipo_meta <> 'META_QUANTITATIVA' " + 
          "      1 = 1 order by hme.previsao, e.id, r.ciclo, m.id, hme.tipo_situacao " + 
          "    ) relstatus " + 
          "    group by status ");
      
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
    
    
    public List<ResumoMetaEntidade> getListaContratadoGeralData(Integer ciclo, Integer regiao, Integer entidade, Integer instituto, String status){
     String sql = "select * from (select distinct e.id as eid, e.razaosocial, c.nome, uf.sigla, r.id as cid, r.ciclo, i.id as iid, i.descricao as instituto, m.id as mid, md.descricao as meta, " + 
          "      hme.tipo_situacao, hme.previsao, hme.conclusao, hme.previsto, hme.realizado, hme.situacao,  " + 
          "    uf.idregiao, " + 
          "    case  " + 
          "      when uf.idregiao =  10 then 'NORTE' " + 
          "      when uf.idregiao =  20 then 'NORDESTE' " + 
          "      when uf.idregiao =  30 then 'SUDESTE' " + 
          "      when uf.idregiao =  40 then 'SUL' " + 
          "      when uf.idregiao =  50 then 'CENTRO-OESTE' " + 
          "    end as regiao, " + 
          "      case  " + 
          "      when hme.previsao is null then 'INDEFINIDO'  " + 
          "      when cast(hme.previsao as date) < cast(date_trunc('month', current_date) as date) then 'ATRASADO' " + 
          "      when cast(hme.previsao as date) > cast(date_trunc('month', current_date) as date) then 'A VENCER' " + 
          "      when cast(hme.previsao as date) = cast(date_trunc('month', current_date) as date) then 'NO PRAZO' " + 
          "      end as status, " +
          "      m.tipo_meta" + 
          "      from entidades e " + 
          "      inner join enderecos ende on e.idendereco = ende.id " + 
          "      inner join cidades c on ende.idcidade = c.id " + 
          "      inner join estados uf on c.idestado = uf.id " + 
          "      inner join metas_entidade m on m.identidade = e.id " + 
          "      inner join ("
          + " select idmeta, idrodizio, max(tipo_situacao) as tipo_situacao from historico_metas_entidade "
          //+  ( ciclo != null ? " where idrodizio = " + ciclo : " " )
          + " group by idmeta, idrodizio "
          + ") hmelast on m.id = hmelast.idmeta " +
          "      inner join historico_metas_entidade hme on hmelast.idmeta = hme.idmeta and hmelast.idrodizio = hme.idrodizio and hmelast.tipo_situacao = hme.tipo_situacao" +
          "      inner join institutos i on m.idinstituto = i.id " + 
          "      inner join ciclos_avaliacao r on r.id = hme.idrodizio " +
          "      inner join (select path_info[1] as id, string_agg(descricao, ' => ') as descricao from ( " + 
          "      with recursive tree(id, descricao, idpai, level, path_info) as ( " + 
          "          select n.id, n.descricao, n.idpai, 1 as level, array[id] as path_info " + 
          "          from metas_instituto n " + 
          "          union all " + 
          "          select n.id, n.descricao, n.idpai, t.level + 1, t.path_info||n.id " + 
          "          from metas_instituto n " + 
          "          join tree t on (n.id = t.idpai) " + 
          "      ) " + 
          "      select * " + 
          "      from tree " + 
          "      order by path_info desc) a " + 
          "      group by path_info[1]) md  on md.id = m.idmetasinstituto  " + 
          "      ) x  " + 
          "      where  " + 
          "      ( tipo_situacao = 1 or tipo_situacao = 2 or tipo_situacao = 3 )  and " + 
          "      ( situacao = 'PLANEJADA' or situacao = 'REPLANEJADA' ) and ";
          if (ciclo != null){
            sql+= "cid = " + ciclo + " and "; 
          }
          if (regiao != null){
            sql+= " idregiao = " + regiao + " and ";
          }
          if (entidade != null){
            sql+= "eid = " + entidade + " and "; 
          }
          if (instituto != null){
            sql+= "iid = '" + instituto + "' and "; 
          }
          if (status != null){
            sql+= " status = '" + status + "' and ";
          }
          //sql+= "     tipo_meta <> 'META_QUANTITATIVA'  and ";
          sql+= "  1=1    order by previsao, eid, ciclo, mid, tipo_situacao ";
          
          Query query = em.createNativeQuery(sql);
      
      List<Object[]> result2 = query.getResultList();
      
      List<ResumoMetaEntidade> result = new ArrayList<ResumoMetaEntidade>();
      
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
      
      
      return result;
    }
    
    public List<StatusAtualInstitutoGraphicData> getStatusContratadoPorRegiaoData(Integer ciclo, Integer instituto ){
      Query query = em.createNativeQuery("select idregiao, regiao, status, count(*) " + 
          "    from ( " + 
          "      select e.id, e.razaosocial, c.nome, uf.sigla, r.ciclo, i.id as idInstituto, i.descricao as instituto, "  + 
          "      m.id, m.descricao, hme.tipo_situacao, hme.previsao, hme.conclusao, hme.previsto, hme.realizado, hme.situacao,  " + 
          "    case  " + 
          "      when uf.id >= 10 and uf.id < 20 then 10 " + 
          "      when uf.id >= 20 and uf.id < 30 then 20 " + 
          "      when uf.id >= 30 and uf.id < 40 then 30 " + 
          "      when uf.id >= 40 and uf.id < 50 then 40 " + 
          "      when uf.id >= 50 and uf.id < 60 then 50 " + 
          "    end as idregiao, " + 
          "    case  " + 
          "      when uf.id >= 10 and uf.id < 20 then 'NORTE' " + 
          "      when uf.id >= 20 and uf.id < 30 then 'NORDESTE' " + 
          "      when uf.id >= 30 and uf.id < 40 then 'SUDESTE' " + 
          "      when uf.id >= 40 and uf.id < 50 then 'SUL' " + 
          "      when uf.id >= 50 and uf.id < 60 then 'CENTRO-OESTE' " + 
          "    end as regiao, " + 
          "      case  " + 
          "       when hme.previsao is null then 'INDEFINIDO'  " + 
          "       when cast(hme.previsao as date) < cast(date_trunc('month', current_date) as date) then 'ATRASADO' " + 
          "       when cast(hme.previsao as date) > cast(date_trunc('month', current_date) as date) then 'A VENCER' " + 
          "       when cast(hme.previsao as date) = cast(date_trunc('month', current_date) as date) then 'NO PRAZO' " + 
          "      end as status " + 
          "      from entidades e " + 
          "      inner join enderecos ende on e.idendereco = ende.id " + 
          "      inner join cidades c on ende.idcidade = c.id " + 
          "      inner join estados uf on c.idestado = uf.id " + 
          "      inner join metas_entidade m on m.identidade = e.id " + 
          "      inner join ("
          + " select idmeta, idrodizio, max(tipo_situacao) as tipo_situacao from historico_metas_entidade "
          //+  ( ciclo != null ? " where idrodizio = " + ciclo : " " )
          + " group by idmeta, idrodizio "
          + ") hmelast on m.id = hmelast.idmeta " +
          "      inner join historico_metas_entidade hme on hmelast.idmeta = hme.idmeta and hmelast.idrodizio = hme.idrodizio and hmelast.tipo_situacao = hme.tipo_situacao" +
          "      inner join institutos i on m.idinstituto = i.id " + 
          "      inner join ciclos_avaliacao r on r.id = hme.idrodizio " + 
          "      where  " + 
          "      r.id = " + ciclo  + " and " +
          "      ( hme.tipo_situacao = 1 or hme.tipo_situacao = 2 or hme.tipo_situacao = 3 )  and " + 
          "      ( hme.situacao = 'PLANEJADA' or hme.situacao = 'REPLANEJADA' ) and " + 
          (instituto != null ? " i.id = " + instituto + " and " : "") + 
          "      m.tipo_meta <> 'META_QUANTITATIVA' and " + 
          "      1=1 order by hme.previsao, e.id, r.ciclo, hme.id, hme.tipo_situacao " + 
          "    ) relstatus " + 
          "    group by idregiao, regiao, status " + 
          "    order by regiao, status");
      
      List<Object[]> result2 = query.getResultList();
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();
      
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
      
      
      return result;
    }
    
    public List<StatusAtualInstitutoGraphicData> getStatusContratadoPorInstitutoData(Integer ciclo, Integer entidade){
      Query query = em.createNativeQuery("select idinstituto, instituto, status, count(*) " + 
          "    from ( " + 
          "      select e.id, e.razaosocial, c.nome, uf.sigla, r.ciclo, i.id as idInstituto, i.descricao as instituto,"  + 
          "      m.id, m.descricao, hme.tipo_situacao, hme.previsao, hme.conclusao, hme.previsto, hme.realizado, hme.situacao,  " + 
          "    case  " + 
          "      when uf.id >= 10 and uf.id < 20 then 10 " + 
          "      when uf.id >= 20 and uf.id < 30 then 20 " + 
          "      when uf.id >= 30 and uf.id < 40 then 30 " + 
          "      when uf.id >= 40 and uf.id < 50 then 40 " + 
          "      when uf.id >= 50 and uf.id < 60 then 50 " + 
          "    end as idregiao, " + 
          "    case  " + 
          "      when uf.id >= 10 and uf.id < 20 then 'NORTE' " + 
          "      when uf.id >= 20 and uf.id < 30 then 'NORDESTE' " + 
          "      when uf.id >= 30 and uf.id < 40 then 'SUDESTE' " + 
          "      when uf.id >= 40 and uf.id < 50 then 'SUL' " + 
          "      when uf.id >= 50 and uf.id < 60 then 'CENTRO-OESTE' " + 
          "    end as regiao, " + 
          "      case  " + 
          "      when hme.previsao is null then 'INDEFINIDO'  " + 
          "      when cast(hme.previsao as date) < cast(date_trunc('month', current_date) as date) then 'ATRASADO' " + 
          "      when cast(hme.previsao as date) > cast(date_trunc('month', current_date) as date) then 'A VENCER' " + 
          "      when cast(hme.previsao as date) = cast(date_trunc('month', current_date) as date) then 'NO PRAZO' " + 
          "      end as status " + 
          "      from entidades e " + 
          "      inner join enderecos ende on e.idendereco = ende.id " + 
          "      inner join cidades c on ende.idcidade = c.id " + 
          "      inner join estados uf on c.idestado = uf.id " + 
          "      inner join metas_entidade m on m.identidade = e.id " + 
          "      inner join ("
          + " select idmeta, idrodizio, max(tipo_situacao) as tipo_situacao from historico_metas_entidade "
          //+  ( ciclo != null ? " where idrodizio = " + ciclo : " " )
          + " group by idmeta, idrodizio "
          + ") hmelast on m.id = hmelast.idmeta " +
          "      inner join historico_metas_entidade hme on hmelast.idmeta = hme.idmeta and hmelast.idrodizio = hme.idrodizio and hmelast.tipo_situacao = hme.tipo_situacao" +
          "      inner join institutos i on m.idinstituto = i.id " + 
          "      inner join ciclos_avaliacao r on r.id = hme.idrodizio " + 
          "      where  " + 
          "      r.id = " + ciclo  + " and " +
          "      ( hme.tipo_situacao = 1 or hme.tipo_situacao = 2 or hme.tipo_situacao = 3 )  and " + 
          "      ( hme.situacao = 'PLANEJADA' or hme.situacao = 'REPLANEJADA' ) and " + 
          (entidade != null? "      e.id = " + entidade + " and ": " " ) +
          "      m.tipo_meta <> 'META_QUANTITATIVA' and " + 
          "     1 = 1 order by hme.previsao, e.id, r.ciclo, m.id, hme.tipo_situacao " + 
          "    ) relstatus " + 
          "    group by idinstituto, instituto, status " + 
          "    order by idinstituto");
      
      List<Object[]> result2 = query.getResultList();
      
      List<StatusAtualInstitutoGraphicData> result = new ArrayList<StatusAtualInstitutoGraphicData>();
      
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
