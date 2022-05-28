package com.kapoorlabs.kiara.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.SdqlNode;
import com.kapoorlabs.kiara.domain.SecondaryCollectionDataType;
import com.kapoorlabs.kiara.domain.SecondarySingleDataType;
import com.kapoorlabs.kiara.domain.Store;

import lombok.extern.slf4j.Slf4j;

public class LogicalUtil {
	
	/**
	 * This function takes the Store and a Condition as an argument, and returns a
	 * boolean if the column associated with the condition is indexed as a numeric
	 * value
	 * <p>
	 * The associated column will be a numeric value if it is a list of numbers,
	 * dates or time stamps.
	 * 
	 * @param store     This argument specifies the store we are using. Each store
	 *                  has its own Java object, that describes its data structure.
	 * @param condition This argument is the condition, that was specified in the
	 *                  query. Each condition lays a restriction on particular store
	 *                  column.
	 * @return It returns a boolean indicating, if the column associated with the
	 *         condition is indexed as a numeric value.
	 */
	public static boolean isIndexNumeric(Store store, Condition condition) {
		return store.getSdqlColumns()[condition.getColumnIndex()].isNumeric()
				|| (store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType() != null
						&& (store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
								.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_TIME
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondaryCollectionType() == SecondaryCollectionDataType.NUMBER
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondarySingleType() == SecondarySingleDataType.DATE
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondarySingleType() == SecondarySingleDataType.DATE_TIME));
	}

	/**
	 * This function takes the Store and a Condition as an argument, and returns a
	 * boolean if the column associated with the condition is a type of a range.
	 * <p>
	 * For example AA10-1004 is a range type with values as AA10, AA11,... AA1003,
	 * AA1004.
	 * 
	 * @param store     This argument specifies the store we are using. Each store
	 *                  has its own Java object, that describes its data structure.
	 * @param condition This argument is the condition, that was specified in the
	 *                  query. Each condition lays a restriction on particular store
	 *                  column.
	 * @return It returns a boolean indicating, if the column associated with the
	 *         condition is of data type range.
	 */

	public static boolean isRangeType(Store store, Condition condition) {
		return !store.getSdqlColumns()[condition.getColumnIndex()].isNumeric()
				&& (store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType() != null
						&& (store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
								.getSecondaryCollectionType() == SecondaryCollectionDataType.NUMERIC_RANGE
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_RANGE
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_TIME_RANGE
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondarySingleType() == SecondarySingleDataType.NUMERIC_RANGE
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondarySingleType() == SecondarySingleDataType.DATE_RANGE
								|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
										.getSecondarySingleType() == SecondarySingleDataType.DATE_TIME_RANGE));
	}
	
	public static Long getLongValue(String input) {
		if (input == null || input.isEmpty() || input.equalsIgnoreCase(SdqlConstants.NULL)) {
			return null;
		}
		Long result = null;
		try {
			result = Long.parseLong(input);
		} catch (Exception ex) {
			System.err.println("Error while transforming, number expected, but got string for :" + input);
			result = null;
		}
		return result;
	}
	
	public static Integer getIntValue(String input) {
		if (input == null || input.isEmpty() || input.equalsIgnoreCase(SdqlConstants.NULL)) {
			return null;
		}
		Integer result = null;
		try {
			result = Integer.parseInt(input);
		} catch (Exception ex) {
			System.err.println("Error while transforming, number expected, but got string for :" + input);
			result = null;
		}
		return result;
	}

	public static Double getDoubleValue(String input) {
		if (input == null || input.isEmpty() || input.equalsIgnoreCase(SdqlConstants.NULL)) {
			return null;
		}
		Double result = null;
		try {
			result = Double.parseDouble(input);
		} catch (Exception ex) {
			System.err.println("Error while transforming, double expected, but got string for :" + input);
			result = null;
		}
		return result;

	}

	public static String getStringValue(String input) {
		if (input == null || input.isEmpty() || input.equalsIgnoreCase(SdqlConstants.NULL)) {
			return null;
		}
		return input;
	}
	
	public static Boolean getBooleanValue(String input) {
		if (input == null || input.isEmpty() || input.equalsIgnoreCase(SdqlConstants.NULL)) {
			return null;
		}
		Boolean result = null;
		try {
			result = Boolean.parseBoolean(input);
		} catch (Exception ex) {
			System.err.println("Error while transforming, boolean expected, but got string for :" + input);
			result = null;
		}
		return result;
	}

	public static long getCollectionSize(ArrayList<ArrayList<SdqlNode>> nodeCollection) {
		if (nodeCollection == null) {
			return 0;
		}
		long count = 0;
		for (ArrayList<SdqlNode> nodes : nodeCollection) {
			count += nodes.size();
		}
		return count;
	}
	
