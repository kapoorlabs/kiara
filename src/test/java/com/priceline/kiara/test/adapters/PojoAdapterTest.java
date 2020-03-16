package com.priceline.kiara.test.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.priceline.kiara.adapters.PojoAdapter;
import com.priceline.kiara.constants.SdqlConstants;
import com.priceline.kiara.domain.SdqlColumn;
import com.priceline.kiara.domain.SecondaryCollectionDataType;
import com.priceline.kiara.domain.SecondarySingleDataType;
import com.priceline.kiara.test.objects.AirportTestObject;
import com.priceline.kiara.test.objects.BooksTestObject;
import com.priceline.kiara.test.objects.DummyTestObject;
import com.priceline.kiara.test.objects.PartialGetterObject;
import com.priceline.kiara.test.objects.PoiTestObject;
import com.priceline.kiara.test.objects.TempRangeTestObject;

public class PojoAdapterTest {
	
	List<SdqlColumn> dummyTestColumns;
	
	public PojoAdapterTest() {
		dummyTestColumns = PojoAdapter.getSdqlColumns(DummyTestObject.class);
	}

	@Test
	public void nullNumericTest() {
		assertFalse(PojoAdapter.isNumeric(null));
		assertFalse(PojoAdapter.isNumeric(""));
	}

	@Test
	public void alphaNumericTest() {
		assertFalse(PojoAdapter.isNumeric("String"));
		assertFalse(PojoAdapter.isNumeric("reference"));
		assertFalse(PojoAdapter.isNumeric("any-string"));
		assertFalse(PojoAdapter.isNumeric("char"));
		assertFalse(PojoAdapter.isNumeric("char[]"));
	}

	@Test
	public void numericTest() {
		assertTrue(PojoAdapter.isNumeric("int"));
		assertTrue(PojoAdapter.isNumeric("INT"));
		assertTrue(PojoAdapter.isNumeric("INTeger"));
		assertTrue(PojoAdapter.isNumeric("float"));
		assertTrue(PojoAdapter.isNumeric("double"));
		assertTrue(PojoAdapter.isNumeric("long"));
		assertTrue(PojoAdapter.isNumeric("LOng"));
	}
	
	@Test
	public void airportObjectTest() {
		List<SdqlColumn> sdqlColumns = PojoAdapter.getSdqlColumns(AirportTestObject.class);
		assertEquals(7,sdqlColumns.size());
		assertEquals(false,sdqlColumns.get(0).isNumeric());
		assertEquals(false,sdqlColumns.get(1).isNumeric());
		assertEquals(false,sdqlColumns.get(2).isNumeric());
		assertEquals(false,sdqlColumns.get(3).isNumeric());
		assertEquals(false,sdqlColumns.get(4).isNumeric());
		assertEquals(true,sdqlColumns.get(5).isNumeric());
		assertEquals(false,sdqlColumns.get(6).isNumeric());
	}
	
	@Test
	public void poiObjectTest() {
		List<SdqlColumn> sdqlColumns = PojoAdapter.getSdqlColumns(PoiTestObject.class);
		assertEquals(4,sdqlColumns.size());
		assertEquals(false,sdqlColumns.get(0).isNumeric());
		assertEquals(false,sdqlColumns.get(1).isNumeric());
		assertEquals(false,sdqlColumns.get(2).isNumeric());
		assertEquals(false,sdqlColumns.get(3).isNumeric());
		
		assertEquals(SecondaryCollectionDataType.STRING,sdqlColumns.get(3).getSecondaryType().getSecondaryCollectionType());
		assertEquals(null,sdqlColumns.get(3).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(3).getSecondaryType().getSecondarySingleType());
	}
	
	@Test
	public void tempRangeObjectTest() {
		List<SdqlColumn> sdqlColumns = PojoAdapter.getSdqlColumns(TempRangeTestObject.class);
		assertEquals(2,sdqlColumns.size());
		assertEquals(false,sdqlColumns.get(0).isNumeric());
		assertEquals(false,sdqlColumns.get(1).isNumeric());
		
		assertEquals(SecondaryCollectionDataType.STRING,sdqlColumns.get(0).getSecondaryType().getSecondaryCollectionType());
		assertEquals(null,sdqlColumns.get(0).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(0).getSecondaryType().getSecondarySingleType());
		
		assertEquals(null,sdqlColumns.get(1).getSecondaryType().getSecondaryCollectionType());
		assertEquals(null,sdqlColumns.get(1).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.NUMERIC_RANGE,sdqlColumns.get(1).getSecondaryType().getSecondarySingleType());
		
	}
	
	@Test
	public void partialGetterObjectTest() {
		List<SdqlColumn> sdqlColumns = PojoAdapter.getSdqlColumns(PartialGetterObject.class);
		assertEquals(1,sdqlColumns.size());
	}
	
