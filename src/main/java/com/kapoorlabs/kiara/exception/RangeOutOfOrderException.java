package com.kapoorlabs.kiara.exception;

/**
 * This exception is raised both at loadtime and searchtime when we try to parse
 * a range with higher range value, which is less than lower range.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
public class RangeOutOfOrderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RangeOutOfOrderException(String message) {
		super(message);
	}

	public RangeOutOfOrderException() {
		super();
	}

}
