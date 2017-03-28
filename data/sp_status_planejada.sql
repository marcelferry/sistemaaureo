CREATE OR REPLACE FUNCTION sp_status_planejado(
		pEntidade integer DEFAULT 0, 
		pCiclo integer DEFAULT 0)
  RETURNS refcursor AS
$BODY$
DECLARE
    ref refcursor;
BEGIN
  OPEN ref FOR SELECT i.id, i.descricao, hme.tipo_situacao, hme.situacao,
		    CASE 
                     WHEN hme.situacao is not null THEN 
                       hme.situacao 
                      ELSE 
                       'NAOINFORMADA' 
                      END,
                  count(*) as total 
              from institutos i  
              inner join metas_entidade me on me.idinstituto = i.id  
              inner join historico_metas_entidade hme on me.id = hme.idmeta 
              INNER JOIN (
				SELECT distinct on ( hme.idmeta ) hme.id, hme.idmeta AS idmeta, hme.tipo_situacao AS tipo_situacao, hme.idrodizio
				FROM historico_metas_entidade hme
				INNER JOIN metas_entidade me ON me.id = hme.idmeta 
				WHERE 
					( pEntidade = 0 OR me.identidade = pEntidade ) and 
					( pCiclo = 0 OR hme.idrodizio <= pCiclo )
				ORDER by hme.idmeta, hme.idrodizio desc, hme.tipo_situacao desc
			  ) hmefiltro ON hme.id =  hmefiltro.id   
              where me.tipo_meta != 'GRUPO_METAS' and 
                ( pEntidade = 0 OR me.identidade = pEntidade ) and 
                ( pCiclo = 0 OR hme.idrodizio = pCiclo ) 
                group by i.id, i.descricao, hme.tipo_situacao, hme.situacao   
              order by i.descricao;
    
  RETURN ref;
END;
$BODY$
LANGUAGE plpgsql; 