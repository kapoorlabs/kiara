package com.kapoorlabs.kiara.test.search;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.InsufficientDataException;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.StoreSearch;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;


public class DummyObjectTest {

	@Test(expected=InsufficientDataException.class)
	public void dummyObjectTest_1() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteField((byte)1);
		dummyTestObject.setShortField((short)2);
		dummyTestObject.setIntField(3);
		dummyTestObject.setCharField('k');
		dummyTestObject.setLongField(100L);
		dummyTestObject.setFloatField((float)12.345);
		dummyTestObject.setDoubleField(12102.018);
		
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		
		try {
			storeLoader.loadTable(dummyTestObject);
		} catch (LoadDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("byteField", Operator.EQUAL, (byte) 1));
		
		storeSearch.query(store, conditions);

	}
	
	@Test
	public void dummyObjectTest_2() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteField((byte)1);
		dummyTestObject.setShortField((short)2);
		dummyTestObject.setIntField(3);
		dummyTestObject.setCharField('k');
		dummyTestObject.setLongField(100L);
		dummyTestObject.setFloatField((float)12.345);
		dummyTestObject.setDoubleField(12102.018);
		
		DummyTestObject dummyTestObject2 = new DummyTestObject();
		dummyTestObject2.setByteField((byte)1);
		dummyTestObject2.setCharField(' ');
		
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		
		try {
			storeLoader.loadTable(dummyTestObject);
			storeLoader.loadTable(dummyTestObject2);
			
		} catch (LoadDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		storeLoader.prepareForSearch();
		
		StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("byteField", Operator.EQUAL, (byte) 1));
		
		List<DummyTestObject> result = storeSearch.query(store, conditions);
		
		assertEquals(1, result.get(0).getByteField());
		assertEquals(2, result.get(0).getShortField());
		assertEquals(3, result.get(0).getIntField());
		assertEquals('k', result.get(0).getCharField());
		assertEquals(100, result.get(0).getLongField());
		assertEquals(new Float(12.35), new Float(result.get(0).getFloatField()));
		assertEquals(new Double(12102.02), new Double(result.get(0).getDoubleField()));
		
		assertEquals(1, result.get(1).getByteField());
		assertEquals(' ', result.get(1).getCharField());

	}
	
	@Test
	public void dummyObjectTest_3() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDates("2018-12-10, 2015-05-20");
		dummyTestObject.setDates2("2018-Dec-10, 2015-May-20");
		dummyTestObject.setDate("2018-12-10");
		dummyTestObject.setDate2("18/12/10");
		dummyTestObject.setDateTimes("2018-12-10 15:00:00, 2015-05-20 15:00:00");
		dummyTestObject.setDateTimes2("2018-Dec-10 15:00, 2015-May-20 15:00");
		dummyTestObject.setDateTime("2018-12-10 15:00:00");
		dummyTestObject.setDateTime2("18/12/10 15");
		
		DummyTestObject dummyTestObject2 = new DummyTestObject();
		dummyTestObject2.setDates("2018-12-10");
		
		
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		
		try {
			storeLoader.loadTable(dummyTestObject);
			storeLoader.loadTable(dummyTestObject2);
		} catch (LoadDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		storeLoader.prepareForSearch();
		
		StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("dates", Operator.CONTAINS_EITHER, "2018-12-10"));
		
		List<DummyTestObject> result = storeSearch.query(store, conditions);
		
		assertEquals("2018-12-10, 2015-05-20", result.get(0).getDates());
		assertEquals("2018-Dec-10, 2015-May-20", result.get(0).getDates2());
		assertEquals("2018-12-10", result.get(0).getDate());
		assertEquals("18/12/10", result.get(0).getDate2());
		assertEquals("2018-12-10 15:00:00, 2015-05-20 15:00:00", result.get(0).getDateTimes());
		assertEquals("2018-Dec-10 15:00, 2015-May-20 15:00", result.get(0).getDateTimes2());
		assertEquals("2018-12-10 15:00:00", result.get(0).getDateTime());
		assertEquals("18/12/10 15", result.get(0).getDateTime2());
		
		assertEquals("2018-12-10", result.get(1).getDates());
		assertEquals(null, result.get(1).getDates2());
		assertEquals(null, result.get(1).getDate());
		assertEquals(null, result.get(1).getDate2());
		assertEquals(null, result.get(1).getDateTimes());
		assertEquals(null, result.get(1).getDateTimes2());
		assertEquals(null, result.get(1).getDateTime());
		assertEquals(null, result.get(1).getDateTime2());


	}
	
	@Test
	public void dummyObjectTest_4() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteObjectField((byte)1);
		dummyTestObject.setShortObjectField((short)2);
		dummyTestObject.setIntegerObjectField(3);
		dummyTestObject.setCharacterObjectField('k');
		dummyTestObject.setLongObjectField(100L);
		dummyTestObject.setFloatObject((float)12.345);
		dummyTestObject.setDoubleObject(12102.018);
		
		DummyTestObject dummyTestObject2 = new DummyTestObject();
		dummyTestObject2.setByteObjectField((byte)1);
		dummyTestObject2.setCharacterObjectField(' ');
		
		DummyTestObject dummyTestObject3 = new DummyTestObject();
		dummyTestObject3.setByteObjectField((byte)1);
		dummyTestObject3.setCharacterObjectField(null);
		
		DummyTestObject dummyTestObject4 = new DummyTestObject();
		dummyTestObject4.setByteObjectField((byte)1);
		dummyTestObject4.setStringField("null");
		
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		
		try {
			storeLoader.loadTable(dummyTestObject);
			storeLoader.loadTable(dummyTestObject2);
			storeLoader.loadTable(dummyTestObject3);
			storeLoader.loadTable(dummyTestObject4);
			
		} catch (LoadDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		storeLoader.prepareForSearch();
		
		StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("byteObjectField", Operator.EQUAL, (byte) 1));
		
		List<DummyTestObject> result = storeSearch.query(store, conditions);
		
		assertEquals(2, result.size());
		
		assertEquals(1, result.get(0).getByteObjectField().byteValue());
		assertEquals(2, result.get(0).getShortObjectField().shortValue());
		assertEquals(3, result.get(0).getIntegerObjectField().intValue());
		assertEquals('k', result.get(0).getCharacterObjectField().charValue());
		assertEquals(100, result.get(0).getLongObjectField().longValue());
		assertEquals(new Float(12.35), new Float(result.get(0).getFloatObject()));
		assertEquals(new Double(12102.02), new Double(result.get(0).getDoubleObject()));
		
		assertEquals(1, result.get(1).getByteObjectField().byteValue());
		assertEquals(null, result.get(1).getCharacterObjectField());

	}
	
	@Test
	public void dummyObjectTest_5() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setNumbers("1,2,3,4");
		
		DummyTestObject dummyTestObject2 = new DummyTestObject();
		dummyTestObject2.setNumbers("4,5");

		
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		
		try {
			storeLoader.loadTable(dummyTestObject);
			storeLoader.loadTable(dummyTestObject2);
			
		} catch (LoadDataException e) {
			e.printStackTrace();
		}
		
		storeLoader.prepareForSearch();
		
		StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("numbers", Operator.CONTAINS_EITHER, new Integer[] {4,5}));
		
		List<DummyTestObject> result = storeSearch.query(store, conditions);
		
		assertEquals(2, result.size());
		
		assertEquals("1,2,3,4", result.get(0).getNumbers());
		assertEquals("4,5", result.get(1).getNumbers());

	}

}
