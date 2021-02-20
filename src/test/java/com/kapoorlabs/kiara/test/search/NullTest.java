package com.kapoorlabs.kiara.test.search;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.domain.annotations.CaseInsensitive;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateRanges;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDates;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedNumbers;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.DateFormat;
import com.kapoorlabs.kiara.domain.annotations.NumericRange;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.StoreSearch;

import lombok.Data;

public class NullTest {
	
	static Set<String> NULL_FILTER_SET = null;
	
	
	@Data
	public static class TestObject {
		
		@CaseInsensitive
		String name;
		
		Long age;
		
		@DateFormat
		String dob;
		
		@CommaSeperatedDates
		String lifeEvents;
		
		@CommaSeperatedDateRanges
		String happyYears;
		
		@NumericRange
		String salaryRange;
		
		@CaseInsensitive
		@CommaSeperatedStrings
		String otherNames;
		
		@CommaSeperatedNumbers
		String luckyNumbers;
		
	}
	
	@Test
	public void null_test_1() throws LoadDataException
	{
		Store<TestObject> store = new Store<>(TestObject.class);
		StoreLoader<TestObject> storeLoader = new StoreLoader<>(store);
		
		TestObject testObject = new TestObject();
		
		testObject.setName("John");
		testObject.setSalaryRange("200-250");
		
		storeLoader.loadTable(testObject);
		storeLoader.prepareForSearch();
		
		StoreSearch storeSearch = new StoreSearch();
		String otherName = null;
		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("name", Operator.EQUAL, "JoHn"));
		conditions.add(new Condition("OTHERNAMES", Operator.EQUAL, otherName));

		List<Map<String,String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		
		assertEquals("john", result.get(0).get("NAME"));
		assertEquals("200-250", result.get(0).get("SALARYRANGE"));
		assertEquals(null, result.get(0).get("AGE"));
		assertEquals(null, result.get(0).get("DOB"));
		assertEquals(null, result.get(0).get("LIFEEVENTS"));
		assertEquals(null, result.get(0).get("HAPPYYEARS"));
		assertEquals(null, result.get(0).get("OTHERNAMES"));
		assertEquals(null, result.get(0).get("LUCKYNUMBERS"));
	}

	
	@Test
	public void null_test_2() throws LoadDataException
	{
		Store<TestObject> store = new Store<>(TestObject.class);
		StoreLoader<TestObject> storeLoader = new StoreLoader<>(store);
		
		TestObject testObject = new TestObject();
		
		testObject.setName("John");
		testObject.setSalaryRange("200-250");
		testObject.setLifeEvents("2015-05-18, null");
		
		storeLoader.loadTable(testObject);
		storeLoader.prepareForSearch();
		
		StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("name", Operator.EQUAL, "John"));

		List<Map<String,String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		
		assertEquals("john", result.get(0).get("NAME"));
		assertEquals("200-250", result.get(0).get("SALARYRANGE"));
		assertEquals(null, result.get(0).get("AGE"));
		assertEquals(null, result.get(0).get("DOB"));
		assertEquals("2015-05-18, null", result.get(0).get("LIFEEVENTS"));
		assertEquals(null, result.get(0).get("HAPPYYEARS"));
		assertEquals(null, result.get(0).get("OTHERNAMES"));
		assertEquals(null, result.get(0).get("LUCKYNUMBERS"));
	}
	
	@Test
	public void null_test_3() throws LoadDataException
	{
		Store<TestObject> store = new Store<>(TestObject.class);
		StoreLoader<TestObject> storeLoader = new StoreLoader<>(store);
		
		TestObject testObject = new TestObject();
		
		testObject.setName("John");
		testObject.setSalaryRange("200-250");
		testObject.setLifeEvents("2015-05-18, null");
		testObject.setLuckyNumbers("1,2,null");
		
		storeLoader.loadTable(testObject);
		storeLoader.prepareForSearch();
		
		StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("name", Operator.EQUAL, "John"));

		List<Map<String,String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		
		assertEquals("john", result.get(0).get("NAME"));
		assertEquals("200-250", result.get(0).get("SALARYRANGE"));
		assertEquals(null, result.get(0).get("AGE"));
		assertEquals(null, result.get(0).get("DOB"));
		assertEquals("2015-05-18, null", result.get(0).get("LIFEEVENTS"));
		assertEquals(null, result.get(0).get("HAPPYYEARS"));
		assertEquals(null, result.get(0).get("OTHERNAMES"));
		assertEquals("1,2,null", result.get(0).get("LUCKYNUMBERS"));
	}
}
