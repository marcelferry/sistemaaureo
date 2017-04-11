package com.concafras.gestao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoSituacaoMeta;

@Entity(name="HISTORICO_METAS_ENTIDADE")
@Access(AccessType.FIELD)
public class HistoricoMetaEntidade implements Serializable, Comparable<HistoricoMetaEntidade> {

	/**
   * 
   */
  private static final long serialVersionUID = -7530787004477869326L;

  @Id @SequenceGenerator(name = "historico_meta_id_seq_name", sequenceName = "historico_meta_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "historico_meta_id_seq_name", strategy = GenerationType.SEQUENCE)
	private Long id;
	
  /**
   * Rodizio do exercicio/Primeiro Rodizio
   */
  @ManyToOne
  @JoinColumn(name = "idRodizio", referencedColumnName = "id")
  @XmlElement
  private Rodizio rodizio;
  
	@ManyToOne
	@JoinColumn(name = "idMeta", referencedColumnName = "id")
	@XmlElement
	private MetaEntidade meta;
	
	/**
	 * Pessoa responsável pela alteração no sistema. 
	 */
	@ManyToOne
	@JoinColumn(name = "idResponsavel", referencedColumnName = "id")
	@XmlElement
	private Pessoa responsavel;
	
	/**
	 * Ação realizada
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name="tipo_situacao")
	private TipoSituacaoMeta tipoSituacao;
	
	/**
	 * Notificar envolvidos (Definir niveis de notificação)
	 * Sugestão: Coordenador, Presidente, Dirigente Nacional)
	 */
	private boolean notificar;
	
	/**
	 * Data Inclusão do Situacao da MetaEntidade
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_situacao")
	private Date dataSituacao;
	
	/**
	 * Situacao da meta
	 */
	@Enumerated(EnumType.STRING)
	private SituacaoMeta situacao;
	
	/**
	 * Mes/ano replanejamento
	 * 
	 * Colocar regra valide o periodo do replanejamento.
	 */
	@Temporal(TemporalType.DATE)
	private Date previsao;
	
	private BigDecimal previsto;
	
	@Temporal(TemporalType.DATE)
	private Date conclusao;
	
	private BigDecimal realizado;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Rodizio getRodizio() {
    return rodizio;
  }
	
	public void setRodizio(Rodizio rodizio) {
    this.rodizio = rodizio;
  }

	public MetaEntidade getMeta() {
		return meta;
	}

	public void setMeta(MetaEntidade meta) {
		this.meta = meta;
	}

	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}

	public TipoSituacaoMeta getTipoSituacao() {
		return tipoSituacao;
	}

	public void setTipoSituacao(TipoSituacaoMeta acao) {
		this.tipoSituacao = acao;
	}

	public boolean isNotificar() {
		return notificar;
	}

	public void setNotificar(boolean notificar) {
		this.notificar = notificar;
	}

	public Date getDataSituacao() {
		return dataSituacao;
	}

	public void setDataSituacao(Date dataAcao) {
		this.dataSituacao = dataAcao;
	}

	public SituacaoMeta getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoMeta situacao) {
		this.situacao = situacao;
	}

	public Date getPrevisao() {
		return previsao;
	}

	public void setPrevisao(Date dataSituacao) {
		this.previsao = dataSituacao;
	}

	public BigDecimal getPrevisto() {
		return previsto;
	}

	public void setPrevisto(BigDecimal qtdeSituacao) {
		this.previsto = qtdeSituacao;
	}
	
	public Date getConclusao() {
		return conclusao;
	}
	
	public void setConclusao(Date conclusao) {
		this.conclusao = conclusao;
	}
	
	public BigDecimal getRealizado() {
		return realizado;
	}
	
	public void setRealizado(BigDecimal realizado) {
		this.realizado = realizado;
	}
	
  @Override
  public int compareTo(HistoricoMetaEntidade o) {
    if (this.dataSituacao.before( o.dataSituacao ) ) {
        return -1;
    }
    if (this.dataSituacao.after(  o.dataSituacao ) ) {
        return 1;
    }
    return 0;
  }

  @Override
  public String toString() {
    return "HistoricoMetaEntidade [id=" + id + ", rodizio=" + rodizio
        + ", meta=" + meta + ", responsavel=" + responsavel + ", tipoSituacao="
        + tipoSituacao + ", situacao=" + situacao + "]";
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
    HistoricoMetaEntidade other = (HistoricoMetaEntidade) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  
  
	
}
