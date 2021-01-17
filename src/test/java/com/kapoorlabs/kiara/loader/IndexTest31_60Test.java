package com.kapoorlabs.kiara.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class IndexTest31_60Test {
	
	Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
	

	@Test
	public void prepareForSearchTests_32() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("2");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("-10");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("NULL");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("-34.10, -34.9, -34.6, 44.55, 100002.6, 9223372036854775807.99");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("2", storeLoader.getStore().getInvertedNumericIndex().get(14).get(2L).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(2L).get(0).getDoubleValue());

		assertEquals("-10", storeLoader.getStore().getInvertedNumericIndex().get(14).get(-10L).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(-10L).get(0).getDoubleValue());

		assertEquals("NULL", storeLoader.getStore().getInvertedNumericIndex().get(14).get(SdqlConstants.LONG_NULL)
				.get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndex().get(14).get(-34L).size());
		assertEquals("-34.10, -34.9, -34.6, 44.55, 100002.6, 9223372036854775807.99",
				storeLoader.getStore().getInvertedNumericIndex().get(14).get(-34L).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(-34L).get(0).getDoubleValue());

		assertEquals("-34.10, -34.9, -34.6, 44.55, 100002.6, 9223372036854775807.99",
				storeLoader.getStore().getInvertedNumericIndex().get(14).get(44L).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(44L).get(0).getDoubleValue());

		assertEquals("-34.10, -34.9, -34.6, 44.55, 100002.6, 9223372036854775807.99",
				storeLoader.getStore().getInvertedNumericIndex().get(14).get(100002L).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(14).get(100002L).get(0).getDoubleValue());

		assertEquals("-34.10, -34.9, -34.6, 44.55, 100002.6, 9223372036854775807.99", storeLoader.getStore()
				.getInvertedNumericIndex().get(14).get(9223372036854775807L).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(9223372036854775807L).get(0)
				.getDoubleValue());

	}

	@Test
	public void prepareForSearchTests_33() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("2,a,null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("2,a,null", storeLoader.getStore().getInvertedIndex().get(15).get("2").get(0).getStringValue());

		assertEquals("2,a,null", storeLoader.getStore().getInvertedIndex().get(15).get("a").get(0).getStringValue());

		assertEquals("2,a,null",
				storeLoader.getStore().getInvertedIndex().get(15).get(SdqlConstants.NULL).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_34() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("2,a,null");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("NULL, nULL");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		Set<String> expectedResult = new HashSet<>();
		expectedResult.add("2,a,null");
		expectedResult.add("NULL, nULL");

		assertTrue(expectedResult.contains(
				storeLoader.getStore().getInvertedIndex().get(15).get(SdqlConstants.NULL).get(1).getStringValue()));

		assertTrue(expectedResult.contains(
				storeLoader.getStore().getInvertedIndex().get(15).get(SdqlConstants.NULL).get(0).getStringValue()));

		assertEquals("2,a,null", storeLoader.getStore().getInvertedIndex().get(15).get("2").get(0).getStringValue());

		assertEquals("2,a,null", storeLoader.getStore().getInvertedIndex().get(15).get("a").get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_35() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("2,,null");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("NULL, nULL");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		Set<String> expectedResult = new HashSet<>();
		expectedResult.add("2,,null");
		expectedResult.add("NULL, nULL");

		assertTrue(expectedResult.contains(
				storeLoader.getStore().getInvertedIndex().get(15).get(SdqlConstants.NULL).get(1).getStringValue()));

		assertTrue(expectedResult.contains(
				storeLoader.getStore().getInvertedIndex().get(15).get(SdqlConstants.NULL).get(0).getStringValue()));

		assertEquals("2,,null", storeLoader.getStore().getInvertedIndex().get(15).get("2").get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_36() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedIndex().get(15).get(SdqlConstants.NULL).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_37() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRange(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedIndex().get(16)
						.get(SdqlConstants.NULL + SdqlConstants.LONG_NULL + "-" + SdqlConstants.LONG_NULL).get(0)
						.getStringValue());

	}

	@Test
	public void prepareForSearchTests_40() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRange("AA1-9");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRange("DL1-9");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRange("AA4-9");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRange("AA-9");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("AA1-9", storeLoader.getStore().getInvertedIndex().get(16).get("AA1-9").get(0).getStringValue());
		assertEquals("DL1-9", storeLoader.getStore().getInvertedIndex().get(16).get("DL1-9").get(0).getStringValue());
		assertEquals("AA4-9", storeLoader.getStore().getInvertedIndex().get(16).get("AA4-9").get(0).getStringValue());
		assertEquals("AA-9", storeLoader.getStore().getInvertedIndex().get(16)
				.get("AA" + SdqlConstants.LONG_NULL + "-9").get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_41() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRanges("23,,-a1-2,null,null");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRanges("0-100");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("23,,-a1-2,null,null",
				storeLoader.getStore().getInvertedIndex().get(17).get(23 + "-" + 23).get(0).getStringValue());

		assertEquals("23,,-a1-2,null,null", storeLoader.getStore().getInvertedIndex().get(17)
				.get("null" + SdqlConstants.LONG_NULL + "-" + SdqlConstants.LONG_NULL).get(0).getStringValue());

		assertEquals("23,,-a1-2,null,null",
				storeLoader.getStore().getInvertedIndex().get(17).get("-a1-2").get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_45() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange("null-Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("null-Null", storeLoader.getStore().getInvertedIndex().get(18)
				.get(SdqlConstants.LONG_NULL + "-" + SdqlConstants.LONG_NULL).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_46() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange("null-2018-01-01 12:01:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_TIME_FORMAT);
		String upperRange = LocalDateTime.parse("2018-01-01 12:01:01", formatter).toEpochSecond(ZoneOffset.of("Z"))
				+ "";

		assertEquals("null-2018-01-01 12:01:01", storeLoader.getStore().getInvertedIndex().get(18)
				.get(SdqlConstants.LONG_NULL + "-" + upperRange).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_47() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange2("18/Jan/10 12:01-19/Jan/10 12:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MMM/dd HH:mm");

		Long lowerRange = LocalDateTime.parse("18/Jan/10 12:01", formatter).toEpochSecond(ZoneOffset.of("Z"));
		Long upperRange = LocalDateTime.parse("19/Jan/10 12:01", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals("18/Jan/10 12:01-19/Jan/10 12:01", storeLoader.getStore().getInvertedIndex().get(19)
				.get(lowerRange + "-" + upperRange).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_50() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange("2018-01-05");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Long lowerRange = LocalDate.parse("2018-01-05", formatter).toEpochDay();

		assertEquals("2018-01-05", storeLoader.getStore().getInvertedIndex().get(20).get(lowerRange + "-" + lowerRange)
				.get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_51() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange("null-2018-01-05");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Long upperRange = LocalDate.parse("2018-01-05", formatter).toEpochDay();

		assertEquals("null-2018-01-05", storeLoader.getStore().getInvertedIndex().get(20)
				.get(SdqlConstants.LONG_NULL + "-" + upperRange).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_53() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange2("18/Jan/05");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MMM/dd");

		Long lowerRange = LocalDate.parse("18/Jan/05", formatter).toEpochDay();

		assertEquals("18/Jan/05", storeLoader.getStore().getInvertedIndex().get(21).get(lowerRange + "-" + lowerRange)
				.get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_56() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRanges("null-Null,2018-01-01 12:01:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_TIME_FORMAT);
		Long lowerRange = LocalDateTime.parse("2018-01-01 12:01:01", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals("null-Null,2018-01-01 12:01:01", storeLoader.getStore().getInvertedIndex().get(22)
				.get(SdqlConstants.LONG_NULL + "-" + SdqlConstants.LONG_NULL).get(0).getStringValue());

		assertEquals("null-Null,2018-01-01 12:01:01", storeLoader.getStore().getInvertedIndex().get(22)
				.get(lowerRange + "-" + lowerRange).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_59() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRanges2("null-Null,18/Jan/01 12:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MMM/dd HH:mm");
		Long lowerRange = LocalDateTime.parse("18/Jan/01 12:01", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals("null-Null,18/Jan/01 12:01", storeLoader.getStore().getInvertedIndex().get(23)
				.get(SdqlConstants.LONG_NULL + "-" + SdqlConstants.LONG_NULL).get(0).getStringValue());

		assertEquals("null-Null,18/Jan/01 12:01", storeLoader.getStore().getInvertedIndex().get(23)
				.get(lowerRange + "-" + lowerRange).get(0).getStringValue());
	}

}
