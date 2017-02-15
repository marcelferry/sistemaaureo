package com.concafras.gestao.enums;

public enum TipoFrequencia {
	DIARIO("Diário"),
	SEMANAL("Semanal"),
	MENSAL("Mensal"),
	BIMESTRAL("Bimestral"),
	TRIMESTRAL("Trimestral"),
	QUADRIMESTRAL("Quadrimestral"),
	SEMESTRAL("Semestral"),
	ANUAL("Anual"),
	SASIONAL("Sasional");
	
	private String titulo;
	
	private TipoFrequencia(String titulo) {
		this.titulo = titulo;
	}
	
    @Override 
    public String toString(){ 
        return titulo; 
    } 
}
