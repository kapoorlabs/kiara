package com.kapoorlabs.kiara.exception;

/**
 * This exception is raised at time of search, when the column specified in
 * query is not found in the store.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
public class ColumnNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ColumnNotFoundException(String message) {
		super(message);
	}

	public ColumnNotFoundException() {
		super();
	}

}
