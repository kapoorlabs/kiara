package com.kapoorlabs.kiara.test.loader;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kapoorlabs.kiara.adapters.PojoAdapter;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.loader.StoreLoader;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class PrepareForSearchTests {
	
	Store store = new Store(PojoAdapter.getSdqlColumns(DummyTestObject.class));
	
	@Test
	public void prepareForSearchTests_1() throws LoadDataException {

		StoreLoader storeLoader = new StoreLoader(store);

		DummyTestObject dummyTestObject = new DummyTestObject();

		storeLoader.loadTable(dummyTestObject);
		
		storeLoader.prepareForSearch();

		assertEquals(37, storeLoader.getStore().getColumnIndex().size());
	}

}
