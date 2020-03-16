package com.priceline.kiara.domain;

import lombok.Data;

/**
 * This class holds some metadata about the fields specified in schema Object.
 * The primary type is weather the data is a pure number or a string. But a
 * String can also be a collection of numbers, like a comma seperated string of
 * numbers, dates, ranges, etc. Thus we need a secondary type to classify the
 * field appropriately.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
@Data
public class SecondaryType {

	private SecondaryCollectionDataType secondaryCollectionType;

	private SecondarySingleDataType secondarySingleType;

	private String format;
	
	private int numericStartPos;

}
