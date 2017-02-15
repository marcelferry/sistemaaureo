package com.concafras.gestao.model.view;

public class ItemPlanoModeloWrapper {
	
	private Integer id;
	private ItemPlanoModeloWrapper[] children;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ItemPlanoModeloWrapper[] getChildren() {
		return children;
	}
	public void setChildren(ItemPlanoModeloWrapper[] children) {
		this.children = children;
	}
	
	
}
