package com.kapoorlabs.kiara.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.exception.NonSupportedOperationException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents a single condition, that is used while searching data.
 * For example if you have 2 criterias while searching, such as city = "nyc" and
 * country = "US", then there are 2 Conditions, one for each criteria.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
@Data
public class Condition implements Comparable<Condition> {

	String columnName;

	Operator operator;

	List<String> value;

	String lowerValue;

	String upperValue;

	int columnIndex;

	/**
	 * Creates a range based condition lowerRange &lt;= column's value &lt;=
	 * upperRange.
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param operator   Only Between operator is allowed up to this version.
	 * @param lowerValue lower value of the range.
	 * @param upperValue upper Value of the range.
	 * 
	 * @throws NonSupportedOperationException Throws an
	 *                                        NonSupportedOperationException if any
	 *                                        operator other than EQUALS
	 *                                        ,CONTAINS_EITHER or CONTAINS_ALL is
	 *                                        used in range based data.
	 */
	public Condition(String columnName, Operator operator, Number lowerValue, Number upperValue) {

		if (operator != Operator.BETWEEN) {
			String message = "Only BETWEEN operation is allowed on lower and upper values.";
			System.err.println(message + "Column :" + columnName);
			throw new NonSupportedOperationException(message);
		}

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = operator;
		this.lowerValue = lowerValue == null ? SdqlConstants.NULL : lowerValue.toString();
		this.upperValue = upperValue == null ? SdqlConstants.NULL : upperValue.toString();

	}

