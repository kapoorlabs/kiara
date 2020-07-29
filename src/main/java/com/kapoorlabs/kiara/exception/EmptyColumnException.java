package com.kapoorlabs.kiara.exception;

/**
 * This exception is raised at loadtime when we try to build a store with no
 * eligible columns.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
public class EmptyColumnException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyColumnException(String message) {
		super(message);
	}

	public EmptyColumnException() {
		super();
	}

}
