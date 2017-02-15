package com.concafras.gestao.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.concafras.gestao.form.FacilitadorForm;

@Component
public class FacilitadorFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return FacilitadorForm.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		FacilitadorForm facilitador = (FacilitadorForm) obj;
		
		if( facilitador.getFase() == 1){
			if(facilitador.getRodizio() == null || 
			   facilitador.getRodizio().getId() == null ||  
			   facilitador.getRodizio().getId() <= 0){
	            errors.rejectValue("rodizio.id", "negativeValue", new Object[]{"'rodizio.id'"}, "Rodizio não selecionado");
	        }
		}
		
		if(facilitador.getFase() == 2){
			if(facilitador.getInstituto() == null || 
					facilitador.getInstituto().getId() == null ||  
					facilitador.getInstituto().getId() <= 0){
				errors.rejectValue("instituto.id", "negativeValue", new Object[]{"'instituto.id'"}, "Instituto não selecionado");
			}
		}
		
		if(facilitador.getFase() == 3){
			if(facilitador.getTrabalhador() == null || 
					facilitador.getTrabalhador().getId() == null ||  
					facilitador.getTrabalhador().getId() <= 0){
					errors.rejectValue("trabalhador.id", "negativeValue", new Object[]{"'trabalhador.id'"}, "Trabalhador não selecionado");
				}
		}
	}
}
