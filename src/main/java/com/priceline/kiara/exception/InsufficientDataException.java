package com.priceline.kiara.exception;

/**
 * This exception is raised during search time, when between operator is used in
 * a condition and both lower and upper ranges are not specified
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
public class InsufficientDataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientDataException(String message) {
		super(message);
	}

	public InsufficientDataException() {
		super();
	}

}
