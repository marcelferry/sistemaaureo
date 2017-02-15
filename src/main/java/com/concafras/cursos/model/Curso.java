package com.concafras.cursos.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import com.concafras.gestao.model.Instituto;


@Entity
@Table(name="CURSOS")
public class Curso {
	
	@Id
    @SequenceGenerator(name = "curso_id_seq_name", sequenceName = "curso_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "curso_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	private String nome;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "idInstituto", referencedColumnName = "id")
	@XmlElement
	private Instituto instituto;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="curso")
	private List<ModuloCurso> modulos;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instituto getInstituto() {
		return instituto;
	}

	public void setInstituto(Instituto instituto) {
		this.instituto = instituto;
	}

	public List<ModuloCurso> getModulos() {
		return modulos;
	}

	public void setModulos(List<ModuloCurso> modulos) {
		this.modulos = modulos;
	}
	
	
	
}
