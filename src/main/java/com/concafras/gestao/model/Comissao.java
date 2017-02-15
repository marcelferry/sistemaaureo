package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;

@Entity
@DiscriminatorValue("COMISSAO")
@Access(AccessType.FIELD)
public class Comissao extends BaseInstituto {
	/**
   * 
   */
  private static final long serialVersionUID = -4248186897544178563L;
  
  /**
	 * Instituto ao qual a comissão faz parte
	 */
	@ManyToOne
	@JoinColumn(name = "idInstituto", referencedColumnName = "id")
	@XmlElement
	private Instituto instituto;
	
	public Instituto getInstituto() {
		return instituto;
	}
	
	public void setInstituto(Instituto instituto) {
		this.instituto = instituto;
	}
	
	
	
}
