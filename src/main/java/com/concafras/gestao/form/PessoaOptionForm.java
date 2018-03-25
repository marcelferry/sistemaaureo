package com.concafras.gestao.form;

import com.concafras.gestao.model.Pessoa;

public class PessoaOptionForm {

	private Integer id;
	private String nome;
	private String cpf;
	private String cidade;
	private String uf;
	private String telefone;
	private String email;

	public PessoaOptionForm() {
		// TODO Auto-generated constructor stub
	}

	public PessoaOptionForm(Pessoa pessoa) {
		if (pessoa != null) {
			this.id = pessoa.getId();
			this.nome = pessoa.getNome();
			this.cpf = pessoa.getCpf();
			if (pessoa.getEndereco() != null && pessoa.getEndereco().getCidade() != null) {
				this.cidade = pessoa.getEndereco().getCidade().getNome();
				this.uf = pessoa.getEndereco().getCidade().getEstado().getSigla();
			}
			this.email = pessoa.getPrimeiroEmail();
			this.telefone = pessoa.getPrimeiroTelefone();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
