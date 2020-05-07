package htools.exception;

public class ValidatorNotExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3040070048644964526L;

	public ValidatorNotExistsException(String msg) {
		super("Validador inexistente: " + msg);
	}
}
