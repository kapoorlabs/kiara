package com.kapoorlabs.kiara.test.loader;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.kapoorlabs.kiara.adapters.PojoAdapter;
import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.SdqlNode;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.EmptyColumnException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class CreateNodeTest {

	@Test(expected = EmptyColumnException.class)
	public void createNodeTest_1() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StoreLoader storeLoader = new StoreLoader(null);
		DummyTestObject dummyTestObject = new DummyTestObject();
		storeLoader.createNode(dummyTestObject, 0);
	}

	@Test(expected = EmptyColumnException.class)
	public void createNodeTest_2() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		storeLoader.createNode(null, 0);
	}

	@Test()
	public void createNodeTest_3() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 0);
		assertEquals(new Double(0), sdQlNode.getDoubleValue());
		assertEquals("0.00", sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_4() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntField(3);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 0);
		assertEquals(new Double(3), sdQlNode.getDoubleValue());
		assertEquals("3.00",sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_5() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntField(Integer.MIN_VALUE);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 0);
		assertEquals(new Double(-2147483648), sdQlNode.getDoubleValue());
		assertEquals("-2147483648.00", sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_6() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntField(Integer.MAX_VALUE);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 0);
		assertEquals(new Double(2147483647), sdQlNode.getDoubleValue());
		assertEquals("2147483647.00", sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_7() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 1);
		assertEquals(new Double(0), sdQlNode.getDoubleValue());
		assertEquals("0.00", sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_8() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		short shortField = 4;
		dummyTestObject.setShortField(shortField);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 1);
		assertEquals(new Double(4), sdQlNode.getDoubleValue());
		assertEquals("4.00", sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_9() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		short shortField = Short.MIN_VALUE;
		dummyTestObject.setShortField(shortField);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 1);
		assertEquals(new Double(-32768), sdQlNode.getDoubleValue());
		assertEquals("-32768.00", sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_10() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 2);
		assertEquals(new Double(0), sdQlNode.getDoubleValue());
		assertEquals("0.00", sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_11() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		byte byteField = Byte.MAX_VALUE;
		dummyTestObject.setByteField(byteField);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 2);
		assertEquals(new Double(127), sdQlNode.getDoubleValue());
		assertEquals("127.00",sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_12() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 3);
		assertEquals(null, sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());	
	}
	
	@Test()
	public void createNodeTest_13() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		char charValue = 'A';
		dummyTestObject.setCharField(charValue);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 3);
		assertEquals(null, sdQlNode.getDoubleValue());
		assertEquals("A",sdQlNode.getStringValue());		
	}
	
	@Test()
	public void createNodeTest_14() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 4);
		assertEquals(new Double(0), sdQlNode.getDoubleValue());
		assertEquals("0.00",sdQlNode.getStringValue());		
	}
	
	@Test()
	public void createNodeTest_15() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		long longValue = 1;
		dummyTestObject.setLongField(longValue);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 4);
		assertEquals(new Double(1), sdQlNode.getDoubleValue());
		assertEquals("1.00",sdQlNode.getStringValue());		
	}
	
	@Test()
	public void createNodeTest_16() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		long longValue = Long.MIN_VALUE;
		dummyTestObject.setLongField(longValue);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 4);
	
		assertEquals(new Double(-9223372036854775808.2), sdQlNode.getDoubleValue());
		//precision till 15 digits
		assertEquals("-9223372036854776000.00",sdQlNode.getStringValue());		
	}
	
	
	@Test()
	public void createNodeTest_17() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		long longValue = Long.MAX_VALUE;
		dummyTestObject.setLongField(longValue);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 4);
		assertEquals(new Double(9223372036854775807L), sdQlNode.getDoubleValue());
		assertEquals("9223372036854776000.00",sdQlNode.getStringValue());		
	}
	
	@Test()
	public void createNodeTest_18() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 5);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals("false",sdQlNode.getStringValue());		
	}
	
	@Test()
	public void createNodeTest_19() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanField(true);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 5);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals("true",sdQlNode.getStringValue());		
	}
	
	@Test()
	public void createNodeTest_20() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 6);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_21() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDates("2018/05/18,2015/05/20");
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 6);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals("2018/05/18,2015/05/20",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_21_1() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDate("2018/05/18");
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 8);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals("2018/05/18",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_22() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDates("");
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 6);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_23() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 27);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_24() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setIntegerObjectField(Integer.MIN_VALUE);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 27);
		assertEquals(new Double(-2147483648.0),sdQlNode.getDoubleValue());
		assertEquals("-2147483648.00",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_25() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 28);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_28() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setShortObjectField((short)45);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 28);
		assertEquals(new Double(45.0),sdQlNode.getDoubleValue());
		assertEquals("45.00",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_29() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 29);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_30() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setByteObjectField((byte)45);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 29);
		assertEquals(new Double(45.0),sdQlNode.getDoubleValue());
		assertEquals("45.00",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_31() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 30);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_32() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setCharacterObjectField('T');
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 30);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals("T",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_33() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 31);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_34() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setLongObjectField(Long.MAX_VALUE);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 31);
		assertEquals(new Double(9223372036854775807L),sdQlNode.getDoubleValue());
		assertEquals("9223372036854776000.00",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_35() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 32);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_36() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setBooleanFieldObject(true);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 32);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals("true",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_37() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 33);
		assertEquals(new Double(0.0),sdQlNode.getDoubleValue());
		assertEquals("0.00",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_38() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatField(2.1009F);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 33);
		assertEquals(new Double(2.1009),sdQlNode.getDoubleValue());
		assertEquals("2.10",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_39() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 34);
		assertEquals(new Double(0),sdQlNode.getDoubleValue());
		assertEquals("0.00",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_40() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleField(255565.33556);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 34);
		assertEquals(new Double(255565.33556),sdQlNode.getDoubleValue());
		assertEquals("255565.34",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_41() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleField(-255565.33556);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 34);
		assertEquals(new Double(-255565.33556),sdQlNode.getDoubleValue());
		assertEquals("-255565.34",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_42() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 35);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_43() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setFloatObject(2565.335F);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 35);
		assertEquals(new Double(2565.335),sdQlNode.getDoubleValue());
		assertEquals("2565.34",sdQlNode.getStringValue());		
	
	}

	
	@Test()
	public void createNodeTest_44() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 36);
		assertEquals(null,sdQlNode.getDoubleValue());
		assertEquals(SdqlConstants.NULL,sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_45() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(-253365.32235);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 36);
		assertEquals(new Double(-253365.32235),sdQlNode.getDoubleValue());
		assertEquals("-253365.32",sdQlNode.getStringValue());		
	
	}
	
	@Test()
	public void createNodeTest_46() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setDoubleObject(9223372036854775807.3);
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 36);
		assertEquals(9223372036854775807L,sdQlNode.getDoubleValue().longValue());
		assertEquals("9223372036854776000.00",sdQlNode.getStringValue());		
	
	}
	
	@Test(expected = EmptyColumnException.class)
	public void createNodeTest_47() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);
		DummyTestObject dummyTestObject = new DummyTestObject();
		@SuppressWarnings("unused")
		SdqlNode sdQlNode = storeLoader.createNode(dummyTestObject, 37);
	
	}

	
	
	

}
