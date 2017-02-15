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
@Table(name="INTRUTORES_MODULO_CURSO")
public class InstrutorModuloCurso {
	@Id
    @SequenceGenerator(name = "instrutormodulocurso_id_seq_name", sequenceName = "instrutormodulocurso_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "instrutormodulocurso_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idInstrutor", referencedColumnName = "id")
	@XmlElement
	private Instrutor instrutor;
	
	@ManyToOne
	@JoinColumn(name = "idTurma", referencedColumnName = "id")
	@XmlElement
	private Turma turma;
	
	@Temporal(TemporalType.DATE)
	private Date inicio;
	
	@Temporal(TemporalType.DATE)
	private Date termino;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}
	
	
	
}
