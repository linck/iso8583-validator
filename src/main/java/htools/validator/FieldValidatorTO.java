package htools.validator;

import java.util.List;

public class FieldValidatorTO {

	private String id;
	private String description;
	private boolean mandatory;
	private String regex;
	private String mandatoryIfExistsField;
	private String mandatoryIfNotExistsField;
	private List<Integer> containsOriginalFields; 
	private List<String> contains; 
	private List<FieldValidatorTO> subfields;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public List<FieldValidatorTO> getSubfields() {
		return subfields;
	}

	public void setSubfields(List<FieldValidatorTO> subfields) {
		this.subfields = subfields;
	}


	public List<String> getContains() {
		return contains;
	}

	public void setContains(List<String> contains) {
		this.contains = contains;
	}

	public List<Integer> getContainsOriginalFields() {
		return containsOriginalFields;
	}

	public void setContainsOriginalFields(List<Integer> containsOriginalFields) {
		this.containsOriginalFields = containsOriginalFields;
	}

	public String getMandatoryIfExistsField() {
		return mandatoryIfExistsField;
	}

	public void setMandatoryIfExistsField(String mandatoryIfExistsFields) {
		this.mandatoryIfExistsField = mandatoryIfExistsFields;
	}

	public String getMandatoryIfNotExistsField() {
		return mandatoryIfNotExistsField;
	}

	public void setMandatoryIfNotExistsFields(String mandatoryIfNotExistsFields) {
		this.mandatoryIfNotExistsField = mandatoryIfNotExistsFields;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
