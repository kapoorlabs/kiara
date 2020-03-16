package com.kapoorlabs.kiara.adapters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.SdqlColumn;
import com.kapoorlabs.kiara.domain.SecondaryCollectionDataType;
import com.kapoorlabs.kiara.domain.SecondarySingleDataType;
import com.kapoorlabs.kiara.domain.SecondaryType;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateRanges;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateTimeRanges;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDateTimes;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedDates;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedNumbers;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedNumericRanges;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.DateFormat;
import com.kapoorlabs.kiara.domain.annotations.DateRange;
import com.kapoorlabs.kiara.domain.annotations.DateTimeFormat;
import com.kapoorlabs.kiara.domain.annotations.DateTimeRange;
import com.kapoorlabs.kiara.domain.annotations.NumericRange;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provides static methods, that converts a POJO to kiara's in memory
 * schema, Each schema is a seperate store in memory.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */

@Slf4j
public class PojoAdapter {

	public static Set<String> allowedTypes;

	public static Set<String> numericTypes;

	/**
	 * This static block executes to populate allowedTypes and numericTypes sets.
	 * 
	 */
	static {

		allowedTypes = new HashSet<>();
		numericTypes = new HashSet<>();

		for (String type : SdqlConstants.ALLOWED_TYPES) {
			allowedTypes.add(type);
		}

		for (String type : SdqlConstants.NUMERIC_TYPES) {
			numericTypes.add(type);
		}
	}

