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

	public void analyze(String msgPath, String originalMsgReqPath, String originalMsgRespPath, String validatorName,
			String validatorFilePath) throws IOException {
		Map<String, Field> fields = getIsoFields(msgPath);
		Map<String, Field> fieldsOriginalResp = getIsoFields(originalMsgRespPath);
		Map<String, Field> fieldsOriginalReq = getIsoFields(originalMsgReqPath);
		Map<String, ValidatorTO> validators = parseValidators(validatorFilePath);

		if (fields != null && validators != null) {
			ValidatorTO validator = validators.get(validatorName);
			ValidatorTO.extendsOf(validators, validator);
			executeBaseValidations(fields, validator);
			validateFieldsAndSubfields(fields, fieldsOriginalReq, fieldsOriginalResp, validator);
		}
	}

	private void validateFieldsAndSubfields(Map<String, Field> fields, Map<String, Field> fieldsOriginalReq,
			Map<String, Field> fieldsOriginalResp, ValidatorTO validator) {
		List<FieldValidatorTO> fieldsValidators = validator.getFields();
		if(fieldsValidators != null) {
			for (FieldValidatorTO fieldValidator : fieldsValidators) {
				boolean ignoreField = false;
				
				if (validator.getExtendsOfRemoveFields() != null && validator.getExtendsOfRemoveFields().stream()
						.anyMatch(fieldToIgnore -> fieldToIgnore.equals(fieldValidator.getId()))) {
					ignoreField = true;
				}
				
				if(!ignoreField) {
					Field field = fields.get(fieldValidator.getId());
					FieldAnalyzer.analyze(fields, fieldsOriginalReq, fieldsOriginalResp, field, fieldValidator);
					
					List<FieldValidatorTO> subfieldsValidators = fieldValidator.getSubfields();
					if (subfieldsValidators != null && field != null) {
						Map<String, String> tlvSubfields = TLVTranslator.translate(field.getValue(), subfieldsValidators);
						for (FieldValidatorTO subfieldsValidator : subfieldsValidators) {
							String subfieldContent = tlvSubfields.get(subfieldsValidator.getId());
							SubfiledAnalyzer.analyze(fields, field, subfieldsValidator, subfieldContent, tlvSubfields);
						}
					}
				}
			}
		}
	}

	private void executeBaseValidations(Map<String, Field> fields, ValidatorTO validator) {
		if (validator.getMandatoryFields() != null) {
			validator.getMandatoryFields().forEach(v -> {
				if (fields.get(v) == null 
						&& (validator.getExtendsOfRemoveFields() != null
						&& validator.getExtendsOfRemoveFields().stream().noneMatch(fieldToIgnore -> fieldToIgnore.equals(v))
						|| validator.getExtendsOfRemoveFields() == null)) {
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
