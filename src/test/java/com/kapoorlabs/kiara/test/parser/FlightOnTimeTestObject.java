package com.kapoorlabs.kiara.test.parser;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateRanges;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateTimeRanges;
import com.kapoorlabs.kiara.domain.annotations.NumericRange;

import lombok.Data;

@Data
public class FlightOnTimeTestObject {
	
	@NumericRange
	String flightNumbers;
	
	@CommaSeperatedDateRanges(value="yyyy/MM/dd")
	String dates;
	
	boolean onTime;
	
	@CommaSeperatedDateTimeRanges(value="yyyy/MM/dd HH:mm:ss")
	String dateTimes;

}
