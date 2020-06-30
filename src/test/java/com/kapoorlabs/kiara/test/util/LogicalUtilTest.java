package com.kapoorlabs.kiara.test.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.SdqlNode;
import com.kapoorlabs.kiara.util.LogicalUtil;

public class LogicalUtilTest {
	
	
	@Test
	public void LogicalUtilTest_1_getCollectionSize() {
		assertEquals(0, LogicalUtil.getCollectionSize(null));
	}
	
	@Test
	public void LogicalUtilTest_2_getCollectionSize() {
		ArrayList<ArrayList<SdqlNode>> nodeCollection = new ArrayList<>();
		assertEquals(0, LogicalUtil.getCollectionSize(nodeCollection));
	}
	
	@Test
	public void LogicalUtilTest_3_getCollectionSize() {
		ArrayList<ArrayList<SdqlNode>> nodeCollection = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		
		nodeCollection.add(col1);
		assertEquals(1, LogicalUtil.getCollectionSize(nodeCollection));
	}
	
	@Test
	public void LogicalUtilTest_4_getCollectionSize() {
		ArrayList<ArrayList<SdqlNode>> nodeCollection = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		
		nodeCollection.add(col1);
		assertEquals(2, LogicalUtil.getCollectionSize(nodeCollection));
	}
	
	@Test
	public void LogicalUtilTest_5_getCollectionSize() {
		ArrayList<ArrayList<SdqlNode>> nodeCollection = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		
		ArrayList<SdqlNode> col2 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		
		nodeCollection.add(col1);
		nodeCollection.add(col2);
		assertEquals(4, LogicalUtil.getCollectionSize(nodeCollection));
	}
	
	@Test
	public void LogicalUtilTest_6_getCollectionSize() {
		ArrayList<ArrayList<SdqlNode>> nodeCollection = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		
		for(long i = 0; i < 1000; i++) {
			col1.add(new SdqlNode(null, new Double(i)));
		}
		nodeCollection.add(col1);

		assertEquals(1000, LogicalUtil.getCollectionSize(nodeCollection));
	}
	
	@Test
	public void LogicalUtilTest_7_getDuplicatesFromCollection() {
		assertEquals(0, LogicalUtil.getDuplicatesFromCollection(null,null).size());
	}
	
	@Test
	public void LogicalUtilTest_8_getDuplicatesFromCollection() {
		ArrayList<ArrayList<SdqlNode>> colA = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		colA.add(col1);
		assertEquals(0, LogicalUtil.getDuplicatesFromCollection(colA,null).size());
		assertEquals(0, LogicalUtil.getDuplicatesFromCollection(null,colA).size());
	}
	
	@Test
	public void LogicalUtilTest_9_getDuplicatesFromCollection() {
		ArrayList<ArrayList<SdqlNode>> colA = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> colB = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		colA.add(col1);
		assertEquals(0, LogicalUtil.getDuplicatesFromCollection(colA,colB).size());
		assertEquals(0, LogicalUtil.getDuplicatesFromCollection(colB,colA).size());
	}
	
	@Test
	public void LogicalUtilTest_10_getDuplicatesFromCollection() {
		ArrayList<ArrayList<SdqlNode>> colA = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> colB = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		colA.add(col1);
		
		ArrayList<SdqlNode> col2 = new ArrayList<>();
		col2.add(new SdqlNode("1", null));
		col2.add(new SdqlNode("3", null));
		col2.add(new SdqlNode("4", null));
		colB.add(col2);
		assertEquals(1, LogicalUtil.getDuplicatesFromCollection(colA,colB).size());
		assertEquals(new SdqlNode("1", null), LogicalUtil.getDuplicatesFromCollection(colA,colB).get(0).get(0));

	}
	
