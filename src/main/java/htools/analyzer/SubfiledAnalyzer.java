package htools.analyzer;

import static htools.utils.ErrorMessagesFormatter.formatMandatoryIfExistsBitMessage;
import static htools.utils.ErrorMessagesFormatter.formatMandatoryIfNotExistsBitMessage;

import java.util.Map;

import htools.utils.Field;
import htools.utils.TLVTranslator;
import htools.validator.FieldValidatorTO;

public class SubfiledAnalyzer {
	
	private SubfiledAnalyzer() {
		
	}
	
	public static boolean analyze(Map<String, Field> fields, Field field, FieldValidatorTO subfieldsValidator) {
		Map<String, String> tlvSubfields = TLVTranslator.translate(field.getValue());

		String subfieldContent = tlvSubfields.get(subfieldsValidator.getId());
		if (subfieldsValidator.isMandatory() && subfieldContent == null) {
			System.out.println("Subcampo mandatorio ausente: " + "Bit-" + field.getId() + " Subcampo-"
					+ subfieldsValidator.getId() + " (" + subfieldsValidator.getDescription() + ")");
			return false;
		}

		if (subfieldsValidator.getMandatoryIfExistsField() != null) {
			Field fieldDependency = fields.get(subfieldsValidator.getMandatoryIfExistsField());
			if (fieldDependency != null && subfieldContent == null) {
				System.out.println(formatMandatoryIfExistsBitMessage(field, subfieldsValidator));
				return false;
			}
		}

		if (subfieldsValidator.getMandatoryIfNotExistsField() != null) {
			Field fieldDependency = fields.get(subfieldsValidator.getMandatoryIfNotExistsField());
			if (fieldDependency == null && subfieldContent == null) {
				System.out.println(formatMandatoryIfNotExistsBitMessage(field, subfieldsValidator));
				return false;
			}
		}

		if (subfieldsValidator.getContains() != null) {
			for (String containsValue : subfieldsValidator.getContains()) {
				if (!subfieldContent.contains(containsValue)) {
					System.out.println("Valor do bit invalido: Bit-" + subfieldsValidator.getId()
							+ "não contém o valor " + containsValue);
					return false;
				}
			}
		}

		if (subfieldContent != null && subfieldsValidator.getRegex() != null
				&& !subfieldContent.matches(subfieldsValidator.getRegex())) {
			System.out.println("Valor do subcampo invalido: " + "Bit-" + field.getId() + " Subcampo-"
					+ subfieldsValidator.getId() + " (Esperado: [" + subfieldsValidator.getRegex() + "] > Atual: ["
					+ subfieldContent + "])");
			return false;
		}
		return true;
	}
}
