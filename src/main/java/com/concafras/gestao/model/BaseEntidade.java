package com.concafras.gestao.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.enums.TipoEntidade;

@Entity
@Table(name = "ENTIDADES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "NIVEL")
@Access(AccessType.FIELD)
public class BaseEntidade extends ObjetoGerenciado {

  /**
   * 
   */
  private static final long serialVersionUID = 2584769651433822641L;

  @Id
  @SequenceGenerator(name = "entidade_id_seq_name", sequenceName = "entidade_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "entidade_id_seq_name", strategy = GenerationType.SEQUENCE)
  protected Integer id;

  protected Integer codigo;

  protected String cnpj;

  protected String inscricao;

  protected String razaoSocial;

  protected String nomeFantasia;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "idEndereco", referencedColumnName = "id")
  @XmlElement
  protected Endereco endereco;

  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Temporal(TemporalType.DATE)
  protected Date dataFundacao;

  protected TipoEntidade tipoEntidade;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "idPresidente", referencedColumnName = "id")
  @XmlElement
  protected Presidente presidente;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "ENTIDADE_PRESIDENTE", joinColumns = @JoinColumn(name = "idEntidade", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "idPresidente", referencedColumnName = "id"))
  protected List<Presidente> presidentes;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "ENTIDADE_INSTITUTO", joinColumns = @JoinColumn(name = "idEntidade", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "idInstituto", referencedColumnName = "id"))
  protected List<BaseInstituto> listaInstitutos;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidade")
  protected List<Dirigente> dirigentes;

  @ManyToOne
  @JoinColumn(name = "idEntidadePai", referencedColumnName = "id")
  @XmlElement
  protected BaseEntidade entidadePai;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "ENTIDADE_TELEFONE", joinColumns = @JoinColumn(name = "idEntidade", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "idTelefone", referencedColumnName = "id"))
  @XmlElement
  protected List<Telefone> telefones;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "ENTIDADE_CONTATO", joinColumns = @JoinColumn(name = "idEntidade", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "idContato", referencedColumnName = "id"))
  @XmlElement
  protected List<ContatoInternet> emails;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidade")
  protected List<PlanoMetas> planosMetas;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidade")
  protected List<AtividadeImplantada> atividadesImplantadas;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "ENTIDADE_ANOTACAO", joinColumns = @JoinColumn(name = "idEntidade", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "idAnotacao", referencedColumnName = "id"))
  @XmlElement
  protected List<Anotacao> anotacoes;

  @ManyToMany
  @JoinTable(name = "ENTIDADE_TRABALHADOR", joinColumns = @JoinColumn(name = "idEntidade", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "idPessoa", referencedColumnName = "id"))
  @XmlElement
  protected List<Pessoa> trabalhadores;

  @Column(nullable = true)
  protected boolean rodizio;

  public BaseEntidade() {
    // TODO Auto-generated constructor stub
  }

  public BaseEntidade(Integer id, String razaoSocial, String cnpj,
      String logradouro, String numero, String cidade, String estado,
      Integer idPresidente, String presidente) {
    super();
    this.id = id;
    this.razaoSocial = razaoSocial;
    this.cnpj = cnpj;
    this.endereco = new Endereco();
    this.endereco.setLogradouro(logradouro);
    this.endereco.setNumero(numero);
    this.endereco.setCidade(new Cidade());
    this.endereco.getCidade().setNome(cidade);
    this.endereco.getCidade().setEstado(new Estado());
    this.endereco.getCidade().getEstado().setSigla(estado);
    this.setPresidente(new Presidente());
    this.getPresidente().setPessoa(new Pessoa());
    this.getPresidente().getPessoa().setId(idPresidente);
    this.getPresidente().getPessoa().setNome(presidente);

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getInscricao() {
    return inscricao;
  }

  public void setInscricao(String inscricao) {
    this.inscricao = inscricao;
  }

  public String getRazaoSocial() {
    return razaoSocial;
  }

  public void setRazaoSocial(String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }

  public String getNomeFantasia() {
    return nomeFantasia;
  }

  public void setNomeFantasia(String nomeFantasia) {
    this.nomeFantasia = nomeFantasia;
  }

  public Date getDataFundacao() {
    return dataFundacao;
  }

  public void setDataFundacao(Date dataFundacao) {
    this.dataFundacao = dataFundacao;
  }

  public Presidente getPresidente() {
    return presidente;
  }

  public void setPresidente(Presidente presidente) {
    this.presidente = presidente;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

  public TipoEntidade getTipoEntidade() {
    return tipoEntidade;
  }

  public void setTipoEntidade(TipoEntidade tipoEntidade) {
    this.tipoEntidade = tipoEntidade;
  }

  public void setListaInstitutos(List<BaseInstituto> listaInstitutos) {
    this.listaInstitutos = listaInstitutos;
  }

  public List<BaseInstituto> getListaInstitutos() {
    return listaInstitutos;
  }

  public List<Dirigente> getDirigentes() {
    return dirigentes;
  }

  public void setDirigentes(List<Dirigente> historicoDirigentes) {
    this.dirigentes = historicoDirigentes;
  }

  public List<Telefone> getTelefones() {
    return telefones;
  }

  public void setTelefones(List<Telefone> telefones) {
    this.telefones = telefones;
  }

  public List<ContatoInternet> getEmails() {
    return emails;
  }

  public void setEmails(List<ContatoInternet> emails) {
    this.emails = emails;
  }

  public List<Presidente> getPresidentes() {
    return presidentes;
  }

  public void setPresidentes(List<Presidente> presidentes) {
    this.presidentes = presidentes;
  }

  public BaseEntidade getEntidadePai() {
    return entidadePai;
  }

  public void setEntidadePai(BaseEntidade entidade) {
    this.entidadePai = entidade;
  }

  public List<PlanoMetas> getPlanosMetas() {
    return planosMetas;
  }

  public void setPlanosMetas(List<PlanoMetas> planosMetas) {
    this.planosMetas = planosMetas;
  }

  public List<AtividadeImplantada> getAtividadesImplantadas() {
    return atividadesImplantadas;
  }

  public void setAtividadesImplantadas(
      List<AtividadeImplantada> atividadesImplantadas) {
    this.atividadesImplantadas = atividadesImplantadas;
  }

  public List<Anotacao> getAnotacoes() {
    return anotacoes;
  }

  public void setAnotacoes(List<Anotacao> anotacoes) {
    this.anotacoes = anotacoes;
  }

  public boolean isRodizio() {
    return rodizio;
  }

  public void setRodizio(boolean rodizio) {
    this.rodizio = rodizio;
  }

  public List<Pessoa> getTrabalhadores() {
    return trabalhadores;
  }

  public void setTrabalhadores(List<Pessoa> trabalhadores) {
    this.trabalhadores = trabalhadores;
  }

  public String getPrimeiroTelefone() {
    return getTelefone(getTelefones());
  }

  protected String getTelefone(List<Telefone> telefones) {
    String fone = "N/A";
    if (telefones != null)
      for (Iterator<Telefone> iterator = telefones.iterator(); iterator.hasNext();) {
        Telefone telefone = iterator.next();
        fone = telefone.getDdd() + " " + telefone.getNumero();
        if (fone.length() > 1) {
          return fone;
        }
      }
    return fone;
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
    BaseEntidade other = (BaseEntidade) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "BaseEntidade [id=" + id + ", cnpj=" + cnpj + ", razaoSocial="
        + razaoSocial + ", endereco=" + endereco + ", presidente=" + presidente
        + "]";
  }
  
  
  

}
