package com.kapoorlabs.kiara.exception;

/**
 * This exception is raised at time of search, when the column specified in
 * query is not found in the store.
 *
 */
public class NoDefaultConstructorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDefaultConstructorException(String message) {
		super(message);
	}

	public NoDefaultConstructorException() {
		super();
	}

}
