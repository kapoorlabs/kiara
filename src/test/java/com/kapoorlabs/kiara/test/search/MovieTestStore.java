package com.kapoorlabs.kiara.test.search;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.StoreSearch;
import com.kapoorlabs.kiara.test.objects.MovieTestObject;
import com.opencsv.CSVReader;

public class MovieTestStore {

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

		StoreSearch storeSearch = new StoreSearch();
		List<Condition> conditions = new LinkedList<>();
		
		conditions.add(new Condition("movieTitle", Operator.EQUAL, "Titanic"));
		List<MovieTestObject> movieResults = storeSearch.query(store, conditions);
		long end = System.currentTimeMillis();
	
		assertEquals(1, movieResults.size());
		assertEquals("Titanic", movieResults.get(0).getMovieTitle());
		assertEquals("Leonardo DiCaprio,Kate Winslet,Gloria Stuart", movieResults.get(0).getActors());
		assertEquals("Color", movieResults.get(0).getColor());
		assertEquals("James Cameron", movieResults.get(0).getDirectorName());
		assertEquals("Drama,Romance", movieResults.get(0).getGenres());
		assertEquals("artist,love,ship,titanic,wet", movieResults.get(0).getPlotKeywords());
	}
	
	@Test
	public void movieTest_2() {

		StoreSearch storeSearch = new StoreSearch();
		List<Condition> conditions = new LinkedList<>();
		
		conditions.add(new Condition("actors", Operator.CONTAINS_ALL, new String[] {"Kate Winslet", "Leonardo DiCaprio"}));
		
		List<MovieTestObject> movieResults = storeSearch.query(store, conditions);
	
		assertEquals(2, movieResults.size());
		
		assertEquals("Titanic", movieResults.get(0).getMovieTitle());
		assertEquals("Leonardo DiCaprio,Kate Winslet,Gloria Stuart", movieResults.get(0).getActors());
		assertEquals("Color", movieResults.get(0).getColor());
		assertEquals("James Cameron", movieResults.get(0).getDirectorName());
		assertEquals("Drama,Romance", movieResults.get(0).getGenres());
		assertEquals("artist,love,ship,titanic,wet", movieResults.get(0).getPlotKeywords());
		
		assertEquals("Revolutionary Road", movieResults.get(1).getMovieTitle());
		assertEquals("Leonardo DiCaprio,Kate Winslet,Joe Komara", movieResults.get(1).getActors());
		assertEquals("Color", movieResults.get(1).getColor());
		assertEquals("Sam Mendes", movieResults.get(1).getDirectorName());
		assertEquals("Drama,Romance", movieResults.get(1).getGenres());
		assertEquals("based on novel,children,connecticut,suburb,work", movieResults.get(1).getPlotKeywords());

	}
	
	
	@Test
	public void movieTest_3() {

		StoreSearch storeSearch = new StoreSearch();
		List<Condition> conditions = new LinkedList<>();
		
		conditions.add(new Condition("actors", Operator.CONTAINS_ALL, "Dwayne Johnson"));
		conditions.add(new Condition("titleYear", Operator.GREATER_THAN, "2011"));
		
		List<MovieTestObject> movieResults = storeSearch.query(store, conditions);
	
		assertEquals(7, movieResults.size());
		
		assertEquals("G.I. Joe: Retaliation", movieResults.get(0).getMovieTitle());
		assertEquals(new Integer(2013), movieResults.get(0).getTitleYear());
		assertEquals(130000000, movieResults.get(0).getBudget());
		assertEquals(new Double(2.35), movieResults.get(0).getAspectRatio());

		assertEquals("San Andreas", movieResults.get(1).getMovieTitle());
		assertEquals(new Integer(2015), movieResults.get(1).getTitleYear());
		assertEquals(110000000, movieResults.get(1).getBudget());
		assertEquals(new Double(2.35), movieResults.get(1).getAspectRatio());
		
		assertEquals("Hercules", movieResults.get(2).getMovieTitle());
		
		assertEquals("Journey 2: The Mysterious Island", movieResults.get(3).getMovieTitle());

	}

}
