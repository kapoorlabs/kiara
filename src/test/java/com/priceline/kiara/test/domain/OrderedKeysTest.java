package com.priceline.kiara.test.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.priceline.kiara.domain.NullableOrderedString;
import com.priceline.kiara.domain.OrderedKeys;



public class OrderedKeysTest {
	
	@Test
	public void OrderedKeysTest_1() {

		OrderedKeys<Integer> orderedKeys = new OrderedKeys<>();

		assertEquals(0, orderedKeys.getAllLessThan(null).size());	
		assertEquals(0, orderedKeys.getAllLessThanEqual(null).size());
		assertEquals(0, orderedKeys.getAllGreaterThan(null).size());
		assertEquals(0, orderedKeys.getAllGreaterThanEqual(null).size());
		assertEquals(0, orderedKeys.getAllBetween(null, null).size());

	}
	
	@Test
	public void OrderedKeysTest_2() {

		OrderedKeys<Integer> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(0);
		orderedKeys.insertKey(1);
		orderedKeys.insertKey(-10);
		
		orderedKeys.prepareForSearch();
		
		assertEquals(1, orderedKeys.getAllLessThan(0).size());	
		assertEquals(2, orderedKeys.getAllLessThanEqual(0).size());
		assertEquals(2, orderedKeys.getAllGreaterThan(-10).size());
		assertEquals(3, orderedKeys.getAllGreaterThanEqual(Integer.MIN_VALUE).size());
		assertEquals(2, orderedKeys.getAllBetween(0, 1).size());

	}
	
	@Test
	public void OrderedKeysTest_3() {
		

		OrderedKeys<NullableOrderedString> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(new NullableOrderedString("AA106"));
		orderedKeys.insertKey(new NullableOrderedString("AA108"));
		orderedKeys.insertKey(new NullableOrderedString("AA140"));
		orderedKeys.insertKey(new NullableOrderedString("AA20"));
		orderedKeys.insertKey(new NullableOrderedString("DL60"));
		orderedKeys.insertKey(new NullableOrderedString("B612"));
		orderedKeys.insertKey(new NullableOrderedString("SS40"));
		orderedKeys.insertKey(new NullableOrderedString("KLM20"));
		orderedKeys.insertKey(new NullableOrderedString("KLM100"));
		orderedKeys.insertKey(new NullableOrderedString(null));
		
		orderedKeys.prepareForSearch();
		
		assertEquals(0, orderedKeys.getAllLessThan(new NullableOrderedString(null)).size());	
		assertEquals(1, orderedKeys.getAllLessThanEqual(new NullableOrderedString(null)).size());
		
		Set<NullableOrderedString> expectedResult = new HashSet<>();
		
		expectedResult.add(new NullableOrderedString("AA106"));
		expectedResult.add(new NullableOrderedString("AA108"));
		expectedResult.add(new NullableOrderedString("AA140"));
		expectedResult.add(new NullableOrderedString("AA20"));
		expectedResult.add(new NullableOrderedString("B612"));
		expectedResult.add(new NullableOrderedString(null));
		
		List<NullableOrderedString> result = orderedKeys.getAllLessThanEqual(new NullableOrderedString("B62"));
		
		assertEquals(6, result.size());
		
		for (int i = 0; i < result.size(); i ++) {
			assertTrue(expectedResult.contains(result.get(i)));
		}

	}
	
