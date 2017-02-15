package com.concafras.gestao.form;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.enums.NivelAnotacao;
import com.concafras.gestao.enums.Sinalizador;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.Rodizio;

public class AnotacaoVO {

  private Integer id;

  private Integer remove;
  
  public AnotacaoVO() {
  }

  public AnotacaoVO(Anotacao anot, Rodizio ciclo) {
    this.id = anot.getId();
    this.ciclo = new RodizioVO(ciclo);
    this.data = anot.getData();
    this.nivel = anot.getNivel();
    this.responsavel = new PessoaOptionForm(anot.getResponsavel());
    this.sinalizador = anot.getSinalizador();
    this.texto = anot.getTexto();
  }
  
  public AnotacaoVO(Anotacao anot) {
    this(anot, null);
  }
  
  public AnotacaoVO(MetaEntidadeAnotacao an) {
    Anotacao anot = an.getAnotacao();
    this.id = anot.getId();
    this.ciclo = new RodizioVO(an.getCiclo());
    this.data = anot.getData();
    this.nivel = anot.getNivel();
    this.responsavel = new PessoaOptionForm(anot.getResponsavel());
    this.sinalizador = anot.getSinalizador();
    this.texto = anot.getTexto();
  }

  public Integer getRemove() {
    return remove;
  }

  public void setRemove(Integer remove) {
    this.remove = remove;
  }
  
  private RodizioVO ciclo;

  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date data;

  private NivelAnotacao nivel;

  private PessoaOptionForm responsavel;

  private String texto;

  private Sinalizador sinalizador;

  private AnotacaoVO emResposta;

  private List<AnotacaoVO> respostas;

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

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public PessoaOptionForm getResponsavel() {
    return responsavel;
  }

  public void setResponsavel(PessoaOptionForm responsavel) {
    this.responsavel = responsavel;
  }

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public Sinalizador getSinalizador() {
    return sinalizador;
  }

  public void setSinalizador(Sinalizador sinalizador) {
    this.sinalizador = sinalizador;
  }

  public NivelAnotacao getNivel() {
    return nivel;
  }

  public void setNivel(NivelAnotacao nivel) {
    this.nivel = nivel;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
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
    AnotacaoVO other = (AnotacaoVO) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (nivel != other.nivel)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AnotacaoVO [id=" + id + ", ciclo=" + ciclo + ", data=" + data
        + ", nivel=" + nivel + ", responsavel=" + responsavel + ", texto="
        + texto + "]";
  }
  
  
  
}
