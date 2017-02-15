package com.concafras.gestao.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.concafras.gestao.enums.TipoTelefone;

@Entity
@Table(name="TELEFONES")
@Access(AccessType.FIELD)
public class Telefone extends ObjetoGerenciado {
  
	/**
   * Generated SerialVerionUID
   */
  private static final long serialVersionUID = -8693311428286036800L;

  @Id
	@SequenceGenerator(name = "telefone_id_seq_name", sequenceName = "telefone_id_seq", allocationSize=1, initialValue=1)
	@GeneratedValue(generator = "telefone_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Transient
	private Integer remove;
	
	@Enumerated(EnumType.STRING)
	private TipoTelefone tipo;
	
	private String operadora;
	
	private String ddd;
	
	private String numero;
	
	private String ramal;
	
	private boolean principal;
	
	public Telefone() {
		// TODO Auto-generated constructor stub
	}
	
	public Telefone(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoTelefone getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public String getOperadora() {
		return operadora;
	}

	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}
	
	public Integer getRemove() {
		return remove;
	}
	
	public void setRemove(Integer remove) {
		this.remove = remove;
	}

	@Override
	public String toString() {
		return "Telefone [ddd=" + ddd + ", numero=" + numero + "]";
	}
}
