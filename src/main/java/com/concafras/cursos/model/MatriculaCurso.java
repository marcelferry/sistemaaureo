package com.concafras.cursos.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name="MATRICULA_CURSO")
public class MatriculaCurso {
	@Id
    @SequenceGenerator(name = "matriculacurso_id_seq_name", sequenceName = "matriculacurso_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "matriculacurso_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idAluno", referencedColumnName = "id")
	@XmlElement
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(name = "idCurso", referencedColumnName = "id")
	@XmlElement
	private Curso curso;
	
	@Enumerated(EnumType.STRING)
	private SituacaoMatriculaCurso situacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public SituacaoMatriculaCurso getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoMatriculaCurso situacao) {
		this.situacao = situacao;
	}
	
	
}
