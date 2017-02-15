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

@Entity
@Table(name="MODULOS_CURSO")
public class ModuloCurso {
	
	@Id
    @SequenceGenerator(name = "modulocurso_id_seq_name", sequenceName = "modulocurso_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "modulocurso_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	private String nome;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "idCurso", referencedColumnName = "id")
	@XmlElement
	private Curso curso;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="disciplina")
	private List<AulaModuloCurso> aulas;

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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<AulaModuloCurso> getAulas() {
		return aulas;
	}

	public void setAulas(List<AulaModuloCurso> aulas) {
		this.aulas = aulas;
	}
	
	
	
}
