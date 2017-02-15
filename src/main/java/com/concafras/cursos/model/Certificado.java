package com.concafras.cursos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name="CERTIFICADOS")
public class Certificado {
	@Id
    @SequenceGenerator(name = "certificado_id_seq_name", sequenceName = "certificado_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "certificado_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idAluno", referencedColumnName = "id")
	@XmlElement
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(name = "idDisciplina", referencedColumnName = "id")
	@XmlElement
	private ModuloCurso disciplina;
	
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;

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

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	
	
}
