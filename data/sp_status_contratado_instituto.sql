CREATE OR REPLACE FUNCTION sp_status_contratado_instituto(
		pEntidade integer DEFAULT 0, 
		pCiclo integer DEFAULT 0)
  RETURNS refcursor AS
$BODY$
DECLARE
    ref refcursor;
BEGIN
  OPEN ref FOR SELECT idinstituto, instituto, status, count(*) 
	from ( 
	  select e.id, e.razaosocial, c.nome, uf.sigla, r.ciclo, i.id as idInstituto, i.descricao as instituto, 
          m.id, m.descricao, hme.tipo_situacao, hme.previsao, hme.conclusao, hme.previsto, hme.realizado, hme.situacao,  
          case  
		when uf.id >= 10 and uf.id < 20 then 10 
		when uf.id >= 20 and uf.id < 30 then 20 
		when uf.id >= 30 and uf.id < 40 then 30 
		when uf.id >= 40 and uf.id < 50 then 40 
		when uf.id >= 50 and uf.id < 60 then 50 
          end as idregiao, 
          case  
		when uf.id >= 10 and uf.id < 20 then 'NORTE' 
		when uf.id >= 20 and uf.id < 30 then 'NORDESTE' 
		when uf.id >= 30 and uf.id < 40 then 'SUDESTE' 
		when uf.id >= 40 and uf.id < 50 then 'SUL' 
		when uf.id >= 50 and uf.id < 60 then 'CENTRO-OESTE' 
          end as regiao, 
          case  
		when hme.previsao is null then 'INDEFINIDO'  
		when cast(hme.previsao as date) < cast(date_trunc('month', current_date) as date) then 'ATRASADO' 
		when cast(hme.previsao as date) > cast(date_trunc('month', current_date) as date) then 'A VENCER' 
		when cast(hme.previsao as date) = cast(date_trunc('month', current_date) as date) then 'NO PRAZO' 
          end as status 
          from entidades e 
		inner join enderecos ende on e.idendereco = ende.id 
		inner join cidades c on ende.idcidade = c.id 
		inner join estados uf on c.idestado = uf.id 
		inner join metas_entidade m on m.identidade = e.id 
		inner join (
			select idmeta, idrodizio, max(tipo_situacao) as tipo_situacao from historico_metas_entidade
			group by idmeta, idrodizio 
		) hmelast on m.id = hmelast.idmeta
		inner join historico_metas_entidade hme on hmelast.idmeta = hme.idmeta and hmelast.idrodizio = hme.idrodizio and hmelast.tipo_situacao = hme.tipo_situacao
		inner join institutos i on m.idinstituto = i.id 
		inner join ciclos_avaliacao r on r.id = hme.idrodizio 
          where  
			r.id = pCiclo and
			( hme.tipo_situacao = 1 or hme.tipo_situacao = 2 or hme.tipo_situacao = 3 )  and 
			( hme.situacao = 'PLANEJADA' or hme.situacao = 'REPLANEJADA' ) and 
			( pEntidade = 0 or e.id = pEntidade) and
			m.tipo_meta <> 'META_QUANTITATIVA'
          order by hme.previsao, e.id, r.ciclo, m.id, hme.tipo_situacao 
      ) relstatus 
      group by idinstituto, instituto, status 
      order by idinstituto;
    
  RETURN ref;
END;
$BODY$
LANGUAGE plpgsql; 