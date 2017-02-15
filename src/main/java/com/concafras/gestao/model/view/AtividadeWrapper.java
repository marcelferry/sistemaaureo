package com.concafras.gestao.model.view;

public class AtividadeWrapper {
	
	private Integer id;
	private AtividadeWrapper[] children;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public AtividadeWrapper[] getChildren() {
		return children;
	}
	public void setChildren(AtividadeWrapper[] children) {
		this.children = children;
	}
	
	
}
