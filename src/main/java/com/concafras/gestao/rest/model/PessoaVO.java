package com.concafras.gestao.rest.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.concafras.gestao.enums.EstadoCivil;
import com.concafras.gestao.enums.TipoContatoInternet;
import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.ObjetoGerenciado;
import com.concafras.gestao.model.Telefone;
import com.concafras.gestao.util.Util;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PessoaVO extends ObjetoGerenciado {

	/**
	 * Generated SerialVerionUID
	 */
	private static final long serialVersionUID = -8553743859436274485L;

	private Integer id;

	private Integer codigo;

	private String nomeCompleto;

	private String nomeCracha;

	private String documentoId;

	private String orgaoEmissor;

	private String ufEmissor;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataEmissao;

	private String cpf;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;

	private String naturalidade;

	private String nacionalidade;

	private EstadoCivil estadoCivil;

	private EnderecoVO endereco;

	private String profissao;

	private boolean associado;

	private List<AnotacaoVO> anotacoes;

	public PessoaVO() {
		// TODO Auto-generated constructor stub
	}

	public PessoaVO(Integer id, String nomeCompleto, String cpf, String cidade, String estado) {
		this(id, nomeCompleto, cpf, cidade, estado, null);
	}

	public PessoaVO(Integer id, String nomeCompleto, String cpf, String cidade, String estado, String email) {
		super();
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.cpf = cpf;
		this.endereco = new EnderecoVO();
		this.endereco.setCidade(new CidadeVO());
		this.endereco.getCidade().setNome(cidade);
		this.endereco.getCidade().setEstado(new EstadoVO());
		this.endereco.getCidade().getEstado().setSigla(estado);
	}

	public PessoaVO(Integer id, String nomeCompleto) {
		super();
		this.id = id;
		this.nomeCompleto = nomeCompleto;
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

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
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
		return cpf;
	}

	public void setCpf(String cpf) {
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

	public EnderecoVO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoVO endereco) {
		this.endereco = endereco;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public boolean isAssociado() {
		return associado;
	}

	public void setAssociado(boolean associado) {
		this.associado = associado;
	}

	public String getNome() {
		return this.nomeCompleto;
	}

	public String getPrimeiroNome() {
		String importado = getNomeCompleto();
		String primeiroNome = !Util.isNullOrEmpty(importado)
				? importado.indexOf(' ') != -1 ? importado.substring(0, importado.indexOf(' ')) : importado
				: "";
		return primeiroNome;
	}

	public void setNome(String importado) {
		// String primeiroNome = !Util.isNullOrEmpty( importado)? importado.indexOf(' ')
		// != -1? importado.substring(0, importado.indexOf(' ')): "":"";
		// String sobreNome = !Util.isNullOrEmpty(importado) ? importado.indexOf(' ') !=
		// -1? importado.substring(importado.indexOf(' ') + 1): "":"";
		this.nomeCompleto = importado;
	}

	public String getEmail(List<ContatoInternet> emails) {
		String fone = "";
		if (emails != null) {

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

	protected String getTelefone(List<Telefone> telefones) {
		String fone = "";
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
	public String toString() {
		return this.nomeCompleto;
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
		PessoaVO other = (PessoaVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
