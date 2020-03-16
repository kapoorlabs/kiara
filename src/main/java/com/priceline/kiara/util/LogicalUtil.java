package com.priceline.kiara.util;

import com.priceline.kiara.domain.Condition;
import com.priceline.kiara.domain.SecondaryCollectionDataType;
import com.priceline.kiara.domain.SecondarySingleDataType;
import com.priceline.kiara.domain.Store;

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

}
