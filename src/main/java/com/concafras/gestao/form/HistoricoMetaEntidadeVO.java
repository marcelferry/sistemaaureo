package com.concafras.gestao.form;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

public class HistoricoMetaEntidadeVO {

	private Long id;

	private MetaForm meta;

	private RodizioVO ciclo;

	private String tipoResponsavel;

	private PessoaOptionForm responsavel;

	private TipoSituacaoMeta tipoSituacao;

	private SituacaoMeta situacao;

	private boolean notificar;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataSituacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
	@DateTimeFormat(pattern = "MM/yyyy")
	private Date previsao;

	private BigDecimal previsto;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
	@DateTimeFormat(pattern = "MM/yyyy")
	private Date conclusao;

	private BigDecimal realizado;

	public HistoricoMetaEntidadeVO() {
		// TODO Auto-generated constructor stub
	}

	public HistoricoMetaEntidadeVO(HistoricoMetaEntidade historico) {
		if (historico != null) {
			this.id = historico.getId();
			this.ciclo = new RodizioVO(historico.getRodizio());
			this.responsavel = new PessoaOptionForm(historico.getResponsavel());
			this.tipoSituacao = historico.getTipoSituacao();
			this.notificar = historico.isNotificar();
			this.dataSituacao = historico.getDataSituacao();
			this.situacao = historico.getSituacao();
			this.previsao = historico.getPrevisao();
			this.previsto = historico.getPrevisto();
			this.conclusao = historico.getConclusao();
			this.realizado = historico.getRealizado();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RodizioVO getCiclo() {
		return ciclo;
	}

	public void setCiclo(RodizioVO ciclo) {
		this.ciclo = ciclo;
	}

	public MetaForm getMeta() {
		return meta;
	}

	public void setMeta(MetaForm meta) {
		this.meta = meta;
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

	public void setTipoResponsavel(String string) {
		this.tipoResponsavel = string;
	}

	public String getTipoResponsavel() {
		return tipoResponsavel;
	}

	@Override
	public String toString() {
		return "HistoricoMetaEntidadeVO [id=" + id + ", meta=" + meta + ", ciclo=" + ciclo + ", tipoResponsavel="
				+ tipoResponsavel + ", responsavel=" + responsavel + ", tipoSituacao=" + tipoSituacao + ", situacao="
				+ situacao + ", notificar=" + notificar + ", dataSituacao=" + dataSituacao + ", previsao=" + previsao
				+ ", previsto=" + previsto + ", conclusao=" + conclusao + ", realizado=" + realizado + "]";
	}

}
