package com.kapoorlabs.kiara.test.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.KeywordSearchResult;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.InsufficientDataException;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.search.KeywordSearch;
import com.kapoorlabs.kiara.test.domain.Config;

public class ConfigTest {

	static Store<Config> configStore;
	KeywordSearch keywordSearch = new KeywordSearch(10);

	static {

		try {
			loadConfigStore();
		} catch (LoadDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void loadConfigStore() throws LoadDataException {
		configStore = new Store<>(Config.class);
		StoreLoader<Config> storeLoader = new StoreLoader<>(configStore);

		storeLoader.loadTable(new Config("in", null, null, 2.00));
		storeLoader.loadTable(new Config("us", null, null, 1.00));
		storeLoader.loadTable(new Config("us", null, "dl", 3.00));
		storeLoader.loadTable(new Config("us", "nyc", null, 2.50));
		storeLoader.loadTable(new Config("us", "nyc", "aa", 4.50));
		storeLoader.loadTable(new Config("us", "nyc", "dl", 5.50));

		storeLoader.prepareForSearch();

	}

	@Test
	public void configTest_1() {

		KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch("", configStore);

		assertEquals(0, result.getResult().size());

	}
	
	@Test
	public void configTest_2() {

		KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch(null, configStore);

		assertEquals(0, result.getResult().size());

	}
	
	@Test(expected=InsufficientDataException.class)
	public void configTest_3() {

		keywordSearch.getMinimumMatch("aa", null);

	}
	
	@Test
	public void configTest_4() {

		KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch("us", configStore);

		assertEquals(1, result.getResult().size());
		assertEquals(new Double(1.0), result.getResult().get(0).getSurCharge());

	}
	
	@Test
	public void configTest_5() {

		KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch("us dl", configStore);

		assertEquals(1, result.getResult().size());
		assertEquals(new Double(3.0), result.getResult().get(0).getSurCharge());

	}
	
	@Test
	public void configTest_6() {

		KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch("us dl nyc", configStore);

		assertEquals(1, result.getResult().size());
		assertEquals(new Double(5.50), result.getResult().get(0).getSurCharge());

	}
	
	@Test
	public void configTest_7() {

		KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch("us nyc", configStore);

		assertEquals(1, result.getResult().size());
		assertEquals(new Double(2.50), result.getResult().get(0).getSurCharge());

	}
	
	@Test
	public void configTest_8() {

		KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch("us nyc abcd", configStore);

		assertEquals(1, result.getResult().size());
		assertEquals(new Double(2.50), result.getResult().get(0).getSurCharge());

	}
	
	@Test
	public void configTest_9() {

		KeywordSearchResult<Config> result = keywordSearch.getMinimumMatch("usa", configStore);

		assertEquals(0, result.getResult().size());

	}
}
