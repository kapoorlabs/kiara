package com.kapoorlabs.kiara.loader;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class IndexTest1_30Test {
	
	Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
	

	@Test
	public void prepareForSearchTests_5() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharField('a');
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharField('c');
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndex().get(3).get("a").size());
		assertEquals(1, storeLoader.getStore().getInvertedIndex().get(3).get("c").size());
		assertEquals(1, storeLoader.getStore().getInvertedIndex().get(3).get(SdqlConstants.NULL).size());

	}

	@Test
	public void prepareForSearchTests_6() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanField(true);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanField(true);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanField(false);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndex().get(5).get("false").size());
		assertEquals(1, storeLoader.getStore().getInvertedIndex().get(5).get("true").size());
	}

	@Test
	public void prepareForSearchTests_8() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();

		dummyTestObject.setDates("null,2015-08-20, 2012-02-14, 1947-08-15");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_FORMAT);
		Long firstDateValue = LocalDate.parse("2015-08-20", formatter).toEpochDay();
		Long secondDateValue = LocalDate.parse("2012-02-14", formatter).toEpochDay();
		Long thirdDateValue = LocalDate.parse("1947-08-15", formatter).toEpochDay();

		assertEquals("null,2015-08-20, 2012-02-14, 1947-08-15",
				storeLoader.getStore().getInvertedNumericIndex().get(6).get(firstDateValue).get(0).getStringValue());

		assertEquals("null,2015-08-20, 2012-02-14, 1947-08-15",
				storeLoader.getStore().getInvertedNumericIndex().get(6).get(secondDateValue).get(0).getStringValue());

		assertEquals("null,2015-08-20, 2012-02-14, 1947-08-15",
				storeLoader.getStore().getInvertedNumericIndex().get(6).get(thirdDateValue).get(0).getStringValue());

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndex().get(6).get(SdqlConstants.LONG_NULL).size());

	}

	@Test
	public void prepareForSearchTests_14() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDates2("2020-Feb-12");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();

		dummyTestObject.setDates2("2020-Feb-12");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		Long firstDateValue = LocalDate.parse("2020-Feb-12", formatter).toEpochDay();

		assertEquals("2020-Feb-12",
				storeLoader.getStore().getInvertedNumericIndex().get(7).get(firstDateValue).get(0).getStringValue());

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndex().get(7).get(firstDateValue).size());

	}

	@Test
	public void prepareForSearchTests_17() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate("2020-02-12");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate("1947-08-15");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate("2018-12-10");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate("2018-12-10");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_FORMAT);

		Long firstDateValue = LocalDate.parse("2020-02-12", formatter).toEpochDay();
		Long secondDateValue = LocalDate.parse("1947-08-15", formatter).toEpochDay();
		Long thirdDateValue = LocalDate.parse("2018-12-10", formatter).toEpochDay();

		assertEquals(SdqlConstants.NULL, storeLoader.getStore().getInvertedNumericIndex().get(8)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());

		assertEquals("2020-02-12",
				storeLoader.getStore().getInvertedNumericIndex().get(8).get(firstDateValue).get(0).getStringValue());

		assertEquals("1947-08-15",
				storeLoader.getStore().getInvertedNumericIndex().get(8).get(secondDateValue).get(0).getStringValue());

		assertEquals("2018-12-10",
				storeLoader.getStore().getInvertedNumericIndex().get(8).get(thirdDateValue).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_20() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate2("20/02/12");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate2("47/08/15");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate2("18/12/10");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate2("18/12/10");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");

		Long firstDateValue = LocalDate.parse("18/12/10", formatter).toEpochDay();
		Long secondDateValue = LocalDate.parse("20/02/12", formatter).toEpochDay();
		Long thirdDateValue = LocalDate.parse("47/08/15", formatter).toEpochDay();

		assertEquals(SdqlConstants.NULL, storeLoader.getStore().getInvertedNumericIndex().get(9)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());

		assertEquals("18/12/10",
				storeLoader.getStore().getInvertedNumericIndex().get(9).get(firstDateValue).get(0).getStringValue());

		assertEquals("20/02/12",
				storeLoader.getStore().getInvertedNumericIndex().get(9).get(secondDateValue).get(0).getStringValue());

		assertEquals("47/08/15",
				storeLoader.getStore().getInvertedNumericIndex().get(9).get(thirdDateValue).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_21() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimes("null, 2020-02-12 12:10:03");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_TIME_FORMAT);
		Long firstDateValue = LocalDateTime.parse("2020-02-12 12:10:03", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals("null, 2020-02-12 12:10:03",
				storeLoader.getStore().getInvertedNumericIndex().get(10).get(firstDateValue).get(0).getStringValue());

		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(10).get(firstDateValue).get(0).getDoubleValue());

		assertEquals("null, 2020-02-12 12:10:03", storeLoader.getStore().getInvertedNumericIndex().get(10)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());

		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(10).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());

	}

	@Test
	public void prepareForSearchTests_23() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimes2("null, 2020-Feb-12 12:10");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimes2("null, 2020-Feb-12 12:10");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm");
		Long firstDateValue = LocalDateTime.parse("2020-Feb-12 12:10", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals("null, 2020-Feb-12 12:10",
				storeLoader.getStore().getInvertedNumericIndex().get(11).get(firstDateValue).get(0).getStringValue());

		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(11).get(firstDateValue).get(0).getDoubleValue());

		assertEquals("null, 2020-Feb-12 12:10", storeLoader.getStore().getInvertedNumericIndex().get(11)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());

		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(11).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());

	}

	@Test
	public void prepareForSearchTests_25() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTime("2020-02-12 12:10:01");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTime("2008-05-12 09:10:00");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		Long firstDateValue = LocalDateTime.parse("2020-02-12 12:10:01", formatter).toEpochSecond(ZoneOffset.of("Z"));
		Long secondDateValue = LocalDateTime.parse("2008-05-12 09:10:00", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals("2020-02-12 12:10:01",
				storeLoader.getStore().getInvertedNumericIndex().get(12).get(firstDateValue).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(12).get(firstDateValue).get(0).getDoubleValue());

		assertEquals("2008-05-12 09:10:00",
				storeLoader.getStore().getInvertedNumericIndex().get(12).get(secondDateValue).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(12).get(secondDateValue).get(0).getDoubleValue());

	}

	@Test
	public void prepareForSearchTests_27() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTime2("20/02/12 12");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTime2("08/05/12 09");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH");
		Long firstDateValue = LocalDateTime.parse("20/02/12 12", formatter).toEpochSecond(ZoneOffset.of("Z"));
		Long secondDateValue = LocalDateTime.parse("08/05/12 09", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals("20/02/12 12",
				storeLoader.getStore().getInvertedNumericIndex().get(13).get(firstDateValue).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(13).get(firstDateValue).get(0).getDoubleValue());

		assertEquals("08/05/12 09",
				storeLoader.getStore().getInvertedNumericIndex().get(13).get(secondDateValue).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(13).get(secondDateValue).get(0).getDoubleValue());

	}

	@Test
	public void prepareForSearchTests_29() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("2,null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("2,null",
				storeLoader.getStore().getInvertedNumericIndex().get(14).get(2L).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(2L).get(0).getDoubleValue());

		assertEquals("2,null", storeLoader.getStore().getInvertedNumericIndex().get(14).get(SdqlConstants.LONG_NULL)
				.get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());

	}

	@Test
	public void prepareForSearchTests_30() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("2,Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("2,Null",
				storeLoader.getStore().getInvertedNumericIndex().get(14).get(2L).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(2L).get(0).getDoubleValue());

		assertEquals("2,Null", storeLoader.getStore().getInvertedNumericIndex().get(14).get(SdqlConstants.LONG_NULL)
				.get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(14).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());

	}

}
