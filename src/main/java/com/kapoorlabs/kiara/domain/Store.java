package com.kapoorlabs.kiara.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kapoorlabs.kiara.constants.ExceptionConstants;
import com.kapoorlabs.kiara.exception.EmptyColumnException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the data store that was build by a particular schema.
 * Once the data store is loaded, inverted indexes are created for each
 * field/column, so that faster searching operations can be performed. Although
 * we have 3 different types of indexes, i.e 1) invertedIndex 2)
 * invertedNumericIndex and 3) ranges, only one type of index will be created
 * for a particular field/column, depending upon its data type and rest will be
 * null.
 * 
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Data
public class Store {

	/**
	 * List of Sdql columns and metadata extracted from POJO schema
	 */
	private SdqlColumn[] sdqlColumns;

	/**
	 * A HashMap that links field/column name to its index position in sdqlColumns
	 * array.
	 */
	private Map<String, Integer> columnIndex;

	/**
	 * Inverted Index for string type
	 */
	private ArrayList<HashMap<String, ArrayList<SdqlNode>>> invertedIndex;

	/**
	 * Ordered list of keys composing invertedIndex
	 */
	private ArrayList<OrderedKeys<NullableOrderedString>> invertedIndexKeys;

	/**
	 * Inverted Index for numeric type
	 */
	private ArrayList<HashMap<Long, ArrayList<SdqlNode>>> invertedNumericIndex;

	/**
	 * Ordered list of keys composing invertedNumericIndexKeys
	 */
	private ArrayList<OrderedKeys<Long>> invertedNumericIndexKeys;

	/**
	 * Interval tree for ranges type
	 */
	private ArrayList<HashMap<String, SearchableRange>> ranges;
	
	/**
	 * Inverted Full key Index for collection type
	 */
	private ArrayList<HashMap<String, ArrayList<SdqlNode>>> collectionFullKeyIndex;


	public Store(List<SdqlColumn> sdqlColumns) {

		if (sdqlColumns == null || sdqlColumns.isEmpty()) {
			log.error(ExceptionConstants.EMPTY_COLUMN_STORE);
			throw new EmptyColumnException(ExceptionConstants.EMPTY_COLUMN_STORE);
		}

		this.sdqlColumns = sdqlColumns.toArray(new SdqlColumn[sdqlColumns.size()]);
		this.columnIndex = new HashMap<>();
		this.invertedIndex = new ArrayList<>();
		this.invertedNumericIndex = new ArrayList<>();
		this.invertedIndexKeys = new ArrayList<>();
		this.invertedNumericIndexKeys = new ArrayList<>();
		this.collectionFullKeyIndex = new ArrayList<>();
		this.ranges = new ArrayList<>();

		for (int i = 0; i < this.sdqlColumns.length; i++) {
			HashMap<String, ArrayList<SdqlNode>> columnIndex = new HashMap<>();
			HashMap<Long, ArrayList<SdqlNode>> columnNumericIndex = new HashMap<>();
			OrderedKeys<NullableOrderedString> orderedStringkeys = new OrderedKeys<>();
			OrderedKeys<Long> orderedNumerickeys = new OrderedKeys<>();
			HashMap<String, SearchableRange> rangeMap = new HashMap<>();
			HashMap<String, ArrayList<SdqlNode>> fullKeyIndex = new HashMap<>();
			this.invertedIndex.add(columnIndex);
			this.invertedNumericIndex.add(columnNumericIndex);
			this.invertedIndexKeys.add(orderedStringkeys);
			this.invertedNumericIndexKeys.add(orderedNumerickeys);
			this.collectionFullKeyIndex.add(fullKeyIndex);
			this.ranges.add(rangeMap);
			this.columnIndex.put(this.sdqlColumns[i].getColumnName(), i);
		}
	}

}
