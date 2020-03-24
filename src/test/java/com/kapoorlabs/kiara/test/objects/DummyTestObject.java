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
	
	//0
	int intField;
	//1
	short shortField;
	//2
	byte byteField;
	//3
	char charField;
	//4
	long longField;
	//5
	boolean booleanField;
	//6
	@CommaSeperatedDates
	String dates;
	//7
	@CommaSeperatedDates(value="yyyy-MMM-dd")
	String dates2;
	//8
	@DateFormat
	String date;
	//9
	@DateFormat(value="yy/MM/dd")
	String date2;
	//10
	@CommaSeperatedDateTimes
	String dateTimes;
	//11
	@CommaSeperatedDateTimes(value="yyyy-MMM-dd HH:mm")
	String dateTimes2;
	//12
	@DateTimeFormat
	String dateTime;
	//13
	@DateTimeFormat(value="yy/MM/dd HH")
	String dateTime2;
	//14
	@CommaSeperatedNumbers
	String numbers;
	//15
	@CommaSeperatedStrings
	String strings;
	//16
	@NumericRange
	String numericRange;
	//17
	@CommaSeperatedNumericRanges
	String numericRanges;
	//18
	@DateTimeRange
	String dateTimeRange;
	//19
	@DateTimeRange(value="yy/MMM/dd HH:mm")
	String dateTimeRange2;
	//20
	@DateRange
	String dateRange;
	//21
	@DateRange(value="yy/MMM/dd")
	String dateRange2;
	//22
	@CommaSeperatedDateTimeRanges
	String dateTimeRanges;
	//23
	@CommaSeperatedDateTimeRanges(value="yy/MMM/dd HH:mm")
	String dateTimeRanges2;
	//24
	@CommaSeperatedDateRanges
	String dateRanges;
	//25
	@CommaSeperatedDateRanges(value="yy/MMM/dd")
	String dateRanges2;
	//26
	String stringField;
	
	int[] intFields;

	short[] shortFields;

	byte[] byteFields;
	
	char[] charFields;

	long[] longFields;

	String[] StringFields;
	//27
	Integer integerObjectField;
	//28
	Short shortObjectField;
	//29
	Byte byteObjectField;
	//30
	Character characterObjectField;
	//31
	Long longObjectField;
	//32
	Boolean booleanFieldObject;
	
	Integer[] integerObjectFields;
	
	Short[] shortObjectFields;
	
	Byte[] byteObjectFields;
	
	Character[] characterObjectFields;
	
	Long[] longObjectFields;
	
	List<String> list;
	
	Object anyObject;
	//33
	float floatField;
	//34
	double doubleField;
	//35
	Float floatObject;
	//36
	Double doubleObject;
	

}
