package com.concafras.gestao.form.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.concafras.gestao.form.UsuarioVO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {   
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {       
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){   
    	UsuarioVO user = (UsuarioVO) obj;
    	if(user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        return user.getPassword().equals(user.getConfirmPassword());    
    }     
}