	/**
	 * Given two array list collections of sdql nodes, this function returns the
	 * duplicate nodes, present in the two collections
	 * 
	 * @param currentNodesCollection       First collection of list of sdql nodes.
	 * @param newQualifyingNodesCollection Second collection of sdql nodes.
	 * @return It returns the duplicate nodes in two sorted lists as a collection.
	 */
	public static ArrayList<ArrayList<SdqlNode>> getDuplicatesFromCollection(
			ArrayList<ArrayList<SdqlNode>> currentNodesCollection,
			ArrayList<ArrayList<SdqlNode>> newQualifyingNodesCollection) {

		if (currentNodesCollection == null || currentNodesCollection.isEmpty()) {
			return new ArrayList<>();
		}

		if (newQualifyingNodesCollection == null || newQualifyingNodesCollection.isEmpty()) {
			return new ArrayList<>();
		}

		ArrayList<ArrayList<SdqlNode>> resultCollection = new ArrayList<>();
		Set<SdqlNode> currentNodeSet = new HashSet<>();

		for (ArrayList<SdqlNode> currentNodes : currentNodesCollection) {
			for (SdqlNode sdqlNode : currentNodes) {
				currentNodeSet.add(sdqlNode);
			}
		}

		for (ArrayList<SdqlNode> newQualifyingNodes : newQualifyingNodesCollection) {
			ArrayList<SdqlNode> resultNodes = new ArrayList<>();
			for (SdqlNode sdqlNode : newQualifyingNodes) {
				if (currentNodeSet.contains(sdqlNode)) {
					resultNodes.add(sdqlNode);
					currentNodeSet.remove(sdqlNode);
				}
			}
			if (!resultNodes.isEmpty()) {
				resultCollection.add(resultNodes);
			}
		}

		return resultCollection;
	}
	
	public static  ArrayList<ArrayList<SdqlNode>> binarySearchCurrentNodes(ArrayList<ArrayList<SdqlNode>> prevColumnCollection,
			ArrayList<ArrayList<SdqlNode>> currentColumnNodesCollection) {

		ArrayList<ArrayList<SdqlNode>> searchResultNodesCollection = new ArrayList<>();
		Set<SdqlNode> nodesAdded = new HashSet<>();

		for (ArrayList<SdqlNode> currentColumnNodes : currentColumnNodesCollection) {
			ArrayList<SdqlNode> searchResultNodes = new ArrayList<>();
			for (SdqlNode sdqlNode : currentColumnNodes) {
				if (nodesAdded.contains(sdqlNode)) {
					continue;
				}
				nodesAdded.add(sdqlNode);
				for (ArrayList<SdqlNode> prevNodes : prevColumnCollection) {
					if (isContained(prevNodes, sdqlNode)) {
						searchResultNodes.add(sdqlNode);
						break;
					}
				}
			}
			if (!searchResultNodes.isEmpty()) {
				searchResultNodesCollection.add(searchResultNodes);
			}

		}

		return searchResultNodesCollection;

	}
	
	private static boolean isContained(ArrayList<SdqlNode> prevColumnNodes, SdqlNode sdqlNode) {

		int left = 0;
		int right = prevColumnNodes.size() - 1;

		boolean isContained = false;

		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (sdqlNode.getLowerBound() < prevColumnNodes.get(mid).getLowerBound()) {
				right = mid - 1;
				continue;
			} else if (sdqlNode.getUpperBound() < prevColumnNodes.get(mid).getUpperBound()) {
				isContained = true;
				break;
			} else if (sdqlNode.getUpperBound() > prevColumnNodes.get(mid).getUpperBound()) {
				left = mid + 1;
				continue;
			}
		}

