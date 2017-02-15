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

@Entity
@Table(name="TURMAS")
public class Turma {
	@Id
    @SequenceGenerator(name = "turma_id_seq_name", sequenceName = "turma_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "turma_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	private Integer semestre;
	
	private Integer ano;
	
	@ManyToOne
	@JoinColumn(name = "idDisciplina", referencedColumnName = "id")
	@XmlElement
	private ModuloCurso disciplina;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public ModuloCurso getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(ModuloCurso disciplina) {
		this.disciplina = disciplina;
	}
	
	
	
}
