package com.concafras.gestao.enums;

public enum SituacaoMeta {
	NAOPLANEJADA("1","Não Planejada"),
	PLANEJADA("2","Planejada"),
	REPLANEJADA("3", "Replanejada"),
	NAOIMPLANTADA("4", "Não Implantada"),
	IMPLPARCIAL("5", "Imp. Parcial"),
	IMPLANTADA("6", "Implantada"),
	CANCELADA("7", "Cancelada");
	
	private String situacao; 
	private String value; 
	
    private SituacaoMeta(String value, String texto) { 
        this.situacao = texto; 
        this.value = value; 
    } 
    
	public String getValue() {
		return value;
	}
	
	public String getSituacao() {
    return situacao;
  }
    
}
