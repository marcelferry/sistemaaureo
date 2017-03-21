CREATE OR REPLACE FUNCTION sp_status_atual_metas(
	pEntidade integer DEFAULT 0, 
	pCiclo integer DEFAULT 0
  )
  RETURNS refcursor AS
$BODY$
DECLARE
    ref refcursor;
    cur_row RECORD;
BEGIN

  CREATE TEMP TABLE TMP_STATUS_METAS ON COMMIT DROP AS
	SELECT i.id, i.descricao, COALESCE(hme.situacao,  
		CASE WHEN hme.realizado > 0 THEN 'IMPLANTADA'             
		ELSE NULL        
		END, 'NAOINFORMADA') AS situacao, count(*) AS total          
	FROM institutos i          
		INNER JOIN metas_instituto mi on i.id = mi.idinstituto          
		LEFT OUTER JOIN metas_entidade me ON me.idmetasinstituto = mi.id          
		LEFT OUTER JOIN historico_metas_entidade hme ON me.id = hme.idmeta
		INNER JOIN (
			SELECT hme.idmeta AS idmeta, max(hme.idrodizio) AS idrodizio
			FROM historico_metas_entidade hme
			INNER JOIN metas_entidade me ON me.id = hme.idmeta 
			WHERE me.identidade = pEntidade AND (hme.tipo_situacao = 0 OR hme.tipo_situacao IS null) 
			GROUP BY hme.idmeta , hme.idrodizio
		) hmefiltro ON hme.idmeta =  hmefiltro.idmeta AND hme.idrodizio = hmefiltro.idrodizio     
	WHERE           
		mi.tipo_meta != 'GRUPO_METAS' AND          
		(hme.tipo_situacao = 0 OR hme.tipo_situacao IS null) AND          
		me.identidade = pEntidade 
	GROUP BY i.id, i.descricao, COALESCE(hme.situacao,  
		CASE WHEN hme.realizado > 0 THEN 'IMPLANTADA'             
		ELSE NULL        
		END, 'NAOINFORMADA') 
	ORDER BY i.descricao,  situacao;

  OPEN ref FOR SELECT * FROM TMP_STATUS_METAS;
    
  RETURN ref;
END;
$BODY$
LANGUAGE plpgsql; 