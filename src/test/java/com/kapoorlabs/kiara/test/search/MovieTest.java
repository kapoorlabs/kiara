package com.kapoorlabs.kiara.test.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.KeywordSearchResult;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.KeywordSearch;
import com.kapoorlabs.kiara.test.objects.MovieTestObject;
import com.kapoorlabs.kiara.util.SpellCheckUtil;
import com.opencsv.CSVReader;

public class MovieTest {

	static Store<MovieTestObject> store;

	KeywordSearch keywordSearch = new KeywordSearch(20);

	static {
		store = new Store<>(MovieTestObject.class);
		StoreLoader<MovieTestObject> storeLoader = new StoreLoader<>(store);

		ClassLoader classLoader = MovieTest.class.getClassLoader();
		InputStream csvFile = classLoader.getResourceAsStream("movie_metadata.csv");
		CSVReader csvReader = null;

		try {

			csvReader = new CSVReader(new InputStreamReader(csvFile));

			String[] words = null;
			csvReader.readNext();
			while ((words = csvReader.readNext()) != null) {

				MovieTestObject movieTestObject = MovieTestObject.builder().build();
				movieTestObject.setColor(words[0]);
				movieTestObject.setDirectorName(words[1]);
				movieTestObject.setGenres(words[6]);
				movieTestObject.setActors(words[7]);
				movieTestObject.setMovieTitle(words[8]);
				movieTestObject.setPlotKeywords(words[12]);
				movieTestObject.setBudget(words[18].isEmpty() ? 0 : Long.parseLong(words[18]));
				movieTestObject.setTitleYear(words[19].isEmpty()? null : Integer.parseInt(words[19]));
				movieTestObject.setAspectRatio(words[21].isEmpty()? null : Double.parseDouble(words[21]));

				try {
					storeLoader.loadTable(movieTestObject);
				} catch (LoadDataException ex) {
					continue;
				}

			}

			storeLoader.prepareForSearch();

		} catch (Exception e) {
			e.printStackTrace();
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
	public void movieTest_1() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Kate Winslet");
		keywords.add("sHIP");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());

	}
	
