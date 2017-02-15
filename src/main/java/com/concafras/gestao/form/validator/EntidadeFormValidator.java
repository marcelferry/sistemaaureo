package com.concafras.gestao.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.concafras.gestao.model.Entidade;

@Component
public class EntidadeFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> paramClass) {
		return Entidade.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Entidade entidade = (Entidade) obj;

		if (entidade.getTipoEntidade() == null) {
			errors.rejectValue("tipoEntidade", "negativeValue",
					new Object[] { "'tipoEntidade'" },
					"Tipo Entidade não selecionado");
		}

		if (entidade.getRazaoSocial() == null) {
			errors.rejectValue("razaoSocial", "negativeValue",
					new Object[] { "'razaoSocial'" },
					"Razão Social obrigatória.");
		}

		if (entidade.getEndereco() == null
				|| entidade.getEndereco().getCidade() == null) {
			errors.rejectValue("endereco.cidade", "negativeValue",
					new Object[] { "'endereco.cidade'" },
					"Cidade é obrigatória");
		}
	}
}
