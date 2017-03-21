CREATE OR REPLACE FUNCTION sp_status_contratado(
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
                    WHEN hme.tipo_situacao = 2 THEN 
                     hme.situacao 
                    ELSE 
                     'NAOINFORMADA' 
                    END 
                END, count(*) as total 
              from historico_metas_entidade hme 
              inner join institutos i on hme.idinstituto = i.id  
              inner join metas_entidade me on me.id = hme.idmeta 
              where me.tipo_meta != 'GRUPO_METAS' and 
                ( pEntidade = 0 OR pme.identidade = pEntidade ) and 
                ( pCiclo = 0 OR pme.idrodizio = pCiclo ) and 
                hme.tipo_situacao <> 1 
              group by i.id, i.descricao, hme.tipo_situacao,  
                CASE 
                WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN 
                   hme.situacao 
                  ELSE 
                    CASE 
                      WHEN hme.tipo_situacao = 2 THEN 
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