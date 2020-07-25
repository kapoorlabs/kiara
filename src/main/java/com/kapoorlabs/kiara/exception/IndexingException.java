package com.kapoorlabs.kiara.exception;

/**
 * This exception is raised during index creation of load time, if there is bad
 * data, such conditions can occur, for example an invalid Range.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
public class IndexingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndexingException(String message) {
		super(message);
	}

	public IndexingException() {
		super();
	}

}