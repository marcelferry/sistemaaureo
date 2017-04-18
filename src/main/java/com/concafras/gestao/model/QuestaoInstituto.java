package com.concafras.gestao.model;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.concafras.gestao.enums.TipoCampo;

@Entity
@Table(name = "QUESTOES_INSTITUTO")
@Access(AccessType.FIELD)
@XmlRootElement 
public class QuestaoInstituto extends ObjetoGerenciado {

	/**
   * 
   */
  private static final long serialVersionUID = 5451721438582580909L;

  @Id
	@SequenceGenerator(name = "questoes_instituto_id_seq_name", sequenceName = "questoes_instituto_id_seq", allocationSize=1)
	@GeneratedValue(generator = "questoes_instituto_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
  
  private String descricao;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_campo")
  private TipoCampo tipoCampo;
  
  private int viewOrder;
  
  @ManyToOne
  @JoinColumn(name = "idCiclo", referencedColumnName = "id")
  @XmlElement
  private Rodizio ciclo;
  
  @ManyToOne
  @JoinColumn(name = "idInstituto", referencedColumnName = "id")
  @XmlElement
  private BaseInstituto instituto;
  
  @ManyToOne
  @JoinColumn(name = "idPai", referencedColumnName = "id")
  @XmlElement
  private QuestaoInstituto pai;
  
  @OneToMany(mappedBy="pai")
  @OrderBy("viewOrder ASC")
  private List<QuestaoInstituto> itens;
  
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idQuestao", referencedColumnName = "id")
	@XmlElement
	private List<QuestaoAlternativa> listQuestaoAlternativa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricaoChave) {
    this.descricao = descricaoChave;
  }

  
  public TipoCampo getTipoCampo() {
    return tipoCampo;
  }
  
  public void setTipoCampo(TipoCampo tipoCampo) {
    this.tipoCampo = tipoCampo;
  }
	
	public int getViewOrder() {
    return viewOrder;
  }

  public void setViewOrder(int viewOrder) {
    this.viewOrder = viewOrder;
  }
  
  public QuestaoInstituto getPai() {
    return pai;
  }
  
  public void setPai(QuestaoInstituto pai) {
    this.pai = pai;
  }
  
  public List<QuestaoInstituto> getItens() {
    return itens;
  }
  
  public void setItens(List<QuestaoInstituto> items) {
    this.itens = items;
  }
  
  public BaseInstituto getInstituto() {
    return instituto;
  }
  
  public void setInstituto(BaseInstituto instituto) {
    this.instituto = instituto;
  }
  
  public Rodizio getCiclo() {
    return ciclo;
  }
  
  public void setCiclo(Rodizio rodizio) {
    this.ciclo = rodizio;
  }

  public List<QuestaoAlternativa> getListQuestaoAlternativa() {
    return listQuestaoAlternativa;
  }

  public void setListQuestaoAlternativa(List<QuestaoAlternativa> listParametroValor) {
    this.listQuestaoAlternativa = listParametroValor;
  }

	
}
