package com.concafras.gestao.form.validator;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoContratante;
import com.concafras.gestao.enums.TipoMeta;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.PlanoMetasForm;
import com.concafras.gestao.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class PlanoMetasFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return PlanoMetasForm.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		PlanoMetasForm plano = (PlanoMetasForm) obj;
		
		String jsonInString = null;
		
		try {
      ObjectMapper mapper = new ObjectMapper();
      
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      
      jsonInString = mapper.writeValueAsString(plano);
            
    } catch (Exception e) {
      e.printStackTrace();
    }
		
		try {
      String file = "validator" + plano.getFase() + "_r_" + 2017 + "_e_" + ( plano.getEntidade() != null ? plano.getEntidade().getId() : "null" ) + "_i_" + (plano.getInstituto() != null ? plano.getInstituto().getId() : "null") + ".json";
      FileUtils.writeStringToFile(new File("/data/metas/" +  file), jsonInString);
    } catch (Exception e) {
      e.printStackTrace();
    }
		
		if(plano.getFase() != null){
			if(plano.getFase() == 1){
				if(plano.getRodizio() == null || 
				   plano.getRodizio().getId() == null ||  
				   plano.getRodizio().getId() <= 0){
		            errors.rejectValue("rodizio.id", "negativeValue", new Object[]{"'rodizio.id'"}, "Rodizio não selecionado");
		        }
				if(plano.getInstituto() == null || 
						plano.getInstituto().getId() == null ||  
						plano.getInstituto().getId() <= 0){
					errors.rejectValue("instituto.id", "negativeValue", new Object[]{"'instituto.id'"}, "Instituto não selecionado");
				}
			}
			if(plano.getFase() == 2){
				if(plano.getEntidade() == null || 
						plano.getEntidade().getId() == null ||  
						plano.getEntidade().getId() <= 0){
					errors.rejectValue("entidade.id", "negativeValue", new Object[]{"'entidade.id'"}, "Entidade não selecionada");
				}
				if(plano.getTipoContratante() == null){
					errors.rejectValue("tipoContratante", "negativeValue", new Object[]{"'tipoContratante'"}, "Tipo Contratante não selecionado");
				} else if(plano.getTipoContratante() == TipoContratante.PRESIDENTE){
					if(plano.getPresidente() == null || 
						plano.getPresidente().getId() == null ||  
						plano.getPresidente().getId() <= 0){
						errors.rejectValue("presidente.id", "negativeValue", new Object[]{"'presidente.id'"}, "Presidente não selecionado");
					}
				} else if(plano.getTipoContratante() == TipoContratante.COORDENADOR){
					if( (plano.getCoordenador() == null || 
							plano.getCoordenador().getId() == null ||  
							plano.getCoordenador().getId() <= 0 ) && 
							plano.getNomeCoordenador() == null
							){
							errors.rejectValue("coordenador.id", "negativeValue", new Object[]{"'coordenador.id'"}, "Coordenador não selecionado");
						}
				} else if(plano.getTipoContratante() == TipoContratante.OUTRO){
					if( (plano.getOutro() == null || 
							plano.getOutro().getId() == null ||  
							plano.getOutro().getId() <= 0 ) && 
              (plano.getOutro() != null && 
              plano.getOutro().getNome() == null)
              ){
							errors.rejectValue("outro.id", "negativeValue", new Object[]{"'outro.id'"}, "Contratante não selecionado");
						}
				}
			}
			if(plano.getFase() == 3 && plano.isFinalizado()){
				for(int i = 0; i < plano.getDependencias().size(); i++){
					MetaForm meta = plano.getDependencias().get(i);
					
					errors.pushNestedPath("dependencias[" + i + "]");
	        validateMeta(meta, errors);
	        errors.popNestedPath();
				}
			}
			
		}
	}
	
	public void validateMeta(Object arg0, Errors errors) {
		MetaForm meta = (MetaForm) arg0;
		if(meta.getDependencias() == null || meta.getDependencias().size() == 0){
			if(!meta.getAtividade().getTipoMeta().equals(TipoMeta.META_QUANTITATIVA)){
				if(meta.getSituacaoAtual() == null 
						|| meta.getSituacaoAtual().getSituacao() == null) { 
					errors.rejectValue("situacaoAtual.situacao","negativeValue", new Object[]{"'situacaoAtual.situacao'"}, "Situacão Atual Obrigatória");
				} else if(meta.getSituacaoAtual().getSituacao() == SituacaoMeta.IMPLANTADA) { 
					//testar data conclusao
					if(meta.getSituacaoAtual().getConclusao() == null){
						errors.rejectValue("situacaoAtual.conclusao","negativeValue", new Object[]{"'situacaoAtual.conclusao'"}, "Data Implantação obrigatória");
					}
				} else { 
					if(meta.getSituacaoAtual().getSituacao() == SituacaoMeta.IMPLPARCIAL) { 
						//testar data conclusao
						if(meta.getSituacaoAtual().getConclusao() == null){
							errors.rejectValue("situacaoAtual.conclusao","negativeValue", new Object[]{"'situacaoAtual.conclusao'"}, "Data Implantação obrigatória");
						}
						// testar comentario
						 if(meta.getObservacoes() == null ||
	                meta.getObservacoes().size() == 0 || 
	                Util.isNullOrEmpty(meta.getObservacoes().get(0).getTexto())){
	              errors.rejectValue("observacoes","negativeValue", new Object[]{"'observacoes'"}, "É necessário pelo menos uma justificativa em caso de uma Implantação Parcial");
	            }
					}
					
					if(meta.getSituacaoDesejada() == null 
							|| meta.getSituacaoDesejada().getSituacao() == null) { 
						errors.rejectValue("situacaoDesejada.situacao","negativeValue", new Object[]{"'situacaoDesejada.situacao'"}, "Planejamento Obrigatório");
					} else if(meta.getSituacaoDesejada().getSituacao() == SituacaoMeta.PLANEJADA) { 
						//testar data previsao
						if(meta.getSituacaoDesejada().getPrevisao() == null){
							errors.rejectValue("situacaoDesejada.previsao","negativeValue", new Object[]{"'situacaoDesejada.previsao'"}, "Previsão obrigatória");
						}
					}
				}
			} else {
				if(meta.getSituacaoAtual().getRealizado() == null){
					errors.rejectValue("situacaoAtual.realizado","negativeValue", new Object[]{"'situacaoAtual.realizado'"}, "Quantidade realizada é obrigatória");	
				} 
				if(meta.getSituacaoDesejada().getPrevisto() == null){	
					errors.rejectValue("situacaoDesejada.previsto","negativeValue", new Object[]{"'situacaoDesejada.previsto'"}, "Quantidade realizada é obrigatória");	
				}
			}
				
			
		} else if(meta.getDependencias() != null && meta.getDependencias().size() > 0){
			for(int i = 0; i < meta.getDependencias().size(); i++){
				MetaForm meta2 = meta.getDependencias().get(i);
				errors.pushNestedPath("dependencias[" + i + "]");
				validateMeta( meta2, errors);
		        errors.popNestedPath();
			}
		}
	}

}
