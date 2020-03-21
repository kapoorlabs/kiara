package com.kapoorlabs.kiara.test.parser;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.Test;

import com.kapoorlabs.kiara.adapters.PojoAdapter;
import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Range;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.RangeOutOfOrderException;
import com.kapoorlabs.kiara.exception.UnknownRangeFormatException;
import com.kapoorlabs.kiara.parser.RangeParser;

public class RangeParserTest {

	Store flightOnTimeStore;

	public RangeParserTest() {
		flightOnTimeStore = new Store(PojoAdapter.getSdqlColumns(FlightOnTimeTestObject.class));
	}

	@Test
	public void rangeParserTest_1() {

		Range range = RangeParser.parseRange("aa106", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

		assertEquals("aa", range.getPrefix());
		assertEquals(106, range.getLowerLimit().longValue());
		assertEquals(106, range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_2() {

		Range range = RangeParser.parseRange("aa", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

		assertEquals("aa", range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(SdqlConstants.LONG_NULL, range.getUpperLimit());
	}

	@Test
	public void rangeParserTest_3() {

		Range range = RangeParser.parseRange("aa-44-77", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

		assertEquals("aa-", range.getPrefix());
		assertEquals(-44, range.getLowerLimit().longValue());
		assertEquals(77, range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_4() {

		Range range = RangeParser.parseRange("123456789101112-123456789101113", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

		assertEquals("", range.getPrefix());
		assertEquals(123456789101112L, range.getLowerLimit().longValue());
		assertEquals(123456789101113L, range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_5() {

		Range range = RangeParser.parseRange("a-222", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

		assertEquals("a", range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(222, range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_6() {

		Range range = RangeParser.parseRange("a-", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

		assertEquals("a", range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(SdqlConstants.LONG_NULL, range.getUpperLimit());
	}
	
	public void rangeParserTest_7() {

		Range range = RangeParser.parseRange("a-10-", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

		assertEquals("a", range.getPrefix());
		assertEquals(-10, range.getLowerLimit().longValue());
		assertEquals(-10, range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_8() {

		Range range = RangeParser.parseRange("", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

		assertEquals(SdqlConstants.NULL, range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(SdqlConstants.LONG_NULL, range.getUpperLimit());
	}

	@Test
	public void rangeParserTest_9() {

		Range range = RangeParser.parseRange(null, flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));
		assertEquals(SdqlConstants.NULL, range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(SdqlConstants.LONG_NULL, range.getUpperLimit());
	}

	@Test
	public void rangeParserTest_10() {

		Range range = RangeParser.parseRange("2", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));
		assertEquals("", range.getPrefix());
		assertEquals(2, range.getLowerLimit().longValue());
		assertEquals(2, range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_11() {

		Range range = RangeParser.parseRange("2,10", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));
		assertEquals("2,", range.getPrefix());
		assertEquals(10, range.getLowerLimit().longValue());
		assertEquals(10, range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_12() {

		Range range = RangeParser.parseRange("2,10", null, flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));
		assertEquals(null, range);
	}

	@Test
	public void rangeParserTest_13() {

		Range range = RangeParser.parseRange("2,10", flightOnTimeStore, -1);
		assertEquals(null, range);
	}

	@Test
	public void rangeParserTest_14() {

		Range range = RangeParser.parseRange("2,10", flightOnTimeStore, flightOnTimeStore.getSdqlColumns().length);
		assertEquals(null, range);
	}

	@Test
	public void rangeParserTest_15() {

		Range range = RangeParser.parseRange("null-12", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));
		assertEquals("null", range.getPrefix());
		assertEquals(Long.MIN_VALUE, range.getLowerLimit().longValue());
		assertEquals(12, range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_16() {

		Range range = RangeParser.parseRange("2018/02/01-2019/02/01", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));
		assertEquals("", range.getPrefix());
		
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(flightOnTimeStore.getSdqlColumns()[flightOnTimeStore.getColumnIndex().get("DATES")]
						.getSecondaryType().getFormat());
		
		assertEquals(LocalDate.parse("2018/02/01", formatter).toEpochDay(), range.getLowerLimit().longValue());
		assertEquals(LocalDate.parse("2019/02/01", formatter).toEpochDay(), range.getUpperLimit().longValue());
	}

	@Test
	public void rangeParserTest_17() {

		Range range = RangeParser.parseRange("2018/02/01", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));
		assertEquals("", range.getPrefix());
		
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(flightOnTimeStore.getSdqlColumns()[flightOnTimeStore.getColumnIndex().get("DATES")]
						.getSecondaryType().getFormat());
		
		assertEquals(LocalDate.parse("2018/02/01", formatter).toEpochDay(), range.getLowerLimit().longValue());
		assertEquals(LocalDate.parse("2018/02/01", formatter).toEpochDay(), range.getUpperLimit().longValue());
	}

	@Test(expected = UnknownRangeFormatException.class)
	public void rangeParserTest_18() {

		@SuppressWarnings("unused")
		Range range = RangeParser.parseRange("2018/02/011", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));
	}

	@Test(expected = DateTimeParseException.class)
	public void rangeParserTest_19() {

		@SuppressWarnings("unused")
		Range range = RangeParser.parseRange("2018/02/32", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));
	}

	@Test
	public void rangeParserTest_20() {

		Range range = RangeParser.parseRange("null", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));
		assertEquals(SdqlConstants.NULL, range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(SdqlConstants.LONG_NULL, range.getUpperLimit());
	}
	
	@Test
	public void rangeParserTest_20_1() {

		Range range = RangeParser.parseRange("Null", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));
		assertEquals(SdqlConstants.NULL, range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(SdqlConstants.LONG_NULL, range.getUpperLimit());
	}
	
	@Test
	public void rangeParserTest_20_2() {

		Range range = RangeParser.parseRange("NULL", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));
		assertEquals(SdqlConstants.NULL, range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(SdqlConstants.LONG_NULL, range.getUpperLimit());
	}

	@Test(expected = RangeOutOfOrderException.class)
	public void rangeParserTest_21() {

		@SuppressWarnings("unused")
		Range range = RangeParser.parseRange("12-11", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("FLIGHTNUMBERS"));

	}

	@Test(expected = RangeOutOfOrderException.class)
	public void rangeParserTest_22() {

		@SuppressWarnings("unused")
		Range range = RangeParser.parseRange("2018/05/18-2018/05/17", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));

	}

	@Test
	public void rangeParserTest_23() {

		Range range = RangeParser.parseRange("null-2018/05/17", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));

		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(flightOnTimeStore.getSdqlColumns()[flightOnTimeStore.getColumnIndex().get("DATES")]
						.getSecondaryType().getFormat());
		assertEquals("", range.getPrefix());
		assertEquals(SdqlConstants.LONG_NULL, range.getLowerLimit());
		assertEquals(LocalDate.parse("2018/05/17", formatter).toEpochDay(), range.getUpperLimit().longValue());

	}
	
	@Test(expected = UnknownRangeFormatException.class)
	public void rangeParserTest_24() {

		@SuppressWarnings("unused")
		Range range = RangeParser.parseRange("2018/05/17-", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATES"));


	}
	
	@Test(expected = UnknownRangeFormatException.class)
	public void rangeParserTest_25() {

		@SuppressWarnings("unused")
		Range range = RangeParser.parseRange("2018/02/01-2019/02/01", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATETIMES"));

	}
	
	@Test
	public void rangeParserTest_26() {

		Range range = RangeParser.parseRange("2018/02/01 05:17:18-2018/02/01 05:17:19", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATETIMES"));
		assertEquals("", range.getPrefix());
		
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(flightOnTimeStore.getSdqlColumns()[flightOnTimeStore.getColumnIndex().get("DATETIMES")]
						.getSecondaryType().getFormat());
		
		assertEquals(LocalDateTime.parse("2018/02/01 05:17:18", formatter).toEpochSecond(ZoneOffset.of("Z")), range.getLowerLimit().longValue());
		assertEquals(LocalDateTime.parse("2018/02/01 05:17:19", formatter).toEpochSecond(ZoneOffset.of("Z")), range.getUpperLimit().longValue());
	}
	
	@Test(expected = RangeOutOfOrderException.class)
	public void rangeParserTest_27() {

		@SuppressWarnings("unused")
		Range range = RangeParser.parseRange("2018/02/01 05:17:18-2018/02/01 05:17:17", flightOnTimeStore,
				flightOnTimeStore.getColumnIndex().get("DATETIMES"));

	}

}
