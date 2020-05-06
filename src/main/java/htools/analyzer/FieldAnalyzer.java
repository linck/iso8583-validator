package htools.analyzer;

import static htools.utils.ErrorMessagesFormatter.formatMandatoryIfExistsBitMessage;
import static htools.utils.ErrorMessagesFormatter.formatMandatoryIfNotExistsBitMessage;

import java.util.List;
import java.util.Map;

import htools.utils.Field;
import htools.validator.FieldValidatorTO;

public class FieldAnalyzer {

	private FieldAnalyzer() {

	}

	public static void analyze(Map<String, Field> fields, Map<String, Field> fieldsOriginalReq,
			Map<String, Field> fieldsOriginalResp, Field field, FieldValidatorTO fieldValidator) {
		if (field == null && fieldValidator.isMandatory()) {
			System.out.println("Bit mandatorio ausente: " + fieldValidator.getId());
		}

		if (fieldValidator.getMandatoryIfExistsField() != null) {
			Field fieldDependency = fields.get(fieldValidator.getMandatoryIfExistsField());
			if (fieldDependency != null && field == null) {
				System.out.println(formatMandatoryIfExistsBitMessage(fieldDependency, fieldValidator));
			}
		}

		if (fieldValidator.getMandatoryIfNotExistsField() != null) {
			Field fieldDependency = fields.get(fieldValidator.getMandatoryIfNotExistsField());
			if (fieldDependency == null && field == null) {
				System.out.println(formatMandatoryIfNotExistsBitMessage(fieldDependency, fieldValidator));
			}
		}

		if (field != null) {
			String value = field.getValue();
			if (fieldValidator.getRegex() != null && !value.matches(fieldValidator.getRegex())) {
				System.out.println("Valor do bit invalido: Bit-" + fieldValidator.getId() + " (Esperado: ["
						+ fieldValidator.getRegex() + "] > Atual: [" + value + "])");
			}

			if (fieldValidator.getContains() != null) {
				for (String containsValue : fieldValidator.getContains()) {
					if (!value.contains(containsValue)) {
						System.out.println("Valor do bit invalido: Bit-" + fieldValidator.getId()
								+ "não contém o valor " + containsValue);
					}
				}
			}
			validateFieldsOriginal(fieldsOriginalReq, fieldValidator, fieldValidator.getContainsOriginalFields(),
					value);
			validateFieldsOriginal(fieldsOriginalResp, fieldValidator, fieldValidator.getContainsOriginalRespFields(),
					value);

		}
	}

	private static void validateFieldsOriginal(Map<String, Field> fieldsOriginal, FieldValidatorTO fieldValidator,
			List<Integer> containsOriginalFields, String value) {
		if (containsOriginalFields != null) {
			for (Integer originalBit : containsOriginalFields) {
				Field fieldOriginal = fieldsOriginal.get(originalBit.toString());
				if (!value.contains(fieldOriginal.getValue())) {
					System.out.println("Valor do bit invalido: Bit-" + fieldValidator.getId() + " não contém o bit "
							+ originalBit + " da transação original");
				}
			}
		}
	}
}
