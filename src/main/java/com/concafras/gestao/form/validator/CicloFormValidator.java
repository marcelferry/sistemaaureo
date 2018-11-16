package com.concafras.gestao.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.concafras.gestao.model.Rodizio;

@Component
public class CicloFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> paramClass) {
		return Rodizio.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		//Rodizio ciclo = (Rodizio) obj;
		
	}
}
