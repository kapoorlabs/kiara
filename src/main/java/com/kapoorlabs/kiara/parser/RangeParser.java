package com.kapoorlabs.kiara.parser;

import com.kapoorlabs.kiara.constants.Delimiters;
import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Range;
import com.kapoorlabs.kiara.domain.SecondaryCollectionDataType;
import com.kapoorlabs.kiara.domain.SecondarySingleDataType;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.RangeOutOfOrderException;
import com.kapoorlabs.kiara.exception.UnknownRangeFormatException;
import com.kapoorlabs.kiara.util.NumericUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provides static methods to parse ranges. Ranges can be of two
 * types, numeric range or a date time range. Numeric ranges can also have an
 * alphanumeric prefix, such as AA1-10, here AA is the prefix and 1-10 is the
 * range.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
public class RangeParser {

	/**
	 * This method takes 3 arguments, the value, store and the column index for
	 * which the value is intended, and returns the parsed range object.
	 * 
	 * @param inputStr The string argument, that is to be parsed.
	 * @param store    The store, that was built from POJO schema.
	 * @param colIndex This argument is the index of column in the store, to whom
	 *                 this inputStr value belongs.
	 * @return Range It returns the parsed range object.
	 * @throws RangeOutOfOrderException    It throws this exception when higher
	 *                                     range value is less than lower range
	 *                                     value. It can throw this exception both
	 *                                     at load or search time.
	 * @throws UnknownRangeFormatException It throws this exception for date or date
	 *                                     time ranges, the length of the range does
	 *                                     not match the date's format length. It
	 *                                     can throw this exception both at load or
	 *                                     search time.
	 */
	public static Range parseRange(String inputStr, Store store, int colIndex) {

		Range range = new Range();

		if (store == null || colIndex < 0 || colIndex >= store.getSdqlColumns().length) {
			return null;
		}
		
		int numericStartIndex = store.getSdqlColumns()[colIndex].getSecondaryType().getNumericStartPos();


		if (store.getSdqlColumns()[colIndex].getSecondaryType()
				.getSecondaryCollectionType() == SecondaryCollectionDataType.NUMERIC_RANGE
				|| store.getSdqlColumns()[colIndex].getSecondaryType()
						.getSecondarySingleType() == SecondarySingleDataType.NUMERIC_RANGE) {
			
			if (inputStr == null || inputStr.isEmpty() || inputStr.equalsIgnoreCase(SdqlConstants.NULL)) {
				range.setPrefix(SdqlConstants.NULL);
				range.setLowerLimit(SdqlConstants.LONG_NULL);
				range.setUpperLimit(SdqlConstants.LONG_NULL);
				return range;
			}

			int indexOfRangeDelimiter = inputStr.lastIndexOf(Delimiters.RANGE_DELIMITER);

			if (indexOfRangeDelimiter < numericStartIndex) {
				indexOfRangeDelimiter = inputStr.length();
			}

			range = getLowerHalf(inputStr, indexOfRangeDelimiter - 1, numericStartIndex);

			Long upperLimit = getHigherNumber(inputStr, indexOfRangeDelimiter + 1);

			if (upperLimit != null) {
				range.setUpperLimit(upperLimit);
			} else if (range.getLowerLimit() != null) {
				range.setUpperLimit(range.getLowerLimit());
			}

		} else {
			
			if (inputStr == null || inputStr.isEmpty() || inputStr.equalsIgnoreCase(SdqlConstants.NULL)) {
				range.setPrefix("");
				range.setLowerLimit(SdqlConstants.LONG_NULL);
				range.setUpperLimit(SdqlConstants.LONG_NULL);
				return range;
			}
			
			String format = store.getSdqlColumns()[colIndex].getSecondaryType().getFormat();

			range.setPrefix("");

			if (inputStr.length() == format.length()) {

				Long value = getNumericRangeValue(inputStr, store, colIndex);
				range.setLowerLimit(value);
				range.setUpperLimit(value);

			} else if (inputStr.length() == (format.length() * 2) + 1) {

				String lowerStr = inputStr.substring(0, format.length());
				String upperStr = inputStr.substring(format.length() + 1);

				Long lowerValue = getNumericRangeValue(lowerStr, store, colIndex);
				Long higherValue = getNumericRangeValue(upperStr, store, colIndex);
				range.setLowerLimit(lowerValue);
				range.setUpperLimit(higherValue);
			}

			if (inputStr.toUpperCase().startsWith((SdqlConstants.NULL.toUpperCase()))
					&& inputStr.toUpperCase().endsWith((SdqlConstants.NULL.toUpperCase()))) {
				range.setLowerLimit(SdqlConstants.LONG_NULL);
				range.setUpperLimit(SdqlConstants.LONG_NULL);
			} else if (inputStr.toUpperCase().startsWith((SdqlConstants.NULL.toUpperCase()))) {
				range.setLowerLimit(SdqlConstants.LONG_NULL);
				String rightHalf = inputStr.substring(SdqlConstants.NULL.length() + 1);
				if (rightHalf.length() == format.length()) {
					Long higherValue = getNumericRangeValue(rightHalf, store, colIndex);
					range.setUpperLimit(higherValue);
				} else {
					throwFormatException(inputStr, format);
				}

			} else if (inputStr.length() != format.length() && inputStr.length() != (format.length() * 2) + 1) {
				throwFormatException(inputStr, format);
			}

		}

		if (range.getUpperLimit().longValue() < range.getLowerLimit().longValue()) {
			throwRangeOutOfOrderException(inputStr, range.getLowerLimit().longValue(),
					range.getUpperLimit().longValue());
		}

		return range;

	}

