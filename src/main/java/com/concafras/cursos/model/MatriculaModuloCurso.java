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
@Table(name="MATRICULA_MODULO_CURSO")
public class MatriculaModuloCurso {
	@Id
    @SequenceGenerator(name = "matriculamodulocurso_id_seq_name", sequenceName = "matriculamodulocurso_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "matriculamodulocurso_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idAluno", referencedColumnName = "id")
	@XmlElement
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(name = "idDisciplina", referencedColumnName = "id")
	@XmlElement
	private ModuloCurso disciplina;
	
	@Enumerated(EnumType.STRING)
	private SituacaoMatriculaModulo situacao;

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

	public ModuloCurso getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(ModuloCurso disciplina) {
		this.disciplina = disciplina;
	}

	public SituacaoMatriculaModulo getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoMatriculaModulo situacao) {
		this.situacao = situacao;
	}
	
	
	
}