	/**
	 * Creates a range based condition lowerRange &lt;= column's value &lt;=
	 * upperRange.
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param lowerValue lower value of the range.
	 * @param upperValue upper Value of the range.
	 * 
	 */
	public Condition(String columnName, Number lowerValue, Number upperValue) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = Operator.BETWEEN;
		this.lowerValue = lowerValue == null ? SdqlConstants.NULL : lowerValue.toString();
		this.upperValue = upperValue == null ? SdqlConstants.NULL : upperValue.toString();

	}

	/**
	 * Creates a range based condition lowerRange &lt;= column's value &lt;=
	 * upperRange.
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param operator   Only Between operator is allowed up to this version.
	 * @param lowerValue lower value of the range.
	 * @param upperValue upper Value of the range.
	 * 
	 * @throws NonSupportedOperationException Throws an
	 *                                        NonSupportedOperationException if any
	 *                                        operator other than EQUALS
	 *                                        ,CONTAINS_EITHER or CONTAINS_ALL is
	 *                                        used in range based data.
	 */
	public Condition(String columnName, Operator operator, String lowerValue, String upperValue) {

		if (operator != Operator.BETWEEN) {
			String message = "Only BETWEEN operation is allowed on lower and upper values.";
			System.err.println(message + "Column :" + columnName);
			throw new NonSupportedOperationException(message);
		}

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = operator;
		this.lowerValue = lowerValue == null || lowerValue.trim().isEmpty() ? SdqlConstants.NULL
				: lowerValue.toString();
		this.upperValue = upperValue == null || upperValue.trim().isEmpty() ? SdqlConstants.NULL
				: upperValue.toString();

	}

	/**
	 * Creates a range based condition lowerRange &lt;= column's value &lt;=
	 * upperRange.
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param lowerValue lower value of the range.
	 * @param upperValue upper Value of the range.
	 * 
	 */
	public Condition(String columnName, String lowerValue, String upperValue) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = Operator.BETWEEN;
		this.lowerValue = lowerValue == null || lowerValue.trim().isEmpty() ? SdqlConstants.NULL
				: lowerValue.toString();
		this.upperValue = upperValue == null || upperValue.trim().isEmpty() ? SdqlConstants.NULL
				: upperValue.toString();

	}

	/**
	 * Creates a condition for operations such as EQUALS, CONTAINS_EITHER,
	 * CONTAINS_ALL, LESS_THAN, LESS_THAN_EQUAL. GREATER_THAN, GREATER_THAN_EQUAL
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param operator   If operator is null, EQUAL operation is assumed by default.
	 * @param values     The list of values, the condition is built on.
	 * 
	 */

	public Condition(String columnName, Operator operator, Iterable<? extends Serializable> values) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = operator != null ? operator : Operator.EQUAL;
		this.value = getCleanedValues(values);

	}

	/**
	 * Creates a condition for EQUALS operation, whether the column is equaltTO a
	 * particular value
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param values     The list of values, the condition is built on. Only the
	 *                   first value in the list is compared
	 * 
	 */
	public Condition(String columnName, Iterable<? extends Serializable> values) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = Operator.EQUAL;
		this.value = getCleanedValues(values);

	}

	/**
	 * Creates a condition for EQUALS operation, whether the column is equaltTO a
	 * particular value
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param value      The value based on which the condition is built on.
	 * 
	 */
	public Condition(String columnName, Serializable value) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = Operator.EQUAL;
		List<String> values = new LinkedList<>();
		values.add(value == null || value.toString().trim().isEmpty() ? SdqlConstants.NULL : value.toString());
		this.value = values;

	}

	/**
	 * Creates a condition for EQUALS operation, whether the column is equaltTO a
	 * particular value
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param operator   If operator is null, EQUAL operation is assumed by default.
	 * @param value      The value based on which the condition is built on.
	 * 
	 */
	public Condition(String columnName, Operator operator, Serializable value) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = operator != null ? operator : Operator.EQUAL;
		List<String> values = new LinkedList<>();
		values.add(value == null || value.toString().trim().isEmpty() ? SdqlConstants.NULL : value.toString());
		this.value = values;

	}

	/**
	 * Creates a condition for operations such as EQUALS, CONTAINS_EITHER,
	 * CONTAINS_ALL, LESS_THAN, LESS_THAN_EQUAL. GREATER_THAN, GREATER_THAN_EQUAL
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param operator   If operator is null, EQUAL operation is assumed by default.
	 * @param valueArray The array of values based on which the condition is built
	 *                   on.
	 * 
	 */
	public Condition(String columnName, Operator operator, String[] valueArray) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = operator != null ? operator : Operator.EQUAL;
		this.value = getCleanedValues(valueArray);

	}

	/**
	 * Creates a condition for EQUALS operation, whether the column is equaltTO a
	 * particular value
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param valueArray The array of values, the condition is built on. Only the
	 *                   first value in array list is compared.
	 * 
	 */
	public Condition(String columnName, String[] valueArray) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = Operator.EQUAL;
		this.value = getCleanedValues(valueArray);

	}

	/**
	 * Creates a condition for operations such as EQUALS, CONTAINS_EITHER,
	 * CONTAINS_ALL, LESS_THAN, LESS_THAN_EQUAL. GREATER_THAN, GREATER_THAN_EQUAL
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param operator   If operator is null, EQUAL operation is assumed by default.
	 * @param valueArray The array of values based on which the condition is built
	 *                   on.
	 * 
	 */
	public Condition(String columnName, Operator operator, Number[] valueArray) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = operator != null ? operator : Operator.EQUAL;
		this.value = getCleanedValues(valueArray);

	}

	/**
	 * Creates a condition for EQUALS operation, whether the column is equal to a
	 * particular value
	 * 
	 * @param columnName Column's name or the SdqlfieldName on which condition is
	 *                   created
	 * @param valueArray The array of values, the condition is built on. Only the
	 *                   first value in array list is compared.
	 * 
	 */
	public Condition(String columnName, Number[] valueArray) {

		columnIndex = -1;
		this.columnName = columnName;
		this.operator = Operator.EQUAL;
		this.value = getCleanedValues(valueArray);

	}

	@Override
	public int compareTo(Condition o) {
		return this.columnIndex - o.columnIndex;
	}

	@Override
	public String toString() {
		return "Condition [columnName=" + columnName + ", operator=" + operator + ", value=" + value + ", lowerValue="
				+ lowerValue + ", upperValue=" + upperValue + ", columnIndex=" + columnIndex + "]";
	}

	private List<String> getCleanedValues(Iterable<? extends Serializable> values) {
		List<String> cleanedValues = new LinkedList<>();

		if (values == null) {
			cleanedValues.add(SdqlConstants.NULL);
		} else {
			for (Serializable value : values) {
				if (value == null || value.toString().trim().isEmpty()) {
					cleanedValues.add(SdqlConstants.NULL);
				} else {
					cleanedValues.add(value.toString());
				}
			}
		}
		return cleanedValues;
	}

	private List<String> getCleanedValues(String[] values) {
		List<String> cleanedValues = new LinkedList<>();

		if (values == null) {
			cleanedValues.add(SdqlConstants.NULL);
		} else {
			for (String value : values) {
				if (value == null) {
					cleanedValues.add(SdqlConstants.NULL);
				} else {
					cleanedValues.add(value);
				}
			}
		}
		return cleanedValues;
	}

	private List<String> getCleanedValues(Number[] values) {
		List<String> cleanedValues = new LinkedList<>();

		if (values == null) {
			cleanedValues.add(SdqlConstants.NULL);
		} else {
			for (Number value : values) {
				if (value == null) {
					cleanedValues.add(SdqlConstants.NULL);
				} else {
					cleanedValues.add(value.toString());
				}
			}
		}
		return cleanedValues;
	}

}
