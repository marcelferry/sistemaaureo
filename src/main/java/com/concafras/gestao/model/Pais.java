package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PAISES")
@Access(AccessType.FIELD)
public class Pais extends ObjetoGerenciado {

	/**
   * Generated SerialVerionUID
   */
  private static final long serialVersionUID = 4778419134695520456L;

  @Id@SequenceGenerator(name = "pais_id_seq_name", sequenceName = "pais_id_seq", allocationSize = 1, initialValue = 1 )
	@GeneratedValue(generator = "pais_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String nome;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
