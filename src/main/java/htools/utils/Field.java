package htools.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Field {

	@XStreamAlias("id")
	private String id;

	@XStreamAlias("value")
	private String value;

	@XStreamAlias("type")
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
