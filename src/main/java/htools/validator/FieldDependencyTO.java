package htools.validator;

import java.util.List;

public class FieldDependencyTO {
	
	private String id;
	private List<String> content;
	private String checkMethod;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> getContent() {
		return content;
	}
	
	public void setContent(List<String> content) {
		this.content = content;
	}
	
	public String getCheckMethod() {
		return checkMethod;
	}
	
	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}
}
