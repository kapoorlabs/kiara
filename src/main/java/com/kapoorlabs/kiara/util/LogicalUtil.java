package com.kapoorlabs.kiara.util;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.SecondaryCollectionDataType;
import com.kapoorlabs.kiara.domain.SecondarySingleDataType;
import com.kapoorlabs.kiara.domain.Store;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			log.error("Error while transforming, number expected, but got string for :" + input);
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
			log.error("Error while transforming, number expected, but got string for :" + input);
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
			log.error("Error while transforming, double expected, but got string for :" + input);
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
			log.error("Error while transforming, boolean expected, but got string for :" + input);
			result = null;
		}
		return result;
	}

}
