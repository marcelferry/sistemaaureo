package com.concafras.cursos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import com.concafras.gestao.model.Pessoa;

@Entity
@Table(name="INSTRUTORES")
public class Instrutor {
	@Id
    @SequenceGenerator(name = "instrutor_id_seq_name", sequenceName = "instrutor_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "instrutor_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idPessoa", referencedColumnName = "id")
	@XmlElement
	private Pessoa pessoa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
	
}
