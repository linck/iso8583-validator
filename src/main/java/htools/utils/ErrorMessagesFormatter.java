package htools.utils;

import htools.validator.FieldValidatorTO;

public class ErrorMessagesFormatter {
	
	private ErrorMessagesFormatter() {

	}
	
	public static String formatMandatoryIfExistsBitMessage(Field field, FieldValidatorTO fieldValidator) {
		return "Subcampo mandatorio ausente: Bit-" + field.getId() + " Subcampo-" + fieldValidator.getId() + " ("
				+ fieldValidator.getDescription() + ") é obrigatório, pois o bit "
				+ fieldValidator.getMandatoryIfExistsField() + " está presente";
	}

	public static String formatMandatoryIfNotExistsBitMessage(Field field, FieldValidatorTO fieldValidator) {
		return "Subcampo mandatorio ausente: Bit-" + field.getId() + " Subcampo-" + fieldValidator.getId() + " ("
				+ fieldValidator.getDescription() + ") é obrigatório, pois o bit "
				+ fieldValidator.getMandatoryIfNotExistsField() + " não está presente";
	}
	public static String formatMandatoryIfNotExistsSubfieldMessage(Field field, FieldValidatorTO fieldValidator) {
		return "Subcampo mandatorio ausente: Bit-" + field.getId() + " Subcampo-" + fieldValidator.getId() + " ("
				+ fieldValidator.getDescription() + ") é obrigatório, pois o subcampo - "
				+ fieldValidator.getMandatoryIfNotExistsSubfield() + " não está presente";
	}
}
