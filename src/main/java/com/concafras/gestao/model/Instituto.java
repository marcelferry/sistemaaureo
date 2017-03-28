package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INSTITUTO")
@Access(AccessType.FIELD)
public class Instituto extends BaseInstituto {
	
	/**
   * 
   */
  private static final long serialVersionUID = -7515270820657547427L;

  public Instituto() {
	}
	
	public Instituto(Integer id, String nome, String descricao, Integer idDirigenteNacional, String dirigenteNacional) {
		super(id, nome, descricao,idDirigenteNacional, dirigenteNacional);
	}

}
