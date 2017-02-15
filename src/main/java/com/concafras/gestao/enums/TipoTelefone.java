package com.concafras.gestao.enums;

public enum TipoTelefone {
	FIXORES("1","Residencial"),
	FIXOCOM("2","Comercial"),
	FIXOFAX("3","Fax"),
	FIXOREC("4","Recado"),
	CELULAR("5","Celular"),
	CELREC("6","Celular Rec.");
	
	private String descricao; 
	private String value; 
	
    private TipoTelefone(String value, String texto) { 
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
