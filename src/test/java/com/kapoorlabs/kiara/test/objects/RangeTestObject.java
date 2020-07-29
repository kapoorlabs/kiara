package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedNumericRanges;

import lombok.Data;

@Data
public class RangeTestObject {
	
	@CommaSeperatedNumericRanges(numericStartPos=2)
	private String range;
	
	private Integer value;
	
	

}
