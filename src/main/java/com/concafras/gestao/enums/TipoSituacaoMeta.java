package com.concafras.gestao.enums;

public enum TipoSituacaoMeta {
	/** Primeira participação do rodizio */
	INICIAL,
	/** MetaEntidade ativa sem contratação feita no pre rodizio ou rodizio */
	PRECONTRATAR,
	/** MetaEntidade contratada planejada ou não planejada no rodizio */
	CONTRATAR,
	/** MetaEntidade replanejada pelo dirigente - posrodizio */
	REPLANEJAR,
	/** MetaEntidade concluida pelo dirigente - posrodizio */
	CONCLUIR,
	/** MetaEntidade concluida parcialmente pelo dirigente - posrodizio */
	CONCLUIR_PARCIALMENTE,
	/** MetaEntidade cancelada pelo dirigente - posrodizio */
	CANCELAR,
	/** Avaliação do resultado prerodizio e rodizio */
	AVALIAR
}
