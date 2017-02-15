package com.concafras.gestao.enums;

public enum SituacaoAtividade {
	ATIVA("1", "Ativa"),
	NAOADERENTE("2", "Ativa Parcial"),
	INATIVA("3", "Inativa");
	
	
	private String situacao; 
	private String value; 
	
    private SituacaoAtividade(String value, String texto) { 
        this.situacao = texto; 
        this.value = value; 
    } 
    
	public String getValue() {
		return value;
	}
    
    @Override 
    public String toString(){ 
        return situacao; 
    } 
	
	
	
}
