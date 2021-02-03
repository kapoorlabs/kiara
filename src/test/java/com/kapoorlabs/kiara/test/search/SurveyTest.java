package com.kapoorlabs.kiara.test.search;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.ColumnNotFoundException;
import com.kapoorlabs.kiara.exception.InsufficientDataException;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.StoreSearch;
import com.kapoorlabs.kiara.test.objects.SurveyTestObject;
import com.opencsv.CSVReader;

public class SurveyTest {

	static Store<SurveyTestObject> store;
	
	static Set<String> NULL_FILTER_SET = null;

	static {
		store = new Store<>(SurveyTestObject.class);
		StoreLoader<SurveyTestObject> storeLoader = new StoreLoader<>(store);

		ClassLoader classLoader = SurveyTest.class.getClassLoader();
		InputStream csvFile = classLoader.getResourceAsStream("survey.csv");
		CSVReader csvReader = null;

		try {

			csvReader = new CSVReader(new InputStreamReader(csvFile));

			String[] words = null;

			while ((words = csvReader.readNext()) != null) {

				SurveyTestObject surveyObject = new SurveyTestObject();
				surveyObject.setRespondentId(Long.parseLong(words[0]));
				surveyObject.setGender(words[1]);
				surveyObject.setAgeRange(words[2]);
				surveyObject.setIncomeRange(words[3]);
				surveyObject.setEducation(words[4]);
				surveyObject.setLocation(words[5]);

				try {
					storeLoader.loadTable(surveyObject);
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
	// how many females, were present in the survey
	public void surveyTest_1() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("gender", "Female"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(548, result.size());

	}
	
	@Test
	// how many females, were present in the survey
	public void surveyTest_1_1() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("gender", "Female"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET, -1);
		assertEquals(0, result.size());

	}
	
	@Test
	// how many females, were present in the survey
	public void surveyTest_1_2() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("gender", "Female"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET, 10);
		assertEquals(10, result.size());

	}
	
	@Test
	// how many females, were present in the survey
	public void surveyTest_1_3() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("gender", "Female"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET, 600);
		assertEquals(548, result.size());

	}

	@Test
	// how many males, with a possible age of 50 did participate
	public void surveyTest_2() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("gender", "Male"));
		conditions.add(new Condition("ageRange", Operator.CONTAINS_EITHER, "50"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(140, result.size());

	}

	@Test(expected = ColumnNotFoundException.class)
	// wrong column name
	public void surveyTest_3() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("gender", "Male"));
		conditions.add(new Condition("age", Operator.CONTAINS_EITHER, "45"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(140, result.size());

	}

	@Test
	// how many males, with a possible age of either 50 or 30 did participate
	public void surveyTest_4() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> possibleAgeValues = new LinkedList<>();

		possibleAgeValues.add("50");
		possibleAgeValues.add("30");

		conditions.add(new Condition("gender", "Male"));
		conditions.add(new Condition("ageRange", Operator.CONTAINS_EITHER, possibleAgeValues));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(258, result.size());

	}

	@Test
	// how many males, with a possible age of either 50 or 30, and whose income was
	// possibly 60,000 did participate
	public void surveyTest_5() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		List<String> possibleAgeValues = new LinkedList<>();

		possibleAgeValues.add("50");
		possibleAgeValues.add("30");

		conditions.add(new Condition("gender", "Male"));
		conditions.add(new Condition("ageRange", Operator.CONTAINS_EITHER, possibleAgeValues));
		conditions.add(new Condition("incomeRange", Operator.CONTAINS_EITHER, "60000"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(93, result.size());

	}
	
	@Test
	// how many records don't have gender info.
	public void surveyTest_6() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("gender", SdqlConstants.NULL));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(92, result.size());

	}
	
	@Test
	// records greater than 3292753795.
	public void surveyTest_7() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("respondentId", Operator.GREATER_THAN, "3292753795"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(10, result.size());

	}
	
	// Invalid argument number expected.
	@Test(expected=NumberFormatException.class)
	public void surveyTest_8() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("respondentId", Operator.GREATER_THAN, "INVALID"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(10, result.size());

	}
	
	// records greater than equal 3292753795.
	@Test
	public void surveyTest_9() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("respondentId", Operator.GREATER_THAN_EQUAL, "3292753795"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(11, result.size());

	}
	
	// records between 3292214390 and 3292282953.
	@Test(expected=InsufficientDataException.class)
	public void surveyTest_10() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("respondentId", Operator.BETWEEN, "3292753795"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(11, result.size());

	}
	
	// records between 3292214390 and 3292282953.
	@Test
	public void surveyTest_11() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("respondentId", Operator.BETWEEN, "3292214390", "3292282953"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(5, result.size());

	}
	
	// records between 3292214389 and 3292214391.
	@Test
	public void surveyTest_12() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("respondentId", Operator.BETWEEN, "3292214389", "3292214391"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, NULL_FILTER_SET);
		assertEquals(1, result.size());
		assertEquals("3292214390", result.get(0).get("RESPONDENTID"));
		assertEquals("Male", result.get(0).get("GENDER"));
		assertEquals("18-29", result.get(0).get("AGERANGE"));
		assertEquals(null, result.get(0).get("INCOMERANGE"));
		assertEquals("Some college or Associate degree", result.get(0).get("EDUCATION"));
		

	}
	
	// records between 3292214389 and 3292214391 with a filter only showing age.
	@Test
	public void surveyTest_13() {

		StoreSearch storeSearch = new StoreSearch();

		Set<String> filterSet = new HashSet<String>();
		filterSet.add("ageRange");
		
		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("respondentId", Operator.BETWEEN, "3292214389", "3292214391"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, filterSet);
		assertEquals(1, result.size());
		assertEquals(1, result.get(0).size());
		assertEquals("18-29", result.get(0).get("AGERANGE"));	

	}
	
	// records less than 3292214389 with a filter only showing age.
	@Test
	public void surveyTest_14() {

		StoreSearch storeSearch = new StoreSearch();

		Set<String> filterSet = new HashSet<String>();
		filterSet.add("ageRange");
		
		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("respondentId", Operator.LESS_THAN, "3292214389"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, filterSet);
		assertEquals(1078, result.size());
		assertEquals(1, result.get(0).size());

	}


}
