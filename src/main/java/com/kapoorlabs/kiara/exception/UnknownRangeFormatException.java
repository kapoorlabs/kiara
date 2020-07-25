package com.kapoorlabs.kiara.exception;

/**
 * This exception is raised both at loadtime and search time for datetime/date
 * based ranges when the length of the range does not match the date's format
 * length.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
public class UnknownRangeFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownRangeFormatException(String message) {
		super(message);
	}

	public UnknownRangeFormatException() {
		super();
	}

}
