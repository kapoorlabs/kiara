package com.kapoorlabs.kiara.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.adapters.PojoAdapter;
import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.StoreSearch;
import com.kapoorlabs.kiara.test.objects.FlightCommissionTestObject;
import com.opencsv.CSVReader;

public class FlightCommisionTest {

	Store store;

	public FlightCommisionTest() {
		store = new Store(PojoAdapter.getSdqlColumns(FlightCommissionTestObject.class));
		StoreLoader storeLoader = new StoreLoader(store);

		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream csvFile = classLoader.getResourceAsStream("flight-commission.csv");
		CSVReader csvReader = null;

		try {

			csvReader = new CSVReader(new InputStreamReader(csvFile));

			String[] words = null;

			while ((words = csvReader.readNext()) != null) {

				FlightCommissionTestObject flightCommission = new FlightCommissionTestObject();
				flightCommission.setFlightNumbers(words[0]);
				flightCommission.setCommission(words[1] != null && !words[1].isEmpty() ? Integer.parseInt(words[1]) : null);

				storeLoader.loadTable(flightCommission);
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
	public void flightCommissionTest_1() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("flightnumbers",Operator.CONTAINS_EITHER, "AA101"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		assertEquals(0, result.size());


	}
	
	@Test
	public void flightCommissionTest_2() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_EITHER, "AA99"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		assertEquals(1, result.size());
		assertEquals("2", result.get(0).get("COMMISSION"));

	}
	
	@Test
	public void flightCommissionTest_3() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("flightnumbers", Operator.EQUAL, "AA10-100"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		assertEquals(1, result.size());
		assertEquals("2", result.get(0).get("COMMISSION"));


	}
	
	@Test
	public void flightCommissionTest_4() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("AA99");
		values.add("DL500");
		values.add("AA2300");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_EITHER, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		
		Set<String> expectedCommission = new HashSet<>();
		
		expectedCommission.add("2");
		expectedCommission.add("1");

		assertEquals(2, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedCommission.contains(result.get(i).get("COMMISSION")));
		}

	}
	
	@Test
	public void flightCommissionTest_5() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("AA99");
		values.add("DL500");
		values.add("AA2300");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		assertEquals(0, result.size());


	}
	
	@Test
	public void flightCommissionTest_6() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("AA55");
		values.add("AA10");
		values.add("JB300");
		values.add("JB400");
		values.add("DL2100");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		Set<String> expectedCommission = new HashSet<>();
		
		expectedCommission.add("2");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedCommission.contains(result.get(i).get("COMMISSION")));
		}


	}
	
	@Test
	public void flightCommissionTest_7() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("DL5000");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_EITHER, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		Set<String> expectedCommission = new HashSet<>();
		
		expectedCommission.add("7");
		expectedCommission.add("4");

		assertEquals(2, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedCommission.contains(result.get(i).get("COMMISSION")));
		}


	}
	
	
	@Test
	public void flightCommissionTest_8() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("AA50");
		values.add("JB201");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		Set<String> expectedCommission = new HashSet<>();
		
		expectedCommission.add("2");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedCommission.contains(result.get(i).get("COMMISSION")));
		}


	}
	
	@Test
	public void flightCommissionTest_9() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("B6200");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		Set<String> expectedCommission = new HashSet<>();
		
		expectedCommission.add("3");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedCommission.contains(result.get(i).get("COMMISSION")));
		}


	}
	
	@Test
	public void flightCommissionTest_10() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("B668");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		Set<String> expectedCommission = new HashSet<>();
		
		expectedCommission.add("3");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedCommission.contains(result.get(i).get("COMMISSION")));
		}


	}
	
	@Test
	public void flightCommissionTest_11() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("-~");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		Set<String> expectedCommission = new HashSet<>();
		
		expectedCommission.add("7");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedCommission.contains(result.get(i).get("COMMISSION")));
		}


	}
	
	@Test
	public void flightCommissionTest_12() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();
		
		values.add("-A4");
		
		conditions.add(new Condition("flightnumbers", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);

		Set<String> expectedCommission = new HashSet<>();
		
		expectedCommission.add("1");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedCommission.contains(result.get(i).get("COMMISSION")));
		}


	}
	
}
