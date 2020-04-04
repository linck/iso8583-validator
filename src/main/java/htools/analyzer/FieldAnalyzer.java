package htools.analyzer;

import static htools.utils.ErrorMessagesFormatter.formatMandatoryIfExistsBitMessage;
import static htools.utils.ErrorMessagesFormatter.formatMandatoryIfNotExistsBitMessage;

import java.util.Map;

import htools.utils.Field;
import htools.validator.FieldValidatorTO;

public class FieldAnalyzer {
	
	private FieldAnalyzer() {
		
	}
	
	public static boolean analyze(Map<String, Field> fields, Map<String, Field> fieldsOriginal, Field field,
			FieldValidatorTO fieldValidator) {
		if (field == null && fieldValidator.isMandatory()) {
			System.out.println("Bit mandatorio ausente: " + fieldValidator.getId());
			return false;
		}

		if (fieldValidator.getMandatoryIfExistsField() != null) {
			Field fieldDependency = fields.get(fieldValidator.getMandatoryIfExistsField());
			if (fieldDependency != null && field == null) {
				System.out.println(formatMandatoryIfExistsBitMessage(fieldDependency, fieldValidator));
				return false;
			}
		}

		if (fieldValidator.getMandatoryIfNotExistsField() != null) {
			Field fieldDependency = fields.get(fieldValidator.getMandatoryIfNotExistsField());
			if (fieldDependency == null && field == null) {
				System.out.println(formatMandatoryIfNotExistsBitMessage(fieldDependency, fieldValidator));
				return false;
			}
		}

		if (field != null) {
			String value = field.getValue();
			if (fieldValidator.getRegex() != null && !value.matches(fieldValidator.getRegex())) {
				System.out.println("Valor do bit invalido: Bit-" + fieldValidator.getId() + " (Esperado: ["
						+ fieldValidator.getRegex() + "] > Atual: [" + value + "])");
				return false;
			}

			if (fieldValidator.getContains() != null) {
				for (String containsValue : fieldValidator.getContains()) {
					if (!value.contains(containsValue)) {
						System.out.println("Valor do bit invalido: Bit-" + fieldValidator.getId()
								+ "não contém o valor " + containsValue);
						return false;
					}
				}
			}

			if (fieldValidator.getContainsOriginalFields() != null) {
				for (Integer containsBit : fieldValidator.getContainsOriginalFields()) {
					Field fieldContains = fieldsOriginal.get(containsBit.toString());
					if (!value.contains(fieldContains.getValue())) {
						System.out.println("Valor do bit invalido: Bit-" + fieldValidator.getId() + " não contém o bit "
								+ containsBit + " da transação original");
						return false;
					}
				}
			}
		}

		return true;
	}
}
