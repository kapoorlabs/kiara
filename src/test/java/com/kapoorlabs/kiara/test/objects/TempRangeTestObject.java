package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.NumericRange;

import lombok.Data;

@Data
public class TempRangeTestObject {
	
	@CommaSeperatedStrings
	private String cityState;
	
	@NumericRange
	private String tempRange;
	
}
