package com.priceline.kiara.test.objects;

import com.priceline.kiara.domain.annotations.CommaSeperatedStrings;

import lombok.Data;

@Data
public class PoiTestObject {
	
	private String cityName;
	
	private String cityCode;
	
	private String stateCode;
	
	@CommaSeperatedStrings
	private String pointOfInterests;

}
