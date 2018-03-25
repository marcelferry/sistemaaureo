package com.concafras.gestao.model.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.concafras.gestao.enums.EstadoCivil;
import com.concafras.gestao.enums.TipoContatoInternet;
import com.concafras.gestao.enums.TipoTelefone;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Telefone;
import com.concafras.gestao.util.Util;

public class PessoaExcel implements Serializable{
	
		public String nome;
		public String pai;
		public String mae;
		public String rg;
		public String cpf;
		public String dataNascimento;
		public String naturalidade;
		public String estadoCivil;
		public EstadoCivil enumEstadoCivil;
		public String conjuge;
		public String endereco;
		public String numero;
		public String bairro;
		public String cep;
		public String estado;
		public String cidade;
		public String telResDDD;
		public String telResNum;
		public String telComDDD;
		public String telComNum;
		public String telCelDDD;
		public String telCelNum;
		public String telCel2DDD;
		public String telCel2Num;
		public String email;
		public String facebook;
		public String profissao;
		public String trabalhaPosto;
		public String posto;
		public String associado;
		public String anotacoes;
		
		
		public String getNome() {
			return nome;
		}



		public void setNome(String nome) {
			this.nome = nome;
		}



		public String getPai() {
			return pai;
		}



		public void setPai(String pai) {
			this.pai = pai;
		}



		public String getMae() {
			return mae;
		}



		public void setMae(String mae) {
			this.mae = mae;
		}



		public String getRg() {
			return rg;
		}



		public void setRg(String rg) {
			this.rg = rg;
		}



		public String getCpf() {
			return cpf;
		}



		public void setCpf(String cpf) {
			this.cpf = cpf;
		}



		public String getDataNascimento() {
			return dataNascimento;
		}



		public void setDataNascimento(String dataNascimento) {
			this.dataNascimento = dataNascimento;
		}



		public String getNaturalidade() {
			return naturalidade;
		}



		public void setNaturalidade(String naturalidade) {
			this.naturalidade = naturalidade;
		}



		public String getEstadoCivil() {
			return estadoCivil;
		}



		public void setEstadoCivil(String estadoCivil) {
			this.estadoCivil = estadoCivil;
		    List<EstadoCivil> estadocivilList = new ArrayList<EstadoCivil>( Arrays.asList(EstadoCivil.values() ));
		    for (EstadoCivil ec : estadocivilList) {
		    	System.out.println("'" + ec.toString().toUpperCase() + "'");
		    	System.out.println("'" + estadoCivil.toUpperCase() + "'");
				if(ec.toString().toUpperCase().equals(estadoCivil.toUpperCase())){
					enumEstadoCivil = ec;
				}
			}
		}
		
		
		public EstadoCivil getEnumEstadoCivil() {
			return enumEstadoCivil;
		}
		
		
		public void setEnumEstadoCivil(EstadoCivil enumEstadoCivil) {
			this.enumEstadoCivil = enumEstadoCivil;
		}



		public String getConjuge() {
			return conjuge;
		}



		public void setConjuge(String conjuge) {
			this.conjuge = conjuge;
		}



