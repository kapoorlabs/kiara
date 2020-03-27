package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.DateFormat;

import lombok.Data;

@Data
public class CongressResignationsTestObject {
	
	private String member;
	
	private char party;
	
	private String district;
	
	private String congress;
	
	@DateFormat(value="yyyy-MM-dd")
	private String resignationDate;
	
	private String reason;
	
	private String source;
	
	@CommaSeperatedStrings
	private String category;
	
}
