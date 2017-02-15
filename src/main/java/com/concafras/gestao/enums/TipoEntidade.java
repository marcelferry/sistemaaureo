package com.concafras.gestao.enums;

public enum TipoEntidade {
	CENTRO("Casa Espírita"),
	POSTO("Posto de Assistência"),
	OBRAS("Obra Social"),
	CRECHE("Creche"),
	ESCOLA("Escola"),
	LAR("Lar"),
	ASILO("Asilo"),
	ALBERGUE("Albergue"),
	LIVRARIA("Livraria");
	
	private String titulo;
	
	private TipoEntidade(String titulo) {
		this.titulo = titulo;
	}
	
    @Override 
    public String toString(){ 
        return titulo; 
    } 
}
