package com.concafras.gestao.model.view;

import java.io.Serializable;
import java.util.List;

public class MetaInstitutoExcel implements Serializable{
	String id;
	int viewOrder;
	String nome;
	boolean grupo;
	boolean atividade;
	boolean acao;
	List<MetaInstitutoExcel> lista;
	
	public MetaInstitutoExcel(String id, String nome, boolean grupo,
			boolean atividade, boolean acao) {
		super();
		this.id = id;
		this.nome = nome;
		this.grupo = grupo;
		this.atividade = atividade;
		this.acao = acao;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getViewOrder() {
		return viewOrder;
	}
	public void setViewOrder(int viewOrder) {
		this.viewOrder = viewOrder;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isGrupo() {
		return grupo;
	}
	public void setGrupo(boolean grupo) {
		this.grupo = grupo;
	}
	public boolean isAtividade() {
		return atividade;
	}
	public void setAtividade(boolean atividade) {
		this.atividade = atividade;
	}
	public boolean isAcao() {
		return acao;
	}
	public void setAcao(boolean acao) {
		this.acao = acao;
	}
	public List<MetaInstitutoExcel> getLista() {
		return lista;
	}
	public void setLista(List<MetaInstitutoExcel> lista) {
		this.lista = lista;
	}
}
