package com.concafras.gestao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.concafras.gestao.enums.TipoCampo;

@Entity
@Table(name = "QUESTOES_ENTIDADE")
@XmlRootElement
public class QuestaoEntidade implements Serializable {

	@Id
	@SequenceGenerator(name = "questoes_entidade_id_seq_name", sequenceName = "questoes_entidade_id_seq", allocationSize=1)
	@GeneratedValue(generator = "questoes_entidade_id_seq_name", strategy = GenerationType.SEQUENCE)
	private long id;

  @ManyToOne
  @JoinColumn(name = "idEntidade", referencedColumnName = "id")
  @XmlElement
  private BaseEntidade entidade;
  
  @ManyToOne
  @JoinColumn(name = "idCiclo", referencedColumnName = "id")
  @XmlElement
  private Rodizio ciclo;

	@JoinColumn(name = "idQuestoesInstituto", referencedColumnName = "id")
	@XmlElement
	private QuestaoInstituto questao;

	private String resposta;
	
	@Transient
	private String descricao;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public BaseEntidade getEntidade() {
    return entidade;
  }
	
	public void setEntidade(BaseEntidade entidade) {
    this.entidade = entidade;
  }
	
	public Rodizio getCiclo() {
    return ciclo;
  }
  
  public void setCiclo(Rodizio rodizio) {
    this.ciclo = rodizio;
  }

	public QuestaoInstituto getQuestao() {
		return questao;
	}

	public void setQuestao(QuestaoInstituto questao) {
		this.questao = questao;
	}
	
	public String getResposta() {
    return resposta;
  }
	
	public void setResposta(String resposta) {
    this.resposta = resposta;
  }

	public String getDescricao() {
		descricao = "";
		if (questao != null){
			List<QuestaoAlternativa> questaoAlternativa = questao.getListQuestaoAlternativa();
			if(questao.getTipoCampo() == TipoCampo.SELECTMULTIPLE){
				
					String[] alternativaes = resposta.split(";");
					for (String string : alternativaes) {
						boolean achou = false;
						for (QuestaoAlternativa p : questaoAlternativa) {
							if (p.getValor().equals(string)){
								descricao += p.getDescricao() + ", ";
								achou = true;
							}
						}
						if(!achou){
							descricao += string + ", ";
						}
					}
					descricao = descricao.length() > 2? descricao.substring(0, descricao.lastIndexOf(", ") ) : descricao;
				
			} else {
				for (QuestaoAlternativa p : questaoAlternativa) {
					if (resposta.equals(p.getValor())){
						descricao = p.getDescricao();
					}
				}
			}
		}
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	


}
