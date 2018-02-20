package com.concafras.gestao.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.TipoContratante;
import com.concafras.gestao.form.AnotacaoVO;
import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.PessoaOptionForm;
import com.concafras.gestao.form.PlanoMetasForm;
import com.concafras.gestao.form.RodizioVO;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.FacilitadorService;
import com.concafras.gestao.service.MetaService;
import com.concafras.gestao.service.MetasInstitutoService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.PlanoMetasService;
import com.concafras.gestao.service.RodizioService;

public class PlanoMetasHelper {
	
	  private MetasInstitutoService metaInstitutoService;
	
	  private PessoaService pessoaService;
	
	  private MetaService metaService;
	  
	  public PlanoMetasHelper(MetaService metaService, MetasInstitutoService metaInstitutoService, PessoaService pessoaService) {
		  this.metaInstitutoService = metaInstitutoService;
		  this.metaService = metaService;
		  this.pessoaService = pessoaService;
	  }

	
	public PlanoMetasForm mapPlanoMetasToPlanoMetasForm(PlanoMetas planoMetasAtual) {
	    
	    PlanoMetasForm planoMetasForm = new PlanoMetasForm();
	    
	    planoMetasForm.setId(planoMetasAtual.getId());
	    planoMetasForm.setValidado(planoMetasAtual.isValidado());
	    planoMetasForm.setFinalizado(planoMetasAtual.isFinalizado());
	    planoMetasForm.setTipoContratante(planoMetasAtual.getTipoContratante());

	    if(planoMetasAtual.getPresidente() != null) {
	    		planoMetasForm.setPresidente(  new PessoaOptionForm(  planoMetasAtual.getPresidente() ) );
	    }
	    planoMetasForm.setNomePresidente(planoMetasAtual.getNomePresidente());
	    planoMetasForm.setEmailPresidente(planoMetasAtual.getEmailPresidente());
	    planoMetasForm.setTelefonePresidente(planoMetasAtual.getTelefonePresidente());

	    if(planoMetasAtual.getCoordenador() != null) {
	    		planoMetasForm.setCoordenador( new PessoaOptionForm( planoMetasAtual.getCoordenador() ) );
	    }
	    planoMetasForm.setNomeCoordenador(planoMetasAtual.getNomeCoordenador());
	    planoMetasForm.setEmailCoordenador(planoMetasAtual.getEmailCoordenador());
	    planoMetasForm.setTelefoneCoordenador(planoMetasAtual.getTelefoneCoordenador());

	    if(planoMetasAtual.getContratante() != null) {
	    		planoMetasForm.setContratante( new PessoaOptionForm( planoMetasAtual.getContratante() ) );
	    }
	    planoMetasForm.setNomeContratante(planoMetasAtual.getNomeContratante());
	    planoMetasForm.setEmailContratante(planoMetasAtual.getEmailContratante());
	    planoMetasForm.setTelefoneContratante(planoMetasAtual.getTelefoneContratante());

	    if (planoMetasAtual.getTipoContratante() == TipoContratante.OUTRO) {
	      planoMetasForm.setOutro( new PessoaOptionForm( planoMetasAtual.getContratante() ) );
	    }
	    if(planoMetasAtual.getFacilitador() != null) {
	    		planoMetasForm.setFacilitador(  new PessoaOptionForm( planoMetasAtual.getFacilitador() ) );
	    }
	    
	    planoMetasForm.setRodizio( new RodizioVO( planoMetasAtual.getRodizio() ) );
	    planoMetasForm.setInstituto( new InstitutoOptionForm( planoMetasAtual.getInstituto() ) );
	    planoMetasForm.setEntidade( new EntidadeOptionForm( planoMetasAtual.getEntidade() ) );
	    
	    return planoMetasForm;
	  }
	
	  public void mapPlanoMetasAnotacoesToPlanoMetasFormAnotacoes(PlanoMetas planoMetasAtual, PlanoMetasForm planoMetasForm) {
	    planoMetasForm.setAnotacoes(new ArrayList<AnotacaoVO>());
	    if (planoMetasAtual.getAnotacoes() != null) {
	      for (Anotacao anot : planoMetasAtual.getAnotacoes()) {
	    	  	AnotacaoVO nova = new AnotacaoVO(anot);
	        planoMetasForm.getAnotacoes().add(nova);
	      }
	    }
	  }
	  
	  public List<MetaForm> loadMetaForm(InstitutoOptionForm instituto, PessoaOptionForm facilitador, PessoaOptionForm contratante, 
			  EventoMeta evento, EntidadeOptionForm entidade, RodizioVO rodizio, boolean full) {
		    
		    List<MetaForm> metasForm = null;

		    List<MetaInstituto> metasIntituto = metaInstitutoService.listMetaInstitutoByInstitutoResumo(instituto.getId());

		    List<MetaEntidade> metasEntidade = metaService.findByEntidadeIdAndInstitutoId( entidade.getId(), instituto.getId());

		    // Primeiro Rodizio
		    if (metasEntidade == null || metasEntidade.size() == 0) {
		      metasForm = new MetasHelper(metaService, pessoaService).createMetaFormFromMetaInstituto(
		          metasIntituto, 
		          facilitador,
		          contratante, 
		          evento,
		          rodizio);
		    } else if (metasEntidade.size() > 0) {
		      metasForm = new MetasHelper(metaService, pessoaService).mapMetaEntidadeToMetaForm(
		          metasIntituto, 
		          facilitador,
		          contratante, 
		          evento, 
		          entidade, 
		          rodizio, full);
		    } else {
		      metasForm = new ArrayList<MetaForm>();
		    }
		    
		    return metasForm;
	}
	

}
