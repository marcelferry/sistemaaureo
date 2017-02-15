package com.concafras.gestao.enums;

public enum DiaSemana {
	DOMINGO("0", "Domingo"),
	SEGUNDA("1", "Segunda-Feira"),
	TERCA("2", "Terça-Feira"),
	QUARTA("3", "Quarta-Feira"),
	QUINTA("4", "Quinta-Feira"),
	SEXTA("5", "Sexta-Feira"),
	SABADO("6", "Sábado");
	
	private String descricao; 
	private String value; 
	
    private DiaSemana(String value, String texto) { 
        this.descricao = texto; 
        this.value = value; 
    } 
    
	public String getValue() {
		return value;
	}
    
    @Override 
    public String toString(){ 
        return descricao; 
    } 
	
}