		return isContained;

	}
	
	public static ArrayList<ArrayList<SdqlNode>> inverseBinarySearchCurrentNodes(
			ArrayList<ArrayList<SdqlNode>> prevColumnNodesCollection,
			ArrayList<ArrayList<SdqlNode>> currentColumnNodesCollection) {

		ArrayList<ArrayList<SdqlNode>> searchResultNodesCollection = new ArrayList<>();
		Set<SdqlNode> nodesAdded = new HashSet<>();

		for (ArrayList<SdqlNode> prevColumnNodes : prevColumnNodesCollection) {
			ArrayList<SdqlNode> searchResultNodes = new ArrayList<>();
			for (SdqlNode sdqlNode : prevColumnNodes) {
				if (nodesAdded.contains(sdqlNode)) {
					continue;
				}
				nodesAdded.add(sdqlNode);
				ArrayList<SdqlNode> prevQualifyingNodes = new ArrayList<>();
				for (ArrayList<SdqlNode> currentColumnNodes : currentColumnNodesCollection) {
					ArrayList<SdqlNode> currentQualifyingNodes = new ArrayList<>();
					addAllContainedNodes(currentColumnNodes, sdqlNode, currentQualifyingNodes);
					if (prevQualifyingNodes.isEmpty()) {
						prevQualifyingNodes = currentQualifyingNodes;
					} else {
						prevQualifyingNodes = mergeIndexNodes(prevQualifyingNodes, currentQualifyingNodes);
					}
				}

				if (!prevQualifyingNodes.isEmpty()) {
					searchResultNodes.addAll(prevQualifyingNodes);
				}
			}
			if (!searchResultNodes.isEmpty()) {
				searchResultNodesCollection.add(searchResultNodes);
			}
		}

		return searchResultNodesCollection;

	}

	private static void addAllContainedNodes(ArrayList<SdqlNode> currentColumnNodes, SdqlNode prevNode,
			ArrayList<SdqlNode> searchResultNodes) {

		int left = 0;
		int right = currentColumnNodes.size() - 1;

		int lowerIndex = -1;
		int upperIndex = -1;

		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (prevNode.getLowerBound() > currentColumnNodes.get(mid).getLowerBound()) {
				left = mid + 1;
				continue;
			} else if (prevNode.getLowerBound() < currentColumnNodes.get(mid).getLowerBound()
					&& prevNode.getUpperBound() < currentColumnNodes.get(mid).getLowerBound()) {
				right = mid - 1;
				continue;
			} else if (doesContain(prevNode, currentColumnNodes.get(mid))) {
				lowerIndex = mid;
				upperIndex = mid;

				int i = mid - 1;
				while (i >= 0) {
					if (doesContain(prevNode, currentColumnNodes.get(i))) {
						lowerIndex = i;
					} else {
						break;
					}

					i--;
				}

				i = mid + 1;

				while (i <= currentColumnNodes.size() - 1) {
					if (doesContain(prevNode, currentColumnNodes.get(i))) {
						upperIndex = i;
					} else {
						break;
					}

					i++;
				}
				break;
			}
		}

		if (lowerIndex != -1) {
			for (int i = lowerIndex; i <= upperIndex; i++) {
				searchResultNodes.add(currentColumnNodes.get(i));
			}
		}
	}
	


	private static boolean doesContain(SdqlNode prevNode, SdqlNode currentNode) {
		return prevNode.getLowerBound() < currentNode.getLowerBound()
				&& prevNode.getUpperBound() > currentNode.getUpperBound();
	}
	
	/**
	 * This function takes two sorted lists of Sdql Nodes and returns a merged and
	 * sorted list.
	 *
	 * @param currentValue nodes is the present, merged sorted list of nodes
	 *                     satisfying conditions so far.
	 * @param nextValue    nodes is the next qualifying nodes matching the next
	 *                     criteria. This will be merged with current value nodes
	 * @return This functions returns the sorted merged list of currentValue Nodes
	 *         and nextValue nodes. Sorting is done based on lower bound and upper
	 *         bound number of the node.
	 */

	public static ArrayList<SdqlNode> mergeIndexNodes(ArrayList<SdqlNode> currentValue, ArrayList<SdqlNode> nextValue) {

		if (currentValue == null) {
			currentValue = new ArrayList<>();
		}

		if (nextValue == null) {
			nextValue = new ArrayList<>();
		}

		ArrayList<SdqlNode> mergedList = new ArrayList<SdqlNode>(currentValue.size() + nextValue.size());

		int currentValuePointer = 0;
		int nextValuePointer = 0;

		while (currentValuePointer < currentValue.size() || nextValuePointer < nextValue.size()) {

			if (currentValuePointer >= currentValue.size()) {
				mergedList.add(nextValue.get(nextValuePointer));
				nextValuePointer++;
				continue;
			} else if (nextValuePointer >= nextValue.size()) {
				mergedList.add(currentValue.get(currentValuePointer));
				currentValuePointer++;
				continue;
			}

			if (currentValue.get(currentValuePointer).getLowerBound() <= nextValue.get(nextValuePointer)
					.getLowerBound()) {
				mergedList.add(currentValue.get(currentValuePointer));
				if (currentValue.get(currentValuePointer).getLowerBound() == nextValue.get(nextValuePointer)
						.getLowerBound()) {
					nextValuePointer++;
				}
				currentValuePointer++;
			} else {
				mergedList.add(nextValue.get(nextValuePointer));
				nextValuePointer++;
			}

		}

		return mergedList;

	}
}