	private static void throwRangeOutOfOrderException(String inputStr, long lowerRange, long upperRange) {
		String message = "Higher range value is less than lower range for input: " + inputStr + " lower Range: "
				+ lowerRange + " higher range: " + upperRange;
		System.err.println(message);
		throw new RangeOutOfOrderException(message);
	}

	private static void throwFormatException(String inputStr, String format) {
		String message = "Unsupported range format for value: " + inputStr + " with format: " + format;
		System.err.println(message);
		throw new UnknownRangeFormatException(message);
	}

	private static Long getNumericRangeValue(String inputStr, Store store, int colIndex) {

		Long value = null;

		if (store.getSdqlColumns()[colIndex].getSecondaryType()
				.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_RANGE
				|| store.getSdqlColumns()[colIndex].getSecondaryType()
						.getSecondarySingleType() == SecondarySingleDataType.DATE_RANGE) {

			value = NumericUtil.getNumericValueFromDate(inputStr,
					store.getSdqlColumns()[colIndex].getSecondaryType().getFormat());

		} else if (store.getSdqlColumns()[colIndex].getSecondaryType()
				.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_TIME_RANGE
				|| store.getSdqlColumns()[colIndex].getSecondaryType()
						.getSecondarySingleType() == SecondarySingleDataType.DATE_TIME_RANGE) {

			value = NumericUtil.getNumericValueFromDateTime(inputStr,
					store.getSdqlColumns()[colIndex].getSecondaryType().getFormat());
		}

		return value;

	}

	private static Range getLowerHalf(String inputStr, int charIndex, int numericStartIndex) {
		Range range = new Range();
		char zeroChar = '0';
		long lowerLimit = SdqlConstants.LONG_NULL;
		long decimalPos = 1;
		if (inputStr == null || charIndex >= inputStr.length() || charIndex < 0) {
			range.setLowerLimit(lowerLimit);
			range.setPrefix("");
			return range;
		}

		while (charIndex >= numericStartIndex && inputStr.charAt(charIndex) - zeroChar >= 0
				&& inputStr.charAt(charIndex) - zeroChar <= 9) {
			if (lowerLimit == SdqlConstants.LONG_NULL) {
				lowerLimit = 0;
			}
			lowerLimit += decimalPos * (inputStr.charAt(charIndex) - zeroChar);
			decimalPos *= 10;
			charIndex--;
		}

		if (charIndex >= numericStartIndex && inputStr.charAt(charIndex) == '-' && lowerLimit > 0) {
			lowerLimit = lowerLimit * -1;
		}

		range.setLowerLimit(lowerLimit);
		range.setPrefix(inputStr.substring(0, ++charIndex));

		return range;
	}

	private static Long getHigherNumber(String inputStr, int charIndex) {
		char zeroChar = '0';
		Long upperLimit = null;
		int decimalPos = 10;
		int multiplier = 1;
		if (inputStr == null || charIndex >= inputStr.length() || charIndex < 0) {
			return upperLimit;
		}
		if (inputStr.charAt(charIndex) == '-') {
			multiplier = -1;
			charIndex++;
		}
		while (charIndex < inputStr.length() && inputStr.charAt(charIndex) - zeroChar >= 0
				&& inputStr.charAt(charIndex) - zeroChar <= 9) {
			if (upperLimit == null) {
				upperLimit = 0L;
			}
			upperLimit = decimalPos * upperLimit + (inputStr.charAt(charIndex) - zeroChar);
			charIndex++;
		}

		if (upperLimit != null) {
			upperLimit *= multiplier;
		}

		return upperLimit;
	}

}
