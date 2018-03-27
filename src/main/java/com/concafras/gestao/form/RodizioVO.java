package com.concafras.gestao.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.model.Rodizio;
import com.fasterxml.jackson.annotation.JsonFormat;

public class RodizioVO {

	@NotNull
	private Integer id;

	private String ciclo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicioAjustes;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date terminoAjustes;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataAprovacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date termino;

	private RodizioVO cicloAnterior;

	public RodizioVO() {
		// TODO Auto-generated constructor stub
	}

	public RodizioVO(Rodizio rodizio) {
		this(rodizio, true, true);
	}
	
	public RodizioVO(Rodizio rodizio, boolean anterior, boolean full) {
		if (rodizio != null) {

			this.id = rodizio.getId();
			this.ciclo = rodizio.getCiclo();
			if(full) {
				this.inicioAjustes = rodizio.getInicioAjustes();
				this.terminoAjustes = rodizio.getTerminoAjustes();
				this.inicio = rodizio.getInicio();
				this.termino = rodizio.getTermino();
				this.dataAprovacao = rodizio.getDataAprovacao();
			}
			if (rodizio.getCicloAnterior() != null && anterior) {
				this.cicloAnterior = new RodizioVO(rodizio.getCicloAnterior(), false, full);
			}
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ano) {
		this.ciclo = ano;
	}

	public Date getInicioAjustes() {
		return inicioAjustes;
	}

	public void setInicioAjustes(Date inicioAjustes) {
		this.inicioAjustes = inicioAjustes;
	}

	public Date getTerminoAjustes() {
		return terminoAjustes;
	}

	public void setTerminoAjustes(Date terminoAjustes) {
		this.terminoAjustes = terminoAjustes;
	}

	public Date getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Date dataRodizio) {
		this.dataAprovacao = dataRodizio;
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

	public RodizioVO getCicloAnterior() {
		return cicloAnterior;
	}

	public void setCicloAnterior(RodizioVO cicloAnterior) {
		this.cicloAnterior = cicloAnterior;
	}

	@Override
	public String toString() {
		return "RodizioVO [id=" + id + ", ciclo=" + ciclo + "]";
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
	    if (getClass() != obj.getClass() && obj.getClass() != Rodizio.class)
	      return false;
	    
	    if(obj instanceof RodizioVO) {
		    RodizioVO other = (RodizioVO) obj;
		    if (id == null) {
		      if (other.id != null)
		        return false;
		    } else if (!id.equals(other.id))
		      return false;
	    }
	    
	    if(obj instanceof Rodizio) {
		    Rodizio other = (Rodizio) obj;
		    if (id == null) {
		      if (other.getId() != null)
		        return false;
		    } else if (!id.equals(other.getId()))
		      return false;
	    }
	    
	    return true;
	  }

}
