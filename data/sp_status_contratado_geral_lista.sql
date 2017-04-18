CREATE OR REPLACE FUNCTION sp_status_contratado_geral_lista(
	pEntidade integer DEFAULT 0, 
	pRegiao integer DEFAULT 0, 
	pInstituto integer DEFAULT 0, 
	pStatus VARCHAR DEFAULT '', 
	pCiclo integer DEFAULT 0
  )
  RETURNS refcursor AS
$BODY$
DECLARE
    ref refcursor;
BEGIN
  OPEN ref FOR SELECT * from (select distinct e.id as eid, e.razaosocial, c.nome, uf.sigla, r.id as cid, r.ciclo, i.id as iid, i.descricao as instituto, m.id as mid, md.descricao as meta,  
            hme.tipo_situacao, hme.previsao, hme.conclusao, hme.previsto, hme.realizado, hme.situacao,   
          uf.idregiao,  
          case   
            when uf.idregiao =  10 then 'NORTE'  
            when uf.idregiao =  20 then 'NORDESTE'  
            when uf.idregiao =  30 then 'SUDESTE'  
            when uf.idregiao =  40 then 'SUL'  
            when uf.idregiao =  50 then 'CENTRO-OESTE'  
          end as regiao,  
            case   
            when hme.previsao is null then 'INDEFINIDO'   
            when cast(hme.previsao as date) < cast(date_trunc('month', current_date) as date) then 'ATRASADO'  
            when cast(hme.previsao as date) > cast(date_trunc('month', current_date) as date) then 'A VENCER'  
            when cast(hme.previsao as date) = cast(date_trunc('month', current_date) as date) then 'NO PRAZO'  
            end as status, 
            m.tipo_meta 
            from entidades e  
            inner join enderecos ende on e.idendereco = ende.id  
            inner join cidades c on ende.idcidade = c.id  
            inner join estados uf on c.idestado = uf.id  
            inner join metas_entidade m on m.identidade = e.id  
            inner join (
          		select DISTINCT ON ( idmeta ) idmeta, idrodizio, tipo_situacao, situacao from historico_metas_entidade
			    ORDER by idmeta, idrodizio desc, tipo_situacao DESC
          	) hmelast on m.id = hmelast.idmeta 
            inner join historico_metas_entidade hme on hmelast.idmeta = hme.idmeta and hmelast.idrodizio = hme.idrodizio and hmelast.tipo_situacao = hme.tipo_situacao
            inner join institutos i on m.idinstituto = i.id  
            inner join ciclos_avaliacao r on r.id = hme.idrodizio 
            inner join (select path_info[1] as id, string_agg(descricao, ' => ') as descricao from (  
            with recursive tree(id, descricao, idpai, level, path_info) as (  
                select n.id, n.descricao, n.idpai, 1 as level, array[id] as path_info  
                from metas_instituto n  
                union all  
                select n.id, n.descricao, n.idpai, t.level + 1, t.path_info||n.id  
                from metas_instituto n  
                join tree t on (n.id = t.idpai)  
            )  
            select *  
            from tree  
            order by path_info desc) a  
            group by path_info[1]) md  on md.id = m.idmetasinstituto   
            ) x   
            where   
            ( tipo_situacao = 1 or tipo_situacao = 2 or tipo_situacao = 3 or tipo_situacao = 7 )  and  
            ( situacao = 'PLANEJADA' or situacao = 'REPLANEJADA' ) and 
            ( pCiclo = 0 OR cid =  pCiclo ) and  
            ( pRegiao = 0 OR idregiao = pRegiao ) and 
            ( pEntidade = 0 OR eid  = pEntidade ) and 
            ( pInstituto = 0 OR iid =  pInstituto ) and 
            ( pStatus = '' OR status = pStatus ) 
          order by previsao, eid, ciclo, mid, tipo_situacao;
    
  RETURN ref;
END;
$BODY$
LANGUAGE plpgsql; 