	@Test
	public void OrderedKeysTest_4() {
		

		OrderedKeys<NullableOrderedString> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(new NullableOrderedString("AA106"));
		orderedKeys.insertKey(new NullableOrderedString("AA108"));
		orderedKeys.insertKey(new NullableOrderedString("AA140"));
		orderedKeys.insertKey(new NullableOrderedString("AA20"));
		orderedKeys.insertKey(new NullableOrderedString("DL60"));
		orderedKeys.insertKey(new NullableOrderedString("B612"));
		orderedKeys.insertKey(new NullableOrderedString("SS40"));
		orderedKeys.insertKey(new NullableOrderedString("KLM20"));
		orderedKeys.insertKey(new NullableOrderedString("KLM100"));
		orderedKeys.insertKey(new NullableOrderedString(null));
		
		orderedKeys.prepareForSearch();
		
		Set<NullableOrderedString> expectedResult = new HashSet<>();
		
		expectedResult.add(new NullableOrderedString("DL60"));
		expectedResult.add(new NullableOrderedString("KLM20"));
		expectedResult.add(new NullableOrderedString("KLM100"));
		
		List<NullableOrderedString> result = orderedKeys.getAllBetween(new NullableOrderedString("DL59"), new NullableOrderedString("SS39"));
		
		assertEquals(3, result.size());
		
		for (int i = 0; i < result.size(); i ++) {
			assertTrue(expectedResult.contains(result.get(i)));
		}

	}
	
	@Test
	public void OrderedKeysTest_5() {
		

		OrderedKeys<NullableOrderedString> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(new NullableOrderedString("AA106"));
		orderedKeys.insertKey(new NullableOrderedString("AA108"));
		orderedKeys.insertKey(new NullableOrderedString("AA140"));
		orderedKeys.insertKey(new NullableOrderedString("AA20"));
		orderedKeys.insertKey(new NullableOrderedString("DL60"));
		orderedKeys.insertKey(new NullableOrderedString("B612"));
		orderedKeys.insertKey(new NullableOrderedString("SS40"));
		orderedKeys.insertKey(new NullableOrderedString("KLM20"));
		orderedKeys.insertKey(new NullableOrderedString("KLM100"));
		orderedKeys.insertKey(new NullableOrderedString(null));
		
		orderedKeys.prepareForSearch();
		

		
		List<NullableOrderedString> result = orderedKeys.getAllGreaterThanEqual(new NullableOrderedString(null));
		
		assertEquals(10, result.size());
		
		result = orderedKeys.getAllGreaterThan(new NullableOrderedString(null));
		
		assertEquals(9, result.size());
		

	}
	
	
	@Test
	public void OrderedKeysTest_6() {
		

		OrderedKeys<Integer> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(Integer.MIN_VALUE);
		orderedKeys.insertKey(Integer.MAX_VALUE);
		orderedKeys.insertKey(-23);
		orderedKeys.insertKey(-230);
		orderedKeys.insertKey(0);
		orderedKeys.insertKey(10);
		orderedKeys.insertKey(20000);
		orderedKeys.insertKey(43000);
		orderedKeys.insertKey(600);
		orderedKeys.insertKey(888);
		orderedKeys.insertKey(767);
		
		orderedKeys.prepareForSearch();
		
		Set<Integer> expectedResult = new HashSet<>();
		
		expectedResult.add(Integer.MAX_VALUE);
		expectedResult.add(-23);
		expectedResult.add(-230);
		expectedResult.add(0);
		expectedResult.add(10);
		expectedResult.add(20000);
		expectedResult.add(43000);
		expectedResult.add(600);
		expectedResult.add(888);
		expectedResult.add(767);
		
		List<Integer> result = orderedKeys.getAllGreaterThanEqual(-300);
		
		assertEquals(10, result.size());
	
		
		for (int i = 0; i < result.size(); i ++) {
			assertTrue(expectedResult.contains(result.get(i)));
		}

	}
	
	@Test
	public void OrderedKeysTest_7() {
		

		OrderedKeys<Integer> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(Integer.MIN_VALUE);
		orderedKeys.insertKey(Integer.MAX_VALUE);
		orderedKeys.insertKey(-23);
		orderedKeys.insertKey(-230);
		orderedKeys.insertKey(0);
		orderedKeys.insertKey(10);
		orderedKeys.insertKey(20000);
		orderedKeys.insertKey(43000);
		orderedKeys.insertKey(600);
		orderedKeys.insertKey(888);
		orderedKeys.insertKey(767);

		
		orderedKeys.prepareForSearch();
		
		
		List<Integer> result = orderedKeys.getAllBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		assertEquals(11, result.size());

	}
	
