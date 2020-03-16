package com.kapoorlabs.kiara.test.domain;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.EmptyColumnException;

public class StoreTest {
	
    @Test(expected=EmptyColumnException.class)
    public void storeNullConstructorTest() {
    	@SuppressWarnings("unused")
		Store store = new Store(null);
    }
    

}
