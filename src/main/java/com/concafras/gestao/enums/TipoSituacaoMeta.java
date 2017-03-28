package com.concafras.gestao.enums;

public enum TipoSituacaoMeta {
	/** Primeira participação do rodizio */
	INICIAL, //0
	/** MetaEntidade ativa sem contratação feita no pre rodizio ou rodizio */
	PRECONTRATAR, //1
	/** MetaEntidade contratada planejada ou não planejada no rodizio */
	CONTRATAR, //2
	/** MetaEntidade replanejada pelo dirigente - posrodizio */
	REPLANEJAR, //3
	/** MetaEntidade concluida pelo dirigente - posrodizio */
	CONCLUIR, //4
	/** MetaEntidade concluida parcialmente pelo dirigente - posrodizio */
	CONCLUIR_PARCIALMENTE, //5
	/** MetaEntidade cancelada pelo dirigente - posrodizio */
	CANCELAR, //6
	/** Avaliação do resultado prerodizio e rodizio */
	AVALIAR //7
}
