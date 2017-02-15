package com.concafras.gestao.model.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity()
@Table(name="ALCADAS")
public class AlcadaUsuario {
	
	@Id
    @SequenceGenerator(name = "userroles_id_seq_name", sequenceName = "userroles_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "userroles_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	private String roleName; 

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
