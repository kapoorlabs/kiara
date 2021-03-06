package com.kapoorlabs.kiara.test.search;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.StoreSearch;
import com.kapoorlabs.kiara.test.objects.DeathRecordsTestObject;
import com.opencsv.CSVReader;

public class DeathYearTest {

	private static Store<DeathRecordsTestObject> store;
	
	static Set<String> NULL_FILTER_SET = null;

	static {
		store = new Store<>(DeathRecordsTestObject.class);
		StoreLoader<DeathRecordsTestObject> storeLoader = new StoreLoader<>(store);

		ClassLoader classLoader = DeathYearTest.class.getClassLoader();
		InputStream csvFile = classLoader.getResourceAsStream("death-records.csv");
		CSVReader csvReader = null;

		try {

			csvReader = new CSVReader(new InputStreamReader(csvFile));

			String[] words = null;

			while ((words = csvReader.readNext()) != null) {
				
				DeathRecordsTestObject deathRecords = new DeathRecordsTestObject();
				deathRecords.setGender(words[0]);
				deathRecords.setBirthDeathRange(words[1]);
				deathRecords.setRecordDate(words[2]);
				
				try {
					storeLoader.loadTable(deathRecords);
				} catch (LoadDataException ex) {
					continue;
				}
				
			}
			
			storeLoader.prepareForSearch();

		} catch (IOException e) {
			throw new RuntimeException();
		} finally {
			if (csvReader != null) {
				try {
					csvReader.close();
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
		}
	}
	
	@Test
	public void deathRecordTest_1() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		List<String> values = new LinkedList<String>();
		values.add("F");
		values.add("M");
		values.add("U");
		
		conditions.add(new Condition("gender", Operator.CONTAINS_EITHER,values));
		assertEquals(31832, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_2() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		conditions.add(new Condition("gender","m"));
		assertEquals(0, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_3() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		conditions.add(new Condition("gender","F"));
		assertEquals(16042, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_4() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		conditions.add(new Condition("gender","U"));
		assertEquals(1, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_5() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		conditions.add(new Condition("gender","M"));
		assertEquals(15789, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_6() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		conditions.add(new Condition("gender","M"));
		assertEquals(15789, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_7() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		List<String> values = new LinkedList<String>();
		values.add("F");
		values.add("M");
		values.add("U");
		
		conditions.add(new Condition("gender", Operator.CONTAINS_ALL, values));
		assertEquals(0, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_8() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		List<String> values = new LinkedList<String>();
		values.add("M");
		values.add("U");
		
		conditions.add(new Condition("gender", Operator.CONTAINS_EITHER, values));
		assertEquals(15790, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_9() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		List<String> values = new LinkedList<String>();
		values.add("M");
		values.add("U");
		
		conditions.add(new Condition("gender", Operator.NOT_EQUAL, values));
		assertEquals(16042, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
 
 
	@Test
	public void deathRecordTest_10() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("gender", Operator.GREATER_THAN, "M"));
		assertEquals(1, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_11() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("gender", Operator.LESS_THAN, "M"));
		assertEquals(16042, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	
	@Test
	public void deathRecordTest_12() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", "2012-07-03"));
		assertEquals(19, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	
	@Test
	public void deathRecordTest_13() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", Operator.LESS_THAN, "2012-07-03"));
		assertEquals(11, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
 
	
	@Test
	public void deathRecordTest_14() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", Operator.LESS_THAN_EQUAL, "2012-07-03"));
		assertEquals(30, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_15() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", Operator.GREATER_THAN, "2012-07-03"));
		assertEquals(31802, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
 
 
	@Test
	public void deathRecordTest_16() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", Operator.GREATER_THAN_EQUAL, "2012-07-03"));
		assertEquals(31821, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
 
 
	@Test
	public void deathRecordTest_17() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", Operator.BETWEEN, "2012-01-01", "2012-12-31"));
		assertEquals(2321, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
 
	
	@Test
	public void deathRecordTest_18() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", Operator.BETWEEN, "2012-01-01", "2012-12-31"));
		conditions.add(new Condition("gender", "M"));
		assertEquals(1130, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_19() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", Operator.BETWEEN, "2012-01-01", "2012-12-31"));
		conditions.add(new Condition("gender", "M"));
		conditions.add(new Condition("birthDeathRange", Operator.CONTAINS_ALL,"1930-07-10"));
		assertEquals(327, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
	@Test
	public void deathRecordTest_20() {

	StoreSearch storeSearch = new StoreSearch();
		
		List<Condition> conditions = new LinkedList<>();
		
		
		conditions.add(new Condition("recordDate", Operator.BETWEEN, "2012-01-01", "2012-12-31"));
		conditions.add(new Condition("gender", "M"));
		conditions.add(new Condition("birthDeathRange", Operator.EQUAL,"1927-06-23-2012-06-28"));
		assertEquals(1, storeSearch.query(store, conditions, NULL_FILTER_SET).size());

	}
	
}
