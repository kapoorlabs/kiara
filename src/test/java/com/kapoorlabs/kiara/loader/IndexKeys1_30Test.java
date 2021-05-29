package com.kapoorlabs.kiara.loader;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.NullableOrderedString;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.IndexingException;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class IndexKeys1_30Test {
	
	Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
	
	@Test
	public void prepareForSearchTests_1() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();

		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(38, storeLoader.getStore().getColumnIndex().size());
		assertEquals(38, storeLoader.getStore().getInvertedIndex().size());
		assertEquals(38, storeLoader.getStore().getInvertedNumericIndex().size());
		assertEquals(38, storeLoader.getStore().getRanges().size());
		assertEquals(38, storeLoader.getStore().getInvertedIndexKeys().size());
		assertEquals(38, storeLoader.getStore().getInvertedNumericIndexKeys().size());
	}

	@Test
	public void prepareForSearchTests_2() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();

		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(38, storeLoader.getStore().getInvertedIndexKeys().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(0).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(1).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(2).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(4).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(6).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(7).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(8).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(9).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(10).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(11).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(12).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(13).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(14).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(16).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(17).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(18).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(19).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(20).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(21).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(22).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(23).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(24).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(25).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(27).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(28).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(29).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(31).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(33).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(34).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(35).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(36).getKeyList().size());

	}

	@Test
	public void prepareForSearchTests_3() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();

		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(38, storeLoader.getStore().getInvertedIndexKeys().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(3).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(5).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(15).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(16).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(17).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(18).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(19).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(20).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(21).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(22).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(23).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(24).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(25).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(26).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(30).getKeyList().size());
		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(32).getKeyList().size());

	}

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

		assertEquals(3, storeLoader.getStore().getInvertedIndexKeys().get(3).getKeyList().size());
		assertEquals(1, storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllLessThanEqual(new NullableOrderedString(SdqlConstants.NULL)).size());

		assertEquals(new NullableOrderedString(SdqlConstants.NULL),
				storeLoader.getStore().getInvertedIndexKeys().get(3).getKeyList().get(0));

		assertEquals(new NullableOrderedString("a"),
				storeLoader.getStore().getInvertedIndexKeys().get(3).getKeyList().get(1));

		assertEquals(new NullableOrderedString("c"),
				storeLoader.getStore().getInvertedIndexKeys().get(3).getKeyList().get(2));

		assertEquals(2, storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllGreaterThan(new NullableOrderedString(SdqlConstants.NULL)).size());

		assertEquals(3, storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllGreaterThanEqual(new NullableOrderedString(SdqlConstants.NULL)).size());

		assertEquals(new NullableOrderedString("c"), storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllGreaterThanEqual(new NullableOrderedString("c")).get(0));

		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllLessThan(new NullableOrderedString(SdqlConstants.NULL)).size());

		assertEquals(2, storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllLessThan(new NullableOrderedString("c")).size());

		assertEquals(3, storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllLessThanEqual(new NullableOrderedString("c")).size());

		assertEquals(new NullableOrderedString(SdqlConstants.NULL), storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllLessThanEqual(new NullableOrderedString("c")).get(0));

		assertEquals(2, storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllBetween(new NullableOrderedString(SdqlConstants.NULL), new NullableOrderedString("b")).size());

		assertEquals(new NullableOrderedString(SdqlConstants.NULL), storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllBetween(new NullableOrderedString(SdqlConstants.NULL), new NullableOrderedString("b")).get(0));

		assertEquals(new NullableOrderedString("a"), storeLoader.getStore().getInvertedIndexKeys().get(3)
				.getAllBetween(new NullableOrderedString(SdqlConstants.NULL), new NullableOrderedString("b")).get(1));

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

		assertEquals(2, storeLoader.getStore().getInvertedIndexKeys().get(5).getKeyList().size());

		assertEquals(new NullableOrderedString("false"),
				storeLoader.getStore().getInvertedIndexKeys().get(5).getKeyList().get(0));

		assertEquals(new NullableOrderedString("true"),
				storeLoader.getStore().getInvertedIndexKeys().get(5).getKeyList().get(1));
	}

	@Test
	public void prepareForSearchTests_7() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();

		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_FORMAT);
		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(6).getKeyList().size());

		assertEquals(SdqlConstants.LONG_NULL,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(6).getKeyList().get(0));

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(6).getKeyList().size());

		assertEquals(SdqlConstants.LONG_NULL, storeLoader.getStore().getInvertedNumericIndexKeys().get(6)
				.getAllLessThan(LocalDate.parse("2015-05-18", formatter).toEpochDay()).get(0));

		assertEquals(SdqlConstants.LONG_NULL, storeLoader.getStore().getInvertedNumericIndexKeys().get(6)
				.getAllLessThan(LocalDate.parse("1501-05-18", formatter).toEpochDay()).get(0));

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
		assertEquals(4, storeLoader.getStore().getInvertedNumericIndexKeys().get(6).getKeyList().size());

		assertEquals(SdqlConstants.LONG_NULL,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(6).getKeyList().get(0));

		assertEquals(LocalDate.parse("1947-08-15", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(6).getKeyList().get(1).longValue());

		assertEquals(LocalDate.parse("2012-02-14", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(6).getKeyList().get(2).longValue());

		assertEquals(LocalDate.parse("2015-08-20", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(6).getKeyList().get(3).longValue());

		assertEquals(3, storeLoader.getStore().getInvertedNumericIndexKeys().get(6)
				.getAllLessThan(LocalDate.parse("2015-05-18", formatter).toEpochDay()).size());

		assertEquals(SdqlConstants.LONG_NULL, storeLoader.getStore().getInvertedNumericIndexKeys().get(6)
				.getAllLessThan(LocalDate.parse("1501-05-18", formatter).toEpochDay()).get(0));

		assertEquals(LocalDate.parse("2015-08-20", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(6)
						.getAllBetween(LocalDate.parse("2014-05-18", formatter).toEpochDay(),
								LocalDate.parse("2017-05-18", formatter).toEpochDay())
						.get(0).longValue());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_9() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();

		dummyTestObject.setDates("null,2015/118/20");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_10() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();

		dummyTestObject.setDates("March 18");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_11() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();

		dummyTestObject.setDates("2018/01/01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_13() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();

		dummyTestObject.setDates2("2018-01-01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

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
		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(7).getKeyList().size());

		assertEquals(LocalDate.parse("2020-Feb-12", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(7).getKeyList().get(0).longValue());

		assertEquals(0, storeLoader.getStore().getInvertedNumericIndexKeys().get(7)
				.getAllLessThan(LocalDate.parse("2015-May-18", formatter).toEpochDay()).size());

		assertEquals(LocalDate.parse("2020-Feb-12", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(7)
						.getAllGreaterThan(LocalDate.parse("2019-Jan-01", formatter).toEpochDay()).get(0).longValue());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_15() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate("null, 2020-02-12");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_16() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate("2020/02/12");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

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
		assertEquals(4, storeLoader.getStore().getInvertedNumericIndexKeys().get(8).getKeyList().size());

		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(8).getKeyList().get(0).longValue());

		assertEquals(LocalDate.parse("1947-08-15", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(8).getKeyList().get(1).longValue());

		assertEquals(LocalDate.parse("2018-12-10", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(8).getKeyList().get(2).longValue());

		assertEquals(LocalDate.parse("2020-02-12", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(8).getKeyList().get(3).longValue());

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(8)
				.getAllLessThan(LocalDate.parse("2015-01-10", formatter).toEpochDay()).size());

		assertEquals(3,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(8).getAllGreaterThan(Long.MIN_VALUE).size());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_18() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate2("null, 2020-02-12");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_19() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate2("2020-02-12");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

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
		assertEquals(4, storeLoader.getStore().getInvertedNumericIndexKeys().get(9).getKeyList().size());

		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(9).getKeyList().get(0).longValue());

		assertEquals(LocalDate.parse("18/12/10", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(9).getKeyList().get(1).longValue());

		assertEquals(LocalDate.parse("20/02/12", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(9).getKeyList().get(2).longValue());

		assertEquals(LocalDate.parse("47/08/15", formatter).toEpochDay(),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(9).getKeyList().get(3).longValue());

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(9)
				.getAllLessThan(LocalDate.parse("15/01/10", formatter).toEpochDay()).size());

		assertEquals(3,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(9).getAllGreaterThan(Long.MIN_VALUE).size());

	}

	@Test
	public void prepareForSearchTests_21() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimes("null, 2020-02-12 12:10:03");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_TIME_FORMAT);
		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(10).getKeyList().size());

		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(10).getKeyList().get(0).longValue());

		assertEquals(LocalDateTime.parse("2020-02-12 12:10:03", formatter).toEpochSecond(ZoneOffset.of("Z")),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(10).getKeyList().get(1).longValue());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_22() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimes2("null, 2020-02-12 12:10:03");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

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
		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(11).getKeyList().size());

		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(11).getKeyList().get(0).longValue());

		assertEquals(LocalDateTime.parse("2020-Feb-12 12:10", formatter).toEpochSecond(ZoneOffset.of("Z")),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(11).getKeyList().get(1).longValue());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_24() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTime("2020/02/12 12:10:03");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

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
		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(12).getKeyList().size());

		assertEquals(LocalDateTime.parse("2008-05-12 09:10:00", formatter).toEpochSecond(ZoneOffset.of("Z")),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(12).getKeyList().get(0).longValue());

		assertEquals(LocalDateTime.parse("2020-02-12 12:10:01", formatter).toEpochSecond(ZoneOffset.of("Z")),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(12).getKeyList().get(1).longValue());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_26() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTime("2020/02/12 12:10:03");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

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
		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(13).getKeyList().size());

		assertEquals(LocalDateTime.parse("08/05/12 09", formatter).toEpochSecond(ZoneOffset.of("Z")),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(13).getKeyList().get(0).longValue());

		assertEquals(LocalDateTime.parse("20/02/12 12", formatter).toEpochSecond(ZoneOffset.of("Z")),
				storeLoader.getStore().getInvertedNumericIndexKeys().get(13).getKeyList().get(1).longValue());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_28() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("2,null,n");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test
	public void prepareForSearchTests_29() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("2,null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(0).longValue());
		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(1).longValue());

	}

	@Test
	public void prepareForSearchTests_30() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("2,Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(0).longValue());
		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(1).longValue());

	}

}
