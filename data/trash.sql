-- select i.id, i.descricao, CASE WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN  hme.situacao ELSE CASE WHEN hme.tipo_situacao = 1 THEN  hme.situacao ELSE  'NAOINFORMADA' END END, count(*) as total from institutos i  inner join metas_entidade me on me.idinstituto = i.id  inner join historico_metas_entidade hme on me.id = hme.idmeta where me.tipo_meta != 'GRUPO_METAS' and me.identidade = 50 and hme.idrodizio = 2 and hme.tipo_situacao <> 2 group by i.id, i.descricao, hme.tipo_situacao,  CASE WHEN hme.tipo_situacao = 0 AND hme.situacao = 'IMPLANTADA'  THEN  hme.situacao ELSE CASE WHEN hme.tipo_situacao = 1 THEN  hme.situacao ELSE  'NAOINFORMADA' END END order by i.descricao;

select id, descricao, situacao, total 
from ( 
	(

	select i.id, i.descricao, mi.id, mi.descricao, coalesce(hme.situacao,  
		CASE WHEN hme.realizado > 0 THEN 'IMPLANTADA'             
		ELSE NULL        
		END, 'NAOINFORMADA') as situacao, hme.idrodizio --, count(*) as total          
	from institutos i          
		inner join metas_instituto mi on i.id = mi.idinstituto          
		left outer join metas_entidade me on me.idmetasinstituto = mi.id          
		left outer join historico_metas_entidade hme on me.id = hme.idmeta
		inner join (
			select hme.idmeta as idmeta , max(hme.idrodizio) as idrodizio, max(hme.tipo_situacao) as tipo_situacao
			from historico_metas_entidade hme
			inner join metas_entidade me on me.id = hme.idmeta 
			where me.identidade = 50 and
			(hme.tipo_situacao = 0 or hme.tipo_situacao = 7 or hme.tipo_situacao is null) 
			group by hme.idmeta , hme.idrodizio
		) hmefiltro on hme.idmeta =  hmefiltro.idmeta and hme.idrodizio =  hmefiltro.idrodizio and      
	where           
		mi.tipo_meta != 'GRUPO_METAS' and          
		(hme.tipo_situacao = 0 or hme.tipo_situacao is null) and          
		me.identidade = 50 

	order by i.descricao, mi.vieworder

	)
	UNION 
	(
	select i.id, i.descricao, coalesce(hme.situacao,  
		CASE WHEN hme.realizado > 0 THEN 'IMPLANTADA'             
		ELSE NULL        
		END, 'NAOINFORMADA') as situacao, count(*) as total          
	from institutos i          
		inner join metas_instituto mi on i.id = mi.idinstituto          
		left outer join metas_entidade me on me.idmetasinstituto = mi.id          
		left outer join historico_metas_entidade hme on me.id = hme.idmeta        
	where           
		mi.tipo_meta != 'GRUPO_METAS' and          
		(hme.tipo_situacao = 0 or hme.tipo_situacao is null) and          
		me.identidade = 50 and hme.idrodizio = 2          
	group by i.id, i.descricao, coalesce(hme.situacao,  
		CASE WHEN hme.realizado > 0 THEN 'IMPLANTADA'             
		ELSE NULL        
		END, 'NAOINFORMADA') 
		order by i.descricao,  situacao 
	) 
	UNION 
	(
		select i.id, i.descricao, 'NAOINFORMADA' as situacao, count(*) as total          
		from institutos i          
		inner join metas_instituto mi on i.id = mi.idinstituto          
		where mi.tipo_meta != 'GRUPO_METAS' and i.id not in (
			select idinstituto from contrato_metas pme where pme.identidade = 50 and pme.idrodizio = 2
		)      
		group by i.id, i.descricao order by i.descricao,  situacao
	) 
) x order by descricao,  situacao;


ALTER TABLE contrato_metas_anotacoes ADD PRIMARY KEY(idplanometas, idanotacao);
ALTER TABLE entidade_anotacao ADD PRIMARY KEY(identidade, idanotacao);
ALTER TABLE entidade_ciclo ADD PRIMARY KEY(idciclo, identidade);
ALTER TABLE entidade_contato ADD PRIMARY KEY(identidade, idcontato);
ALTER TABLE entidade_instituto ADD PRIMARY KEY(identidade, idinstituto);
ALTER TABLE entidade_presidente ADD PRIMARY KEY(identidade, idpresidente);
ALTER TABLE entidade_telefone ADD PRIMARY KEY(identidade, idtelefone);
ALTER TABLE entidade_trabalhador ADD PRIMARY KEY(identidade, idpessoa);
ALTER TABLE metas_dependencias ADD PRIMARY KEY(idmeta, iddependencia);	
ALTER TABLE pessoa_anotacao ADD PRIMARY KEY(idpessoa, idanotacao);
ALTER TABLE pessoa_contato ADD PRIMARY KEY(idpessoa, idcontato);
ALTER TABLE pessoa_endereco ADD PRIMARY KEY(idpessoa, idendereco);
ALTER TABLE pessoa_telefone ADD PRIMARY KEY(idpessoa, idtelefone);
ALTER TABLE usuario_alcadas ADD PRIMARY KEY(iduser, idrole);