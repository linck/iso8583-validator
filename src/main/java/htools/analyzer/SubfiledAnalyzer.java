package htools.analyzer;

import htools.utils.Field;
import htools.validator.FieldValidatorTO;

import java.util.Map;

import static htools.utils.ErrorMessagesFormatter.*;

public class SubfiledAnalyzer {
	
	private SubfiledAnalyzer() {
		
	}
	
	public static void analyze(Map<String, Field> fields, Field field, FieldValidatorTO subfieldsValidator, Map<String, String> tlvSubfields) {
		
		String subfieldContent = tlvSubfields.get(subfieldsValidator.getId());
		if (subfieldsValidator.isMandatory() && subfieldContent == null) {
			System.out.println("Subcampo mandatorio ausente: " + "Bit-" + field.getId() + " Subcampo-"
					+ subfieldsValidator.getId() + " (" + subfieldsValidator.getDescription() + ")");
		}

		if (subfieldsValidator.getMandatoryIfExistsField() != null) {
			Field fieldDependency = fields.get(subfieldsValidator.getMandatoryIfExistsField());
			if (fieldDependency != null && subfieldContent == null) {
				System.out.println(formatMandatoryIfExistsBitMessage(field, subfieldsValidator));
			}
		}

		if (subfieldsValidator.getMandatoryIfNotExistsField() != null) {
			Field fieldDependency = fields.get(subfieldsValidator.getMandatoryIfNotExistsField());
			if (fieldDependency == null && subfieldContent == null) {
				System.out.println(formatMandatoryIfNotExistsBitMessage(field, subfieldsValidator));
			}
		}

		if (subfieldsValidator.getMandatoryIfNotExistsSubfield() != null) {
			if(!tlvSubfields.containsKey(subfieldsValidator.getMandatoryIfNotExistsSubfield())) {
				System.out.println(formatMandatoryIfNotExistsSubfieldMessage(field, subfieldsValidator));
			}
		}

		if (subfieldsValidator.getContains() != null) {
			for (String containsValue : subfieldsValidator.getContains()) {
				if (!subfieldContent.contains(containsValue)) {
					System.out.println("Valor do bit invalido: Bit-" + subfieldsValidator.getId()
							+ "não contém o valor " + containsValue);
				}
			}
		}

		if (subfieldContent != null && subfieldsValidator.getRegex() != null
				&& !subfieldContent.matches(subfieldsValidator.getRegex())) {
			System.out.println("Valor do subcampo invalido: " + "Bit-" + field.getId() + " Subcampo-"
					+ subfieldsValidator.getId() + " (Esperado: [" + subfieldsValidator.getRegex() + "] > Atual: ["
					+ subfieldContent + "])");
		}
	}
}
