package com.kapoorlabs.kiara.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * This class represents an interval tree, that can be used to efficiently
 * search range objects. The array list object is only used at loading time,
 * when data is being prepared for search, arraylist will be converted to a tree
 * and null will be assigned to array;list to free up the space.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
@Data
public class SearchableRange {

	/**
	 * This field is only used during load time. When converted to tree, it will be
	 * null
	 */
	ArrayList<Range> rangeArray;

	/**
	 * This represents an interval tree, which is created from a sorted arraylist to
	 * make sure it is balanced. Once the balanced tree is created, arraylist is
	 * discarded.
	 */
	RangeTreeElement treeRoot;

	/**
	 * This class represents an interval tree element, which is binary search tree
	 * sorted by lower range values. Each element also stores the maximum value,
	 * which is the maximum value of higher range values, that may be present in
	 * either left or right children of this element. The maximum value helps in
	 * determining if the children of this node should be searched or not.
	 */
	@Data
	private class RangeTreeElement {

		Range value;

		RangeTreeElement leftChild;

		RangeTreeElement rightChild;

		Long max;

	}

	public SearchableRange() {
		this.rangeArray = new ArrayList<>();
	}

	/**
	 * This method just takes a range element and appends it to the end of the
	 * Arraylist. Arraylist will be sorted afterwards.
	 * 
	 * @param range This method just takes the range attribute and appends it to the
	 *              array.
	 */
	public void insert(Range range) {
		this.rangeArray.add(range);
	}

	/**
	 * This method takes the array of ranges, sorts it and convert it into an
	 * interval tree. Once the tree is created, arraylist is discarded.
	 * 
	 */
	public void prepareForSearch() {
		Collections.sort(rangeArray);
		treeRoot = convertToTree(0, rangeArray.size() - 1);
		this.rangeArray = null;
	}

	/**
	 * This method takes the sorted arraylist and converts it into a balanced
	 * interval tree
	 */
	private RangeTreeElement convertToTree(int start, int end) {
		if (end < start) {
			return null;
		}
		int mid = start + (end - start) / 2;

		RangeTreeElement rangeTreeElement = new RangeTreeElement();
		rangeTreeElement.setValue(rangeArray.get(mid));
		rangeTreeElement.setLeftChild(convertToTree(start, mid - 1));
		rangeTreeElement.setRightChild(convertToTree(mid + 1, end));

		Long leftMax = rangeTreeElement.getLeftChild() == null ? null
				: getMax(rangeTreeElement.getLeftChild().value.getUpperLimit(),
						rangeTreeElement.getLeftChild().getMax());

		Long rightMax = rangeTreeElement.getRightChild() == null ? null
				: getMax(rangeTreeElement.getRightChild().value.getUpperLimit(),
						rangeTreeElement.getRightChild().getMax());

		rangeTreeElement.setMax(getMax(leftMax, rightMax));

		return rangeTreeElement;
	}

	/**
	 * This method takes in a long number and returns a list of ranges, in which the
	 * number falls into.
	 * 
	 * @param number The long number that has to be searched in the ranges.
	 * 
	 * @return List   List of ranges, that the number being searched, falls into. It
	 *               returns an empty list, if no range is found, that contains the
	 *               number being searched.
	 * 
	 */
	public List<Range> getInRange(Long number) {

		List<Range> result = new LinkedList<>();

		searchTree(treeRoot, number, result);

		return result;
	}

	/**
	 * This method searches the interval tree recursively and enter ranges that
	 * satisfy the condition in the result set. Any subtree than can be discarded
	 * from the search operation is not searched.
	 * 
	 * @param treeRoot This param is the root element of the tree, as the recursive
	 *                 search progresses, this param will be the root of subtree
	 *                 that has to be searched.
	 * 
	 * @param number   The long number that has to be searched in the ranges.
	 * 
	 * @param result   The list of ranges, that the number satisfies.
	 * 
	 */
	private void searchTree(RangeTreeElement treeRoot, Long number, List<Range> result) {
		if (treeRoot != null) {
			if (number < treeRoot.getValue().getLowerLimit()) {
				searchTree(treeRoot.getLeftChild(), number, result);
			} else {
				if (number >= treeRoot.getValue().getLowerLimit() && number <= treeRoot.getValue().getUpperLimit()) {
					result.add(treeRoot.getValue());
				}
				if (treeRoot.getMax() != null && number <= treeRoot.getMax()) {
					searchTree(treeRoot.getLeftChild(), number, result);
					searchTree(treeRoot.getRightChild(), number, result);
				}

			}

		}

	}

	/**
	 * This method takes two Long objects and returns the greater of two considering
	 * null values
	 * 
	 * @param first  first long object.
	 * @param second second long object.
	 * 
	 * @return Long It returns the value greater than either first or second, Null
	 *         value is treated as the Minimum value the number can have.
	 * 
	 */
	private Long getMax(Long first, Long second) {
		if (first == null && second == null) {
			return null;
		} else if (first == null) {
			return second;
		} else if (second == null) {
			return first;
		}

		return Math.max(first, second);

	}

}
