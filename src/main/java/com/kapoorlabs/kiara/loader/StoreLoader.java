package com.kapoorlabs.kiara.loader;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import com.kapoorlabs.kiara.constants.Delimiters;
import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.NullableOrderedString;
import com.kapoorlabs.kiara.domain.OrderedKeys;
import com.kapoorlabs.kiara.domain.Range;
import com.kapoorlabs.kiara.domain.SdqlNode;
import com.kapoorlabs.kiara.domain.SearchableRange;
import com.kapoorlabs.kiara.domain.SecondaryCollectionDataType;
import com.kapoorlabs.kiara.domain.SecondarySingleDataType;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.EmptyColumnException;
import com.kapoorlabs.kiara.exception.IndexingException;
import com.kapoorlabs.kiara.exception.LoadDataException;
import com.kapoorlabs.kiara.parser.RangeParser;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreLoader {

	@Getter
	private SdqlNode trieRoot;

	@Getter
	private Store store;

	/**
	 * This function prepares the store for optimized searching, this includes
	 * indexing all the columns, sorting ordered keys and preparing interval trees.
	 * 
	 * @param store This takes store as an argument.
	 */
	public StoreLoader(Store store) {
		this.trieRoot = new SdqlNode("root", null);
		this.store = store;
	}

	/**
	 * This function loads a record into the in-memory table. The Table is stored in
	 * a de-depulicated Trie based structure.
	 * 
	 * <p>
	 * 
	 * If any record fails to load, it will be ignored, and the program will
	 * 
	 * @param pojo This argument represents the parent pojo object that represents a
	 *             record in the table or document.
	 * 
	 * 
	 * @throws LoadDataException It throws this exception when the load function
	 *                           fails to load an object in memory, primarily
	 *                           because the pojo object does not matches the class
	 *                           using which the store was created. or if the getter
	 *                           methods are not public.
	 */
	public void loadTable(Object pojo) throws LoadDataException {

		SdqlNode nextLevelParentNode = trieRoot;

		try {
			for (int level = 0; level < store.getSdqlColumns().length; level++) {
				SdqlNode currentNode = createNode(pojo, level);
				currentNode.setParent(nextLevelParentNode);
				nextLevelParentNode = getMatchingChild(nextLevelParentNode, currentNode);
			}
		} catch (Exception e) {
			String message = "Static data load failed for pojo: " + pojo;
			log.error(message);
			e.printStackTrace();
			throw new LoadDataException(message);
		}

	}

	/**
	 * This function prepares the store for optimized searching, this includes
	 * indexing all the columns, sorting ordered keys and preparing interval trees.
	 * 
	 * @param parentNode  This argument represents the parent node, whose children
	 *                    are to be searched
	 * 
	 * @param currentNode This argument represents the child node, which is being
	 *                    searched.
	 * @return SdqlNode It returns the SdqlNode that was found, if it was not
	 *         already found, the function creates a new node and link it to the
	 *         parent node.
	 */
	public SdqlNode getMatchingChild(SdqlNode parentNode, SdqlNode currentNode) {

		if (parentNode == null || currentNode == null) {
			throw new EmptyColumnException();
		}

		String key = currentNode.getDoubleValue() != null ? currentNode.getDoubleValue().toString()
				: currentNode.getStringValue().trim();

		if (parentNode.getChildren().get(key) == null) {
			parentNode.getChildren().put(key, currentNode);
		}

		return parentNode.getChildren().get(key);
	}

	/**
	 * This function creates a Node in the trie. Every column has its own level.
	 * 
	 * @param pojo  This object represents a single record of type POJO
	 * @param level This integer represent the level in the trie, for which node is
	 *              being created
	 * @return SdqlNode IT returns the Sdql node that is created by this function
	 * @throws IllegalAccessException    This exception is thrown if an error occurs
	 *                                   while accessing pojo element via reflection
	 *                                   api.
	 * @throws IllegalArgumentException  This exception is thrown if an error occurs
	 *                                   while accessing pojo element via reflection
	 *                                   api.
	 * @throws InvocationTargetException This exception is thrown if an error occurs
	 *                                   while accessing pojo element via reflection
	 *                                   api.
	 */
	public SdqlNode createNode(Object pojo, int level)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if (store == null || pojo == null) {
			log.error("Store or POJO cannot be null, while loading");
			throw new EmptyColumnException();
		}

		if (level < 0 || store.getSdqlColumns().length == 0 || level >= store.getSdqlColumns().length) {
			log.error("Column level is not in range");
			throw new EmptyColumnException();
		}

		Double numericValue = null;
		String stringValue = null;
		if (store.getSdqlColumns()[level].isNumeric()) {
			Object value = store.getSdqlColumns()[level].getGetter().invoke(pojo);
			if (value != null) {
				numericValue = Double.parseDouble(value.toString());
				if (numericValue.isNaN()) {
					numericValue = null;
				}
			}
			stringValue = numericValue != null ? String.format("%.2f", numericValue) : SdqlConstants.NULL;
		} else {
			numericValue = null;
			Object value = store.getSdqlColumns()[level].getGetter().invoke(pojo);
			stringValue = value != null && !value.toString().trim().isEmpty() ? value.toString().trim()
					: SdqlConstants.NULL;
		}

		SdqlNode sdqlNode = new SdqlNode(stringValue, numericValue);

		return sdqlNode;

	}

	/**
	 * This function prepares the store for optimized searching, this includes
	 * indexing all the columns, sorting ordered keys and preparing interval trees.
	 * 
	 * @throws IndexingException it throws this exception when the function is not
	 *                           able to create indexes for the store, due to data
	 *                           format issues.
	 */
	public void prepareForSearch() {

		int count = 0;

		int level = -1;

		try {

			performDfs(trieRoot, count, level);
			sortOrderedKeys();
			prepareIntervalTrees();

		} catch (Exception e) {
			String message = "Static data indexing failure";
			log.error(message);
			e.printStackTrace();
			throw new IndexingException(message);
		}

	}

	public int performDfs(SdqlNode node, int count, int level) {

		node.setLowerBound(count++);

		for (Entry<String, SdqlNode> childNodeEntry : node.getChildren().entrySet()) {
			count = performDfs(childNodeEntry.getValue(), count, level + 1);
		}

		node.setUpperBound(count++);

		if (level >= 0) {

			if (!store.getSdqlColumns()[level].isNumeric()) {
				String[] keys = null;
				Long[] longKeys = null;
				boolean isNumericType = false;
				boolean isRangeType = false;

				if (store.getSdqlColumns()[level].getSecondaryType() != null) {

					if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondaryCollectionType() == SecondaryCollectionDataType.STRING) {

						keys = node.getStringValue().split(Delimiters.COMMA);

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondaryCollectionType() == SecondaryCollectionDataType.NUMBER) {

						keys = node.getStringValue().split(Delimiters.COMMA);
						isNumericType = true;

						longKeys = new Long[keys.length];

						String stringKey = null;

						for (int i = 0; i < longKeys.length; i++) {
							stringKey = keys[i].trim();
							longKeys[i] = stringKey.equalsIgnoreCase(SdqlConstants.NULL) || stringKey.isEmpty()
									? SdqlConstants.LONG_NULL
									: ((Double)Double.parseDouble(stringKey)).longValue();
						}

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE) {

						keys = node.getStringValue().split(Delimiters.COMMA);
						isNumericType = true;

						longKeys = new Long[keys.length];
						String dateFormat = store.getSdqlColumns()[level].getSecondaryType().getFormat();

						if (dateFormat == null || dateFormat.isEmpty()) {
							dateFormat = SdqlConstants.DEFAULT_DATE_FORMAT;
						}

						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

						String stringKey = null;
						for (int i = 0; i < longKeys.length; i++) {
							stringKey = keys[i].trim();
							longKeys[i] = stringKey.equalsIgnoreCase(SdqlConstants.NULL) || stringKey.isEmpty()
									? SdqlConstants.LONG_NULL
									: LocalDate.parse(stringKey, formatter).toEpochDay();

						}

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_TIME) {

						keys = node.getStringValue().split(Delimiters.COMMA);
						isNumericType = true;

						longKeys = new Long[keys.length];
						String dateTimeFormat = store.getSdqlColumns()[level].getSecondaryType().getFormat();

						if (dateTimeFormat == null || dateTimeFormat.isEmpty()) {
							dateTimeFormat = SdqlConstants.DEFAULT_DATE_TIME_FORMAT;

						}

						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
						String stringKey = null;
						for (int i = 0; i < longKeys.length; i++) {
							stringKey = keys[i].trim();
							longKeys[i] = stringKey.equalsIgnoreCase(SdqlConstants.NULL) || stringKey.isEmpty()
									? SdqlConstants.LONG_NULL
									: LocalDateTime.parse(keys[i].trim(), formatter).toEpochSecond(ZoneOffset.of("Z"));
						}

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondaryCollectionType() == SecondaryCollectionDataType.NUMERIC_RANGE) {

						keys = node.getStringValue().split(Delimiters.COMMA);
						isRangeType = true;

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_RANGE) {

						keys = node.getStringValue().split(Delimiters.COMMA);
						isRangeType = true;

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondaryCollectionType() == SecondaryCollectionDataType.DATE_TIME_RANGE) {

						keys = node.getStringValue().split(Delimiters.COMMA);
						isRangeType = true;

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondarySingleType() == SecondarySingleDataType.DATE) {

						isNumericType = true;

						longKeys = new Long[1];
						String dateFormat = store.getSdqlColumns()[level].getSecondaryType().getFormat();

						if (dateFormat == null || dateFormat.isEmpty()) {
							dateFormat = SdqlConstants.DEFAULT_DATE_FORMAT;
						}
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
						String stringKey = node.getStringValue().trim();
						longKeys[0] = stringKey.equalsIgnoreCase(SdqlConstants.NULL) || stringKey.isEmpty()
								? SdqlConstants.LONG_NULL
								: LocalDate.parse(stringKey, formatter).toEpochDay();

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondarySingleType() == SecondarySingleDataType.DATE_TIME) {

						isNumericType = true;

						longKeys = new Long[1];
						String dateTimeFormat = store.getSdqlColumns()[level].getSecondaryType().getFormat();

						if (dateTimeFormat == null || dateTimeFormat.isEmpty()) {
							dateTimeFormat = SdqlConstants.DEFAULT_DATE_TIME_FORMAT;
						}
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
						String stringKey = node.getStringValue().trim();
						longKeys[0] = stringKey.equalsIgnoreCase(SdqlConstants.NULL) || stringKey.isEmpty()
								? SdqlConstants.LONG_NULL
								: LocalDateTime.parse(stringKey, formatter).toEpochSecond(ZoneOffset.of("Z"));

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondarySingleType() == SecondarySingleDataType.NUMERIC_RANGE) {

						isRangeType = true;
						keys = new String[1];
						keys[0] = node.getStringValue().trim();

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondarySingleType() == SecondarySingleDataType.DATE_RANGE) {

						isRangeType = true;
						keys = new String[1];
						keys[0] = node.getStringValue().trim();

					} else if (store.getSdqlColumns()[level].getSecondaryType()
							.getSecondarySingleType() == SecondarySingleDataType.DATE_TIME_RANGE) {

						isRangeType = true;
						keys = new String[1];
						keys[0] = node.getStringValue().trim();

					}

				} else {
					keys = new String[1];
					keys[0] = node.getStringValue().trim();
				}

				if (isNumericType) {
					for (Long key : longKeys) {
						ArrayList<SdqlNode> columLevelIndex = store.getInvertedNumericIndex().get(level).get(key);
						OrderedKeys<Long> orderedKeys = store.getInvertedNumericIndexKeys().get(level);

						if (columLevelIndex == null) {
							columLevelIndex = new ArrayList<>();
							store.getInvertedNumericIndex().get(level).put(key, columLevelIndex);
							orderedKeys.insertKey(key);
						}

						columLevelIndex.add(node);

					}

				} else {
					if (isRangeType) {
						for (String key : keys) {
							Range range = RangeParser.parseRange(key.trim(), store, level);
							ArrayList<SdqlNode> columLevelIndex = store.getInvertedIndex().get(level)
									.get(range.toString());
							HashMap<String, SearchableRange> intervalIndex = store.getRanges().get(level);

							if (columLevelIndex == null) {
								columLevelIndex = new ArrayList<>();
								store.getInvertedIndex().get(level).put(range.toString(), columLevelIndex);

								SearchableRange searchableRange = intervalIndex.get(range.getPrefix());

								if (searchableRange == null) {
									searchableRange = new SearchableRange();
									intervalIndex.put(range.getPrefix(), searchableRange);
								}
								searchableRange.insert(range);
							}

							columLevelIndex.add(node);

						}

					} else {
						for (String key : keys) {
							key = key.trim();
							if (key.equalsIgnoreCase(SdqlConstants.NULL) || key.isEmpty() ) {
								key = SdqlConstants.NULL;
							}
							ArrayList<SdqlNode> columLevelIndex = store.getInvertedIndex().get(level).get(key);
							OrderedKeys<NullableOrderedString> orderedKeys = store.getInvertedIndexKeys().get(level);

							if (columLevelIndex == null) {
								columLevelIndex = new ArrayList<>();
								store.getInvertedIndex().get(level).put(key, columLevelIndex);
								orderedKeys.insertKey(new NullableOrderedString(key));
							}

							columLevelIndex.add(node);

						}
					}

				}

			} else {
				Long longKey = node.getDoubleValue() == null ? SdqlConstants.LONG_NULL
						: node.getDoubleValue().longValue();
				ArrayList<SdqlNode> columLevelIndex = store.getInvertedNumericIndex().get(level).get(longKey);
				OrderedKeys<Long> orderedKeys = store.getInvertedNumericIndexKeys().get(level);
				if (columLevelIndex == null) {
					columLevelIndex = new ArrayList<>();
					store.getInvertedNumericIndex().get(level).put(longKey, columLevelIndex);
					orderedKeys.insertKey(longKey);
				}

				columLevelIndex.add(node);
			}
		}

		return count;

	}

	/**
	 * This function sorts all ordered keys of the store, to prepare them for
	 * optimized searching.
	 * 
	 */
	public void sortOrderedKeys() {

		for (OrderedKeys<NullableOrderedString> orderedKeys : store.getInvertedIndexKeys()) {
			orderedKeys.prepareForSearch();
		}

		for (OrderedKeys<Long> orderedKeys : store.getInvertedNumericIndexKeys()) {
			orderedKeys.prepareForSearch();
		}

	}

	/**
	 * This function pre-processes all the interval trees of the store, for an
	 * optimized searching. It sorts the ranges array according to their lower range
	 * and then convert it into a tree(Interval tree). This ensures that the tree
	 * will be balanced.
	 * 
	 */
	public void prepareIntervalTrees() {

		for (HashMap<String, SearchableRange> rangeMap : store.getRanges()) {
			for (Map.Entry<String, SearchableRange> entry : rangeMap.entrySet()) {
				entry.getValue().prepareForSearch();
			}
		}
	}

	/**
	 * This class is used as internal data structure used while doing breadth first
	 * search of the Trie in order to serialize it. While doing breadth first search
	 * each node stores a reference to its parent node.
	 * 
	 */
	class BfsSerialzeNode {
		private long parentId;
		private long nodeId;
		private SdqlNode sdqlNode;

		public BfsSerialzeNode(long parentId, long nodeId, SdqlNode sdqlNode) {
			super();
			this.parentId = parentId;
			this.nodeId = nodeId;
			this.sdqlNode = sdqlNode;
		}

		public long getParentId() {
			return parentId;
		}

		public void setParentId(long parentId) {
			this.parentId = parentId;
		}

		public long getNodeId() {
			return nodeId;
		}

		public void setNodeId(long nodeId) {
			this.nodeId = nodeId;
		}

		public SdqlNode getNodeValue() {
			return sdqlNode;
		}

		public void setNodeValue(SdqlNode sdqlNode) {
			this.sdqlNode = sdqlNode;
		}
	}

	/**
	 * This function serializes the Trie, which can be used to store your Trie in
	 * disk storage, or is useful while testing. The Serialization format is
	 * parentID - followed by parent delimiter - followed by node's value - followed
	 * by serialization delimiter.
	 * 
	 * @return String It returns the serialized string.
	 */
	public String serializeTrie() {
		StringBuilder result = new StringBuilder();
		long id = 0;

		Queue<BfsSerialzeNode> nodeQueue = new LinkedList<>();
		BfsSerialzeNode bfsNode = new BfsSerialzeNode(-1, id, trieRoot);
		nodeQueue.add(bfsNode);

		while (!nodeQueue.isEmpty()) {

			BfsSerialzeNode currentNode = nodeQueue.poll();
			result.append(currentNode.parentId).append(SdqlConstants.SERIALIZER_PARENT_IDENTIFIER);
			if (currentNode.getNodeValue().getDoubleValue() != null) {
				result.append(String.format("%.2f", currentNode.getNodeValue().getDoubleValue()))
						.append(SdqlConstants.SERIALIZER_DELIMITER);
			} else {
				result.append(currentNode.getNodeValue().getStringValue()).append(SdqlConstants.SERIALIZER_DELIMITER);
			}

			String[] keys = new String[currentNode.getNodeValue().getChildren().keySet().size()];
			currentNode.getNodeValue().getChildren().keySet().toArray(keys);
			Arrays.sort(keys);

			for (int i = 0; i < keys.length; i++) {
				BfsSerialzeNode childNode = new BfsSerialzeNode(currentNode.getNodeId(), ++id,
						currentNode.getNodeValue().getChildren().get(keys[i]));
				nodeQueue.add(childNode);
			}

		}

		return result.deleteCharAt(result.length() - 1).toString();
	}

}