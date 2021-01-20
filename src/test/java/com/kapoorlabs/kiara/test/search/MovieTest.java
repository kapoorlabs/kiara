package com.kapoorlabs.kiara.test.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.KeywordSearchResult;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.KeywordSearch;
import com.kapoorlabs.kiara.test.objects.MovieTestObject;
import com.kapoorlabs.kiara.util.SpellCheckUtil;
import com.opencsv.CSVReader;

public class MovieTest {
	
	static Store<MovieTestObject> store;
	
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

		KeywordSearch keywordSearch = new KeywordSearch();
		Set<String> keywords = new HashSet<>();
		
		keywords.add("Kate Winslet");
		keywords.add("ship");
		
		
		KeywordSearchResult keywordSearchResult = keywordSearch.getBestMatch(keywords, store);
		
		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).get("MOVIETITLE"));


	}
	
	@Test
	public void movieTest_2() {

		KeywordSearch keywordSearch = new KeywordSearch();
		Set<String> keywords = new HashSet<>();
		
		keywords.add("Kae Winslet");
		keywords.add("ship");
		
		
		KeywordSearchResult keywordSearchResult = keywordSearch.getBestMatch(keywords, store);
		
		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Titanic", keywordSearchResult.getResult().get(0).get("MOVIETITLE"));

	}
	
	@Test
	public void movieTest_3() {

		KeywordSearch keywordSearch = new KeywordSearch();
		Set<String> keywords = new HashSet<>();
		
		keywords.add("Ka Winslet");
		keywords.add("ship");
		
		KeywordSearchResult keywordSearchResult = keywordSearch.getBestMatch(keywords, store);

		assertEquals(21, keywordSearchResult.getResult().size());
		assertEquals(1, keywordSearchResult.getKeywords().size());
		assertTrue(keywordSearchResult.getKeywords().contains("ship"));

	}
	
	@Test
	public void movieTest_4() {
		
		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Kate W", store.getSpellCheckTrie()));

		assertEquals(2, suggestions.size());
		assertTrue(suggestions.contains("Kate Winslet"));
		assertTrue(suggestions.contains("Kate Walsh"));


	}
	
	@Test
	public void movieTest_5() {
		
		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Tom C", store.getSpellCheckTrie()));

		assertEquals(4, suggestions.size());
		assertTrue(suggestions.contains("Tom Cavanagh"));
		assertTrue(suggestions.contains("Tom Conti"));
		assertTrue(suggestions.contains("Tom Cruise"));
		assertTrue(suggestions.contains("Tom Cullen"));

	}
	
	@Test
	public void movieTest_6() {
		
		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Tom Cr", store.getSpellCheckTrie()));

		assertEquals(1, suggestions.size());
		assertTrue(suggestions.contains("Tom Cruise"));
	}
	
	@Test
	public void movieTest_7() {
		
		Set<String> suggestions = new HashSet<>(SpellCheckUtil.getTextPredictions("Tom Cr", store.getSpellCheckTrie()));

		assertEquals(1, suggestions.size());
		assertTrue(suggestions.contains("Tom Cruise"));
	}
	
	@Test
	public void movieTest_8() {

		KeywordSearch keywordSearch = new KeywordSearch();
		Set<String> keywords = new HashSet<>();
		
		keywords.add("Tom Hanks");
		keywords.add("christmas");
		
		Set<String> expectedMovies = new HashSet<>();
		expectedMovies.add("Cast Away");
		expectedMovies.add("The Polar Express");
		
		KeywordSearchResult keywordSearchResult = keywordSearch.getBestMatch(keywords, store);
		
		assertEquals(2, keywordSearchResult.getResult().size());
		for (Map<String, String> movie : keywordSearchResult.getResult())
		{
			assertTrue(expectedMovies.contains(movie.get("MOVIETITLE")));
		}
		

	}
	
	@Test
	public void movieTest_9() {

		KeywordSearch keywordSearch = new KeywordSearch();
		Set<String> keywords = new HashSet<>();
		
		keywords.add("Tom Hanks");
		keywords.add("christmas");
		keywords.add("survival");
		
		KeywordSearchResult keywordSearchResult = keywordSearch.getBestMatch(keywords, store);
				
		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Cast Away", keywordSearchResult.getResult().get(0).get("MOVIETITLE"));

	}
	
	@Test
	public void movieTest_10() {

		KeywordSearch keywordSearch = new KeywordSearch();
		Set<String> keywords = new HashSet<>();
		
		keywords.add("Tom Hanks");
		keywords.add("christmas");
		keywords.add("survive");
		
		KeywordSearchResult keywordSearchResult = keywordSearch.getBestMatch(keywords, store);
				
		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Cast Away", keywordSearchResult.getResult().get(0).get("MOVIETITLE"));

	}
	
	@Test
	public void movieTest_11() {

		KeywordSearch keywordSearch = new KeywordSearch();
		Set<String> keywords = new HashSet<>();
		
		keywords.add("Tom Hanks");
		keywords.add("christmas");
		keywords.add("survival");
		keywords.add("war hero");
		keywords.add("Sam Anderson");
		keywords.add("vietnam");
		
		Set<String> expectedMatchingKeywords= new HashSet<>();
		expectedMatchingKeywords.add("Tom Hanks");
		expectedMatchingKeywords.add("war hero");
		expectedMatchingKeywords.add("Sam Anderson");
		expectedMatchingKeywords.add("vietnam");
		
		KeywordSearchResult keywordSearchResult = keywordSearch.getBestMatch(keywords, store);
				
		assertEquals(1, keywordSearchResult.getResult().size());
		assertEquals("Forrest Gump", keywordSearchResult.getResult().get(0).get("MOVIETITLE"));
		assertEquals(expectedMatchingKeywords, keywordSearchResult.getKeywords());
	}

}