	@Test
	public void OrderedKeysTest_8() {
		

		OrderedKeys<Integer> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(Integer.MIN_VALUE);
		orderedKeys.insertKey(Integer.MAX_VALUE);
		orderedKeys.insertKey(-23);
		orderedKeys.insertKey(-230);
		orderedKeys.insertKey(0);
		orderedKeys.insertKey(10);
		orderedKeys.insertKey(20000);
		orderedKeys.insertKey(43000);
		orderedKeys.insertKey(600);
		orderedKeys.insertKey(888);
		orderedKeys.insertKey(767);

		
		orderedKeys.prepareForSearch();
		
		
		Set<Integer> expectedResult = new HashSet<>();
		

		expectedResult.add(10);
		expectedResult.add(600);
		expectedResult.add(888);
		expectedResult.add(767);
		
		List<Integer> result = orderedKeys.getAllBetween(10, 10000);
		
		assertEquals(4, result.size());
	
		
		for (int i = 0; i < result.size(); i ++) {
			assertTrue(expectedResult.contains(result.get(i)));
		}
	}
	
	
	@Test
	public void OrderedKeysTest_9() {
		

		OrderedKeys<Integer> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(Integer.MIN_VALUE);
		orderedKeys.insertKey(Integer.MAX_VALUE);
		orderedKeys.insertKey(-23);
		orderedKeys.insertKey(-230);
		orderedKeys.insertKey(0);
		orderedKeys.insertKey(10);
		orderedKeys.insertKey(20000);
		orderedKeys.insertKey(43000);
		orderedKeys.insertKey(600);
		orderedKeys.insertKey(888);
		orderedKeys.insertKey(767);

		
		orderedKeys.prepareForSearch();
		
		
		Set<Integer> expectedResult = new HashSet<>();
		

		expectedResult.add(Integer.MAX_VALUE);
		expectedResult.add(10);
		expectedResult.add(20000);
		expectedResult.add(43000);
		expectedResult.add(600);
		expectedResult.add(888);
		expectedResult.add(767);
		
		List<Integer> result = orderedKeys.getAllGreaterThan(0);
		
		assertEquals(7, result.size());
	
		
		for (int i = 0; i < result.size(); i ++) {
			assertTrue(expectedResult.contains(result.get(i)));
		}
	}
	
	@Test
	public void OrderedKeysTest_10() {
		

		OrderedKeys<Integer> orderedKeys = new OrderedKeys<>();

		orderedKeys.insertKey(Integer.MIN_VALUE);
		orderedKeys.insertKey(Integer.MAX_VALUE);
		orderedKeys.insertKey(-23);
		orderedKeys.insertKey(-230);
		orderedKeys.insertKey(0);
		orderedKeys.insertKey(10);
		orderedKeys.insertKey(20000);
		orderedKeys.insertKey(43000);
		orderedKeys.insertKey(600);
		orderedKeys.insertKey(888);
		orderedKeys.insertKey(767);

		
		orderedKeys.prepareForSearch();
		
		
		Set<Integer> expectedResult = new HashSet<>();
		

		expectedResult.add(Integer.MAX_VALUE);
		expectedResult.add(0);
		expectedResult.add(10);
		expectedResult.add(20000);
		expectedResult.add(43000);
		expectedResult.add(600);
		expectedResult.add(888);
		expectedResult.add(767);
		
		List<Integer> result = orderedKeys.getAllGreaterThanEqual(0);
		
		assertEquals(8, result.size());
	
		
		for (int i = 0; i < result.size(); i ++) {
			assertTrue(expectedResult.contains(result.get(i)));
		}
	}
	
	

}
