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
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.StoreSearch;
import com.kapoorlabs.kiara.test.objects.CongressResignationsTestObject;
import com.opencsv.CSVReader;

public class CongressResignationsTest {

	static Store<CongressResignationsTestObject> store;

	static {
		store = new Store<>(CongressResignationsTestObject.class);
		StoreLoader<CongressResignationsTestObject> storeLoader = new StoreLoader<>(store);

		ClassLoader classLoader = CongressResignationsTest.class.getClassLoader();
		InputStream csvFile = classLoader.getResourceAsStream("congress-resignations.csv");
		CSVReader csvReader = null;

		try {

			csvReader = new CSVReader(new InputStreamReader(csvFile));

			String[] words = null;

			while ((words = csvReader.readNext()) != null) {

				CongressResignationsTestObject resignations = new CongressResignationsTestObject();

				resignations.setMember(words[0]);
				resignations.setParty(words[1].charAt(0));
				resignations.setDistrict(words[2]);
				resignations.setCongress(words[3]);
				resignations.setResignationDate(words[4]);
				resignations.setReason(words[5]);
				resignations.setSource(words[6]);
				resignations.setCategory(words[7]);

				try {
					storeLoader.loadTable(resignations);
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

	// Number of Democratic party's resignations
	@Test
	public void CongressTest_1() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("party", "D"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(357, result.size());

	}

	// Number of Democratic party's resignations under category B or D.
	@Test
	public void CongressTest_2() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> categoryValues = new LinkedList<>();

		categoryValues.add("B");
		categoryValues.add("D");

		conditions.add(new Condition("party", "D"));
		conditions.add(new Condition("category", Operator.CONTAINS_EITHER, categoryValues));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(50, result.size());

	}

	// Number of Democratic party's resignations under category B and D.
	@Test
	public void CongressTest_3() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> categoryValues = new LinkedList<>();

		categoryValues.add("B");
		categoryValues.add("D");

		conditions.add(new Condition("party", "D"));
		conditions.add(new Condition("category", Operator.CONTAINS_ALL, categoryValues));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(1, result.size());

		assertEquals("Rob Andrews", result.get(0).get("MEMBER"));
		assertEquals("D", result.get(0).get("PARTY"));
		assertEquals("NJ-01", result.get(0).get("DISTRICT"));
		assertEquals("113th", result.get(0).get("CONGRESS"));
		assertEquals("2014-02-18", result.get(0).get("RESIGNATIONDATE"));
		assertEquals("Ethics investigation; took a job as a lobbyist", result.get(0).get("REASON"));
		assertEquals("Philadelphia Inquirer", result.get(0).get("SOURCE"));
		assertEquals("B, D", result.get(0).get("CATEGORY"));
	}

	// Number of Republican party's resignations under category B or D or X before
	// 2015-01-05.
	@Test
	public void CongressTest_4() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> categoryValues = new LinkedList<>();

		categoryValues.add("B");
		categoryValues.add("D");
		categoryValues.add("X");

		conditions.add(new Condition("party", "R"));
		conditions.add(new Condition("category", Operator.CONTAINS_EITHER, categoryValues));
		conditions.add(new Condition("resignationDate", Operator.LESS_THAN, "2015-01-05"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(38, result.size());

	}

	// Number of Republican party's resignations under category B or D or X on or
	// before 2015-01-05.
	@Test
	public void CongressTest_5() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> categoryValues = new LinkedList<>();

		categoryValues.add("B");
		categoryValues.add("D");
		categoryValues.add("X");

		conditions.add(new Condition("party", "R"));
		conditions.add(new Condition("category", Operator.CONTAINS_EITHER, categoryValues));
		conditions.add(new Condition("resignationDate", Operator.LESS_THAN_EQUAL, "2015-01-05"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(39, result.size());

	}

	// Number of Republican party's resignations under category B or D or X between
	// 2015-01-05 and 1932-12-03.
	@Test
	public void CongressTest_6() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> categoryValues = new LinkedList<>();

		categoryValues.add("B");
		categoryValues.add("D");
		categoryValues.add("X");

		conditions.add(new Condition("party", "R"));
		conditions.add(new Condition("category", Operator.CONTAINS_EITHER, categoryValues));
		conditions.add(new Condition("resignationDate", Operator.BETWEEN, "1932-12-03", "2015-01-05"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(32, result.size());

	}

	// Number of Republican party's resignations under category B or D or X between
	// 1980-01-01 and 1932-12-03.
	@Test
	public void CongressTest_7() {

		StoreSearch storeSearch = new StoreSearch();

		Set<String> expectedMembers = new HashSet<>();
		expectedMembers.add("James Hastings");
		expectedMembers.add("Ed Gurney");
		expectedMembers.add("William Keating");
		expectedMembers.add("Bradford Morse");
		expectedMembers.add("Sterling Cole");
		expectedMembers.add("Samuel K. McConnell");
		expectedMembers.add("Clifford Case");
		expectedMembers.add("J. Parnell Thomas");
		expectedMembers.add("Frederick Steiwer");
		expectedMembers.add("John Tilson");

		List<Condition> conditions = new LinkedList<>();

		List<String> categoryValues = new LinkedList<>();

		categoryValues.add("B");
		categoryValues.add("D");
		categoryValues.add("X");

		conditions.add(new Condition("party", "R"));
		conditions.add(new Condition("category", Operator.CONTAINS_EITHER, categoryValues));
		conditions.add(new Condition("resignationDate", Operator.BETWEEN, "1932-12-03", "1980-01-01"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(10, result.size());

		for (int i = 0; i < result.size(); i++) {
			assertTrue(expectedMembers.contains(result.get(i).get("MEMBER")));
		}

	}

	// Number of Republican party's resignations under category B or D or X before
	// 2015-01-05, excluding 2004-08-31 .
	@Test
	public void CongressTest_8() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> categoryValues = new LinkedList<>();

		categoryValues.add("B");
		categoryValues.add("D");
		categoryValues.add("X");

		conditions.add(new Condition("party", "R"));
		conditions.add(new Condition("category", Operator.CONTAINS_EITHER, categoryValues));
		conditions.add(new Condition("resignationDate", Operator.LESS_THAN, "2015-01-05"));
		conditions.add(new Condition("resignationDate", Operator.NOT_EQUAL, "2004-08-31"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(37, result.size());

	}

	// Number of Republican party's resignations under category B or D or X before
	// 2015-01-05, excluding 2004-08-31 and 2008-11-24.
	@Test
	public void CongressTest_9() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> categoryValues = new LinkedList<>();

		categoryValues.add("B");
		categoryValues.add("D");
		categoryValues.add("X");
		
		List<String> excludedDates = new LinkedList<>();

		excludedDates.add("2004-08-31");
		excludedDates.add("2008-11-24");

		conditions.add(new Condition("party", "R"));
		conditions.add(new Condition("category", Operator.CONTAINS_EITHER, categoryValues));
		conditions.add(new Condition("resignationDate", Operator.LESS_THAN, "2015-01-05"));
		conditions.add(new Condition("resignationDate", Operator.NOT_EQUAL, excludedDates));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		assertEquals(36, result.size());

	}

}
