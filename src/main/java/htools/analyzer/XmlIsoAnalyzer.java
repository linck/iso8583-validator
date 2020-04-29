package htools.analyzer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import htools.utils.Field;
import htools.utils.IsoMsgXml;
import htools.utils.TLVTranslator;
import htools.validator.FieldValidatorTO;
import htools.validator.ValidatorTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XmlIsoAnalyzer {

	public static final String VALIDATOR = "validator.json";

	public void analyze(String msgPath, String originalMsgPath, String transaction) throws IOException {
		Map<String, Field> fields = getIsoFields(msgPath);
		Map<String, Field> fieldsOriginal = getIsoFields(originalMsgPath);
		Map<String, ValidatorTO> validators = parseValidators(VALIDATOR);

		if (fields != null && validators != null) {
			ValidatorTO validator = validators.get(transaction);
			ValidatorTO.extendsOf(validators, validator);
			executeBaseValidations(fields, validator);
			validateFieldsAndSubfields(fields, fieldsOriginal, validator);
		}
	}

	private void validateFieldsAndSubfields(Map<String, Field> fields, Map<String, Field> fieldsOriginal,
			ValidatorTO validator) {
		List<FieldValidatorTO> fieldsValidators = validator.getFields();
		for (FieldValidatorTO fieldValidator : fieldsValidators) {
			Field field = fields.get(fieldValidator.getId());
			FieldAnalyzer.analyze(fields, fieldsOriginal, field, fieldValidator);

			List<FieldValidatorTO> subfieldsValidators = fieldValidator.getSubfields();
			if (subfieldsValidators != null) {
				Map<String, String> tlvSubfields = TLVTranslator.translate(field.getValue(), subfieldsValidators);
				for (FieldValidatorTO subfieldsValidator : subfieldsValidators) {
					String subfieldContent = tlvSubfields.get(subfieldsValidator.getId());
					SubfiledAnalyzer.analyze(fields, field, subfieldsValidator, subfieldContent, tlvSubfields);
				}
			}
		}
	}

	private void executeBaseValidations(Map<String, Field> fields, ValidatorTO validator) {
		if (validator.getMandatoryFields() != null) {
			validator.getMandatoryFields().forEach(v -> {
				if (fields.get(v) == null) {
					System.out.println("Bit mandatorio ausente: Bit-" + v);
				}
			});
		}

		if (validator.getNotExistsFields() != null) {
			validator.getNotExistsFields().forEach(v -> {
				if (fields.get(v) != null) {
					System.out.println("Bit-" + v + " nao deveria existir na mensageria");
				}
			});
		}
	}

	public static Map<String, ValidatorTO> parseValidators(String validatorPath) throws IOException {
		byte[] validatorsContent = Files.readAllBytes((Paths.get(validatorPath)));
		String json = new String(validatorsContent);
		return new Gson().fromJson(json, new TypeToken<Map<String, ValidatorTO>>() {
		}.getType());
	}

	private Map<String, Field> getIsoFields(String msgPath) throws IOException {
		if (msgPath != null) {
			byte[] msgContent = Files.readAllBytes((Paths.get(msgPath)));
			IsoMsgXml isoMsgXml = (IsoMsgXml) IsoMsgXml.fromXML(msgContent);
			return isoMsgXml.getFields().stream().collect(Collectors.toMap(Field::getId, field -> field));
		}
		return null;
	}
}
