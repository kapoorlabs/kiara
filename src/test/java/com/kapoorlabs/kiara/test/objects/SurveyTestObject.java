package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.NumericRange;

import lombok.Data;

@Data
public class SurveyTestObject {

	private Long respondentId;

	private String gender;

	@NumericRange
	private String ageRange;

	@NumericRange
	private String incomeRange;

	private String education;

	private String location;

}
