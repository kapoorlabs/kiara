package com.kapoorlabs.kiara.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.kapoorlabs.kiara.constants.Delimiters;
import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.SecondaryCollectionDataType;
import com.kapoorlabs.kiara.domain.SecondarySingleDataType;
import com.kapoorlabs.kiara.domain.Store;

public class NumericUtil {

	public static long[] getNumericValues(Store store, String value, int colIndex) {

		String[] values = value.split(Delimiters.COMMA);
		long[] longValues = new long[values.length];

		for (int i = 0; i < longValues.length; i++) {
			longValues[i] = getNumericValue(store, values[i], colIndex);
		}

		return longValues;
	}

	/**
	 * This function parses the string value into a numeric value based on the
	 * annotated type.
	 * <p>
	 * Any string value can be annotated
	 * as @CommaSeperatedNumbers, @CommaSeperatedDates, @CommaSeperatedDateTimes, @DateFormat
	 * or @DateTimeFormat In all such cases the string value must be converted to a
	 * long value, that represents the string. Dates are converted to Epoch number.
	 *
	 * @param store    This argument specifies the store we are using. Each store
	 *                 has its own Java object, that describes its data structure.
	 * @param value    This argument is the string value that needs to be converted
	 *                 to its numeric value.
	 * @param colIndex This column specifies the index of the column in sdqlColumns
	 *                 array of the store, whose value is represented by the value
	 *                 argument
	 * @return It returns the numeric value of string passed in the argument,
	 *         considering the data type of the column.
	 */
	public static Long getNumericValue(Store store, String value, int colIndex) {

		if (value == null || value.equals(SdqlConstants.NULL) || value.isEmpty()) {
			return SdqlConstants.LONG_NULL;
		}

		if (store.getSdqlColumns()[colIndex].isNumeric()) {
			return ((Double) Double.parseDouble(value)).longValue();
		}

		if (store.getSdqlColumns()[colIndex].getSecondaryType() != null && (store.getSdqlColumns()[colIndex]
				.getSecondaryType().getSecondaryCollectionType() == SecondaryCollectionDataType.DATE
				|| store.getSdqlColumns()[colIndex].getSecondaryType()
						.getSecondarySingleType() == SecondarySingleDataType.DATE)) {

			try {
				return LocalDate.parse(value).toEpochDay();
			} catch (Exception ex) {
				String dateFormat = store.getSdqlColumns()[colIndex].getSecondaryType().getFormat();
				return getNumericValueFromDate(value, dateFormat);
			}

		}

		if (store.getSdqlColumns()[colIndex].getSecondaryType() != null && (store.getSdqlColumns()[colIndex]
				.getSecondaryType().getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_TIME
				|| store.getSdqlColumns()[colIndex].getSecondaryType()
						.getSecondarySingleType() == SecondarySingleDataType.DATE_TIME)) {

			try {
				return LocalDateTime.parse(value).toEpochSecond(ZoneOffset.of("Z"));
			} catch (Exception ex) {
				String dateTimeFormat = store.getSdqlColumns()[colIndex].getSecondaryType().getFormat();
				return getNumericValueFromDateTime(value, dateTimeFormat);
			}

		}

		if (store.getSdqlColumns()[colIndex].getSecondaryType() != null && store.getSdqlColumns()[colIndex]
				.getSecondaryType().getSecondaryCollectionType() == SecondaryCollectionDataType.NUMBER) {

			return ((Double) Double.parseDouble(value)).longValue();

		}

		return null;

	}

	public static Long getNumericValueFromDate(String value, String dateFormat) {
		if (dateFormat == null || dateFormat.isEmpty()) {
			dateFormat = SdqlConstants.DEFAULT_DATE_FORMAT;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		return LocalDate.parse(value, formatter).toEpochDay();
	}

	public static Long getNumericValueFromDateTime(String value, String dateTimeFormat) {
		if (dateTimeFormat == null || dateTimeFormat.isEmpty()) {
			dateTimeFormat = SdqlConstants.DEFAULT_DATE_TIME_FORMAT;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
		return LocalDateTime.parse(value, formatter).toEpochSecond(ZoneOffset.of("Z"));
	}

}
