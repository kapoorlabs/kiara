package com.kapoorlabs.kiara.exception;

/**
 * This exception is raised during load time, when we try to load an object that
 * does not match the class(based on which the store was created),or it has an
 * inaccessible private getter method.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
public class LoadDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadDataException(String message) {
		super(message);
	}

	public LoadDataException() {
		super();
	}

}
