package com.concafras.gestao.service.impl;

import java.math.BigDecimal;
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

import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.service.MetaService;

@Service
public class MetaServiceImpl implements MetaService {

  @PersistenceContext
  EntityManager em;

  @Transactional
  public MetaEntidade findById(Integer id) {
    
    MetaEntidade meta = em.find(MetaEntidade.class, id);

    Hibernate.initialize(meta.getAnotacoes());
    Hibernate.initialize(meta.getHistorico());

    return meta;
  }

  public String getCaminhoMeta(Integer id) {
    Query query = em
        .createNativeQuery("select path_info[1] as id, string_agg(descricao, ' => ') as descricao from ( "
            + "with recursive tree(id, descricao, idpai, level, path_info) as ( "
            + "select n.id, n.descricao, n.idpai, 1 as level, array[id] as path_info "
            + "from metas_instituto n "
            + "union all "
            + "select n.id, n.descricao, n.idpai, t.level + 1, t.path_info||n.id "
            + "from metas_instituto n "
            + "join tree t on (n.id = t.idpai) "
            + ") "
            + "select * "
            + "from tree "
            + "order by path_info desc) a "
            + "where path_info[1] = " + id + " " + "group by path_info[1] ");
    List<Object[]> result = query.getResultList();

    for (Object[] resultElement : result) {
      return (String) resultElement[1];
    }
    return null;
  }

  @Transactional
  public void save(MetaEntidade itemPlanoMetas) {
    em.persist(itemPlanoMetas);
  }

  @Transactional
  public void update(MetaEntidade entidade) {
    em.merge(entidade);
  }

  @Transactional
  public List<MetaEntidade> findByPlanoMetasId(Integer id) {

    PlanoMetas base = em.find(PlanoMetas.class, id);

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<MetaEntidade> c = cb.createQuery(MetaEntidade.class);
    Root<MetaEntidade> emp = c.from(MetaEntidade.class);

    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add(cb.equal(emp.<PlanoMetas> get("planoMetas"), base));
    criteria.add(cb.isNull(emp.<MetaEntidade> get("pai")));
    c.where(criteria.toArray(new Predicate[] {}));

    CriteriaQuery<MetaEntidade> select = c.select(emp);

    select.orderBy(cb.asc(emp.get("viewOrder")));

    List<MetaEntidade> retorno = em.createQuery(select).getResultList();

    for (MetaEntidade b : retorno) {
      Hibernate.initialize(b.getAnotacoes());
      Hibernate.initialize(b.getHistorico());
    }

    return retorno;
  }
  
  @Transactional
  public List<MetaEntidade> findByEntidadeIdAndInstitutoId(Integer idEntidade, Integer idInstituto) {
    
    BaseInstituto base = em.find(BaseInstituto.class, idInstituto);
    BaseEntidade entidade  = em.find(BaseEntidade.class, idEntidade);
    
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<MetaEntidade> c = cb.createQuery(MetaEntidade.class);
    Root<MetaEntidade> emp = c.from(MetaEntidade.class);
    
    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add( cb.equal( emp.<BaseInstituto>get("instituto") , base ));
    criteria.add( cb.equal( emp.<BaseEntidade>get("entidade") , entidade ));
    c.where(criteria.toArray(new Predicate[]{}));
    
    CriteriaQuery<MetaEntidade> select = c.select(emp);
    
    List<MetaEntidade> retorno = em.createQuery(select).getResultList();
    
    for (MetaEntidade b : retorno) {
      Hibernate.initialize(b.getAnotacoes());
      Hibernate.initialize(b.getHistorico());
    }
    
    return retorno;
  }

  @Transactional
  public void remove(Integer id) {
    MetaEntidade entidade = em.find(MetaEntidade.class, id);
    if (null != entidade) {
      em.remove(entidade);
    }
  }

  @Override
  public List<ResumoMetaEntidade> findByMetaInstitutoAndStatus(Integer atividade,
      SituacaoMeta status) {
    String sql = "select e.id, e.razaoSocial, c.nome, uf.sigla, r.id as cid, r.ciclo, i.id as iid, i.descricao as instituto, me.id as mid, me.descricao as meta,  me.tipo_situacao, me.previsao, me.conclusao, me.previsto, me.realizado, me.situacao, me.tipo_meta " + 
           "from metas_entidade me  " +
           "inner join institutos i on me.idinstituto = i.id " + 
           "inner join ciclos_avaliacao r on r.id = me.idrodizio " + 
           "inner join entidades e on me.identidade = e.id  " +
           "inner join enderecos ende on e.idendereco = ende.id " + 
           "inner join cidades c on ende.idcidade = c.id " + 
           "inner join estados uf on c.idestado = uf.id " + 
           "where idmetasinstituto = " + atividade + " " +
           "and (  ";
    if(status == SituacaoMeta.IMPLANTADA){
      sql += "( me.tipo_situacao = 0 and ( me.situacao = 'IMPLANTADA' or me.situacao = 'IMPLPARCIAL' ) ) or  " + 
             "( me.tipo_situacao = 4 and me.situacao = 'IMPLANTADA' ) or  " +
             "( me.tipo_situacao = 5 and me.situacao = 'IMPLPARCIAL' )  ";
    } else if(status == SituacaoMeta.PLANEJADA){
      sql += "( me.tipo_situacao = 1 and me.situacao = 'PLANEJADA' ) or  " +
             "( me.tipo_situacao = 2 and me.situacao = 'PLANEJADA' ) or  " +
             "( me.tipo_situacao = 3 and me.situacao = 'REPLANEJADA' ) ";
    } else if(status == SituacaoMeta.NAOPLANEJADA ) {
      sql += "( me.tipo_situacao = 0 and me.situacao = 'NAOIMPLANTADA') OR  " +
          "( me.tipo_situacao = 1 and me.situacao = 'NAOPLANEJADA' ) OR " +
          "( me.tipo_situacao = 2 and me.situacao = 'NAOPLANEJADA' )  OR " +
          "( me.tipo_situacao = 6 and me.situacao = 'NAOIMPLANTADA' )  " ;
    }
    sql += ")   " +
           "order by e.razaoSocial";
    
    Query query = em
        .createNativeQuery(sql);
    
    List<Object[]> result = query.getResultList();
    
    List<ResumoMetaEntidade> retorno = new ArrayList<ResumoMetaEntidade>();
    
    ResumoMetaEntidade institutoAtual = null;

    for (Object[] resultElement : result) {
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
      institutoAtual.setTipoMeta((String)resultElement[16]);
      
      retorno.add(institutoAtual);
    }
    
    return retorno;
  }
  
