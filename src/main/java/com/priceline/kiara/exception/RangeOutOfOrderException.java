package com.priceline.kiara.exception;

/**
 * This exception is raised both at loadtime and searchtime when we try to parse
 * a range with higher range value less than lower range.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
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
