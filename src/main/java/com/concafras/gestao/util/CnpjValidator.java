/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concafras.gestao.util;

import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

/**
 * 
 * @author marcelo
 */
public class CnpjValidator {

	/**
	 * Valida CNPJ do usuário.
	 * 
	 * @param cnpj
	 *            String valor com 14 dígitos
	 */
	public static boolean validaCNPJ(String cnpj) {

		cnpj = cnpj.replaceAll(Pattern.compile("\\s").toString(), "");
		cnpj = cnpj.replaceAll(Pattern.compile("\\D").toString(), "");

		if (cnpj == null || cnpj.length() != 14 || isCNPJPadrao(cnpj)) {
			return false;
		}

		try {
			Long.parseLong(cnpj);
		} catch (NumberFormatException e) { // CNPJ não possui somente números
			return false;
		}

		int soma = 0;
		String cnpj_calc = cnpj.substring(0, 12);

		char chr_cnpj[] = cnpj.toCharArray();
		for (int i = 0; i < 4; i++) {
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
				soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
			}
		}

		for (int i = 0; i < 8; i++) {
			if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
				soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
			}
		}

		int dig = 11 - soma % 11;
		cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(
				dig != 10 && dig != 11 ? Integer.toString(dig) : "0")
				.toString();
		soma = 0;
		for (int i = 0; i < 5; i++) {
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
				soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
			}
		}

		for (int i = 0; i < 8; i++) {
			if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
				soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
			}
		}

		dig = 11 - soma % 11;
		cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(
				dig != 10 && dig != 11 ? Integer.toString(dig) : "0")
				.toString();

		return cnpj.equals(cnpj_calc);
	}

	// Elimina CNPJs invalidos conhecidos
	private static boolean isCNPJPadrao(String cnpj) {

		return Arrays.asList("00000000000000", "11111111111111",
				"22222222222222", "33333333333333", "44444444444444",
				"55555555555555", "66666666666666", "77777777777777",
				"88888888888888", "99999999999999").contains(cnpj);
	}

	public static String format(String value) {
		value = value.replaceAll(Pattern.compile("\\s").toString(), "");
		value = value.replaceAll(Pattern.compile("\\D").toString(), "");
		String pattern = "##.###.###/####-##";
		MaskFormatter mask;
		try {
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
