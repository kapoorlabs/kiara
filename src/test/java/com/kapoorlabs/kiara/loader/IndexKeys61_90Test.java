package com.kapoorlabs.kiara.loader;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.NullableOrderedString;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.IndexingException;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class IndexKeys61_90Test {
	
	Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
	


	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_61() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRanges("2018-01");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

	}

	@Test
	public void prepareForSearchTests_62() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRanges("2018-01-05, null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Long lowerRange = LocalDate.parse("2018-01-05", formatter).toEpochDay();

		assertEquals(1, storeLoader.getStore().getRanges().get(24).size());
		assertEquals(lowerRange + "-" + lowerRange,
				storeLoader.getStore().getRanges().get(24).get("").getInRange(lowerRange).get(0).toString());

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
		Long searchRange = LocalDate.parse("2010-01-05", formatter).toEpochDay();
		Long outRange = LocalDate.parse("2018-01-06", formatter).toEpochDay();

		assertEquals(1, storeLoader.getStore().getRanges().get(24).size());
		assertEquals(SdqlConstants.LONG_NULL + "-" + upperRange,
				storeLoader.getStore().getRanges().get(24).get("").getInRange(searchRange).get(0).toString());
		assertEquals(
				LocalDate.parse("2010-01-01", formatter).toEpochDay() + "-"
						+ LocalDate.parse("2012-01-01", formatter).toEpochDay(),
				storeLoader.getStore().getRanges().get(24).get("").getInRange(searchRange).get(1).toString());

		assertEquals(0, storeLoader.getStore().getRanges().get(24).get("").getInRange(outRange).size());

	}

	@Test(expected = IndexingException.class)
	public void prepareForSearchTests_64() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDateRanges2("2018-01-05, null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Long lowerRange = LocalDate.parse("2018-01-05", formatter).toEpochDay();

		assertEquals(1, storeLoader.getStore().getRanges().get(24).size());
		assertEquals(lowerRange + "-" + lowerRange,
				storeLoader.getStore().getRanges().get(24).get("").getInRange(lowerRange).get(0).toString());

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

		assertEquals(1, storeLoader.getStore().getRanges().get(25).size());
		assertEquals(lowerRange + "-" + lowerRange,
				storeLoader.getStore().getRanges().get(25).get("").getInRange(lowerRange).get(0).toString());

	}

	@Test
	public void prepareForSearchTests_66() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().size());
		assertEquals(new NullableOrderedString(null),
				storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().get(0));

	}

	@Test
	public void prepareForSearchTests_67() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().size());
		assertEquals(new NullableOrderedString(null),
				storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().get(0));

	}

	@Test
	public void prepareForSearchTests_68() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("Null");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().size());
		assertEquals(new NullableOrderedString(null),
				storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().get(0));

	}

	@Test
	public void prepareForSearchTests_69() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("Nulls");
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().size());
		assertEquals(new NullableOrderedString("Nulls"),
				storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().get(0));

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

		assertEquals(4, storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().size());
		assertEquals(new NullableOrderedString(null),
				storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().get(0));

		assertEquals(new NullableOrderedString("Apple"),
				storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().get(1));

		assertEquals(new NullableOrderedString("Ball"),
				storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().get(2));

		assertEquals(new NullableOrderedString("apple"),
				storeLoader.getStore().getInvertedIndexKeys().get(26).getKeyList().get(3));

	}

	@Test
	public void prepareForSearchTests_71() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntegerObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(27).getKeyList().size());
		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(27).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(27).getKeyList().get(0).longValue());

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

		assertEquals(3, storeLoader.getStore().getInvertedNumericIndexKeys().get(27).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(27).getKeyList().get(0).longValue());
		assertEquals(Integer.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(27).getKeyList().get(1).longValue());
		assertEquals(Integer.MAX_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(27).getKeyList().get(2).longValue());

	}

	@Test
	public void prepareForSearchTests_73() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setShortObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(28).getKeyList().size());
		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(28).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(28).getKeyList().get(0).longValue());

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

		assertEquals(3, storeLoader.getStore().getInvertedNumericIndexKeys().get(28).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(28).getKeyList().get(0).longValue());
		assertEquals(Short.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(28).getKeyList().get(1).longValue());
		assertEquals(Short.MAX_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(28).getKeyList().get(2).longValue());

	}

	@Test
	public void prepareForSearchTests_75() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(29).getKeyList().size());
		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(29).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(29).getKeyList().get(0).longValue());

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

		assertEquals(3, storeLoader.getStore().getInvertedNumericIndexKeys().get(29).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(29).getKeyList().get(0).longValue());
		assertEquals(Byte.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(29).getKeyList().get(1).longValue());
		assertEquals(Byte.MAX_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(29).getKeyList().get(2).longValue());

	}

	@Test
	public void prepareForSearchTests_77() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharacterObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndexKeys().get(30).getKeyList().size());
		assertEquals(new NullableOrderedString(null),
				storeLoader.getStore().getInvertedIndexKeys().get(30).getKeyList().get(0));

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

		assertEquals(4, storeLoader.getStore().getInvertedIndexKeys().get(30).getKeyList().size());
		assertEquals(new NullableOrderedString(null),
				storeLoader.getStore().getInvertedIndexKeys().get(30).getKeyList().get(0));

		assertEquals(new NullableOrderedString("1"),
				storeLoader.getStore().getInvertedIndexKeys().get(30).getKeyList().get(1));

		assertEquals(new NullableOrderedString("`"),
				storeLoader.getStore().getInvertedIndexKeys().get(30).getKeyList().get(2));

		assertEquals(new NullableOrderedString("a"),
				storeLoader.getStore().getInvertedIndexKeys().get(30).getKeyList().get(3));

	}

	@Test
	public void prepareForSearchTests_79() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setLongObjectField(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(0, storeLoader.getStore().getInvertedIndexKeys().get(31).getKeyList().size());
		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(31).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(31).getKeyList().get(0).longValue());

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

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(31).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(31).getKeyList().get(0).longValue());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(31).getKeyList().get(1).longValue());

	}

	@Test
	public void prepareForSearchTests_81() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanFieldObject(null);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedIndexKeys().get(32).getKeyList().size());
		assertEquals(new NullableOrderedString(SdqlConstants.NULL),
				storeLoader.getStore().getInvertedIndexKeys().get(32).getKeyList().get(0));
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

		assertEquals(3, storeLoader.getStore().getInvertedIndexKeys().get(32).getKeyList().size());
		assertEquals(new NullableOrderedString(SdqlConstants.NULL),
				storeLoader.getStore().getInvertedIndexKeys().get(32).getKeyList().get(0));

		assertEquals(new NullableOrderedString("false"),
				storeLoader.getStore().getInvertedIndexKeys().get(32).getKeyList().get(1));

		assertEquals(new NullableOrderedString("true"),
				storeLoader.getStore().getInvertedIndexKeys().get(32).getKeyList().get(2));
	}

	@Test
	public void prepareForSearchTests_83() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatField(Float.NaN);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(33).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(33).getKeyList().get(0).longValue());
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

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(33).getKeyList().size());
		assertEquals(-1141,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(33).getKeyList().get(0).longValue());
		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(33).getKeyList().get(1).longValue());
	}

	@Test
	public void prepareForSearchTests_85() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleField(Double.NaN);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(34).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(34).getKeyList().get(0).longValue());
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

		assertEquals(3, storeLoader.getStore().getInvertedNumericIndexKeys().get(34).getKeyList().size());
		assertEquals(-1141,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(34).getKeyList().get(0).longValue());
		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(34).getKeyList().get(1).longValue());

		assertEquals(9223372036854775807L,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(34).getKeyList().get(2).longValue());
	}

	@Test
	public void prepareForSearchTests_87() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatObject(Float.NaN);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(35).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(35).getKeyList().get(0).longValue());
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

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(35).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(35).getKeyList().get(0).longValue());
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

		assertEquals(2, storeLoader.getStore().getInvertedNumericIndexKeys().get(35).getKeyList().size());
		assertEquals(-1141L,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(35).getKeyList().get(0).longValue());
		assertEquals(1L,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(35).getKeyList().get(1).longValue());
	}

	@Test
	public void prepareForSearchTests_90() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(Double.NaN);
		storeLoader.loadTable(dummyTestObject);

		storeLoader.prepareForSearch();

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(36).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(36).getKeyList().get(0).longValue());
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

		assertEquals(1, storeLoader.getStore().getInvertedNumericIndexKeys().get(36).getKeyList().size());
		assertEquals(Long.MIN_VALUE,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(36).getKeyList().get(0).longValue());
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

		assertEquals(3, storeLoader.getStore().getInvertedNumericIndexKeys().get(36).getKeyList().size());
		assertEquals(-1141,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(36).getKeyList().get(0).longValue());
		assertEquals(1,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(36).getKeyList().get(1).longValue());
		assertEquals(9223372036854775807L,
				storeLoader.getStore().getInvertedNumericIndexKeys().get(36).getKeyList().get(2).longValue());
	}


}
