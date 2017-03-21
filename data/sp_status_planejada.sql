CREATE OR REPLACE FUNCTION sp_status_planejado(
		pEntidade integer DEFAULT 0, 
		pCiclo integer DEFAULT 0)
  RETURNS refcursor AS
$BODY$
DECLARE
    ref refcursor;
BEGIN
  OPEN ref FOR SELECT i.id, i.descricao, 
              CASE 
                WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN 
                 hme.situacao 
                ELSE 
                  CASE 
                    WHEN hme.tipo_situacao = 1 THEN 
                     hme.situacao 
                    ELSE 
                     'NAOINFORMADA' 
                    END 
                END, count(*) as total 
              from institutos i  
              inner join metas_entidade me on me.idinstituto = i.id  
              inner join historico_metas_entidade hme on me.id = hme.idmeta 
              where me.tipo_meta != 'GRUPO_METAS' and 
                ( pEntidade = 0 OR me.identidade = pEntidade ) and 
                ( pCiclo = 0 OR hme.idrodizio = pCiclo ) and 
                hme.tipo_situacao <> 2 
              group by i.id, i.descricao, hme.tipo_situacao,  
                CASE 
                WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN 
                   hme.situacao 
                  ELSE 
                    CASE 
                      WHEN hme.tipo_situacao = 1 THEN 
                       hme.situacao 
                      ELSE 
                       'NAOINFORMADA' 
                      END 
                END 
              order by i.descricao;
    
  RETURN ref;
END;
$BODY$
LANGUAGE plpgsql; 