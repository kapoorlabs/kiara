package com.kapoorlabs.kiara.constants;

/**
 * This class provides static fields, that represents reasons for failure,
 * commonly used at various places in code.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */

public class ExceptionConstants {

	public static final String EMPTY_COLUMN_STORE = "Sdql store cannot be created with empty list of columns";

	public static final String EMPTY_SCHEMA_STORE = "Sdql schema cannot be null";

	public static final String UNSUPPORTED_REFERENCE_TYPE = "Reference type is not supported";

	public static final String CONDITIONS_NOT_PRESENT = "Conditions is a required field";

	public static final String EMPTY_STORE_SEARCH = "Empty store cannot be searched";

	public static final String SEARCHING_UNCOMMTTED_STORE = "Store that is not prepared for search, cannot be searched. Use StoreLoader's prepareForSearch method prior to begin searching";

}
