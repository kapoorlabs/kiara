package com.kapoorlabs.kiara.loader;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.SdqlNode;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.EmptyColumnException;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;


public class MatchingChildTest {

	SdqlNode[] nodesToAdd;

	public MatchingChildTest() {

		nodesToAdd = new SdqlNode[10];

		nodesToAdd[0] = new SdqlNode("K", null);
		nodesToAdd[1] = new SdqlNode("k", null);
		nodesToAdd[2] = new SdqlNode("-23", -23.009);
		nodesToAdd[3] = new SdqlNode("2018", 2018.0);
		nodesToAdd[4] = new SdqlNode("0", 0.0);
		// precision of 15 decimal points
		nodesToAdd[5] = new SdqlNode("9223372036854775807", 9223372036854775807.4);
		nodesToAdd[6] = new SdqlNode("1234567890", 1234567890.4);

	}

	@Test(expected = EmptyColumnException.class)
	public void getMatchingChildTest_1() {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<DummyTestObject>(null);
		storeLoader.getMatchingChild(null, null);

	}

	@Test(expected = EmptyColumnException.class)
	public void getMatchingChildTest_2() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		storeLoader.getMatchingChild(storeLoader.getTrieRoot(), null);

	}

	@Test
	public void getMatchingChildTest_3() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		for (int i = 0; i < 2; i++) {
			assertEquals(nodesToAdd[i], storeLoader.getMatchingChild(storeLoader.getTrieRoot(), nodesToAdd[i]));
		}
		assertEquals("-1#root~0#K~0#k", storeLoader.serializeTrie());
	}

	@Test
	public void getMatchingChildTest_4() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		for (int i = 0; i < 2; i++) {
			assertEquals(nodesToAdd[i], storeLoader.getMatchingChild(storeLoader.getTrieRoot(), nodesToAdd[i]));
		}

		assertEquals(nodesToAdd[0], storeLoader.getTrieRoot().getChildren().get(0));
		assertEquals(nodesToAdd[1], storeLoader.getTrieRoot().getChildren().get(1));

	}

	@Test
	public void getMatchingChildTest_5() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		for (int i = 0; i < 2; i++) {
			assertEquals(nodesToAdd[i], storeLoader.getMatchingChild(storeLoader.getTrieRoot(), nodesToAdd[i]));
		}

		assertEquals(storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[2]),
				nodesToAdd[2]);
		assertEquals(storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[3]),
				nodesToAdd[3]);
		
		assertEquals("-1#root~0#K~0#k~1#-23.01~1#2018.00", storeLoader.serializeTrie());

	}
	
	@Test
	public void getMatchingChildTest_6() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		for (int i = 0; i < 2; i++) {
			assertEquals(nodesToAdd[i], storeLoader.getMatchingChild(storeLoader.getTrieRoot(), nodesToAdd[i]));
		}

		assertEquals(nodesToAdd[2],storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[2]));
		assertEquals(nodesToAdd[3],storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[3]));
		
		assertEquals("-1#root~0#K~0#k~1#-23.01~1#2018.00", storeLoader.serializeTrie());

	}
	
	@Test
	public void getMatchingChildTest_7() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		for (int i = 0; i < 2; i++) {
			assertEquals(nodesToAdd[i], storeLoader.getMatchingChild(storeLoader.getTrieRoot(), nodesToAdd[i]));
		}

		assertEquals(nodesToAdd[2], storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[2]));
		assertEquals(nodesToAdd[3], storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[3]));
		
		assertEquals(nodesToAdd[4],storeLoader.getMatchingChild(nodesToAdd[2], nodesToAdd[4]));
		
		assertEquals("-1#root~0#K~0#k~1#-23.01~1#2018.00~3#0.00", storeLoader.serializeTrie());

	}
	
	@Test
	public void getMatchingChildTest_8() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		for (int i = 0; i < 2; i++) {
			assertEquals(nodesToAdd[i], storeLoader.getMatchingChild(storeLoader.getTrieRoot(), nodesToAdd[i]));
		}

		assertEquals(nodesToAdd[2], storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[2]));
		assertEquals(nodesToAdd[3], storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[3]));
		
		assertEquals(nodesToAdd[4],storeLoader.getMatchingChild(nodesToAdd[2], nodesToAdd[4]));
		
		assertEquals(nodesToAdd[5],storeLoader.getMatchingChild(nodesToAdd[1], nodesToAdd[5]));
		
		assertEquals("-1#root~0#K~0#k~1#-23.01~1#2018.00~2#9223372036854776000.00~3#0.00", storeLoader.serializeTrie());

	}
	
	@Test
	public void getMatchingChildTest_9() {

		Store<DummyTestObject> store = new Store<>(DummyTestObject.class);
		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);
		for (int i = 0; i < 2; i++) {
			assertEquals(nodesToAdd[i], storeLoader.getMatchingChild(storeLoader.getTrieRoot(), nodesToAdd[i]));
		}

		assertEquals(nodesToAdd[2], storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[2]));
		assertEquals(nodesToAdd[3], storeLoader.getMatchingChild(storeLoader.getTrieRoot().getChildren().get(0), nodesToAdd[3]));
		
		assertEquals(nodesToAdd[4],storeLoader.getMatchingChild(nodesToAdd[2], nodesToAdd[4]));
		
		assertEquals(nodesToAdd[5],storeLoader.getMatchingChild(nodesToAdd[1], nodesToAdd[5]));
		
		assertEquals(nodesToAdd[6],storeLoader.getMatchingChild(nodesToAdd[3], nodesToAdd[6]));
		
		assertEquals("-1#root~0#K~0#k~1#-23.01~1#2018.00~2#9223372036854776000.00~3#0.00~4#1234567890.40", storeLoader.serializeTrie());

	}

}
