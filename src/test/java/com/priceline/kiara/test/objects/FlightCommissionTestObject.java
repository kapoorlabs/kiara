package com.priceline.kiara.test.objects;

import com.priceline.kiara.domain.annotations.CommaSeperatedNumericRanges;

import lombok.Data;

@Data
public class FlightCommissionTestObject {
	
	@CommaSeperatedNumericRanges(numericStartPos=2)
	private String flightNumbers;
	
	private Integer commission;
	
	

}
