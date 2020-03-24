package com.kapoorlabs.kiara.test.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.adapters.PojoAdapter;
import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.NullableOrderedString;
import com.kapoorlabs.kiara.domain.Range;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.IndexingException;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class IndexKeys31_60Test {
	
	Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
	

	@Test
	public void prepareForSearchTests_31() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

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
		dummyTestObject.setNumbers("1234567890,44,54.5");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(6, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(0).longValue());
		assertEquals(-10, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(1).longValue());
		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(2).longValue());
		assertEquals(44, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(3).longValue());
		assertEquals(54, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(4).longValue());
		assertEquals(1234567890,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(5).longValue());

	}

	@Test
	public void prepareForSearchTests_32() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

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

		assertEquals(7, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(0).longValue());
		assertEquals(-34, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(1).longValue());
		assertEquals(-10, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(2).longValue());
		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(3).longValue());
		assertEquals(44, storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(4).longValue());
		assertEquals(100002,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(5).longValue());
		assertEquals(9223372036854775807L,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(14).getKeyList().get(6).longValue());

	}

	@Test
	public void prepareForSearchTests_33() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("2,a,null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(3, storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().size());
		assertEquals(new NullableOrderedString(SdqlConstants.NULL),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(0));
		assertEquals(new NullableOrderedString("2"),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(1));
		assertEquals(new NullableOrderedString("a"),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(2));

	}

	@Test
	public void prepareForSearchTests_34() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("2,a,null");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("NULL, nULL");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(3, storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().size());
		assertEquals(new NullableOrderedString(SdqlConstants.NULL),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(0));
		assertEquals(new NullableOrderedString("2"),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(1));
		assertEquals(new NullableOrderedString("a"),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(2));

	}

	@Test
	public void prepareForSearchTests_35() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("2,,null");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings("NULL, nULL");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(2, storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().size());
		assertEquals(new NullableOrderedString(SdqlConstants.NULL),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(0));
		assertEquals(new NullableOrderedString("2"),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(1));

	}

	@Test
	public void prepareForSearchTests_36() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStrings(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().size());
		assertEquals(new NullableOrderedString(SdqlConstants.NULL),
				storeLoader.getStore().getInvertedIndexKeys().get(15).getKeyList().get(0));

	}

	@Test
	public void prepareForSearchTests_37() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRange(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(16).getKeyList().size());

		assertEquals(1, storeLoader.getStore().getRanges().get(16).size());

		assertEquals(1, storeLoader.getStore().getRanges().get(16).get(SdqlConstants.NULL)
				.getInRange(SdqlConstants.LONG_NULL).size());

		assertEquals(0, storeLoader.getStore().getRanges().get(16).get(SdqlConstants.NULL).getInRange(0L).size());

		assertEquals(new Range(SdqlConstants.NULL, SdqlConstants.LONG_NULL, SdqlConstants.LONG_NULL), storeLoader
				.getStore().getRanges().get(16).get(SdqlConstants.NULL).getInRange(SdqlConstants.LONG_NULL).get(0));

	}

	@Test
	public void prepareForSearchTests_38() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRange("23,,-a1-2");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getRanges().get(16).size());

		assertEquals("23,,-a", storeLoader.getStore().getRanges().get(16).keySet().iterator().next());
		assertEquals(0, storeLoader.getStore().getRanges().get(16).get("23,,-a").getInRange(0L).size());
		assertEquals(new Range("23,,-a", 1L, 2L),
				storeLoader.getStore().getRanges().get(16).get("23,,-a").getInRange(1L).get(0));
		assertEquals(0, storeLoader.getStore().getRanges().get(16).get("23,,-a").getInRange(3L).size());

	}

	@Test
	public void prepareForSearchTests_39() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

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

		assertEquals(2, storeLoader.getStore().getRanges().get(16).size());

		assertEquals(1, storeLoader.getStore().getRanges().get(16).get("AA").getInRange(0L).size());
		assertEquals(new Range("AA", SdqlConstants.LONG_NULL, 9L),
				storeLoader.getStore().getRanges().get(16).get("AA").getInRange(0L).get(0));

		assertEquals(3, storeLoader.getStore().getRanges().get(16).get("AA").getInRange(5L).size());

		Set<Range> expectedRanges = new HashSet<>();

		expectedRanges.add(new Range("AA", 1L, 9L));
		expectedRanges.add(new Range("AA", 4L, 9L));
		expectedRanges.add(new Range("AA", SdqlConstants.LONG_NULL, 9L));

		for (int i = 0; i < 3; i++) {

			assertTrue(expectedRanges
					.contains(storeLoader.getStore().getRanges().get(16).get("AA").getInRange(5L).get(i)));

		}

	}

	@Test
	public void prepareForSearchTests_40() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

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

		assertEquals(2, storeLoader.getStore().getRanges().get(16).size());

		assertEquals(0, storeLoader.getStore().getRanges().get(16).get("DL").getInRange(0L).size());
		assertEquals(0, storeLoader.getStore().getRanges().get(16).get("DL").getInRange(Long.MAX_VALUE).size());
		assertEquals("DL1-9", storeLoader.getStore().getRanges().get(16).get("DL").getInRange(6L).get(0).toString());

	}

	@Test
	public void prepareForSearchTests_41() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRanges("23,,-a1-2,null,null");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumericRanges("0-100");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(3, storeLoader.getStore().getRanges().get(17).size());
		assertEquals(0, storeLoader.getStore().getRanges().get(17).get(SdqlConstants.NULL).getInRange(0L).size());
		assertEquals(1,
				storeLoader.getStore().getRanges().get(17).get(SdqlConstants.NULL).getInRange(Long.MIN_VALUE).size());
		assertEquals("0-100", storeLoader.getStore().getRanges().get(17).get("").getInRange(23L).get(0).toString());
		assertEquals("23-23", storeLoader.getStore().getRanges().get(17).get("").getInRange(23L).get(1).toString());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_42() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange("2018-01-01 12:01:01-2019");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_43() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange("2018-01-01 12:01:01-null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_44() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange("2018-01-01 12:01:01-Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test
	public void prepareForSearchTests_45() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange("null-Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getRanges().get(18).size());
		assertEquals("-9223372036854775808--9223372036854775808",
				storeLoader.getStore().getRanges().get(18).get("").getInRange(Long.MIN_VALUE).get(0).toString());
	}

	@Test
	public void prepareForSearchTests_46() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange("null-2018-01-01 12:01:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_TIME_FORMAT);
		String upperRange = LocalDateTime.parse("2018-01-01 12:01:01", formatter).toEpochSecond(ZoneOffset.of("Z"))
				+ "";

		assertEquals(1, storeLoader.getStore().getRanges().get(18).size());
		assertEquals("-9223372036854775808-" + upperRange,
				storeLoader.getStore().getRanges().get(18).get("").getInRange(Long.MIN_VALUE).get(0).toString());
	}

	@Test
	public void prepareForSearchTests_47() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRange2("18/Jan/10 12:01-19/Jan/10 12:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MMM/dd HH:mm");

		Long searchRange = LocalDateTime.parse("18/May/08 12:01", formatter).toEpochSecond(ZoneOffset.of("Z"));
		Long outRange = LocalDateTime.parse("19/Jan/10 12:02", formatter).toEpochSecond(ZoneOffset.of("Z"));
		Long lowerRange = LocalDateTime.parse("18/Jan/10 12:01", formatter).toEpochSecond(ZoneOffset.of("Z"));
		Long upperRange = LocalDateTime.parse("19/Jan/10 12:01", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals(1, storeLoader.getStore().getRanges().get(19).size());
		assertEquals(lowerRange + "-" + upperRange,
				storeLoader.getStore().getRanges().get(19).get("").getInRange(searchRange).get(0).toString());

		assertEquals(0, storeLoader.getStore().getRanges().get(19).get("").getInRange(outRange).size());
	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_48() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange("2018-01-01-Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_49() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange("2018-01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test
	public void prepareForSearchTests_50() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange("2018-01-05");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Long lowerRange = LocalDate.parse("2018-01-05", formatter).toEpochDay();

		assertEquals(1, storeLoader.getStore().getRanges().get(20).size());
		assertEquals(lowerRange + "-" + lowerRange,
				storeLoader.getStore().getRanges().get(20).get("").getInRange(lowerRange).get(0).toString());

	}

	@Test
	public void prepareForSearchTests_51() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange("null-2018-01-05");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Long upperRange = LocalDate.parse("2018-01-05", formatter).toEpochDay();
		Long searchRange = LocalDate.parse("2010-01-05", formatter).toEpochDay();
		Long outRange = LocalDate.parse("2018-01-06", formatter).toEpochDay();

		assertEquals(1, storeLoader.getStore().getRanges().get(21).size());
		assertEquals(SdqlConstants.LONG_NULL + "-" + upperRange,
				storeLoader.getStore().getRanges().get(20).get("").getInRange(searchRange).get(0).toString());

		assertEquals(0, storeLoader.getStore().getRanges().get(20).get("").getInRange(outRange).size());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_52() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange2("2018-01-05");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test
	public void prepareForSearchTests_53() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRange2("18/Jan/05");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MMM/dd");

		Long lowerRange = LocalDate.parse("18/Jan/05", formatter).toEpochDay();

		assertEquals(1, storeLoader.getStore().getRanges().get(21).size());
		assertEquals(lowerRange + "-" + lowerRange,
				storeLoader.getStore().getRanges().get(21).get("").getInRange(lowerRange).get(0).toString());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_54() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRanges("2018-01-01 12:01:01-null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_55() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRanges("2018-01-01 12:01:01-Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test
	public void prepareForSearchTests_56() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRanges("null-Null,2018-01-01 12:01:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SdqlConstants.DEFAULT_DATE_TIME_FORMAT);
		Long searchRange = LocalDateTime.parse("2018-01-01 12:01:01", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals(1, storeLoader.getStore().getRanges().get(22).size());
		assertEquals("-9223372036854775808--9223372036854775808",
				storeLoader.getStore().getRanges().get(22).get("").getInRange(Long.MIN_VALUE).get(0).toString());
		assertEquals(searchRange + "-" + searchRange,
				storeLoader.getStore().getRanges().get(22).get("").getInRange(searchRange).get(0).toString());
	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_57() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRanges2("2018-01-01 12:01:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_58() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRanges2("null-2018-01-01 12:01:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test
	public void prepareForSearchTests_59() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateTimeRanges2("null-Null,18/Jan/01 12:01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MMM/dd HH:mm");
		Long searchRange = LocalDateTime.parse("18/Jan/01 12:01", formatter).toEpochSecond(ZoneOffset.of("Z"));

		assertEquals(1, storeLoader.getStore().getRanges().get(23).size());
		assertEquals("-9223372036854775808--9223372036854775808",
				storeLoader.getStore().getRanges().get(23).get("").getInRange(Long.MIN_VALUE).get(0).toString());
		assertEquals(searchRange + "-" + searchRange,
				storeLoader.getStore().getRanges().get(23).get("").getInRange(searchRange).get(0).toString());
	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_60() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRanges("null-null,2018-01-01-Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

}
