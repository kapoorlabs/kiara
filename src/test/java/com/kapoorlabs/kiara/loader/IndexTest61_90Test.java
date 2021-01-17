package com.kapoorlabs.kiara.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class IndexTest61_90Test {
	
	Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
	

	@Test
	public void prepareForSearchTests_62() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRanges("2018-01-05, null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Long lowerRange = LocalDate.parse("2018-01-05", formatter).toEpochDay();

		assertEquals("2018-01-05, null", storeLoader.getStore().getInvertedIndex().get(24)
				.get(SdqlConstants.LONG_NULL + "-" + SdqlConstants.LONG_NULL).get(0).getStringValue());

		assertEquals("2018-01-05, null", storeLoader.getStore().getInvertedIndex().get(24)
				.get(lowerRange + "-" + lowerRange).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_63() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRanges("null-2018-01-05,   2010-01-01-2012-01-01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Long upperRange = LocalDate.parse("2018-01-05", formatter).toEpochDay();
		Long lowerRange = LocalDate.parse("2010-01-01", formatter).toEpochDay();
		Long upperRange2 = LocalDate.parse("2012-01-01", formatter).toEpochDay();

		assertEquals("null-2018-01-05,   2010-01-01-2012-01-01", storeLoader.getStore().getInvertedIndex().get(24)
				.get(SdqlConstants.LONG_NULL + "-" + upperRange).get(0).getStringValue());

		assertEquals("null-2018-01-05,   2010-01-01-2012-01-01", storeLoader.getStore().getInvertedIndex().get(24)
				.get(lowerRange + "-" + upperRange2).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_65() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRanges2("18/Jan/05, null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MMM/dd");

		Long lowerRange = LocalDate.parse("18/Jan/05", formatter).toEpochDay();

		assertEquals("18/Jan/05, null", storeLoader.getStore().getInvertedIndex().get(25)
				.get(lowerRange + "-" + lowerRange).get(0).getStringValue());

		assertEquals("18/Jan/05, null", storeLoader.getStore().getInvertedIndex().get(25)
				.get(SdqlConstants.LONG_NULL + "-" + SdqlConstants.LONG_NULL).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_66() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("null",
				storeLoader.getStore().getInvertedIndex().get(26).get(SdqlConstants.NULL).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_67() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("null",
				storeLoader.getStore().getInvertedIndex().get(26).get(SdqlConstants.NULL).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_68() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("Null",
				storeLoader.getStore().getInvertedIndex().get(26).get(SdqlConstants.NULL).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_69() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("Nulls");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("Nulls", storeLoader.getStore().getInvertedIndex().get(26).get("Nulls").get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_70() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("Null");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("apple");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("Apple");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("Ball");
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("apple");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals("Null",
				storeLoader.getStore().getInvertedIndex().get(26).get(SdqlConstants.NULL).get(0).getStringValue());

		assertEquals("Apple", storeLoader.getStore().getInvertedIndex().get(26).get("Apple").get(0).getStringValue());

		assertEquals("Ball", storeLoader.getStore().getInvertedIndex().get(26).get("Ball").get(0).getStringValue());

		assertEquals("apple", storeLoader.getStore().getInvertedIndex().get(26).get("apple").get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_71() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntegerObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedNumericIndex().get(27).get(Long.MIN_VALUE).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(27).get(Long.MIN_VALUE).get(0).getDoubleValue());

	}

	@Test
	public void prepareForSearchTests_72() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntegerObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntegerObjectField(Integer.MIN_VALUE);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntegerObjectField(Integer.MAX_VALUE);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedNumericIndex().get(27).get(Long.MIN_VALUE).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(27).get(Long.MIN_VALUE).get(0).getDoubleValue());

		assertEquals(Integer.MIN_VALUE + ".00", storeLoader.getStore().getInvertedNumericIndex().get(27)
				.get((long) Integer.MIN_VALUE).get(0).getStringValue());
		assertEquals(Integer.MIN_VALUE, storeLoader.getStore().getInvertedNumericIndex().get(27)
				.get((long) Integer.MIN_VALUE).get(0).getDoubleValue().intValue());

		assertEquals(Integer.MAX_VALUE + ".00", storeLoader.getStore().getInvertedNumericIndex().get(27)
				.get((long) Integer.MAX_VALUE).get(0).getStringValue());
		assertEquals(Integer.MAX_VALUE, storeLoader.getStore().getInvertedNumericIndex().get(27)
				.get((long) Integer.MAX_VALUE).get(0).getDoubleValue().intValue());

	}

	@Test
	public void prepareForSearchTests_73() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setShortObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedNumericIndex().get(28).get(Long.MIN_VALUE).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(28).get(Long.MIN_VALUE).get(0).getDoubleValue());

	}

	@Test
	public void prepareForSearchTests_74() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setShortObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setShortObjectField(Short.MIN_VALUE);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setShortObjectField(Short.MAX_VALUE);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedNumericIndex().get(28).get(Long.MIN_VALUE).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(28).get(Long.MIN_VALUE).get(0).getDoubleValue());

		assertEquals(Short.MIN_VALUE + ".00", storeLoader.getStore().getInvertedNumericIndex().get(28)
				.get((long) Short.MIN_VALUE).get(0).getStringValue());
		assertEquals(Short.MIN_VALUE, storeLoader.getStore().getInvertedNumericIndex().get(28)
				.get((long) Short.MIN_VALUE).get(0).getDoubleValue().shortValue());

		assertEquals(Short.MAX_VALUE + ".00", storeLoader.getStore().getInvertedNumericIndex().get(28)
				.get((long) Short.MAX_VALUE).get(0).getStringValue());
		assertEquals(Short.MAX_VALUE, storeLoader.getStore().getInvertedNumericIndex().get(28)
				.get((long) Short.MAX_VALUE).get(0).getDoubleValue().shortValue());
	}

	@Test
	public void prepareForSearchTests_75() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedNumericIndex().get(29).get(Long.MIN_VALUE).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(29).get(Long.MIN_VALUE).get(0).getDoubleValue());

	}

	public void prepareForSearchTests_76() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteObjectField(Byte.MIN_VALUE);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteObjectField(Byte.MAX_VALUE);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedNumericIndex().get(29).get(Long.MIN_VALUE).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(29).get(Long.MIN_VALUE).get(0).getDoubleValue());

		assertEquals(Byte.MIN_VALUE + ".00", storeLoader.getStore().getInvertedNumericIndex().get(29)
				.get((long) Byte.MIN_VALUE).get(0).getStringValue());
		assertEquals(Byte.MIN_VALUE, storeLoader.getStore().getInvertedNumericIndex().get(29).get((long) Byte.MIN_VALUE)
				.get(0).getDoubleValue().byteValue());

		assertEquals(Byte.MAX_VALUE + ".00", storeLoader.getStore().getInvertedNumericIndex().get(29)
				.get((long) Byte.MAX_VALUE).get(0).getStringValue());
		assertEquals(Byte.MAX_VALUE, storeLoader.getStore().getInvertedNumericIndex().get(29).get((long) Byte.MAX_VALUE)
				.get(0).getDoubleValue().byteValue());

	}

	@Test
	public void prepareForSearchTests_77() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharacterObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedIndex().get(30).get(SdqlConstants.NULL).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_78() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharacterObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharacterObjectField('a');
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharacterObjectField('1');
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharacterObjectField('`');
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharacterObjectField('1');
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedIndex().get(30).get(SdqlConstants.NULL).get(0).getStringValue());

		assertEquals("1", storeLoader.getStore().getInvertedIndex().get(30).get("1").get(0).getStringValue());

		assertEquals("`", storeLoader.getStore().getInvertedIndex().get(30).get("`").get(0).getStringValue());

		assertEquals("a", storeLoader.getStore().getInvertedIndex().get(30).get("a").get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_79() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setLongObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedNumericIndex().get(31).get(Long.MIN_VALUE).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedNumericIndex().get(31).get(Long.MIN_VALUE).get(0).getDoubleValue());

	}

	public void prepareForSearchTests_80() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setLongObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setLongObjectField(Long.MIN_VALUE);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setLongObjectField(Long.MAX_VALUE);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		Set<String> expectedStrings = new HashSet<>();
		Set<Long> expectedLongs = new HashSet<>();

		expectedStrings.add(SdqlConstants.NULL);
		expectedStrings.add(Long.MIN_VALUE + "");

		expectedLongs.add(null);
		expectedLongs.add(Long.MIN_VALUE);

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndex().get(31).size());
		assertTrue(expectedStrings.contains(
				storeLoader.getStore().getInvertedNumericIndex().get(31).get(Long.MIN_VALUE).get(0).getStringValue()));
		assertTrue(expectedLongs.contains(storeLoader.getStore().getInvertedNumericIndex().get(31).get(Long.MIN_VALUE)
				.get(0).getDoubleValue().longValue()));

		assertTrue(expectedStrings.contains(
				storeLoader.getStore().getInvertedNumericIndex().get(31).get(Long.MIN_VALUE).get(1).getStringValue()));
		assertTrue(expectedLongs.contains(storeLoader.getStore().getInvertedNumericIndex().get(31).get(Long.MIN_VALUE)
				.get(1).getDoubleValue().longValue()));

		assertEquals(Long.MAX_VALUE,
				storeLoader.getStore().getInvertedNumericIndex().get(31).get(Long.MAX_VALUE).get(1).getStringValue());
		assertEquals(Long.MAX_VALUE, storeLoader.getStore().getInvertedNumericIndex().get(31).get(Long.MAX_VALUE).get(1)
				.getDoubleValue().longValue());

	}

	@Test
	public void prepareForSearchTests_81() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanFieldObject(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedIndex().get(32).get(SdqlConstants.NULL).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedIndex().get(32).get(SdqlConstants.NULL).get(0).getDoubleValue());
	}

	@Test
	public void prepareForSearchTests_82() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanFieldObject(null);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanFieldObject(false);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanFieldObject(true);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanFieldObject(false);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(3, storeLoader.getStore().getInvertedIndex().get(32).size());
		assertEquals(SdqlConstants.NULL,
				storeLoader.getStore().getInvertedIndex().get(32).get(SdqlConstants.NULL).get(0).getStringValue());
		assertEquals(null,
				storeLoader.getStore().getInvertedIndex().get(32).get(SdqlConstants.NULL).get(0).getDoubleValue());

		assertEquals("false", storeLoader.getStore().getInvertedIndex().get(32).get("false").get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedIndex().get(32).get("false").get(0).getDoubleValue());

		assertEquals("true", storeLoader.getStore().getInvertedIndex().get(32).get("true").get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedIndex().get(32).get("true").get(0).getDoubleValue());
	}

	@Test
	public void prepareForSearchTests_83() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatField(Float.NaN);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(33).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());
		assertEquals(SdqlConstants.NULL, storeLoader.getStore().getInvertedNumericIndex().get(33)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_84() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatField(1.44F);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatField(-1141.4554F);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatField(-1141.4554F);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndex().get(33).size());
		assertEquals(-1141, storeLoader.getStore().getInvertedNumericIndex().get(33).get(-1141L).get(0).getDoubleValue()
				.longValue());
		assertEquals("-1141.46",
				storeLoader.getStore().getInvertedNumericIndex().get(33).get(-1141L).get(0).getStringValue());

		assertEquals(1,
				storeLoader.getStore().getInvertedNumericIndex().get(33).get(1L).get(0).getDoubleValue().longValue());
		assertEquals("1.44", storeLoader.getStore().getInvertedNumericIndex().get(33).get(1L).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_85() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleField(Double.NaN);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(34).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());
		assertEquals(SdqlConstants.NULL, storeLoader.getStore().getInvertedNumericIndex().get(34)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_86() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleField(1.44F);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleField(-1141.4554F);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleField(9223372036854775807.4554F);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(3, storeLoader.getStore().getInvertedNumericIndex().get(34).size());
		assertEquals(-1141, storeLoader.getStore().getInvertedNumericIndex().get(34).get(-1141L).get(0).getDoubleValue()
				.longValue());
		assertEquals("-1141.46",
				storeLoader.getStore().getInvertedNumericIndex().get(34).get(-1141L).get(0).getStringValue());

		assertEquals(1,
				storeLoader.getStore().getInvertedNumericIndex().get(34).get(1L).get(0).getDoubleValue().longValue());
		assertEquals("1.44", storeLoader.getStore().getInvertedNumericIndex().get(34).get(1L).get(0).getStringValue());

		assertEquals(9223372036854775807L, storeLoader.getStore().getInvertedNumericIndex().get(34)
				.get(9223372036854775807L).get(0).getDoubleValue().longValue());
		assertEquals("9223372036854776000.00", storeLoader.getStore().getInvertedNumericIndex().get(34)
				.get(9223372036854775807L).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_87() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatObject(Float.NaN);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(35).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());
		assertEquals(SdqlConstants.NULL, storeLoader.getStore().getInvertedNumericIndex().get(35)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_88() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatObject(Float.NaN);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatObject(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(35).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());
		assertEquals(SdqlConstants.NULL, storeLoader.getStore().getInvertedNumericIndex().get(35)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());
	}

	@Test
	public void prepareForSearchTests_89() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatObject(1.44F);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatObject(-1141.4554F);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatObject(-1141.4554F);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndex().get(35).size());
		assertEquals(-1141, storeLoader.getStore().getInvertedNumericIndex().get(35).get(-1141L).get(0).getDoubleValue()
				.longValue());
		assertEquals("-1141.46",
				storeLoader.getStore().getInvertedNumericIndex().get(35).get(-1141L).get(0).getStringValue());

		assertEquals(1,
				storeLoader.getStore().getInvertedNumericIndex().get(35).get(1L).get(0).getDoubleValue().longValue());
		assertEquals("1.44", storeLoader.getStore().getInvertedNumericIndex().get(35).get(1L).get(0).getStringValue());

	}

	@Test
	public void prepareForSearchTests_90() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(Double.NaN);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndex().get(36).size());
		assertEquals(SdqlConstants.NULL, storeLoader.getStore().getInvertedNumericIndex().get(36)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(36).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());
	}

	@Test
	public void prepareForSearchTests_91() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(Double.NaN);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndex().get(36).size());
		assertEquals(SdqlConstants.NULL, storeLoader.getStore().getInvertedNumericIndex().get(36)
				.get(SdqlConstants.LONG_NULL).get(0).getStringValue());
		assertEquals(null, storeLoader.getStore().getInvertedNumericIndex().get(36).get(SdqlConstants.LONG_NULL).get(0)
				.getDoubleValue());
	}

	@Test
	public void prepareForSearchTests_92() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(1.44);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(-1141.4554);
		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(9223372036854775807.4554);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndex().get(36).get(9223372036854775807L).size());
		assertEquals("9223372036854776000.00", storeLoader.getStore().getInvertedNumericIndex().get(36)
				.get(9223372036854775807L).get(0).getStringValue());
		assertEquals(9223372036854775807L, storeLoader.getStore().getInvertedNumericIndex().get(36)
				.get(9223372036854775807L).get(0).getDoubleValue().longValue());

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndex().get(36).get(1L).size());
		assertEquals("1.44", storeLoader.getStore().getInvertedNumericIndex().get(36).get(1L).get(0).getStringValue());
		assertEquals(1L,
				storeLoader.getStore().getInvertedNumericIndex().get(36).get(1L).get(0).getDoubleValue().longValue());

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndex().get(36).get(-1141L).size());
		assertEquals("-1141.46",
				storeLoader.getStore().getInvertedNumericIndex().get(36).get(-1141L).get(0).getStringValue());
		assertEquals(-1141L, storeLoader.getStore().getInvertedNumericIndex().get(36).get(-1141L).get(0)
				.getDoubleValue().longValue());
	}


}
