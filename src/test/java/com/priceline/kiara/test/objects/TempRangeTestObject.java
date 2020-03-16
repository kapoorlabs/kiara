package com.priceline.kiara.test.objects;

import com.priceline.kiara.domain.annotations.CommaSeperatedStrings;
import com.priceline.kiara.domain.annotations.NumericRange;

import lombok.Data;

@Data
public class TempRangeTestObject {
	
	@CommaSeperatedStrings
	private String cityState;
	
	@NumericRange
	private String tempRange;
	
}
