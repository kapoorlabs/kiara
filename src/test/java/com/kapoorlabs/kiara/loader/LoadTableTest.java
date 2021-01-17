package com.kapoorlabs.kiara.loader;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.test.objects.DummyTestObject;

public class LoadTableTest {

	Store<DummyTestObject> store = new Store<>(DummyTestObject.class);

	@Test
	public void getLoadTableTests_1() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();

		storeLoader.loadTable(dummyTestObject);

		String expectedTrie = "-1#root~0#0.00~1#0.00~2#0.00~3#null~4#0.00~5#false~6#null~7#null~8#null~9#null~10#null~11#null~12#null~13#null~14#null~15#null~16#null~17#null~18#null~19#null~20#null~21#null~22#null~23#null~24#null~25#null~26#null~27#null~28#null~29#null~30#null~31#null~32#null~33#0.00~34#0.00~35#null~36#null";

		assertEquals(expectedTrie, storeLoader.serializeTrie());
	}

	// Duplicate objects
	@Test
	public void getLoadTableTests_3() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();

		dummyTestObject.setStringField("sampleString");

		storeLoader.loadTable(dummyTestObject);
		storeLoader.loadTable(dummyTestObject);
		storeLoader.loadTable(dummyTestObject);
		storeLoader.loadTable(dummyTestObject);
		storeLoader.loadTable(dummyTestObject);

		String expectedTrie = "-1#root~0#0.00~1#0.00~2#0.00~3#null~4#0.00~5#false~6#null~7#null~8#null~9#null~10#null~11#null~12#null~13#null~14#null~15#null~16#null~17#null~18#null~19#null~20#null~21#null~22#null~23#null~24#null~25#null~26#sampleString~27#null~28#null~29#null~30#null~31#null~32#null~33#0.00~34#0.00~35#null~36#null";

		assertEquals(expectedTrie, storeLoader.serializeTrie());
	}

	@Test
	public void getLoadTableTests_4() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("sampleString");

		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("sampleString2");

		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("sampleString3");
		storeLoader.loadTable(dummyTestObject);
		storeLoader.loadTable(dummyTestObject);

		String expectedTrie = "-1#root~0#0.00~1#0.00~2#0.00~3#null~4#0.00~5#false~6#null~7#null~8#null~9#null~10#null~11#null~12#null~13#null~14#null~15#null~16#null~17#null~18#null~19#null~20#null~21#null~22#null~23#null~24#null~25#null~26#sampleString~26#sampleString2~26#sampleString3~27#null~28#null~29#null~30#null~31#null~32#null~33#null~34#null~35#null~36#null~37#null~38#null~39#null~40#null~41#null~42#null~43#null~44#null~45#0.00~46#0.00~47#0.00~48#0.00~49#0.00~50#0.00~51#null~52#null~53#null~54#null~55#null~56#null";

		assertEquals(expectedTrie, storeLoader.serializeTrie());
	}

	@Test
	public void getLoadTableTests_5() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("sampleString");

		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("sampleString2");
		dummyTestObject.setDoubleObject(11.30);

		storeLoader.loadTable(dummyTestObject);

		dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("sampleString3");
		dummyTestObject.setDoubleObject(12.30);
		storeLoader.loadTable(dummyTestObject);
		storeLoader.loadTable(dummyTestObject);

		String expectedTrie = "-1#root~0#0.00~1#0.00~2#0.00~3#null~4#0.00~5#false~6#null~7#null~8#null~9#null~10#null~11#null~12#null~13#null~14#null~15#null~16#null~17#null~18#null~19#null~20#null~21#null~22#null~23#null~24#null~25#null~26#sampleString~26#sampleString2~26#sampleString3~27#null~28#null~29#null~30#null~31#null~32#null~33#null~34#null~35#null~36#null~37#null~38#null~39#null~40#null~41#null~42#null~43#null~44#null~45#0.00~46#0.00~47#0.00~48#0.00~49#0.00~50#0.00~51#null~52#null~53#null~54#null~55#11.30~56#12.30";

		assertEquals(expectedTrie, storeLoader.serializeTrie());
	}

	@Test
	public void getLoadTableTests_6() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("");

		storeLoader.loadTable(dummyTestObject);

		String expectedTrie = "-1#root~0#0.00~1#0.00~2#0.00~3#null~4#0.00~5#false~6#null~7#null~8#null~9#null~10#null~11#null~12#null~13#null~14#null~15#null~16#null~17#null~18#null~19#null~20#null~21#null~22#null~23#null~24#null~25#null~26#null~27#null~28#null~29#null~30#null~31#null~32#null~33#0.00~34#0.00~35#null~36#null";

		assertEquals(expectedTrie, storeLoader.serializeTrie());
	}

	@Test
	public void getLoadTableTests_7() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("  ");

		storeLoader.loadTable(dummyTestObject);

		String expectedTrie = "-1#root~0#0.00~1#0.00~2#0.00~3#null~4#0.00~5#false~6#null~7#null~8#null~9#null~10#null~11#null~12#null~13#null~14#null~15#null~16#null~17#null~18#null~19#null~20#null~21#null~22#null~23#null~24#null~25#null~26#null~27#null~28#null~29#null~30#null~31#null~32#null~33#0.00~34#0.00~35#null~36#null";

		assertEquals(expectedTrie, storeLoader.serializeTrie());
	}

	@Test
	public void getLoadTableTests_8() throws LoadDataException {

		StoreLoader<DummyTestObject> storeLoader = new StoreLoader<>(store);

		DummyTestObject dummyTestObject = new DummyTestObject();
		dummyTestObject.setStringField("  ");
		dummyTestObject.setDoubleField(Double.NaN);

		storeLoader.loadTable(dummyTestObject);

		String expectedTrie = "-1#root~0#0.00~1#0.00~2#0.00~3#null~4#0.00~5#false~6#null~7#null~8#null~9#null~10#null~11#null~12#null~13#null~14#null~15#null~16#null~17#null~18#null~19#null~20#null~21#null~22#null~23#null~24#null~25#null~26#null~27#null~28#null~29#null~30#null~31#null~32#null~33#0.00~34#null~35#null~36#null";

		assertEquals(expectedTrie, storeLoader.serializeTrie());
	}

}
