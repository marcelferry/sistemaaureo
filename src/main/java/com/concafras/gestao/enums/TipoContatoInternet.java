package com.concafras.gestao.enums;

public enum TipoContatoInternet {
	EMAILCOM("1", "Email Comercial"),
	EMAILPES("2", "Email Pessoal"),
	FACEBOOK("3", "Facebook"),
	TWITTER("4", "Twitter"),
	SKYPE("5", "Skype"),
	WHATSAPP("6", "Whats App"),
	SITE("7", "WebSite");
	
	private String descricao; 
	private String value; 
	
    private TipoContatoInternet(String valor, String descricao) { 
        this.descricao = descricao; 
        this.value = valor; 
    } 
    
	public String getValue() {
		return value;
	}
    
    @Override 
    public String toString(){ 
        return descricao; 
    } 
}
