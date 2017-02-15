package com.concafras.gestao.form.validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.concafras.gestao.form.UsuarioVO;
import com.concafras.gestao.model.security.Usuario;
 
public class PasswordValidator implements Validator{
 
	@Override
	public boolean supports(Class clazz) {
		//just validate the Customer instances
		return UsuarioVO.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
			"required.password", "Field name is required.");
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword", "Field name is required.");
 
		Usuario cust = (Usuario)target;
 
		if(!(cust.getPassword().equals(cust.getConfirmPassword()))){
			errors.rejectValue("password", "required.confirmPassword", "Field name is required.");
		}
 
	}
 
}