	@Test
	public void movieTest_1_1() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Kate Winslet");
		keywords.add("ship");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, -1);

		assertEquals(0, keywordSearchResult.getResult().size());

	}
	
	@Test
	public void movieTest_1_2() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Kate Winslet");
		keywords.add("ship");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, 0);

		assertEquals(0, keywordSearchResult.getResult().size());

	}
	
	@Test
	public void movieTest_1_3() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Kate Winslet");
		keywords.add("ship");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, 1);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());

	}
	
	@Test
	public void movieTest_1_4() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Kate Winslet");
		keywords.add("ship");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, 2);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());

	}
	
	@Test
	public void movieTest_1_5() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Kate Winslet");
		keywords.add("ship");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, 100);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());

	}


	@Test
	public void movieTest_2() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Kae Winslet");
		keywords.add("ship");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());

	}

	@Test
	public void movieTest_3() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Ka Winslet");
		keywords.add("ship");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(21, keywordSearchResult.getResult().size());
		assertEquals(1, keywordSearchResult.getKeywords().size());
		assertTrue(keywordSearchResult.getKeywords().contains("ship"));

	}
	
	@Test
	public void movieTest_3_1() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Ka Winslet");
		keywords.add("ship");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, 2);

		assertEquals(2, keywordSearchResult.getResult().size());
		assertEquals(1, keywordSearchResult.getKeywords().size());
		assertTrue(keywordSearchResult.getKeywords().contains("ship"));

	}

	@Test
	public void movieTest_4() {

		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Kate W", store.getSpellCheckTrie(),10));

		assertEquals(2, suggestions.size());
		assertTrue(suggestions.contains("Kate Winslet"));
		assertTrue(suggestions.contains("Kate Walsh"));

	}

	@Test
	public void movieTest_5() {

		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Tom C", store.getSpellCheckTrie(),4));

		assertEquals(4, suggestions.size());
		assertTrue(suggestions.contains("Tom Cavanagh"));
		assertTrue(suggestions.contains("Tom Conti"));
		assertTrue(suggestions.contains("Tom Cruise"));
		assertTrue(suggestions.contains("Tom Cullen"));

	}
	
	@Test
	public void movieTest_5_1() {

		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Tom C", store.getSpellCheckTrie(),2));

		assertEquals(2, suggestions.size());
		assertTrue(suggestions.contains("Tom Conti"));
		assertTrue(suggestions.contains("Tom Cruise"));

	}

	@Test
	public void movieTest_6() {

		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Tom Cr", store.getSpellCheckTrie(),10));

		assertEquals(1, suggestions.size());
		assertTrue(suggestions.contains("Tom Cruise"));
	}

	@Test
	public void movieTest_7() {

		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Tom Cr", store.getSpellCheckTrie(),10));

		assertEquals(1, suggestions.size());
		assertTrue(suggestions.contains("Tom Cruise"));
	}
	
	@Test
	public void movieTest_7_1() {

		List<String> suggestions = SpellCheckUtil.getTextPredictions("T", store.getSpellCheckTrie(),10000);
		Set<String> uniqieSuggestions = new HashSet<>(suggestions);

		assertEquals(uniqieSuggestions.size(), suggestions.size());
	}
	
	@Test
	public void movieTest_7_2() {

		List<String> suggestions = SpellCheckUtil.getTextPredictions("Troy", store.getSpellCheckTrie(),50);

		assertEquals(6, suggestions.size());
		assertTrue(suggestions.contains("Troy"));
		assertTrue(suggestions.contains("Troy Duffy"));
		assertTrue(suggestions.contains("Troy Evans"));
		assertTrue(suggestions.contains("Troy Nixey"));
		assertTrue(suggestions.contains("Troy Garity"));
		assertTrue(suggestions.contains("Troy Miller"));
	}
	
	@Test
	public void movieTest_7_3() {

		List<String> suggestions = SpellCheckUtil.getTextPredictions("Troy", store.getSpellCheckTrie(),1);

		assertEquals(1, suggestions.size());
		assertTrue(suggestions.contains("Troy"));

	}
	
	@Test
	public void movieTest_7_4() {

		List<String> suggestions = SpellCheckUtil.getTextPredictions("Troy", store.getSpellCheckTrie(),2);

		assertEquals(2, suggestions.size());
		assertTrue(suggestions.contains("Troy"));
		assertTrue(suggestions.contains("Troy Duffy"));

	}
	
	@Test
	public void movieTest_7_5() {

		List<String> suggestions = SpellCheckUtil.getTextPredictions("Troy", store.getSpellCheckTrie(),0);

		assertEquals(0, suggestions.size());

	}
	
	@Test
	public void movieTest_7_6() {

		List<String> suggestions = SpellCheckUtil.getTextPredictions("Troy", store.getSpellCheckTrie(),-1);

		assertEquals(0, suggestions.size());

	}

	@Test
	public void movieTest_8() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Tom Hanks");
		keywords.add("christmas");

		Set<String> expectedMovies = new HashSet<>();
		expectedMovies.add("Cast Away");
		expectedMovies.add("The Polar Express");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(2, keywordSearchResult.getResult().size());
		for (MovieTestObject movie : keywordSearchResult.getResult()) {
			assertTrue(expectedMovies.contains(movie.getMovieTitle()));
		}

	}

	@Test
	public void movieTest_9() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Tom Hanks");
		keywords.add("christmas");
		keywords.add("survival");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Cast Away", keywordSearchResult.getResult().get(0).getMovieTitle());

	}

	@Test
	public void movieTest_10() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Tom Hanks");
		keywords.add("christmas");
		keywords.add("survive");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Cast Away", keywordSearchResult.getResult().get(0).getMovieTitle());

	}

	@Test
	public void movieTest_11() {

		Set<String> keywords = new HashSet<>();

		keywords.add("Tom Hanks");
		keywords.add("christmas");
		keywords.add("survival");
		keywords.add("war hero");
		keywords.add("Sam Anderson");
		keywords.add("vietnam");

		Set<String> expectedMatchingKeywords = new HashSet<>();
		expectedMatchingKeywords.add("Tom Hanks");
		expectedMatchingKeywords.add("war hero");
		expectedMatchingKeywords.add("Sam Anderson");
		expectedMatchingKeywords.add("vietnam");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Forrest Gump", keywordSearchResult.getResult().get(0).getMovieTitle());
		assertEquals(expectedMatchingKeywords, keywordSearchResult.getKeywords());
	}

	@Test
	public void movieTest_12() {

		Set<String> keywords = new HashSet<>();

		keywords.add("No Actor");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(0, keywordSearchResult.getResult().size());

	}

	@Test
	public void movieTest_13() {

		String sentence = null;

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(sentence, store, null);

		assertEquals(0, keywordSearchResult.getResult().size());

	}
	
	@Test
	public void movieTest_14() {

		String sentence = "    ";

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(sentence, store, null);

		assertEquals(0, keywordSearchResult.getResult().size());

	}
	
	@Test
	public void movieTest_15() {

		String sentence = "NoKeyword";

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(sentence, store, null);

		assertEquals(0, keywordSearchResult.getResult().size());

	}
	
	@Test
	public void movieTest_16() {

		Set<String> keywords = null;

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(0, keywordSearchResult.getResult().size());

	}
	
	@Test
	public void movieTest_17() {

		Set<String> keywords = new HashSet<>();

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(0, keywordSearchResult.getResult().size());
	}
	
	@Test
	public void movieTest_18() {

		keywordSearch = new KeywordSearch(1);
		Set<String> keywords = new HashSet<>();
		keywords.add("Titanic");
		keywords.add("Forrest Gump");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, null);

		assertEquals(1, keywordSearchResult.getResult().size());
	}
	
	@Test
	public void movieTest_19() {

		keywordSearch = new KeywordSearch(1);
		Set<String> keywords = new HashSet<>();
		keywords.add("ship");
		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("movieTitle", Operator.EQUAL, "Titanic"));

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, preConditions, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());
	}
	
	@Test
	public void movieTest_20() {

		keywordSearch = new KeywordSearch(1);
		Set<String> keywords = new HashSet<>();
		keywords.add("ship");
		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("actors", Operator.CONTAINS_EITHER, "Kate Winslet"));

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, preConditions, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());
	}
	
	@Test
	public void movieTest_21() {

		keywordSearch = new KeywordSearch(1);
		Set<String> keywords = new HashSet<>();
		keywords.add("ship");
		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("actors", Operator.CONTAINS_EITHER, "Kate Winslet"));

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, preConditions, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());
	}
	
	@Test
	public void movieTest_22() {

		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("plotKeywords", Operator.CONTAINS_EITHER, "magic"));

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch("magic", store, preConditions, null);

		assertEquals(31, keywordSearchResult.getResult().size());
	}
	
	@Test
	public void movieTest_23() {
		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("plotKeywords", Operator.CONTAINS_EITHER, "magic"));
		
		Set<String> keywords = new HashSet<>();
		keywords.add("sister love");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, preConditions, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Frozen", keywordSearchResult.getResult().get(0).getMovieTitle());
	}
	
	@Test
	public void movieTest_24() {
		
		List<Condition> preConditions = new LinkedList<>();
		Integer nullInt = null;
		preConditions.add(new Condition("budget", Operator.EQUAL, 0));
		preConditions.add(new Condition("titleYear", Operator.EQUAL, nullInt));
		
		Set<String> expectedMatchingKeywords = new HashSet<>();
		expectedMatchingKeywords.add("Drama");
		expectedMatchingKeywords.add("Crime");
		expectedMatchingKeywords.add("iceland");
	

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch("Crime Drama iceland", store, preConditions, null);
		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Trapped", keywordSearchResult.getResult().get(0).getMovieTitle());
		assertEquals(expectedMatchingKeywords,keywordSearchResult.getKeywords());
		
	}
	
	@Test
	public void movieTest_25() {
		
		List<Condition> preConditions = new LinkedList<>();
		Integer nullInt = null;
		preConditions.add(new Condition("budget", Operator.EQUAL, 0));
		preConditions.add(new Condition("titleYear", Operator.EQUAL, nullInt));
		
		Set<String> expectedMatchingKeywords = new HashSet<>();
		expectedMatchingKeywords.add("Drama");
		expectedMatchingKeywords.add("Crime");
		expectedMatchingKeywords.add("Biography");
		expectedMatchingKeywords.add("Carlos");
	

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch("Crime Drama iceland Biography Carlos", store, preConditions, null);
		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Carlos", keywordSearchResult.getResult().get(0).getMovieTitle());
		assertEquals(expectedMatchingKeywords,keywordSearchResult.getKeywords());
		
	}
	
	@Test
	public void movieTest_26() {
		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("titleYear", Operator.GREATER_THAN, 2000));
		
		Set<String> expectedMatchingKeywords = new HashSet<>();
		expectedMatchingKeywords.add("Action");
		expectedMatchingKeywords.add("Horror");
		expectedMatchingKeywords.add("Thriller");
		expectedMatchingKeywords.add("Fantasy");
		expectedMatchingKeywords.add("Mystery");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch("Mystery Thriller Fantasy Horror Action", store, preConditions, null);
		
		assertEquals(2, keywordSearchResult.getResult().size());
		assertEquals(expectedMatchingKeywords,keywordSearchResult.getKeywords());
		assertEquals("Dylan Dog: Dead of Night",keywordSearchResult.getResult().get(0).getMovieTitle());
		assertEquals("Ultramarines: A Warhammer 40,000 Movie",keywordSearchResult.getResult().get(1).getMovieTitle());
		
	}
	
	@Test
	public void movieTest_27() {
		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("titleYear", Operator.GREATER_THAN, 2000));
		
		Set<String> expectedMatchingKeywords = new HashSet<>();
		expectedMatchingKeywords.add("Action");
		expectedMatchingKeywords.add("Horror");
		expectedMatchingKeywords.add("Thriller");
		expectedMatchingKeywords.add("Fantasy");
		expectedMatchingKeywords.add("Mystery");
		expectedMatchingKeywords.add("Comedy");

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch("Mystery Thriller Fantasy Horror Action Comedy", store, preConditions, null);
		
		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals(expectedMatchingKeywords,keywordSearchResult.getKeywords());
		assertEquals("Dylan Dog: Dead of Night",keywordSearchResult.getResult().get(0).getMovieTitle());
		
	}
	
	@Test
	//Case insensitive doesn't work with spelling forgiveness
	public void movieTest_28() {

		keywordSearch = new KeywordSearch(1);
		Set<String> keywords = new HashSet<>();
		keywords.add("SHIPP");
		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("actors", Operator.CONTAINS_EITHER, "Kate Winslet"));

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, preConditions, null);

		assertEquals(0, keywordSearchResult.getResult().size());
	}
	
	@Test
	public void movieTest_29() {

		keywordSearch = new KeywordSearch(1);
		Set<String> keywords = new HashSet<>();
		keywords.add("ships");
		
		List<Condition> preConditions = new LinkedList<>();
		preConditions.add(new Condition("actors", Operator.CONTAINS_EITHER, "Kate Winslet"));

		KeywordSearchResult<MovieTestObject> keywordSearchResult = keywordSearch.getBestMatch(keywords, store, preConditions, null);

		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).getMovieTitle());
	}
	



}
