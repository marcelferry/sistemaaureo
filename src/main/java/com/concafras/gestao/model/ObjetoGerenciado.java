package com.concafras.gestao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.security.UsuarioAutenticado;

@MappedSuperclass
@Access(AccessType.FIELD)
public class ObjetoGerenciado implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -7733218713482605696L;
  
  
  
	// ************************************************************************
	// Trilha de Rastreamento
	// ************************************************************************

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Usuario.class)
	@JoinColumn(name = "idUsuarioCadastro", referencedColumnName = "id")
	@XmlElement
	private Usuario usuarioCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Usuario.class)
	@JoinColumn(name = "idUsuarioAlteracao", referencedColumnName = "id")
	@XmlElement
	private Usuario usuarioAlteracao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Usuario.class)
	@JoinColumn(name = "idUsuarioExclusao", referencedColumnName = "id")
	@XmlElement
	private Usuario usuarioExclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataExclusao;

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Usuario getUsuarioExclusao() {
		return usuarioExclusao;
	}

	public void setUsuarioExclusao(Usuario usuarioExclusao) {
		this.usuarioExclusao = usuarioExclusao;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	
	@PrePersist
  protected void onPrePersist(){
      populateTimestamp();
  }
  
  @PreUpdate
  protected void onPreUpdate(){
      populateTimestamp();
  }
  
  protected void populateTimestamp() {
    Authentication auth = SecurityContextHolder.getContext()
        .getAuthentication();
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    if (userDetails instanceof UsuarioAutenticado) {
      UsuarioAutenticado usuario = (UsuarioAutenticado) userDetails;
      Usuario usuarioLogado = usuario.getUsuario();
      if (usuarioLogado != null) {
        if (this.getUsuarioCadastro() == null) {
          this.setUsuarioCadastro(usuarioLogado);
          this.setUsuarioAlteracao(usuarioLogado);
        } else {
          this.setUsuarioAlteracao(usuarioLogado);
        }
      }
    }

    if (this.getDataCadastro() == null) {
      this.setDataCadastro(new Date());
      this.setDataAlteracao(new Date());
    } else {
      this.setDataAlteracao(new Date());
    }
  }
	

}
