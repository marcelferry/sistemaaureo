package com.concafras.gestao.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity()
@Table(name="ALCADAS")
public class AlcadaUsuario  implements Serializable {
	
	/**
   * 
   */
  private static final long serialVersionUID = 629553407676454155L;

  @Id
    @SequenceGenerator(name = "userroles_id_seq_name", sequenceName = "userroles_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "userroles_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	private String roleName; 
	
	private String descricao; 

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
	
	public String getDescricao() {
    return descricao;
  }
	
	public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
