package htools.analyzer;

public class InvalidCheckMethodException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4357151736507875822L;

	public InvalidCheckMethodException(String msg) {
		super("Check Method Invalido: " + msg);
	}
}