	@Test
	public void LogicalUtilTest_11_getDuplicatesFromCollection() {
		ArrayList<ArrayList<SdqlNode>> colA = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> colB = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		colA.add(col1);
		
		ArrayList<SdqlNode> col2 = new ArrayList<>();
		col2.add(new SdqlNode("1", null));
		col2.add(new SdqlNode("3", null));
		col2.add(new SdqlNode("4", null));
		colB.add(col2);
		assertEquals(1, LogicalUtil.getDuplicatesFromCollection(colA,colB).size());
		assertEquals(new SdqlNode("1", null), LogicalUtil.getDuplicatesFromCollection(colA,colB).get(0).get(0));

	}
	
	@Test
	public void LogicalUtilTest_12_getDuplicatesFromCollection() {
		ArrayList<ArrayList<SdqlNode>> colA = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> colB = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		colA.add(col1);
		
		ArrayList<SdqlNode> col2 = new ArrayList<>();
		col2.add(new SdqlNode("1", null));
		col2.add(new SdqlNode("3", null));
		col2.add(new SdqlNode("4", null));
		colB.add(col2);
		
		ArrayList<SdqlNode> col3 = new ArrayList<>();
		col3.add(new SdqlNode("2", null));
		col3.add(new SdqlNode("5", null));
		col3.add(new SdqlNode("6", null));
		colB.add(col3);
		assertEquals(2, LogicalUtil.getDuplicatesFromCollection(colA,colB).size());
		assertEquals(new SdqlNode("1", null), LogicalUtil.getDuplicatesFromCollection(colA,colB).get(0).get(0));
		assertEquals(new SdqlNode("2", null), LogicalUtil.getDuplicatesFromCollection(colA,colB).get(1).get(0));

	}
	
	@Test
	public void LogicalUtilTest_13_getDuplicatesFromCollection() {
		ArrayList<ArrayList<SdqlNode>> colA = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> colB = new ArrayList<>();
		ArrayList<SdqlNode> col1 = new ArrayList<>();
		col1.add(new SdqlNode("1", null));
		col1.add(new SdqlNode("2", null));
		colA.add(col1);
		
		ArrayList<SdqlNode> col2 = new ArrayList<>();
		col2.add(new SdqlNode("1", null));
		col2.add(new SdqlNode("3", null));
		col2.add(new SdqlNode("4", null));
		colB.add(col2);
		
		ArrayList<SdqlNode> col3 = new ArrayList<>();
		col3.add(new SdqlNode("2", null));
		col3.add(new SdqlNode("1", null));
		col3.add(new SdqlNode("3", null));
		colB.add(col3);
		assertEquals(2, LogicalUtil.getDuplicatesFromCollection(colA,colB).size());
		assertEquals(new SdqlNode("1", null), LogicalUtil.getDuplicatesFromCollection(colA,colB).get(0).get(0));
		assertEquals(new SdqlNode("2", null), LogicalUtil.getDuplicatesFromCollection(colA,colB).get(1).get(0));

	}
	
	@Test
	public void LogicalUtilTest_14_binarySearchCurrentNodes() {
		
		assertEquals(new ArrayList<>(), LogicalUtil.binarySearchCurrentNodes(new ArrayList<>(), new ArrayList<>()));

	}
	
	@Test
	public void LogicalUtilTest_15_binarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(16);
		sdqlNode.setUpperBound(17);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(1, LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals("3", LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());

	}
	