  @Transactional
  public HistoricoMetaEntidade findLastByMetaEntidadeIdAndRodizioId(
      Integer idmeta, Integer ciclo, boolean atual){
    
    StoredProcedureQuery query = em.createStoredProcedureQuery("sp_ultima_historico_meta");
    query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
    query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(4, Boolean.class, ParameterMode.IN);
    query.setParameter(2, idmeta);
    query.setParameter(3, ciclo);
    query.setParameter(4, atual);
    
    List<Object[]> result = query.getResultList();
    
    if(result == null || result.size() == 0){
      return null;
    }
    
    if(result.size() > 1) {
      throw new IllegalStateException("Resultado maior que 1");
    }
    
    Integer idhme = (Integer)result.get(0)[0];

    HistoricoMetaEntidade hme = em.find(HistoricoMetaEntidade.class, new Long(idhme));
    
    return hme;
  }
  
  @Transactional
  public HistoricoMetaEntidade findLastByMetaEntidadeIdAndRodizioIdPreAvaliar(
      Integer idmeta, Integer ciclo){
    
    StoredProcedureQuery query = em.createStoredProcedureQuery("sp_ultima_historico_meta_pre_avaliar");
    query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
    query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
    query.setParameter(2, idmeta);
    query.setParameter(3, ciclo);
    
    List<Object[]> result = query.getResultList();
    
    if(result == null || result.size() == 0){
      return null;
    }
    
    if(result.size() > 1) {
      throw new IllegalStateException("Resultado maior que 1");
    }
    
    Integer idhme = (Integer)result.get(0)[0];

    HistoricoMetaEntidade hme = em.find(HistoricoMetaEntidade.class, new Long(idhme));
    
    return hme;
  }
  
  @Transactional
  public HistoricoMetaEntidade findLastByMetaEntidadeIdAndRodizioIdAndTipoSituacao(
      Integer idmeta, Integer ciclo, Integer tipoSituacao, boolean atual){
    
    StoredProcedureQuery query = em.createStoredProcedureQuery("sp_ultima_historico_meta_tipo");
    query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
    query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(5, Boolean.class, ParameterMode.IN);
    query.setParameter(2, idmeta);
    query.setParameter(3, tipoSituacao);
    query.setParameter(4, ciclo);
    query.setParameter(5, atual);
    
    List<Object[]> result = query.getResultList();
    
    if(result == null || result.size() == 0){
      return null;
    }
    
    if(result.size() > 1) {
      throw new IllegalStateException("Resultado maior que 1");
    }
    
    Integer idhme = (Integer)result.get(0)[0];
    
    HistoricoMetaEntidade hme = em.find(HistoricoMetaEntidade.class, new Long(idhme));
    
    return hme;
  }
  
  public List<HistoricoMetaEntidade> findByMetaEntidadeIdAndRodizioId(Integer metaentidade, Integer ciclo){

    MetaEntidade meta = em.find(MetaEntidade.class, metaentidade);
    Rodizio rodizio  = em.find(Rodizio.class, ciclo);
    
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<HistoricoMetaEntidade> c = cb.createQuery(HistoricoMetaEntidade.class);
    Root<HistoricoMetaEntidade> emp = c.from(HistoricoMetaEntidade.class);
    
    List<Predicate> criteria = new ArrayList<Predicate>();
    criteria.add( cb.equal( emp.<MetaEntidade>get("meta") , meta ));
    criteria.add( cb.equal( emp.<Rodizio>get("rodizio") , rodizio ));
    c.where(criteria.toArray(new Predicate[]{}));
    
    c.orderBy(cb.asc(emp.<Date> get("dataSituacao")));
    
    CriteriaQuery<HistoricoMetaEntidade> select = c.select(emp);
    
    List<HistoricoMetaEntidade> retorno = em.createQuery(select).getResultList();
    
    return retorno;
  }

  @Override
  public void saveAnotacao(Anotacao anotacao) {
    em.persist(anotacao);
  }

  @Override
  public void saveMetaAnotacao(MetaEntidadeAnotacao metaEntidadeAnotacao) {
    em.persist(metaEntidadeAnotacao);
  }

}
