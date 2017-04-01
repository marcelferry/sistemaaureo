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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import com.concafras.gestao.enums.TipoMeta;

@Entity
@Table(name="METAS_INSTITUTO")
@Access(AccessType.FIELD)
public class MetaInstituto extends ObjetoGerenciado {
	
	/**
   * 
   */
  private static final long serialVersionUID = 4460432910409060409L;

  @Id 
	@SequenceGenerator(name = "metas_instituto_id_seq_name", sequenceName = "metas_instituto_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "metas_instituto_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String descricao;
	
	@Enumerated(EnumType.STRING)
  @Column(name = "tipo_meta")
  private TipoMeta tipoMeta;
	
	private int viewOrder;
	
	private Integer prioridade;
	
	/**
	 * Rodizio no qual foi inserido
	 */
	@ManyToOne
	@JoinColumn(name = "idRodizio", referencedColumnName = "id")
	@XmlElement
	private Rodizio rodizio;
	
	@ManyToOne
	@JoinColumn(name = "idInstituto", referencedColumnName = "id")
	@XmlElement
	private BaseInstituto instituto;
	
	@ManyToOne
	@JoinColumn(name = "idPai", referencedColumnName = "id")
	@XmlElement
	private MetaInstituto pai;
	
	@ManyToOne
	@JoinColumn(name = "idAtividade", referencedColumnName = "id")
	@XmlElement
	private Atividade atividade;	
	
	/**
	 * Lista de Metas das esta depende
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="METAS_DEPENDENCIAS",
    joinColumns = @JoinColumn(name = "id", 
                              referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "idDependencia", 
                              referencedColumnName = "id"))
	@XmlElement
	private List<MetaInstituto> dependencias;
	
	/**
	 * Lista de Metas que podem compor esta meta
	 */
	@OneToMany(mappedBy="pai")
	@OrderBy("viewOrder ASC")
	private List<MetaInstituto> itens;
	
	
	public MetaInstituto() {
		// TODO Auto-generated constructor stub
	}

	public MetaInstituto(Integer id, String descricao, TipoMeta tipoMeta, int viewOrder) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.tipoMeta = tipoMeta;
		this.viewOrder = viewOrder;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoMeta getTipoMeta() {
    return tipoMeta;
  }
	
	public void setTipoMeta(TipoMeta tipoMeta) {
    this.tipoMeta = tipoMeta;
  }

	public int getViewOrder() {
		return viewOrder;
	}

	public void setViewOrder(int viewOrder) {
		this.viewOrder = viewOrder;
	}
	
	public Integer getPrioridade() {
    return prioridade;
  }
	
	public void setPrioridade(Integer prioridade) {
    this.prioridade = prioridade;
  }

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	
	public MetaInstituto getPai() {
		return pai;
	}
	
	public void setPai(MetaInstituto pai) {
		this.pai = pai;
	}

	public List<MetaInstituto> getDependencias() {
		return dependencias;
	}

	public void setDependencias(List<MetaInstituto> dependencias) {
		this.dependencias = dependencias;
	}
	
	public List<MetaInstituto> getItens() {
		return itens;
	}
	
	public void setItens(List<MetaInstituto> items) {
		this.itens = items;
	}
	
	public BaseInstituto getInstituto() {
		return instituto;
	}
	
	public void setInstituto(BaseInstituto instituto) {
		this.instituto = instituto;
	}
	
	public Rodizio getRodizio() {
		return rodizio;
	}
	
	public void setRodizio(Rodizio rodizio) {
		this.rodizio = rodizio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetaInstituto other = (MetaInstituto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

  @Override
  public String toString() {
    return "MetaInstituto [id=" + id + ", descricao=" + descricao
        + ", tipoMeta=" + tipoMeta + ", rodizio=" + rodizio + ", instituto="
        + instituto + "]";
  }
	
	
	
	
	
}