	@Test
	public void dummyObjectTest() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		assertEquals(37,sdqlColumns.size());
		
		assertEquals(true,sdqlColumns.get(0).isNumeric());
		assertEquals(true,sdqlColumns.get(1).isNumeric());
		assertEquals(true,sdqlColumns.get(2).isNumeric());
		assertEquals(false,sdqlColumns.get(3).isNumeric());
		assertEquals(true,sdqlColumns.get(4).isNumeric());
		assertEquals(false,sdqlColumns.get(5).isNumeric());
		assertEquals(false,sdqlColumns.get(6).isNumeric());
		assertEquals(false,sdqlColumns.get(7).isNumeric());
		assertEquals(false,sdqlColumns.get(8).isNumeric());
		assertEquals(false,sdqlColumns.get(9).isNumeric());
		assertEquals(false,sdqlColumns.get(10).isNumeric());
		assertEquals(false,sdqlColumns.get(11).isNumeric());
		assertEquals(false,sdqlColumns.get(12).isNumeric());
		assertEquals(false,sdqlColumns.get(13).isNumeric());
		assertEquals(false,sdqlColumns.get(14).isNumeric());
		assertEquals(false,sdqlColumns.get(15).isNumeric());
		assertEquals(false,sdqlColumns.get(16).isNumeric());
		assertEquals(false,sdqlColumns.get(17).isNumeric());
		assertEquals(false,sdqlColumns.get(18).isNumeric());
		assertEquals(false,sdqlColumns.get(19).isNumeric());
		assertEquals(false,sdqlColumns.get(20).isNumeric());
		assertEquals(false,sdqlColumns.get(21).isNumeric());
		assertEquals(false,sdqlColumns.get(22).isNumeric());
		assertEquals(false,sdqlColumns.get(23).isNumeric());
		assertEquals(false,sdqlColumns.get(24).isNumeric());
		assertEquals(false,sdqlColumns.get(25).isNumeric());
		assertEquals(false,sdqlColumns.get(26).isNumeric());
		assertEquals(true,sdqlColumns.get(27).isNumeric());
		assertEquals(true,sdqlColumns.get(28).isNumeric());
		assertEquals(true,sdqlColumns.get(29).isNumeric());
		assertEquals(false,sdqlColumns.get(30).isNumeric());
		assertEquals(true,sdqlColumns.get(31).isNumeric());
		assertEquals(false,sdqlColumns.get(32).isNumeric());
		assertEquals(true,sdqlColumns.get(33).isNumeric());
		assertEquals(true,sdqlColumns.get(34).isNumeric());
		assertEquals(true,sdqlColumns.get(35).isNumeric());
		assertEquals(true,sdqlColumns.get(36).isNumeric());
	
	}
	
	@Test
	public void dummyObjectTest_0() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(0).getSecondaryType());
		assertEquals(null,sdqlColumns.get(0).getSecondaryType());
		assertEquals(null,sdqlColumns.get(0).getSecondaryType());
	
	}
	
	@Test
	public void dummyObjectTest_1() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(1).getSecondaryType());
		assertEquals(null,sdqlColumns.get(1).getSecondaryType());
		assertEquals(null,sdqlColumns.get(1).getSecondaryType());
	
	}
	
	@Test
	public void dummyObjectTest_2() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(2).getSecondaryType());
		assertEquals(null,sdqlColumns.get(2).getSecondaryType());
		assertEquals(null,sdqlColumns.get(2).getSecondaryType());
	
	}
	
	@Test
	public void dummyObjectTest_3() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(3).getSecondaryType());
		assertEquals(null,sdqlColumns.get(3).getSecondaryType());
		assertEquals(null,sdqlColumns.get(3).getSecondaryType());
	
	}
	
	@Test
	public void dummyObjectTest_4() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(4).getSecondaryType());
		assertEquals(null,sdqlColumns.get(4).getSecondaryType());
		assertEquals(null,sdqlColumns.get(4).getSecondaryType());
	
	}
	
	@Test
	public void dummyObjectTest_5() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(5).getSecondaryType());
		assertEquals(null,sdqlColumns.get(5).getSecondaryType());
		assertEquals(null,sdqlColumns.get(5).getSecondaryType());
	
	}
	
	@Test
	public void dummyObjectTest_6() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.DATE,sdqlColumns.get(6).getSecondaryType().getSecondaryCollectionType());
		assertEquals(SdqlConstants.DEFAULT_DATE_FORMAT,sdqlColumns.get(6).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(6).getSecondaryType().getSecondarySingleType());
	
	}
	
	
	@Test
	public void dummyObjectTest_7() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.DATE,sdqlColumns.get(7).getSecondaryType().getSecondaryCollectionType());
		assertEquals("MMM-dd",sdqlColumns.get(7).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(7).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_8() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(8).getSecondaryType().getSecondaryCollectionType());
		assertEquals(SdqlConstants.DEFAULT_DATE_FORMAT,sdqlColumns.get(8).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.DATE,sdqlColumns.get(8).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_9() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(9).getSecondaryType().getSecondaryCollectionType());
		assertEquals("yy/MM/dd",sdqlColumns.get(9).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.DATE,sdqlColumns.get(9).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_10() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.DATE_TIME,sdqlColumns.get(10).getSecondaryType().getSecondaryCollectionType());
		assertEquals(SdqlConstants.DEFAULT_DATE_TIME_FORMAT,sdqlColumns.get(10).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(10).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_11() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.DATE_TIME,sdqlColumns.get(11).getSecondaryType().getSecondaryCollectionType());
		assertEquals("MMM-dd HH:mm",sdqlColumns.get(11).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(11).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_12() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(12).getSecondaryType().getSecondaryCollectionType());
		assertEquals(SdqlConstants.DEFAULT_DATE_TIME_FORMAT,sdqlColumns.get(12).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.DATE_TIME,sdqlColumns.get(12).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_13() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(13).getSecondaryType().getSecondaryCollectionType());
		assertEquals("yy/MM/dd HH",sdqlColumns.get(13).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.DATE_TIME,sdqlColumns.get(13).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_14() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.NUMBER,sdqlColumns.get(14).getSecondaryType().getSecondaryCollectionType());
		assertEquals(null,sdqlColumns.get(14).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(14).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_15() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.STRING,sdqlColumns.get(15).getSecondaryType().getSecondaryCollectionType());
		assertEquals(null,sdqlColumns.get(15).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(15).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_16() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(16).getSecondaryType().getSecondaryCollectionType());
		assertEquals(null,sdqlColumns.get(16).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.NUMERIC_RANGE,sdqlColumns.get(16).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_17() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.NUMERIC_RANGE,sdqlColumns.get(17).getSecondaryType().getSecondaryCollectionType());
		assertEquals(null,sdqlColumns.get(17).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(17).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_18() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(18).getSecondaryType().getSecondaryCollectionType());
		assertEquals(SdqlConstants.DEFAULT_DATE_TIME_FORMAT,sdqlColumns.get(18).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.DATE_TIME_RANGE,sdqlColumns.get(18).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_19() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(19).getSecondaryType().getSecondaryCollectionType());
		assertEquals("yy/MMM HH:mm",sdqlColumns.get(19).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.DATE_TIME_RANGE,sdqlColumns.get(19).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_20() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(20).getSecondaryType().getSecondaryCollectionType());
		assertEquals(SdqlConstants.DEFAULT_DATE_FORMAT,sdqlColumns.get(20).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.DATE_RANGE,sdqlColumns.get(20).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_21() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(null,sdqlColumns.get(21).getSecondaryType().getSecondaryCollectionType());
		assertEquals("yy/MMM",sdqlColumns.get(21).getSecondaryType().getFormat());
		assertEquals(SecondarySingleDataType.DATE_RANGE,sdqlColumns.get(21).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_22() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.DATE_TIME_RANGE,sdqlColumns.get(22).getSecondaryType().getSecondaryCollectionType());
		assertEquals(SdqlConstants.DEFAULT_DATE_TIME_FORMAT,sdqlColumns.get(22).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(22).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_23() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.DATE_TIME_RANGE,sdqlColumns.get(23).getSecondaryType().getSecondaryCollectionType());
		assertEquals("yy/MMM HH:mm",sdqlColumns.get(23).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(23).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_24() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.DATE_RANGE,sdqlColumns.get(24).getSecondaryType().getSecondaryCollectionType());
		assertEquals(SdqlConstants.DEFAULT_DATE_FORMAT,sdqlColumns.get(24).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(24).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void dummyObjectTest_25() {
		List<SdqlColumn> sdqlColumns = dummyTestColumns;
		
		assertEquals(SecondaryCollectionDataType.DATE_RANGE,sdqlColumns.get(25).getSecondaryType().getSecondaryCollectionType());
		assertEquals("yy/MMM",sdqlColumns.get(25).getSecondaryType().getFormat());
		assertEquals(null,sdqlColumns.get(25).getSecondaryType().getSecondarySingleType());
	
	}
	
	@Test
	public void pojoAdapterTest_1() {
		List<SdqlColumn> sdqlColumns = PojoAdapter.getSdqlColumns(null);
		assertEquals(null,sdqlColumns);
	}
	
	
	
}
