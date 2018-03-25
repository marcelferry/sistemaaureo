package com.concafras.gestao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.enums.EstadoCivil;
import com.concafras.gestao.enums.TipoContatoInternet;
import com.concafras.gestao.util.Util;

@Entity
@Table(name="PESSOAS")
@Access(AccessType.FIELD)
public class Pessoa extends ObjetoGerenciado {

  /**
   * Generated SerialVerionUID
   */
  private static final long serialVersionUID = -8553743859436274485L;

    @Id
    @SequenceGenerator(name = "pessoa_id_seq_name", sequenceName = "pessoa_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "pessoa_id_seq_name", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    private Integer codigo;

    @Column(name = "nomeCompleto")
    private String nome;
    
    private String nomeCracha;
    
    private String documentoId;
    
    private String orgaoEmissor;
    
    private String ufEmissor;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    
    private String cpf;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    
    private String naturalidade;
    
    private String nacionalidade;
    
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;
    
    @OneToOne(cascade = CascadeType.ALL)
  	@JoinColumn(name = "idEndereco", referencedColumnName = "id")
  	@XmlElement
  	private Endereco endereco;
    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(
        name="PESSOA_ENDERECO"
        , joinColumns={
            @JoinColumn(name="idPessoa", referencedColumnName="id")
            }
        , inverseJoinColumns={
            @JoinColumn(name="idEndereco",  referencedColumnName="id")
            }
        )
	@XmlElement
    private List<Endereco> enderecos;
    
    @OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="PESSOA_TELEFONE",
    joinColumns = @JoinColumn(name = "idPessoa", 
                              referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "idTelefone", 
                              referencedColumnName = "id"))
	@XmlElement
    private List<Telefone> telefones;
    
    @OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="PESSOA_CONTATO",
    joinColumns = @JoinColumn(name = "idPessoa", 
                              referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "idContato", 
                              referencedColumnName = "id"))
	@XmlElement
    private List<ContatoInternet> emails;
    
    private String profissao;
    
    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idTrabalhador", referencedColumnName = "id")
	@XmlElement
    private List<TrabalhadorPosto> trabalhadorPostos;
    
    private boolean associado;
    
    @OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="PESSOA_ANOTACAO",
    joinColumns = @JoinColumn(name = "idPessoa", 
                              referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "idAnotacao", 
                              referencedColumnName = "id"))
	@XmlElement
    private List<Anotacao> anotacoes;
    
    public Pessoa() {
		// TODO Auto-generated constructor stub
	}

    public Pessoa(Integer id, String nome, String cpf, String cidade, String estado) {
      this(id,nome, cpf, cidade, estado, null);
    }
    
	public Pessoa(Integer id, String nome, String cpf, String cidade, String estado, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = new Endereco();
		this.endereco.setCidade(new Cidade());
		this.endereco.getCidade().setNome(cidade);
		this.endereco.getCidade().setEstado(new Estado());
		this.endereco.getCidade().getEstado().setSigla(estado);
		if(email != null){
  		this.setEmails(new ArrayList<ContatoInternet>());
  		this.getEmails().add(new ContatoInternet(TipoContatoInternet.EMAILPES));
  		this.getEmails().get(0).setContato(email);
		}
	}

	public Pessoa(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNomeCracha() {
		return nomeCracha;
	}
	
	public void setNomeCracha(String nomeCracha) {
		this.nomeCracha = nomeCracha;
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String rg) {
		this.documentoId = rg;
	}
	
	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}
	
	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}
	
	public String getUfEmissor() {
		return ufEmissor;
	}
	
	public void setUfEmissor(String ufEmissor) {
		this.ufEmissor = ufEmissor;
	}
	
	public Date getDataEmissao() {
		return dataEmissao;
	}
	
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	
	public String getCpf() {
	  if(cpf != null){
      return cpf.replaceAll("[^0-9]+", "");
    }
      return null;
	}

	public void setCpf(String cpf) {
	  if(cpf != null){
	    cpf = cpf.replaceAll("[^0-9]+", "");
    }
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNaturalidade() {
		return naturalidade;
	}
	
	public String getNacionalidade() {
		return nacionalidade;
	}
	
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
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

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public List<TrabalhadorPosto> getTrabalhadorPostos() {
		return trabalhadorPostos;
	}

	public void setTrabalhadorPostos(List<TrabalhadorPosto> trabalhadorPosto) {
		this.trabalhadorPostos = trabalhadorPosto;
	}

	public boolean isAssociado() {
		return associado;
	}

	public void setAssociado(boolean associado) {
		this.associado = associado;
	}

	public List<Anotacao> getAnotacoes() {
		return anotacoes;
	}

	public void setAnotacoes(List<Anotacao> anotacoes) {
		this.anotacoes = anotacoes;
	}
	
	public String getPrimeiroNome(){
		String importado = getNome();
		String primeiroNome = !Util.isNullOrEmpty( importado)? importado.indexOf(' ') != -1? importado.substring(0, importado.indexOf(' ')): importado:""; 
		return primeiroNome;
	}
	
	public String getPrimeiroEmail(){
		return getEmail(getEmails());
	}
	
  protected String getEmail(List<ContatoInternet> emails) {
    String fone = "";
    if (emails != null){
      
      for (Iterator<ContatoInternet> iterator = emails.iterator(); iterator.hasNext();) {
        ContatoInternet email = iterator.next();
        if (email.isPrincipal()) {
          return email.getContato();
        }
      }
      
      for (Iterator<ContatoInternet> iterator = emails.iterator(); iterator.hasNext();) {
        ContatoInternet email = iterator.next();
        if (email.getTipo() == TipoContatoInternet.EMAILPES
            || email.getTipo() == TipoContatoInternet.EMAILCOM) {
          return email.getContato();
        }
      }
    }
    return fone;
  }
	
	public String getPrimeiroTelefone(){
		return getTelefone(getTelefones());
	}
	
	protected String getTelefone(List<Telefone> telefones) {
    	String fone = "";
    	if(telefones != null)
	    	for (Iterator<Telefone> iterator = telefones.iterator(); iterator.hasNext();) {
				Telefone telefone = iterator.next();
				fone = telefone.getDdd() + " " + telefone.getNumero();
				if(fone.length() > 1){
					return fone;
				}
			}
    	return fone;
	}

	@Override
	public String toString() {
		return this.nome;
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
    Pessoa other = (Pessoa) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
	
	
    
}
