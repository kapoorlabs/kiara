package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;

import lombok.Data;

@Data
public class PoiTestObject {
	
	private String cityName;
	
	private String cityCode;
	
	private String stateCode;
	
	@CommaSeperatedStrings
	private String pointOfInterests;

}
