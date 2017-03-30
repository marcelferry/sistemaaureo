CREATE OR REPLACE FUNCTION sp_ultima_historico_meta_tipo(
		pMeta integer DEFAULT 0, 
		pTipoSituacao integer DEFAULT -1, 
		pCiclo integer DEFAULT 0)
  RETURNS refcursor AS
$BODY$
DECLARE
    ref refcursor;
BEGIN
  OPEN ref FOR SELECT distinct on ( hme.idmeta ) hme.id, hme.idmeta AS idmeta, hme.idrodizio, hme.tipo_situacao AS tipo_situacao
		FROM historico_metas_entidade hme
		INNER JOIN metas_entidade me ON me.id = hme.idmeta 
		WHERE 
		( pMeta = 0 OR hme.idmeta = pMeta ) and
		( pTipoSituacao = -1 OR hme.tipo_situacao = pTipoSituacao ) and
		( pCiclo = 0 OR hme.idrodizio <= pCiclo )
		ORDER by hme.idmeta, hme.idrodizio desc, hme.tipo_situacao DESC;
    
  RETURN ref;
END;
$BODY$
LANGUAGE plpgsql; 