		public String getEndereco() {
			return endereco;
		}



		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}



		public String getNumero() {
			return numero;
		}



		public void setNumero(String numero) {
			this.numero = numero;
		}



		public String getBairro() {
			return bairro;
		}



		public void setBairro(String bairro) {
			this.bairro = bairro;
		}



		public String getCep() {
			return cep;
		}



		public void setCep(String cep) {
			this.cep = cep;
		}



		public String getEstado() {
			return estado;
		}



		public void setEstado(String estado) {
			this.estado = estado;
		}



		public String getCidade() {
			return cidade;
		}



		public void setCidade(String cidade) {
			this.cidade = cidade;
		}



		public String getTelResDDD() {
			return telResDDD;
		}



		public void setTelResDDD(String telResDDD) {
			this.telResDDD = telResDDD;
		}



		public String getTelResNum() {
			return telResNum;
		}



		public void setTelResNum(String telResNum) {
			this.telResNum = telResNum;
		}



		public String getTelComDDD() {
			return telComDDD;
		}



		public void setTelComDDD(String telComDDD) {
			this.telComDDD = telComDDD;
		}



		public String getTelComNum() {
			return telComNum;
		}



		public void setTelComNum(String telComNum) {
			this.telComNum = telComNum;
		}



		public String getTelCelDDD() {
			return telCelDDD;
		}



		public void setTelCelDDD(String telCelDDD) {
			this.telCelDDD = telCelDDD;
		}



		public String getTelCelNum() {
			return telCelNum;
		}



		public void setTelCelNum(String telCelNum) {
			this.telCelNum = telCelNum;
		}



		public String getTelCel2DDD() {
			return telCel2DDD;
		}



		public void setTelCel2DDD(String telCel2DDD) {
			this.telCel2DDD = telCel2DDD;
		}



		public String getTelCel2Num() {
			return telCel2Num;
		}



		public void setTelCel2Num(String telCel2Num) {
			this.telCel2Num = telCel2Num;
		}



		public String getEmail() {
			return email;
		}



		public void setEmail(String email) {
			this.email = email;
		}



		public String getFacebook() {
			return facebook;
		}



		public void setFacebook(String facebook) {
			this.facebook = facebook;
		}



		public String getProfissao() {
			return profissao;
		}



		public void setProfissao(String profissao) {
			this.profissao = profissao;
		}



		public String getTrabalhaPosto() {
			return trabalhaPosto;
		}



		public void setTrabalhaPosto(String trabalhaPosto) {
			this.trabalhaPosto = trabalhaPosto;
		}



		public String getPosto() {
			return posto;
		}



		public void setPosto(String posto) {
			this.posto = posto;
		}



		public String getAssociado() {
			return associado;
		}



		public void setAssociado(String associado) {
			this.associado = associado;
		}



		public String getAnotacoes() {
			return anotacoes;
		}



		public void setAnotacoes(String anotacoes) {
			this.anotacoes = anotacoes;
		}



		@Override
		public String toString() {
			return "PessoaExcel [nome=" + nome + ", pai=" + pai + ", mae="
					+ mae + ", rg=" + rg + ", cpf=" + cpf + ", dataNascimento="
					+ dataNascimento + ", naturalidade=" + naturalidade
					+ ", estadoCivil=" + estadoCivil + ", conjuge=" + conjuge
					+ ", endereco=" + endereco + ", numero=" + numero
					+ ", bairro=" + bairro + ", cep=" + cep + ", estado="
					+ estado + ", cidade=" + cidade + ", telResDDD="
					+ telResDDD + ", telResNum=" + telResNum + ", telComDDD="
					+ telComDDD + ", telComNum=" + telComNum + ", telCelDDD="
					+ telCelDDD + ", telCelNum=" + telCelNum + ", telCel2DDD="
					+ telCel2DDD + ", telCel2Num=" + telCel2Num + ", email="
					+ email + ", facebook=" + facebook + ", profissao="
					+ profissao + ", trabalhaPosto=" + trabalhaPosto
					+ ", posto=" + posto + ", associado=" + associado
					+ ", anotacoes=" + anotacoes + "]";
		}



		public static Pessoa getPessoa(PessoaExcel importado) {
			
			System.out.println(importado);
			
			Pessoa pessoa = new Pessoa();
			//Nome
			String primeiroNome = !Util.isNullOrEmpty( importado.getNome() )? importado.getNome().indexOf(' ') != -1? importado.getNome().substring(0, importado.getNome().indexOf(' ')): "":""; 
			String sobreNome = !Util.isNullOrEmpty(importado.getNome() ) ? importado.getNome().indexOf(' ') != -1? importado.getNome().substring(importado.getNome().indexOf(' ') + 1): "":""; 
			pessoa.setNome(importado.getNome());
			pessoa.setNomeCracha(primeiroNome);

			//RG
			String rg = !Util.isNullOrEmpty( importado.getRg() ) ? importado.getRg().replace("[^\\d]", ""):"";
			pessoa.setDocumentoId(rg);
		    //orgaoEmissor;
			//ufEmissor;
		    //dataEmissao;
		    
			//CPF
			String cpf = !Util.isNullOrEmpty( importado.getCpf() ) ? importado.getRg().replace("[^\\d]", ""):"";
			pessoa.setCpf(cpf);
			
			//DataNascimento
			String dataNascimento = importado.getDataNascimento();
			if(!Util.isNullOrEmpty(dataNascimento)){
				Date data = Util.formatDataPadrao(dataNascimento);
				pessoa.setDataNascimento(data);
			}
			
			//Naturalidade
			String naturalidade = importado.getNaturalidade();
		    pessoa.setNaturalidade(naturalidade);
		    
		    //Nacionalidade
	
		    
		    //EstadoCivil
		    EstadoCivil estadoCivil = importado.getEnumEstadoCivil();
			pessoa.setEstadoCivil(estadoCivil);
		    
//		    
//		    @OneToMany(cascade = CascadeType.ALL)
//		    @JoinTable(
//		        name="PESSOA_ENDERECO"
//		        , joinColumns={
//		            @JoinColumn(name="IdPessoa", referencedColumnName="id")
//		            }
//		        , inverseJoinColumns={
//		            @JoinColumn(name="IdColaborador",  referencedColumnName="id")
//		            }
//		        )
//			@XmlElement
//		    private Set<Endereco> enderecos;
//		    
			
			// Telefones
			List<Telefone> telefones = new ArrayList<Telefone>();
			String ddd = importado.getTelResDDD();
			String num = importado.getTelResNum();
			if(!StringUtils.isEmpty(ddd) && !StringUtils.isEmpty(num)){
				Telefone tel = new Telefone();
				tel.setTipo(TipoTelefone.FIXORES);
				tel.setDdd(ddd);
				tel.setNumero(num);
				telefones.add(tel);
			}
			ddd = importado.getTelComDDD();
			num = importado.getTelComNum();
			if(!StringUtils.isEmpty(ddd) && !StringUtils.isEmpty(num)){
				Telefone tel = new Telefone();
				tel.setTipo(TipoTelefone.FIXOCOM);
				tel.setDdd(ddd);
				tel.setNumero(num);
				telefones.add(tel);
			}
			
			ddd = importado.getTelCelDDD();
			num = importado.getTelCelNum();
			if(!StringUtils.isEmpty(ddd) && !StringUtils.isEmpty(num)){
				Telefone tel = new Telefone();
				tel.setTipo(TipoTelefone.CELULAR);
				tel.setDdd(ddd);
				tel.setNumero(num);
				telefones.add(tel);
			}
			ddd = importado.getTelCel2DDD();
			num = importado.getTelCel2Num();
			if(!StringUtils.isEmpty(ddd) && !StringUtils.isEmpty(num)){
				Telefone tel = new Telefone();
				tel.setTipo(TipoTelefone.CELREC);
				tel.setDdd(ddd);
				tel.setNumero(num);
				telefones.add(tel);
			}
			
			if(telefones.size() > 0){
				pessoa.setTelefones(telefones);
			}
			
			
			// Contatos Internet
			List<ContatoInternet> emails = new ArrayList<ContatoInternet>();
			String email = importado.getEmail();
			
			if(email != null && !email.trim().equals("") ){
				ContatoInternet ci = new ContatoInternet();
				ci.setTipo(TipoContatoInternet.EMAILPES);
				ci.setContato(email);
				emails.add(ci);
			}
			
			String facebook = importado.getFacebook();
			if(facebook != null && !facebook.trim().equals("") ){
				ContatoInternet ci = new ContatoInternet();
				ci.setTipo(TipoContatoInternet.FACEBOOK);
				ci.setContato(facebook);
				emails.add(ci);
			}
			
			if(emails.size() > 0){
				pessoa.setEmails(emails);
			}
			
			//Profissao
			String profissao = importado.getProfissao();
			pessoa.setProfissao(profissao);

//		    @OneToMany(cascade = CascadeType.ALL)
//			@JoinColumn(name = "idEstado", referencedColumnName = "id")
//			@XmlElement
//		    private List<TrabalhadorPosto> trabalhadorPostos;
//		    
//		    private boolean associado;
//		    
//		    @OneToMany(cascade = CascadeType.ALL)
//			@JoinColumn(name = "idPessoa", referencedColumnName = "id")
//			@XmlElement
//		    private List<AnotacaoVO> anotacoes;
			
			List<Anotacao> anotacoes = new ArrayList<Anotacao>();
			String anotacao = importado.getAnotacoes();
			if(!StringUtils.isEmpty(anotacao)){
				Anotacao an = new Anotacao();
				an.setData(new Date());
				an.setTexto(anotacao);
				anotacoes.add(an);
			}
			
			
			System.out.println(pessoa);
			
			return pessoa;
		}
		
		
	}