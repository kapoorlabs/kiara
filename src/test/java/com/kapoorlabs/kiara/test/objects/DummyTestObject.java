package com.kapoorlabs.kiara.test.objects;

import java.util.List;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateRanges;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateTimeRanges;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateTimes;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDates;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedNumbers;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedNumericRanges;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.DateFormat;
import com.kapoorlabs.kiara.domain.annotations.DateRange;
import com.kapoorlabs.kiara.domain.annotations.DateTimeFormat;
import com.kapoorlabs.kiara.domain.annotations.DateTimeRange;
import com.kapoorlabs.kiara.domain.annotations.NumericRange;

import lombok.Data;

@Data
public class DummyTestObject {
	
	int intField;
	
	short shortField;
	
	byte byteField;
	
	char charField;
	
	long longField;
	
	boolean booleanField;
	
	@CommaSeperatedDates
	String dates;
	
	@CommaSeperatedDates(value="MMM-dd")
	String dates2;
	
	@DateFormat
	String date;
	
	@DateFormat(value="yy/MM/dd")
	String date2;
	
	@CommaSeperatedDateTimes
	String dateTimes;
	
	@CommaSeperatedDateTimes(value="MMM-dd HH:mm")
	String dateTimes2;
	
	@DateTimeFormat
	String dateTime;
	
	@DateTimeFormat(value="yy/MM/dd HH")
	String dateTime2;
	
	@CommaSeperatedNumbers
	String numbers;
	
	@CommaSeperatedStrings
	String strings;
	
	@NumericRange
	String numericRange;
	
	@CommaSeperatedNumericRanges
	String numericRanges;
	
	@DateTimeRange
	String dateTimeRange;
	
	@DateTimeRange(value="yy/MMM HH:mm")
	String dateTimeRange2;
	
	@DateRange
	String dateRange;
	
	@DateRange(value="yy/MMM")
	String dateRange2;
	
	@CommaSeperatedDateTimeRanges
	String dateTimeRanges;
	
	@CommaSeperatedDateTimeRanges(value="yy/MMM HH:mm")
	String dateTimeRanges2;
	
	@CommaSeperatedDateRanges
	String dateRanges;
	
	@CommaSeperatedDateRanges(value="yy/MMM")
	String dateRanges2;
	
	String stringField;
	
	int[] intFields;
	
	short[] shortFields;
	
	byte[] byteFields;
	
	char[] charFields;
	
	long[] longFields;
	
	String[] StringFields;
	
	Integer integerObjectField;
	
	Short shortObjectField;
	
	Byte byteObjectField;
	
	Character characterObjectField;
	
	Long longObjectField;
	
	Boolean booleanFieldObject;
	
	Integer[] integerObjectFields;
	
	Short[] shortObjectFields;
	
	Byte[] byteObjectFields;
	
	Character[] characterObjectFields;
	
	Long[] longObjectFields;
	
	List<String> list;
	
	Object anyObject;
	
	float floatField;
	
	double doubleField;
	
	Float floatObject;
	
	Double doubleObject;
	
	

}
