package com.kapoorlabs.kiara.domain;

/**
 * This enum lists all the operations that are supported by Kiara on search
 * operations.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
public enum Operator {

	EQUAL, NOT_EQUAL, LESS_THAN, LESS_THAN_EQUAL, GREATER_THAN, GREATER_THAN_EQUAL, CONTAINS_EITHER, CONTAINS_ALL,
	BETWEEN, CONTAINS_EITHER_INCLUDING_NULL, NOT_CONTAINS

}