	/**
	 * This function takes a POJO Object class as an argument and builds a list of
	 * SdqlColumn. Each SdqlColumn maps to a field in POJO and also stores some
	 * metadata about the field.
	 * <p>
	 * Type of fields that are not supported by Kiara will be ignored and logged as
	 * warnings.
	 * <p>
	 * Fields supported by Kiara includes :- "BYTE", "SHORT", "CHAR", "CHARACTER",
	 * "INT", "INTEGER", "LONG", "DOUBLE", "FLOAT", "STRING", "BOOLEAN"
	 * <p>
	 * 
	 * Kiara does support Lists and Arrays, but in a serialized form. For example,
	 * kiara can be configured to treat comma seperated values to either collection
	 * of strings or numbers.
	 * 
	 * @param pojoClass The POJO object, which is the schema for the store.
	 * @return List It returns a list of Sdql columns that store metadata foe all
	 *         the allowed fields that builds up schema.
	 */
	public static List<SdqlColumn> getSdqlColumns(Class<? extends Object> pojoClass) {

		if (pojoClass == null) {
			return null;
		}

		Method[] methods = pojoClass.getDeclaredMethods();

		List<SdqlColumn> sdqlColumns = new LinkedList<SdqlColumn>();

		for (Field field : pojoClass.getDeclaredFields()) {

			if (!allowedTypes.contains(field.getType().getSimpleName().toUpperCase())) {
				logIgnoredWarning(field.getName(),
						"Data type:- " + field.getType().getSimpleName() + " is not supported");
				continue;
			}

			SdqlColumn sdqlColumn = new SdqlColumn();
			sdqlColumn.setColumnName(field.getName().toUpperCase());

			for (int j = 0; j < methods.length; j++) {
				if ((methods[j].getName().toUpperCase().equals("GET" + sdqlColumn.getColumnName())
						|| methods[j].getName().toUpperCase().equals("IS" + sdqlColumn.getColumnName()))
						&& methods[j].getReturnType() != void.class && methods[j].getParameterTypes().length == 0) {
					sdqlColumn.setGetter(methods[j]);
				}
			}

			if (sdqlColumn.getGetter() == null) {
				logIgnoredWarning(field.getName(),
						"No getter function found for this field. Getter should follow getFieldName naming convention");
				continue;
			}

			if (isNumeric(field.getType().getSimpleName())) {
				sdqlColumn.setNumeric(true);
			} else {
				sdqlColumn.setNumeric(false);
			}

			for (Annotation anno : field.getAnnotations()) {
				if (anno.annotationType().equals(CommaSeperatedStrings.class)) {
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondaryCollectionType(SecondaryCollectionDataType.STRING);
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(CommaSeperatedNumbers.class)) {
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondaryCollectionType(SecondaryCollectionDataType.NUMBER);
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(CommaSeperatedDates.class)) {
					CommaSeperatedDates commaSeperatedDates = (CommaSeperatedDates) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondaryCollectionType(SecondaryCollectionDataType.DATE);
					secondaryType.setFormat(commaSeperatedDates.value());
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(CommaSeperatedDateTimes.class)) {
					CommaSeperatedDateTimes commaSeperatedDateTimes = (CommaSeperatedDateTimes) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondaryCollectionType(SecondaryCollectionDataType.DATE_TIME);
					secondaryType.setFormat(commaSeperatedDateTimes.value());
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(CommaSeperatedNumericRanges.class)) {
					CommaSeperatedNumericRanges commaSeperatedNumericRanges = (CommaSeperatedNumericRanges) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondaryCollectionType(SecondaryCollectionDataType.NUMERIC_RANGE);
					sdqlColumn.setSecondaryType(secondaryType);
					secondaryType.setNumericStartPos(commaSeperatedNumericRanges.numericStartPos() < 0 ? 0
							: commaSeperatedNumericRanges.numericStartPos());
				} else if (anno.annotationType().equals(CommaSeperatedDateRanges.class)) {
					CommaSeperatedDateRanges commaSeperatedDateRanges = (CommaSeperatedDateRanges) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondaryCollectionType(SecondaryCollectionDataType.DATE_RANGE);
					secondaryType.setFormat(commaSeperatedDateRanges.value());
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(CommaSeperatedDateTimeRanges.class)) {
					CommaSeperatedDateTimeRanges commaSeperatedDateTimeRanges = (CommaSeperatedDateTimeRanges) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondaryCollectionType(SecondaryCollectionDataType.DATE_TIME_RANGE);
					secondaryType.setFormat(commaSeperatedDateTimeRanges.value());
					sdqlColumn.setSecondaryType(secondaryType);
				}

				if (anno.annotationType().equals(DateFormat.class)) {
					DateFormat dateFormat = (DateFormat) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondarySingleType(SecondarySingleDataType.DATE);
					secondaryType.setFormat(dateFormat.value());
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(DateTimeFormat.class)) {
					DateTimeFormat dateTimeFormat = (DateTimeFormat) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondarySingleType(SecondarySingleDataType.DATE_TIME);
					secondaryType.setFormat(dateTimeFormat.value());
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(NumericRange.class)) {
					NumericRange numericRanges = (NumericRange) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondarySingleType(SecondarySingleDataType.NUMERIC_RANGE);
					secondaryType.setNumericStartPos(numericRanges.numericStartPos() < 0 ? 0
							: numericRanges.numericStartPos());
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(DateRange.class)) {
					DateRange dateRange = (DateRange) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondarySingleType(SecondarySingleDataType.DATE_RANGE);
					secondaryType.setFormat(dateRange.value());
					sdqlColumn.setSecondaryType(secondaryType);
				} else if (anno.annotationType().equals(DateTimeRange.class)) {
					DateTimeRange dateTimeRange = (DateTimeRange) anno;
					SecondaryType secondaryType = new SecondaryType();
					secondaryType.setSecondarySingleType(SecondarySingleDataType.DATE_TIME_RANGE);
					secondaryType.setFormat(dateTimeRange.value());
					sdqlColumn.setSecondaryType(secondaryType);
				}
			}

			sdqlColumns.add(sdqlColumn);

		}

		return sdqlColumns;

	}

	/**
	 * This function returns a boolean indicating if the type passed in argument is
	 * of numeric type. It returns null, if null is specified in the argument.
	 * <p>
	 * Numeric types include :- "DOUBLE", "FLOAT", "INT", "INTEGER", "LONG",
	 * "SHORT", "BYTE"
	 * 
	 * @param type This string argument represents the data type.
	 * @return It returns a boolean true, if the supplied argument matches any
	 *         numeric type.
	 */
	public static boolean isNumeric(String type) {
		return type == null ? false : numericTypes.contains(type.toUpperCase());
	}

	/**
	 * This is an internal function used only for logging warnings
	 */
	private static void logIgnoredWarning(String name, String reason) {

		log.error(name + " field was ignored, reason: " + reason);

	}

}
