package com.priceline.kiara.constants;

/**
 * This class provides static fields, that are commonly used in code as
 * constants.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
public class SdqlConstants {

	public static String[] ALLOWED_TYPES = { "BYTE", "SHORT", "CHAR", "CHARACTER", "INT", "INTEGER", "LONG", "DOUBLE",
			"FLOAT", "STRING", "BOOLEAN" };
	public static String[] NUMERIC_TYPES = { "DOUBLE", "FLOAT", "INT", "INTEGER", "LONG", "SHORT", "BYTE" };

	public static final String NULL = "null";
	public static final Long LONG_NULL = Long.MIN_VALUE;
	public static final String SERIALIZER_DELIMITER = "~";
	public static final String SERIALIZER_PARENT_IDENTIFIER = "#";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

}
