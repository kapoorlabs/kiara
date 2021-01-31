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
import com.kapoorlabs.kiara.domain.KeywordSearchResult;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.exception.NonSupportedOperationException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.KeywordSearch;
import com.kapoorlabs.kiara.search.StoreSearch;
import com.kapoorlabs.kiara.test.objects.BooksTestObject;
import com.opencsv.CSVReader;

public class BooksTest {

	static Store<BooksTestObject> store;

	static {
		store = new Store<>(BooksTestObject.class);
		
		StoreLoader<BooksTestObject> storeLoader = new StoreLoader<>(store);

		ClassLoader classLoader = BooksTest.class.getClassLoader();
		InputStream csvFile = classLoader.getResourceAsStream("books.csv");
		CSVReader csvReader = null;

		try {

			csvReader = new CSVReader(new InputStreamReader(csvFile));

			String[] words = null;

			while ((words = csvReader.readNext()) != null) {

				BooksTestObject books = new BooksTestObject();
				books.setId(Integer.parseInt(words[0]));
				books.setIsbn(words[1]);
				books.setAuthors(words[2]);
				Double year = words[3] != null && !words[3].isEmpty() ? Double.parseDouble(words[3]) : null;
				books.setYear(year != null ? year.intValue() : null);
				books.setTitle(words[4]);
				books.setLanguage(words[5]);
				books.setRating(Double.parseDouble(words[6]));
				
				try {
					storeLoader.loadTable(books);
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
	public void booksTest_1() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();
		conditions.add(new Condition("title", "The Hobbit"));
		
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		assertEquals(1, result.size());
		assertEquals("7", result.get(0).get("ID"));
		assertEquals("618260307", result.get(0).get("ISBN"));
		assertEquals("J.R.R. Tolkien", result.get(0).get("AUTHORS"));
		assertEquals("1937", result.get(0).get("YEAR"));
		assertEquals("en-US", result.get(0).get("LANGUAGE"));
		assertEquals("4.25", result.get(0).get("RATING"));
		
		assertEquals(1, boosResult.size());
		assertEquals(7, boosResult.get(0).getId());
		assertEquals("618260307", boosResult.get(0).getIsbn());
		assertEquals("J.R.R. Tolkien", boosResult.get(0).getAuthors());
		assertEquals(new Integer(1937), boosResult.get(0).getYear());
		assertEquals("en-US", boosResult.get(0).getLanguage());
		assertEquals(new Double(4.25), boosResult.get(0).getRating());

	}

	@Test
	public void booksTest_2() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();
		List<String> values = new LinkedList<String>();
		values.add("Mark Twain");
		values.add("John Seelye");

		conditions.add(new Condition("authors", Operator.CONTAINS_ALL, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		Set<String> resultId = new HashSet<>();
		resultId.add("58");
		resultId.add("116");
		

		assertEquals(2, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(resultId.contains(result.get(i).get("ID")));
		}
		assertEquals(58, boosResult.get(0).getId());
		assertEquals(116, boosResult.get(1).getId());
		

	}
	
	@Test
	public void booksTest_3() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();
		List<String> values = new LinkedList<String>();
		values.add("Mark Twain");
		values.add("John Seelye");

		conditions.add(new Condition("authors", Operator.CONTAINS_EITHER, values));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		Set<String> resultId = new HashSet<>();
		
		resultId.add("58");
		resultId.add("116");
		resultId.add("1178");
		resultId.add("1622");
		resultId.add("3252");
		resultId.add("2569");

		assertEquals(6, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(resultId.contains(result.get(i).get("ID")));
		}
		
		assertEquals(58, boosResult.get(0).getId());
		assertEquals(116, boosResult.get(1).getId());
		assertEquals(1178, boosResult.get(2).getId());
		assertEquals(1622, boosResult.get(3).getId());
		assertEquals(3252, boosResult.get(4).getId());
		assertEquals(2569, boosResult.get(5).getId());
		

	}
	
	@Test
	public void booksTest_4() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("isbn", "null"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		assertEquals(320, result.size());
		assertEquals(320, boosResult.size());
		

	}
	
	@Test
	public void booksTest_5() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("isbn", "null"));
		conditions.add(new Condition("language", "null"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		assertEquals(18, result.size());
		assertEquals(18, boosResult.size());
		

	}
	
	@Test
	public void booksTest_6() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("isbn", "null"));
		conditions.add(new Condition("year", "null"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		assertEquals(2, result.size());
		assertEquals(2, boosResult.size());
		
	}
	
	@Test
	public void booksTest_7() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();
		List<String> values = new LinkedList<String>();
		values.add("eng");
		values.add("en-US");

		conditions.add(new Condition("rating", Operator.GREATER_THAN_EQUAL, "4"));
		conditions.add(new Condition("language", Operator.CONTAINS_EITHER, values));
		conditions.add(new Condition("year", Operator.BETWEEN, "2010", "2012"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		assertEquals(466, result.size());
		assertEquals(466, boosResult.size());
		

	}
	
	@Test
	public void booksTest_8() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();
		List<String> values = new LinkedList<String>();
		values.add("eng");
		values.add("en-US");

		conditions.add(new Condition("rating", Operator.GREATER_THAN_EQUAL, "4"));
		conditions.add(new Condition("language", Operator.CONTAINS_EITHER, values));
		conditions.add(new Condition("year", Operator.BETWEEN, "2010", "2012"));
		conditions.add(new Condition("authors", "Stephen King"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		Set<String> resultId = new HashSet<>();
		
		resultId.add("295");
		resultId.add("1576");
		resultId.add("2412");

		assertEquals(3, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(resultId.contains(result.get(i).get("ID")));
		}
		
		assertEquals(295, boosResult.get(0).getId());
		assertEquals(1576, boosResult.get(1).getId());
		assertEquals(2412, boosResult.get(2).getId());
		

	}
	
	@Test
	public void booksTest_9() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();
		List<String> values = new LinkedList<String>();
		values.add("eng");
		values.add("en-US");
		
		List<String> authors = new LinkedList<String>();
		authors.add("Stephen King");
		authors.add("George R.R. Martin");
		
		

		conditions.add(new Condition("rating", Operator.GREATER_THAN_EQUAL, "4"));
		conditions.add(new Condition("language", Operator.CONTAINS_EITHER, values));
		conditions.add(new Condition("year", Operator.BETWEEN, "2010", "2012"));
		conditions.add(new Condition("authors", Operator.CONTAINS_EITHER, authors));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		Set<String> resultId = new HashSet<>();
		
		resultId.add("295");
		resultId.add("1576");
		resultId.add("2412");
		resultId.add("188");
		resultId.add("2149");
		resultId.add("3282");
		resultId.add("5344");

		assertEquals(7, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(resultId.contains(result.get(i).get("ID")));
		}
		assertEquals(295, boosResult.get(0).getId());
		assertEquals(1576, boosResult.get(1).getId());
		assertEquals(2412, boosResult.get(2).getId());
		assertEquals(188, boosResult.get(3).getId());
		assertEquals(2149, boosResult.get(4).getId());
		assertEquals(3282, boosResult.get(5).getId());
		assertEquals(5344, boosResult.get(6).getId());
		

	}
	
	@Test
	public void booksTest_10() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();
		List<String> values = new LinkedList<String>();
		values.add("eng");
		values.add("en-US");
		
		List<String> authors = new LinkedList<String>();
		authors.add("Stephen King");
		authors.add("George R.R. Martin");
		
		

		conditions.add(new Condition("rating", Operator.GREATER_THAN_EQUAL, "4"));
		conditions.add(new Condition("language", Operator.CONTAINS_EITHER, values));
		conditions.add(new Condition("year", Operator.BETWEEN, "2010", "2012"));
		conditions.add(new Condition("year", Operator.NOT_EQUAL, "2011"));
		conditions.add(new Condition("authors", Operator.CONTAINS_EITHER, authors));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		Set<String> resultId = new HashSet<>();
		
		resultId.add("1576");
		resultId.add("2412");

		assertEquals(2, result.size());
		for (int i = 0; i < result.size(); i++) {
			assertTrue(resultId.contains(result.get(i).get("ID")));
		}
		assertEquals(1576, boosResult.get(0).getId());
		assertEquals(2412, boosResult.get(1).getId());
		

	}
	
	@Test(expected=NonSupportedOperationException.class)
	public void booksTest_11() {

		StoreSearch storeSearch = new StoreSearch();

		List<Condition> conditions = new LinkedList<>();

		conditions.add(new Condition("id", "some value"));
		List<Map<String, String>> result = storeSearch.query(store, conditions, null);
		List<BooksTestObject> boosResult = storeSearch.query(store, conditions);

		assertEquals(0, result.size());
		assertEquals(0, boosResult.size());
		

	}
	
	@Test
	public void booksTest_12() {

		KeywordSearch keywordSearch = new KeywordSearch(50);
		Set<String> keywords = new HashSet<>();
		keywords.add("The Diary of a Young Girl");
		

		KeywordSearchResult<BooksTestObject> result = keywordSearch.getBestMatch( keywords, store);

		assertEquals(1, result.getKeywords().size());
		assertEquals("The Diary of a Young Girl", result.getResult().get(0).getTitle());
	}
	



}
