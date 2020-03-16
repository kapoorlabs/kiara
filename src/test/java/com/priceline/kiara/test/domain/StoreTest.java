package com.priceline.kiara.test.domain;

import org.junit.Test;

import com.priceline.kiara.domain.Store;
import com.priceline.kiara.exception.EmptyColumnException;

public class StoreTest {
	
    @Test(expected=EmptyColumnException.class)
    public void storeNullConstructorTest() {
    	@SuppressWarnings("unused")
		Store store = new Store(null);
    }
    

}
