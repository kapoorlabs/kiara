package com.kapoorlabs.kiara.exception;

/**
 * This exception is raised during search time, if any operator other than
 * EQUALS ,CONTAINS_EITHER or CONTAINS_ALL is used in range based data, OR, if
 * the operator NOT_CONTAINS is used for single data type columns.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
public class NonSupportedOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NonSupportedOperationException(String message) {
		super(message);
	}

	public NonSupportedOperationException() {
		super();
	}
}
