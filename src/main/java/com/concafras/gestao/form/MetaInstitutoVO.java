package com.concafras.gestao.form;

import java.util.ArrayList;
import java.util.List;

import com.concafras.gestao.enums.TipoMeta;
import com.concafras.gestao.model.MetaInstituto;

public class MetaInstitutoVO {
	
	private Integer id;
	
	private String descricao;
	
	private TipoMeta tipoMeta;
	
	private int viewOrder;
	
	private Integer prioridade;
	
	private RodizioVO rodizio;
	
	private InstitutoOptionForm instituto;
	
	private MetaInstitutoVO pai;
	
	private AtividadeVO atividade;	
	
	private List<MetaInstitutoVO> dependencias;
	
	private List<MetaInstitutoVO> itens;
	
	
	public MetaInstitutoVO() {
		// TODO Auto-generated constructor stub
	}

	public MetaInstitutoVO(Integer id, String descricao, TipoMeta tipoMeta, Integer prioridade, int viewOrder) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.tipoMeta = tipoMeta;
		this.prioridade = prioridade;
		this.viewOrder = viewOrder;
	}
	
	public MetaInstitutoVO(MetaInstituto meta) {
		this(meta, true, true);
	}

	public MetaInstitutoVO(MetaInstituto meta, boolean dependencias, boolean pai) {
		this.id = meta.getId();
		this.descricao = meta.getDescricao();
		this.prioridade = meta.getPrioridade();
		this.tipoMeta = meta.getTipoMeta();
		this.viewOrder = meta.getViewOrder();
		if(meta.getDependencias() != null && dependencias) {
			this.dependencias = new ArrayList<MetaInstitutoVO>();
			for (MetaInstituto metaInstituto : meta.getDependencias()) {
				MetaInstitutoVO aux = new MetaInstitutoVO(metaInstituto, false, false);
				this.dependencias.add(aux);
			}
		}
		if(meta.getPai() != null && pai) { 
			this.pai = new MetaInstitutoVO(meta.getPai(), false, false);
		}
		if(meta.getItens() != null) {
			this.itens = new ArrayList<MetaInstitutoVO>();
			for (MetaInstituto metaInstituto : meta.getItens()) {
				MetaInstitutoVO aux = new MetaInstitutoVO(metaInstituto, false, false);
				this.itens.add(aux);
			}
		}
		this.rodizio = new RodizioVO( meta.getRodizio() );
		this.instituto = new InstitutoOptionForm( meta.getInstituto() );
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

	public AtividadeVO getAtividade() {
		return atividade;
	}

	public void setAtividade(AtividadeVO atividadeVO) {
		this.atividade = atividadeVO;
	}
	
	public MetaInstitutoVO getPai() {
		return pai;
	}
	
	public void setPai(MetaInstitutoVO pai) {
		this.pai = pai;
	}

	public List<MetaInstitutoVO> getDependencias() {
		return dependencias;
	}

	public void setDependencias(List<MetaInstitutoVO> dependencias) {
		this.dependencias = dependencias;
	}
	
	public List<MetaInstitutoVO> getItens() {
		return itens;
	}
	
	public void setItens(List<MetaInstitutoVO> items) {
		this.itens = items;
	}
	
	public InstitutoOptionForm getInstituto() {
		return instituto;
	}
	
	public void setInstituto(InstitutoOptionForm instituto) {
		this.instituto = instituto;
	}
	
	public RodizioVO getRodizio() {
		return rodizio;
	}
	
	public void setRodizio(RodizioVO rodizio) {
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
		MetaInstitutoVO other = (MetaInstitutoVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

  @Override
  public String toString() {
    return "MetaInstitutoVO [id=" + id + ", descricao=" + descricao
        + ", tipoMeta=" + tipoMeta + ", rodizio=" + rodizio + ", instituto="
        + instituto + "]";
  }
	
	
	
	
	
}
