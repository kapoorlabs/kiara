package com.kapoorlabs.kiara.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * This class takes a Comparable Data type and provides methods that can
 * efficiently perform operations such as less than, less than equal, greater
 * than, greater than equal, between and equal operations.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
@Data
public class OrderedKeys<T extends Comparable<T>> {

	ArrayList<T> keyList;

	public OrderedKeys() {
		keyList = new ArrayList<>();
	}

	/**
	 * This method inserts a new value in the data structure. Duplicate values are
	 * not allowed.
	 * 
	 * @param key This method will append this key argument in the data structure.
	 */
	public void insertKey(T key) {
		keyList.add(key);
	}

	/**
	 * This method will prepare the data structure to be searched. To prepare the
	 * data structure will be sorted in ascending order.
	 * 
	 */
	public void prepareForSearch() {
		Collections.sort(keyList);
	}

	/**
	 * This method returns all values less than a specified value in argument
	 * 
	 * @param upperRange This method will return all values less than the upper
	 *                   range.
	 * @return List This method will return a list of objects in the data structure
	 *         that satisfy the condition of &lt; argument's value
	 */
	public List<T> getAllLessThan(T upperRange) {

		List<T> result = new LinkedList<>();

		for (int i = 0; i < keyList.size(); i++) {
			if (keyList.get(i).compareTo(upperRange) >= 0) {
				break;
			}
			result.add(keyList.get(i));
		}

		return result;

	}

	/**
	 * This method returns all values less than equal a specified value in argument
	 * 
	 * @param upperRange This method will return all values less than equal to the
	 *                   upper range.
	 * @return List This method will return a list of objects in the data structure
	 *         that satisfy the condition of &lt;= argument's value
	 */
	public List<T> getAllLessThanEqual(T upperRange) {

		List<T> result = new LinkedList<>();

		for (int i = 0; i < keyList.size(); i++) {
			if (keyList.get(i).compareTo(upperRange) > 0) {
				break;
			}
			result.add(keyList.get(i));
		}

		return result;

	}

	/**
	 * This method returns all values greater than equal a specified value in
	 * argument
	 * 
	 * @param lowerRange This method will return all values greater than equal to
	 *                   the lowerRange.
	 * @return List This method will return a list of objects in the data structure
	 *         that satisfy the condition of &gt;= argument's value
	 */
	public List<T> getAllGreaterThanEqual(T lowerRange) {

		List<T> result = new LinkedList<>();

		for (int i = keyList.size() - 1; i >= 0; i--) {
			if (keyList.get(i).compareTo(lowerRange) < 0) {
				break;
			}
			result.add(keyList.get(i));
		}

		return result;

	}

	/**
	 * This method returns all values greater than a specified value in argument
	 * 
	 * @param lowerRange This method will return all values greater than the
	 *                   lowerRange
	 * @return List This method will return a list of objects in the data structure
	 *         that satisfy the condition of &gt; argument's value
	 */
	public List<T> getAllGreaterThan(T lowerRange) {

		List<T> result = new LinkedList<>();

		for (int i = keyList.size() - 1; i >= 0; i--) {
			if (keyList.get(i).compareTo(lowerRange) <= 0) {
				break;
			}
			result.add(keyList.get(i));
		}

		return result;

	}

	/**
	 * This method returns all values that falls in between lowerRange and
	 * upperRange, including both lowerRange and upperRange value.
	 * 
	 * @param lowerRange This method will return all values greater than equal the
	 *                   lower range.
	 * @param upperRange This method will return all values less than equal to the
	 *                   upper range.
	 * @return List This method will return a list of objects in the data structure
	 *         that satisfy the condition of lowerRange &gt;= argument's value &lt;=
	 *         upperRange
	 */
	public List<T> getAllBetween(T lowerRange, T upperRange) {

		List<T> result = new LinkedList<>();

		int lowerIndex = binarySearch(lowerRange);

		for (int i = lowerIndex; i < keyList.size(); i++) {
			if (keyList.get(i).compareTo(upperRange) > 0) {
				break;
			}
			result.add(keyList.get(i));
		}

		return result;

	}

	/**
	 * This method binary searches for a value in the sorted array. If the value is
	 * not found, it returns the index where the item should be inserted.
	 * 
	 * @param value This method searches for this value in the sorted array.
	 * 
	 * @return int This method returns the index, where the value specified in the
	 *         argument is present or should be added
	 */
	private int binarySearch(T value) {

		int left = 0;
		int right = keyList.size() - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (keyList.get(mid).compareTo(value) > 0) {
				right = mid - 1;
				continue;

			} else if (keyList.get(mid).compareTo(value) < 0) {
				left = mid + 1;
				continue;

			} else {
				return mid;
			}
		}

		return left;

	}

}
