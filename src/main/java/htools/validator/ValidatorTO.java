package htools.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidatorTO {
	private List<String> mandatoryFields;
//	private List<String> mandatorySubFields;
	private String extendsOf;
	private List<String> extendsOfRemoveFields;
	private List<String> notExistsFields;
	private List<FieldValidatorTO> fields;

	public ValidatorTO() {
		super();
	}

	public List<FieldValidatorTO> getFields() {
		return fields;
	}

	public void setFields(List<FieldValidatorTO> fields) {
		this.fields = fields;
	}

	public List<String> getMandatoryFields() {
		return mandatoryFields;
	}

	public void setMandatoryFields(List<String> mandatoryFields) {
		this.mandatoryFields = mandatoryFields;
	}

	public String getExtendsOf() {
		return extendsOf;
	}

	public void setExtendsOf(String extendsOf) {
		this.extendsOf = extendsOf;
	}

	public List<String> getExtendsOfRemoveFields() {
		return extendsOfRemoveFields;
	}

	public void setExtendsOfRemoveFields(List<String> extendsOfRemoveFields) {
		this.extendsOfRemoveFields = extendsOfRemoveFields;
	}

	public List<String> getNotExistsFields() {
		return notExistsFields;
	}

	public void setNotExistsFields(List<String> notExistsFields) {
		this.notExistsFields = notExistsFields;
	}
	
	public static void extendsOf(Map<String, ValidatorTO> validators, ValidatorTO validator) {
		if (validator.getExtendsOf() != null && !validator.getExtendsOf().isEmpty()) {
			ValidatorTO validatorToExtends = validators.get(validator.getExtendsOf());

			if (validatorToExtends != null) {
				if (validatorToExtends.getExtendsOf() != null) {
					extendsOf(validators, validatorToExtends);
				}
				if (validator.getMandatoryFields() == null) {
					validator.setMandatoryFields(validatorToExtends.getMandatoryFields());
				}

				if (validator.getExtendsOfRemoveFields() == null) {
					validator.setExtendsOfRemoveFields(validatorToExtends.getExtendsOfRemoveFields());
				}

				if (validator.getNotExistsFields() == null) {
					validator.setNotExistsFields(validatorToExtends.getNotExistsFields());
				}

				if (validator.getFields() == null) {
					validator.setFields(validatorToExtends.getFields());
				} else {
					Map<String, FieldValidatorTO> fieldsValidatorsToExtends = validatorToExtends.getFields().stream()
							.collect(Collectors.toMap(FieldValidatorTO::getId, f -> f));

					Map<String, FieldValidatorTO> fieldsValidatorsCurrent = validator.getFields().stream()
							.collect(Collectors.toMap(FieldValidatorTO::getId, f -> f));

					fieldsValidatorsToExtends.putAll(fieldsValidatorsCurrent);

					ArrayList<FieldValidatorTO> fieldsValidatorsMerged = new ArrayList<>();
					fieldsValidatorsToExtends.forEach((fieldId, value) -> {
						if (validator.getExtendsOfRemoveFields() == null
								|| (validator.getExtendsOfRemoveFields() != null
										&& !validator.getExtendsOfRemoveFields().contains(fieldId))) {
							fieldsValidatorsMerged.add(value);
						}
					});
					validator.setFields(fieldsValidatorsMerged);
				}
			}
		}
	}

}
