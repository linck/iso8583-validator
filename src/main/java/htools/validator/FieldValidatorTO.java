package htools.validator;

import java.util.List;

public class FieldValidatorTO {

	private String id;
	private String description;
	private boolean mandatory;
	private String regex;
	private String mandatoryIfExistsField;
	private String mandatoryIfNotExistsField;
	private String mandatoryIfNotExistsSubfield;
	private List<Integer> containsOriginalFields;
	private List<Integer> containsOriginalRespFields;
	private List<String> contains; 
	private List<FieldValidatorTO> subfields;
	private String contentType;

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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getMandatoryIfNotExistsSubfield() {
		return mandatoryIfNotExistsSubfield;
	}

	public void setMandatoryIfNotExistsSubfield(String mandatoryIfNotExistsSubfield) {
		this.mandatoryIfNotExistsSubfield = mandatoryIfNotExistsSubfield;
	}

	public List<Integer> getContainsOriginalFields() {
		return containsOriginalFields;
	}

	public void setContainsOriginalFields(List<Integer> containsOriginalFields) {
		this.containsOriginalFields = containsOriginalFields;
	}

	public List<Integer> getContainsOriginalRespFields() {
		return containsOriginalRespFields;
	}

	public void setContainsOriginalRespFields(List<Integer> containsOriginalRespFields) {
		this.containsOriginalRespFields = containsOriginalRespFields;
	}
}
