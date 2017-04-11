package com.concafras.gestao.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "QUESTOES_ALTERNATIVAS")
@Access(AccessType.FIELD)
@XmlRootElement
public class QuestaoAlternativa implements Serializable {

	/**
   * 
   */
  private static final long serialVersionUID = -5974112191099015701L;

  @Id
	@SequenceGenerator(name = "questoes_alternativa_id_seq_name", sequenceName = "questoes_alternativa_id_seq", allocationSize=1)
	@GeneratedValue(generator = "questoes_alternativa_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String valor;
	
	private String descricao;
	
  @ManyToOne
  @JoinColumn(name = "idQuestoesInstituto", referencedColumnName = "id")
  @XmlElement
  private QuestaoInstituto questaoInstituto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public QuestaoInstituto getQuestaoInstituto() {
    return questaoInstituto;
  }
	
	public void setQuestao(QuestaoInstituto questaoInstituto) {
    this.questaoInstituto = questaoInstituto;
  }
	
}
