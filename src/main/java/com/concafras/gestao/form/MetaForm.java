package com.concafras.gestao.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.rest.model.AnotacaoVO;
import com.concafras.gestao.rest.model.HistoricoMetaEntidadeVO;
import com.concafras.gestao.rest.model.MetaInstitutoVO;
import com.fasterxml.jackson.annotation.JsonFormat;

public class MetaForm {

	private Integer id;

	private RodizioVO ciclo;

	private EntidadeOptionForm entidade;

	private InstitutoOptionForm instituto;

	private MetaInstitutoVO atividade;

	private String descricaoCompleta;

	private PessoaOptionForm responsavel;

	private TipoSituacaoMeta tipoSituacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataSituacao;

	private SituacaoMeta situacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
	@DateTimeFormat(pattern = "MM/yyyy")
	private Date previsao;

	private BigDecimal previsto;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
	@DateTimeFormat(pattern = "MM/yyyy")
	private Date conclusao;

	private BigDecimal realizado;

	private HistoricoMetaEntidadeVO situacaoAnterior;

	private HistoricoMetaEntidadeVO situacaoAtual;

	private HistoricoMetaEntidadeVO situacaoDesejada;

	private List<AnotacaoVO> observacoes;

	// TODO: REMOVER
	// private List<AnotacaoVO> alertas;

	private List<MetaForm> dependencias;

	private List<HistoricoMetaEntidadeVO> historico;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RodizioVO getCiclo() {
		return ciclo;
	}

	public void setCiclo(RodizioVO ciclo) {
		this.ciclo = ciclo;
	}

	public InstitutoOptionForm getInstituto() {
		return instituto;
	}

	public void setInstituto(InstitutoOptionForm instituto) {
		this.instituto = instituto;
	}

	public EntidadeOptionForm getEntidade() {
		return entidade;
	}

	public void setEntidade(EntidadeOptionForm entidade) {
		this.entidade = entidade;
	}

	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}

	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}

	public MetaInstitutoVO getAtividade() {
		return atividade;
	}

	public void setAtividade(MetaInstitutoVO atividade) {
		this.atividade = atividade;
	}

	public PessoaOptionForm getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(PessoaOptionForm responsavel) {
		this.responsavel = responsavel;
	}

	public TipoSituacaoMeta getTipoSituacao() {
		return tipoSituacao;
	}

	public void setTipoSituacao(TipoSituacaoMeta acao) {
		this.tipoSituacao = acao;
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

	public HistoricoMetaEntidadeVO getSituacaoAnterior() {
		return situacaoAnterior;
	}

	public void setSituacaoAnterior(HistoricoMetaEntidadeVO situacaoAnterior) {
		this.situacaoAnterior = situacaoAnterior;
	}

	public HistoricoMetaEntidadeVO getSituacaoAtual() {
		return situacaoAtual;
	}

	public void setSituacaoAtual(HistoricoMetaEntidadeVO situacaoAtual) {
		this.situacaoAtual = situacaoAtual;
	}

	public HistoricoMetaEntidadeVO getSituacaoDesejada() {
		return situacaoDesejada;
	}

	public void setSituacaoDesejada(HistoricoMetaEntidadeVO situacaoDesejada) {
		this.situacaoDesejada = situacaoDesejada;
	}

	public List<AnotacaoVO> getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(List<AnotacaoVO> observacoes) {
		this.observacoes = observacoes;
	}

	// TODO: REMOVER
	// public List<AnotacaoVO> getAlertas() {
	// return alertas;
	// }

	// public void setAlertas(List<AnotacaoVO> alertas) {
	// this.alertas = alertas;
	// }

	public List<MetaForm> getDependencias() {
		return dependencias;
	}

	public void setDependencias(List<MetaForm> dependencias) {
		this.dependencias = dependencias;
	}

	public List<HistoricoMetaEntidadeVO> getHistorico() {
		return historico;
	}

	public void setHistorico(List<HistoricoMetaEntidadeVO> historico) {
		this.historico = historico;
	}

	public void addHistorico(HistoricoMetaEntidadeVO hmeform) {
		if (this.historico == null) {
			this.historico = new ArrayList<HistoricoMetaEntidadeVO>();
		}
		this.historico.add(hmeform);
	}

	public void load(MetaEntidade metaEntidade, HistoricoMetaEntidade atual, RodizioVO ciclo, boolean loadAtividade) {

		this.setId(metaEntidade.getId());
		// TODO: Como setar rodizio
		this.setInstituto(new InstitutoOptionForm(metaEntidade.getInstituto()));
		this.setEntidade(new EntidadeOptionForm(metaEntidade.getEntidade()));
		this.setCiclo(ciclo);
		if(loadAtividade) {
			this.setAtividade(new MetaInstitutoVO(metaEntidade.getMeta()));
		} else {
			this.setAtividade(new MetaInstitutoVO());
			this.getAtividade().setId(metaEntidade.getMeta().getId());
			this.getAtividade().setDescricao(metaEntidade.getMeta().getDescricao());
			this.getAtividade().setTipoMeta(metaEntidade.getMeta().getTipoMeta());
			this.getAtividade().setViewOrder(metaEntidade.getMeta().getViewOrder());
		}

		// Estado Atual
		this.responsavel = new PessoaOptionForm(atual.getResponsavel());
		this.tipoSituacao = atual.getTipoSituacao();
		this.dataSituacao = atual.getDataSituacao();
		this.situacao = atual.getSituacao();
		this.previsao = atual.getPrevisao();
		this.previsto = atual.getPrevisto();
		this.conclusao = atual.getConclusao();
		this.realizado = atual.getRealizado();

		this.setObservacoes(new ArrayList<AnotacaoVO>());
		if (metaEntidade.getAnotacoes() != null) {
			for (MetaEntidadeAnotacao an : metaEntidade.getAnotacoes()) {
				this.getObservacoes().add(new AnotacaoVO(an));
			}
		}

		for (HistoricoMetaEntidade historico : metaEntidade.getHistorico()) {
			HistoricoMetaEntidadeVO hmeform = new HistoricoMetaEntidadeVO(historico);
			this.addHistorico(hmeform);
		}
	}

	@Override
	public String toString() {
		return "MetaForm [id=" + id + ", ciclo=" + ciclo + ", entidade=" + entidade + ", instituto=" + instituto
				+ ", situacao=" + situacao + "]";
	}

}