	@Test
	public void LogicalUtilTest_16_binarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(25);
		sdqlNode.setUpperBound(27);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(16);
		sdqlNode.setUpperBound(17);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(0, LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).size());

	}
	
	@Test
	public void LogicalUtilTest_17_binarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(12);
		sdqlNode.setUpperBound(13);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(1, LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals("3", LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());
		assertEquals("4", LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).get(0).get(1).getStringValue());

	}
	
	@Test
	public void LogicalUtilTest_18_binarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		prevSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(21);
		sdqlNode.setUpperBound(25);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("5", null);
		sdqlNode.setLowerBound(12);
		sdqlNode.setUpperBound(13);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(1, LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals("4", LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());
		assertEquals("5", LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).get(0).get(1).getStringValue());

	}
	
	@Test
	public void LogicalUtilTest_19_binarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		prevSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(21);
		sdqlNode.setUpperBound(25);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		currSubColl = new ArrayList<>();
		
		sdqlNode = new SdqlNode("5", null);
		sdqlNode.setLowerBound(12);
		sdqlNode.setUpperBound(13);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(2, LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals("4", LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());
		assertEquals("5", LogicalUtil.binarySearchCurrentNodes(prevColl, currColl).get(1).get(0).getStringValue());

	}
	
	@Test
	public void LogicalUtilTest_20_inverseBinarySearchCurrentNodes() {
		
		assertEquals(new ArrayList<>(), LogicalUtil.inverseBinarySearchCurrentNodes(new ArrayList<>(), new ArrayList<>()));

	}
	
	@Test
	public void LogicalUtilTest_21_inverseBinarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(16);
		sdqlNode.setUpperBound(17);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(1, LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals("3", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());

	}
	
	@Test
	public void LogicalUtilTest_22_inverseBinarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(16);
		sdqlNode.setUpperBound(17);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(1, LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals("3", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());

	}
	
	
	@Test
	public void LogicalUtilTest_23_inverseBinarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(12);
		sdqlNode.setUpperBound(13);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		currSubColl = new ArrayList<>();
		
		sdqlNode = new SdqlNode("5", null);
		sdqlNode.setLowerBound(2);
		sdqlNode.setUpperBound(3);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(1, LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals("5", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());
		assertEquals("3", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(1).getStringValue());
		assertEquals("4", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(2).getStringValue());

	}
	
	@Test
	public void LogicalUtilTest_24_inverseBinarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(12);
		sdqlNode.setUpperBound(13);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		currSubColl = new ArrayList<>();
		

		
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("5", null);
		sdqlNode.setLowerBound(8);
		sdqlNode.setUpperBound(9);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(1, LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals("3", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());
		assertEquals("5", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(1).getStringValue());
		assertEquals("4", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(2).getStringValue());

	}
	
	@Test
	public void LogicalUtilTest_25_inverseBinarySearchCurrentNodes() {
		
		ArrayList<ArrayList<SdqlNode>> prevColl = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> currColl = new ArrayList<>();
		
		ArrayList<SdqlNode> prevSubColl = new ArrayList<>();
		SdqlNode sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("2", null);
		sdqlNode.setLowerBound(11);
		sdqlNode.setUpperBound(15);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		prevSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("1", null);
		sdqlNode.setLowerBound(1);
		sdqlNode.setUpperBound(10);
		prevSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("3", null);
		sdqlNode.setLowerBound(21);
		sdqlNode.setUpperBound(30);
		prevSubColl.add(sdqlNode);
		
		prevColl.add(prevSubColl);
		
		ArrayList<SdqlNode> currSubColl = new ArrayList<>();
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("5", null);
		sdqlNode.setLowerBound(12);
		sdqlNode.setUpperBound(13);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		currSubColl = new ArrayList<>();
		

		
		sdqlNode = new SdqlNode("4", null);
		sdqlNode.setLowerBound(5);
		sdqlNode.setUpperBound(7);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("6", null);
		sdqlNode.setLowerBound(8);
		sdqlNode.setUpperBound(9);
		currSubColl.add(sdqlNode);
		
		sdqlNode = new SdqlNode("7", null);
		sdqlNode.setLowerBound(28);
		sdqlNode.setUpperBound(29);
		currSubColl.add(sdqlNode);
		
		currColl.add(currSubColl);
		
		
		assertEquals(2, LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).size());
		assertEquals(3, LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).size());
		assertEquals(1, LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(1).size());
		assertEquals("4", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(0).getStringValue());
		assertEquals("6", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(1).getStringValue());
		assertEquals("5", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(0).get(2).getStringValue());
		assertEquals("7", LogicalUtil.inverseBinarySearchCurrentNodes(prevColl, currColl).get(1).get(0).getStringValue());

	}

}
