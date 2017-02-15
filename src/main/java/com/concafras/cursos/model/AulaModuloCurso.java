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
@Table(name="ALUNOS_MODULO_CURSO")
public class AulaModuloCurso {
	@Id
    @SequenceGenerator(name = "aulamodulocurso_id_seq_name", sequenceName = "aulamodulocurso_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "aulamodulocurso_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	private Integer nroseq;
	
	private String nome;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "idDisciplina", referencedColumnName = "id")
	@XmlElement
	private ModuloCurso disciplina;
	
	private TipoAula tipo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNroseq() {
		return nroseq;
	}

	public void setNroseq(Integer nroseq) {
		this.nroseq = nroseq;
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
	
	public ModuloCurso getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(ModuloCurso disciplina) {
		this.disciplina = disciplina;
	}
	
	public TipoAula getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoAula tipo) {
		this.tipo = tipo;
	}
	
}
