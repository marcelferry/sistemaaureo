package com.concafras.gestao.enums;

public enum EstadoCivil {
	
	SOLTEIRO("1","Solteiro"),
	CASADO("2","Casado"),
	VIUVO("3","Viúvo"),
	SEPARADO("4","Separado"),
	DIVORCIADO("5","Divorciado"),
	UNIAO_ESTAVEL("6","União Estável");
	
	private String estadoCivil; 
	private String value; 
	
    private EstadoCivil(String value, String brand) { 
        this.estadoCivil = brand; 
        this.value = value; 
    } 
    
	public String getValue() {
		return value;
	}
    
    @Override 
    public String toString(){ 
        return estadoCivil; 
    } 
	
}
