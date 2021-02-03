package com.kapoorlabs.kiara.test.search;

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

import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.exception.NonSupportedOperationException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.StoreSearch;
import com.kapoorlabs.kiara.test.objects.RangeTestObject;
import com.opencsv.CSVReader;

public class RangeTest {

	static Store<RangeTestObject> store;

	static Set<String> NULL_FILTER_SET = null;
	
	static {
		store = new Store<>(RangeTestObject.class);
		StoreLoader<RangeTestObject> storeLoader = new StoreLoader<>(store);

		ClassLoader classLoader = RangeTest.class.getClassLoader();
		InputStream csvFile = classLoader.getResourceAsStream("range-test.csv");
		CSVReader csvReader = null;

		try {

			csvReader = new CSVReader(new InputStreamReader(csvFile));

			String[] words = null;

			while ((words = csvReader.readNext()) != null) {

				RangeTestObject rangeValue = new RangeTestObject();
				rangeValue.setRange(words[0]);
				rangeValue
						.setValue(words[1] != null && !words[1].isEmpty() ? Integer.parseInt(words[1]) : null);

				try {
					storeLoader.loadTable(rangeValue);
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
	public void rangeValueTest_1() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("range", Operator.CONTAINS_EITHER, "AA101"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		assertEquals(0, result.size());

	}

	@Test
	public void rangeValueTest_2() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("range", Operator.CONTAINS_EITHER, "AA99"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		assertEquals(1, result.size());
		assertEquals("2", result.get(0).get("VALUE"));

	}

	@Test
	public void rangeValueTest_3() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("range", Operator.EQUAL, "AA10-100"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		assertEquals(1, result.size());
		assertEquals("2", result.get(0).get("VALUE"));

	}

	@Test
	public void rangeValueTest_4() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("AA99");
		values.add("DL500");
		values.add("AA2300");

		conditions.add(new Condition("range", Operator.CONTAINS_EITHER, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("2");
		expectedvalue.add("1");

		assertEquals(2, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}

	@Test
	public void rangeValueTest_5() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("AA99");
		values.add("DL500");
		values.add("AA2300");

		conditions.add(new Condition("range", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		assertEquals(0, result.size());

	}

	@Test
	public void rangeValueTest_6() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("AA55");
		values.add("AA10");
		values.add("JB300");
		values.add("JB400");
		values.add("DL2100");

		conditions.add(new Condition("range", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("2");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}

	@Test
	public void rangeValueTest_7() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("DL5000");

		conditions.add(new Condition("range", Operator.CONTAINS_EITHER, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("7");
		expectedvalue.add("4");

		assertEquals(2, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}

	@Test
	public void rangeValueTest_8() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("AA50");
		values.add("JB201");

		conditions.add(new Condition("range", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("2");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}

	@Test
	public void rangeValueTest_9() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("B6200");

		conditions.add(new Condition("range", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("3");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}

	@Test
	public void rangeValueTest_10() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("B668");

		conditions.add(new Condition("range", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("3");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}

	@Test
	public void rangeValueTest_11() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("-~");

		conditions.add(new Condition("range", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("7");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}

	@Test
	public void rangeValueTest_12() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("-A4");

		conditions.add(new Condition("range", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("1");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}
	
	@Test
	public void rangeValueTest_13() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = null;

		conditions.add(new Condition("range", Operator.CONTAINS_EITHER, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("0");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}
	
	@Test
	public void rangeValueTest_14() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("Al1");
		conditions.add(new Condition("range", Operator.CONTAINS_EITHER, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("0");

		assertEquals(0, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}
	
	@Test
	public void rangeValueTest_15() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("Al1");
		conditions.add(new Condition("range", Operator.CONTAINS_EITHER_INCLUDING_NULL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);

		Set<String> expectedvalue = new HashSet<>();

		expectedvalue.add("0");

		assertEquals(1, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedvalue.contains(result.get(i).get("VALUE")));
		}

	}
	
	@Test
	public void rangeValueTest_16() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("AA11");
		conditions.add(new Condition("range", Operator.NOT_CONTAINS, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);


		assertEquals(7, result.size());

	}
	
	@Test
	public void rangeValueTest_17() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("AA11");
		values.add("CA2");
		values.add("DL5000");
		conditions.add(new Condition("range", Operator.NOT_CONTAINS, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);


		assertEquals(4, result.size());

	}
	
	@Test(expected=NonSupportedOperationException.class)
	public void rangeValueTest_18() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> values = new LinkedList<>();

		values.add("2");
		conditions.add(new Condition("value", Operator.NOT_CONTAINS, values));
		@SuppressWarnings("unused")
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);


	}

}
