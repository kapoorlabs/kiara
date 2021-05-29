package com.kapoorlabs.kiara.search;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.kapoorlabs.kiara.constants.ExceptionConstants;
import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.NullableOrderedString;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.OrderedKeys;
import com.kapoorlabs.kiara.domain.Range;
import com.kapoorlabs.kiara.domain.SdqlColumn;
import com.kapoorlabs.kiara.domain.SdqlNode;
import com.kapoorlabs.kiara.domain.SearchableRange;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.ColumnNotFoundException;
import com.kapoorlabs.kiara.exception.InsufficientDataException;
import com.kapoorlabs.kiara.exception.NonSupportedOperationException;
import com.kapoorlabs.kiara.exception.RangeOutOfOrderException;
import com.kapoorlabs.kiara.parser.RangeParser;
import com.kapoorlabs.kiara.util.LogicalUtil;
import com.kapoorlabs.kiara.util.NumericUtil;

import lombok.extern.slf4j.Slf4j;
import opennlp.tools.stemmer.snowball.SnowballStemmer;

@Slf4j
public class StoreSearch {

	/**
	 * Condition Merger class is used to merge results if more than one condition is
	 * specified on the same column.
	 * 
	 * similarSdqlNodes specifies all the columns that are returned by the query on
	 * the particular column.
	 * 
	 * nextIndex specifies the index in the conditions array, that should be
	 * processed next.
	 * 
	 */

	private class ConditionMerger {
		ArrayList<ArrayList<SdqlNode>> similarSdqlNodesCollection;
		int nextIndex;
	}

	/**
	 * This function will search the store with provided conditions. The results
	 * will returned as the List of POJO class, that was used to create the Store.
	 * 
	 * @param <T>        Type of Store, for compile time Safety
	 * @param store      This parameter will pass the store, which will be searched.
	 * @param conditions This parameter will pass all the conditions that your
	 *                   result should satisfy.
	 * @return List - It returns a list of POJO's using the POJO class that was used
	 *         to create the store.
	 * 
	 * @throws ColumnNotFoundException        This exception is thrown when the
	 *                                        column mentioned in the condition, is
	 *                                        not found in the store.
	 * @throws InsufficientDataException      This exception is raised when between
	 *                                        operator is used in a condition, and
	 *                                        both lower and upper ranges are not
	 *                                        specified.
	 * @throws NonSupportedOperationException This exception is raised if any
	 *                                        operator (inside a condition) other
	 *                                        than EQUALS ,CONTAINS_EITHER or
	 *                                        CONTAINS_ALL is used in range based
	 *                                        data.
	 * @throws RangeOutOfOrderException       This exception is raised when we try
	 *                                        to parse a range with higher range
	 *                                        value, which is less than lower range.
	 */
	public <T> List<T> query(Store<T> store, List<Condition> conditions) {
		Integer resultLimit = null;
		return query(store, conditions, resultLimit);
	}

	/**
	 * This function will search the store with provided conditions. The results
	 * will returned as the List of POJO class, that was used to create the Store.
	 * 
	 * @param <T>         Type of Store, for compile time Safety
	 * @param store       This parameter will pass the store, which will be
	 *                    searched.
	 * @param conditions  This parameter will pass all the conditions that your
	 *                    result should satisfy.
	 *
	 * @param resultLimit This integer argument will limit your search results to
	 *                    this upper limit
	 * @return List - It returns a list of POJO's using the POJO class that was used
	 *         to create the store.
	 * 
	 * @throws ColumnNotFoundException        This exception is thrown when the
	 *                                        column mentioned in the condition, is
	 *                                        not found in the store.
	 * @throws InsufficientDataException      This exception is raised when between
	 *                                        operator is used in a condition, and
	 *                                        both lower and upper ranges are not
	 *                                        specified.
	 * @throws NonSupportedOperationException This exception is raised if any
	 *                                        operator (inside a condition) other
	 *                                        than EQUALS ,CONTAINS_EITHER or
	 *                                        CONTAINS_ALL is used in range based
	 *                                        data.
	 * @throws RangeOutOfOrderException       This exception is raised when we try
	 *                                        to parse a range with higher range
	 *                                        value, which is less than lower range.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(Store<T> store, List<Condition> conditions, Integer resultLimit) {

		List<Object> pojoList = new ArrayList<>();

		checkIfSearchIsValid(store, conditions);

		Condition[] sortedConditions = sortConditions(store, conditions);

		ArrayList<ArrayList<SdqlNode>> prevColumnNodesCollection = getPassingNodes(store, sortedConditions);

		TreeSet<Integer> filterColPos = getFilterColPos(store);

		buidResult(null, pojoList, store, sortedConditions, prevColumnNodesCollection, filterColPos, true,
				store.getPojoClass(), resultLimit);

		return (List<T>) pojoList;
	}

	/**
	 * This function will search the store with provided conditions,The results can
	 * be filtered with only the columns/data-attributes, you want in the result,
	 * using the filterSet argument.
	 * 
	 * @param store      This parameter will pass the store, which will be searched.
	 * 
	 * @param conditions This parameter will pass all the conditions that your
	 *                   result should satisfy.
	 * 
	 * @param filterSet  This parameter will pass a set of columns that should be
	 *                   present in the result, if this parameter is null, all the
	 *                   columns (which are there in the store) will be present in
	 *                   the result.
	 * @return List It returns a list of Map with Key as column Name and Value as
	 *         the value of the column. All values are represented as a String. The
	 *         column names are stored in upper case form. Therefore, each map is a
	 *         individual record, that satisfied the mentioned conditions, and we
	 *         return list of such records.
	 * 
	 * @throws ColumnNotFoundException        This exception is thrown when the
	 *                                        column mentioned in the condition, is
	 *                                        not found in the store.
	 * @throws InsufficientDataException      This exception is raised when between
	 *                                        operator is used in a condition, and
	 *                                        both lower and upper ranges are not
	 *                                        specified.
	 * @throws NonSupportedOperationException This exception is raised if any
	 *                                        operator (inside a condition) other
	 *                                        than EQUALS ,CONTAINS_EITHER or
	 *                                        CONTAINS_ALL is used in range based
	 *                                        data.
	 * @throws RangeOutOfOrderException       This exception is raised when we try
	 *                                        to parse a range with higher range
	 *                                        value, which is less than lower range.
	 * 
	 */
	public <T> List<Map<String, String>> query(Store<T> store, List<Condition> conditions, Set<String> filterSet) {
		Integer resultLimit = null;
		return query(store, conditions, filterSet, resultLimit);
	}

	/**
	 * This function will search the store with provided conditions,The results can
	 * be filtered with only the columns/data-attributes, you want in the result,
	 * using the filterSet argument.
	 * 
	 * @param store       This parameter will pass the store, which will be
	 *                    searched.
	 * 
	 * @param conditions  This parameter will pass all the conditions that your
	 *                    result should satisfy.
	 * 
	 * @param filterSet   This parameter will pass a set of columns that should be
	 *                    present in the result, if this parameter is null, all the
	 *                    columns (which are there in the store) will be present in
	 *                    the result.
	 * 
	 * @param resultLimit This integer argument will limit your search results to
	 *                    this upper limit
	 * @return List It returns a list of Map with Key as column Name and Value as
	 *         the value of the column. All values are represented as a String. The
	 *         column names are stored in upper case form. Therefore, each map is a
	 *         individual record, that satisfied the mentioned conditions, and we
	 *         return list of such records.
	 * 
	 * @throws ColumnNotFoundException        This exception is thrown when the
	 *                                        column mentioned in the condition, is
	 *                                        not found in the store.
	 * @throws InsufficientDataException      This exception is raised when between
	 *                                        operator is used in a condition, and
	 *                                        both lower and upper ranges are not
	 *                                        specified.
	 * @throws NonSupportedOperationException This exception is raised if any
	 *                                        operator (inside a condition) other
	 *                                        than EQUALS ,CONTAINS_EITHER or
	 *                                        CONTAINS_ALL is used in range based
	 *                                        data.
	 * @throws RangeOutOfOrderException       This exception is raised when we try
	 *                                        to parse a range with higher range
	 *                                        value, which is less than lower range.
	 * 
	 */
	public <T> List<Map<String, String>> query(Store<T> store, List<Condition> conditions, Set<String> filterSet,
			Integer resultLimit) {

		List<Map<String, String>> result = new ArrayList<>();

		checkIfSearchIsValid(store, conditions);

		if (filterSet == null) {
			filterSet = new HashSet<>();
		}

		Condition[] sortedConditions = sortConditions(store, conditions);

		ArrayList<ArrayList<SdqlNode>> prevColumnNodesCollection = getPassingNodes(store, sortedConditions);

		TreeSet<Integer> filterColPos = getFilterColPos(filterSet, store);

		buidResult(result, null, store, sortedConditions, prevColumnNodesCollection, filterColPos, false, null,
				resultLimit);

		return result;
	}

	private <T> void buidResult(List<Map<String, String>> result, List<Object> pojoResult, Store<T> store,
								Condition[] sortedConditions, ArrayList<ArrayList<SdqlNode>> prevColumnNodesCollection,
								TreeSet<Integer> filterColPos, boolean buildPojo, Class<?> pojoClass, Integer resultLimit) {
		int lastConditionColPos = sortedConditions[sortedConditions.length - 1].getColumnIndex();

		Set<SdqlNode> resultNodeSet = new HashSet<>();

		for (ArrayList<SdqlNode> prevColumnNodes : prevColumnNodesCollection) {
			for (SdqlNode resultNode : prevColumnNodes) {
				if (resultNodeSet.contains(resultNode)) {
					continue;
				}
				resultNodeSet.add(resultNode);

				Map<String, String> resultObject = null;
				Object resultPojo = null;

				if (buildPojo && pojoClass != null) {
					try {
						resultPojo = pojoClass.getDeclaredConstructor().newInstance();
					} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
						log.error("Insufficient access to POJO field", e);
						throw new RuntimeException("Insufficient access to POJO field");
					}
				} else {
					resultObject = new HashMap<>();
				}

				if (filterColPos.first() <= lastConditionColPos) {
					buildPreFixMap(resultNode, lastConditionColPos, filterColPos, store, resultObject, resultPojo,
							buildPojo);
				}

				buildPostfixMap(result, pojoResult, resultNode, lastConditionColPos + 1, filterColPos,
						filterColPos.last(), store, resultObject, resultPojo, buildPojo, resultLimit);
			}
		}
	}

	private <T> void checkIfSearchIsValid(Store<T> store, List<Condition> conditions) {
		if (store == null || store.getSdqlColumns().length == 0) {
			throw new InsufficientDataException(ExceptionConstants.EMPTY_STORE_SEARCH);
		}

		if (conditions == null || conditions.isEmpty()) {
			throw new InsufficientDataException(ExceptionConstants.CONDITIONS_NOT_PRESENT);
		}

		if (store.getComittedHash() != ~SdqlConstants.UNCOMITTED_HASH) {
			throw new InsufficientDataException(ExceptionConstants.SEARCHING_UNCOMMTTED_STORE);
		}
	}

	private <T> ArrayList<ArrayList<SdqlNode>> getPassingNodes(Store<T> store, Condition[] sortedConditions) {
		normalizeNull(store, sortedConditions);

		ArrayList<ArrayList<SdqlNode>> prevColumnNodesCollection = new ArrayList<>();

		for (int i = 0; i < sortedConditions.length; i++) {

			ConditionMerger conditionMerger = getNextQualifyingNodes(sortedConditions, i, store);

			if (conditionMerger.similarSdqlNodesCollection.isEmpty()) {
				return new ArrayList<>();
			}

			if (i == 0) {
				prevColumnNodesCollection = conditionMerger.similarSdqlNodesCollection;
			} else {

				if (LogicalUtil.getCollectionSize(conditionMerger.similarSdqlNodesCollection) < LogicalUtil
						.getCollectionSize(prevColumnNodesCollection)) {
					prevColumnNodesCollection = LogicalUtil.binarySearchCurrentNodes(prevColumnNodesCollection,
							conditionMerger.similarSdqlNodesCollection);
				} else {
					prevColumnNodesCollection = LogicalUtil.inverseBinarySearchCurrentNodes(prevColumnNodesCollection,
							conditionMerger.similarSdqlNodesCollection);
				}

			}

			i = conditionMerger.nextIndex - 1;

		}
		return prevColumnNodesCollection;
	}

	private <T> ConditionMerger getNextQualifyingNodes(Condition[] conditions, int currentIndex, Store<T> store) {

		ConditionMerger conditionMerger = new ConditionMerger();
		ArrayList<ArrayList<SdqlNode>> currentColumnNodesCollection = new ArrayList<>();
		ArrayList<ArrayList<SdqlNode>> nextColumnNodesCollection = new ArrayList<>();
		int colIndex = conditions[currentIndex].getColumnIndex();

		for (int i = currentIndex; i < conditions.length; i++) {

			if (i == currentIndex) {
				currentColumnNodesCollection = getQualifyingNodes(conditions[i], store);
				continue;
			}

			if (colIndex != conditions[i].getColumnIndex()) {
				conditionMerger.similarSdqlNodesCollection = currentColumnNodesCollection;
				conditionMerger.nextIndex = i;
				break;
			} else {
				nextColumnNodesCollection = getQualifyingNodes(conditions[i], store);
				currentColumnNodesCollection = LogicalUtil.getDuplicatesFromCollection(currentColumnNodesCollection,
						nextColumnNodesCollection);
			}

		}

		if (conditionMerger.similarSdqlNodesCollection == null) {
			conditionMerger.similarSdqlNodesCollection = currentColumnNodesCollection;
			conditionMerger.nextIndex = conditions.length;
		}

		return conditionMerger;
	}

	private <T> ArrayList<ArrayList<SdqlNode>> getQualifyingNodes(Condition condition, Store<T> store) {

		ArrayList<ArrayList<SdqlNode>> currentColumnNodesCollection = new ArrayList<>();

		if (condition.getOperator() == Operator.EQUAL || condition.getOperator() == Operator.CONTAINS_EITHER) {
			if (LogicalUtil.isIndexNumeric(store, condition)) {
				HashMap<Long, ArrayList<SdqlNode>> columnNumericMap = store.getInvertedNumericIndex()
						.get(condition.getColumnIndex());
				for (String value : condition.getValue()) {
					Long numericValue = NumericUtil.getNumericValue(store, value, condition.getColumnIndex());
					if (columnNumericMap.get(numericValue) != null) {
						currentColumnNodesCollection.add(columnNumericMap.get(numericValue));
					}
				}
			} else {
				HashMap<String, ArrayList<SdqlNode>> columnMap = store.getInvertedIndex()
						.get(condition.getColumnIndex());

				if (LogicalUtil.isRangeType(store, condition) && condition.getOperator() == Operator.CONTAINS_EITHER) {
					HashMap<String, SearchableRange> rangeMap = store.getRanges().get(condition.getColumnIndex());
					for (String value : condition.getValue()) {
						Range rangeCondition = RangeParser.parseRange(value, store, condition.getColumnIndex());
						SearchableRange searchableRange = rangeMap.get(rangeCondition.getPrefix());
						if (searchableRange != null) {
							List<Range> qualifiedRanges = searchableRange.getInRange(rangeCondition.getLowerLimit());
							for (Range qualifiedRange : qualifiedRanges) {
								currentColumnNodesCollection.add(columnMap.get(qualifiedRange.toString()));
							}
						}
					}

				} else {
					for (String value : condition.getValue()) {
						if (LogicalUtil.isRangeType(store, condition)) {
							Range rangeCondition = RangeParser.parseRange(value, store, condition.getColumnIndex());
							value = rangeCondition.toString();
						}
						if (columnMap.get(value) != null) {
							currentColumnNodesCollection.add(columnMap.get(value));
						}
					}
				}

			}

		} else if (condition.getOperator() == Operator.CONTAINS_ALL) {

			ArrayList<SdqlNode> qualifyingNodes = null;

			if (LogicalUtil.isIndexNumeric(store, condition)) {
				HashMap<Long, ArrayList<SdqlNode>> columnNumericMap = store.getInvertedNumericIndex()
						.get(condition.getColumnIndex());
				for (String value : condition.getValue()) {
					Long numericValue = NumericUtil.getNumericValue(store, value, condition.getColumnIndex());
					if (columnNumericMap.get(numericValue) == null) {
						return new ArrayList<>();
					} else {
						if (qualifyingNodes == null) {
							qualifyingNodes = columnNumericMap.get(numericValue);
						} else {
							qualifyingNodes = getDuplicates(columnNumericMap.get(numericValue), qualifyingNodes);
						}

					}
				}

			} else {

				HashMap<String, ArrayList<SdqlNode>> columnMap = store.getInvertedIndex()
						.get(condition.getColumnIndex());

				if (LogicalUtil.isRangeType(store, condition)) {
					HashMap<String, SearchableRange> rangeMap = store.getRanges().get(condition.getColumnIndex());
					for (String value : condition.getValue()) {
						Range rangeCondition = RangeParser.parseRange(value, store, condition.getColumnIndex());
						SearchableRange searchableRange = rangeMap.get(rangeCondition.getPrefix());
						if (searchableRange == null) {
							return new ArrayList<>();
						} else {
							List<Range> qualifiedRanges = searchableRange.getInRange(rangeCondition.getLowerLimit());
							if (qualifiedRanges.size() == 0) {
								return new ArrayList<>();
							}

							ArrayList<SdqlNode> currentSetNodes = null;
							for (Range qualifiedRange : qualifiedRanges) {
								if (currentSetNodes == null) {
									currentSetNodes = columnMap.get(qualifiedRange.toString());
								} else {
									currentSetNodes = LogicalUtil.mergeIndexNodes(currentSetNodes,
											columnMap.get(qualifiedRange.toString()));

								}
							}

							if (qualifyingNodes == null) {
								qualifyingNodes = currentSetNodes;
							} else {
								qualifyingNodes = getDuplicates(currentSetNodes, qualifyingNodes);
							}

						}

					}

				} else {

					for (String value : condition.getValue()) {
						if (columnMap.get(value) == null) {
							return new ArrayList<>();
						} else {
							if (qualifyingNodes == null) {
								qualifyingNodes = columnMap.get(value);
							} else {
								qualifyingNodes = getDuplicates(columnMap.get(value), qualifyingNodes);
							}
						}
					}

				}

			}

			if (qualifyingNodes != null) {
				currentColumnNodesCollection.add(qualifyingNodes);
			}

		} else if (condition.getOperator() == Operator.NOT_CONTAINS) {

			if (store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType() == null
					|| store.getSdqlColumns()[condition.getColumnIndex()].getSecondaryType()
							.getSecondaryCollectionType() == null) {

				String message = "Not_Contains operator is not supported for single data types. It is only supported for collections"
						+ condition;
				log.error(message);
				throw new NonSupportedOperationException(message);

			}

			Set<String> keysToSkip = new HashSet<>();

			if (LogicalUtil.isIndexNumeric(store, condition)) {
				HashMap<Long, ArrayList<SdqlNode>> columnNumericMap = store.getInvertedNumericIndex()
						.get(condition.getColumnIndex());
				for (String value : condition.getValue()) {
					Long numericValue = NumericUtil.getNumericValue(store, value, condition.getColumnIndex());
					if (columnNumericMap.get(numericValue) != null) {
						for (SdqlNode matchingNode : columnNumericMap.get(numericValue)) {
							keysToSkip.add(matchingNode.getStringValue());
						}
					}
				}
			} else {
				HashMap<String, ArrayList<SdqlNode>> columnMap = store.getInvertedIndex()
						.get(condition.getColumnIndex());

				if (LogicalUtil.isRangeType(store, condition)) {
					HashMap<String, SearchableRange> rangeMap = store.getRanges().get(condition.getColumnIndex());
					for (String value : condition.getValue()) {
						Range rangeCondition = RangeParser.parseRange(value, store, condition.getColumnIndex());
						SearchableRange searchableRange = rangeMap.get(rangeCondition.getPrefix());
						if (searchableRange != null) {
							List<Range> qualifiedRanges = searchableRange.getInRange(rangeCondition.getLowerLimit());
							for (Range qualifiedRange : qualifiedRanges) {
								for (SdqlNode matchingNode : columnMap.get(qualifiedRange.toString())) {
									keysToSkip.add(matchingNode.getStringValue());
								}
							}
						}
					}

				} else {
					for (String value : condition.getValue()) {
						if (columnMap.get(value) != null) {
							for (SdqlNode matchingNode : columnMap.get(value)) {
								keysToSkip.add(matchingNode.getStringValue());
							}
						}
					}
				}

			}

			HashMap<String, ArrayList<SdqlNode>> fullKeyMap = store.getCollectionFullKeyIndex()
					.get(condition.getColumnIndex());

			for (Entry<String, ArrayList<SdqlNode>> entry : fullKeyMap.entrySet()) {
				if (!keysToSkip.contains(entry.getKey())) {
					currentColumnNodesCollection.add(entry.getValue());
				}
			}

		} else if (condition.getOperator() == Operator.NOT_EQUAL) {
			if (LogicalUtil.isIndexNumeric(store, condition)) {

				Set<Long> conditionValues = new HashSet<>();
				for (String value : condition.getValue()) {
					conditionValues.add(NumericUtil.getNumericValue(store, value, condition.getColumnIndex()));
				}

				HashMap<Long, ArrayList<SdqlNode>> columnNumericMap = store.getInvertedNumericIndex()
						.get(condition.getColumnIndex());

				for (Entry<Long, ArrayList<SdqlNode>> entry : columnNumericMap.entrySet()) {
					if (!conditionValues.contains(entry.getKey())) {
						currentColumnNodesCollection.add(entry.getValue());
					}
				}

			} else {
				Set<String> conditionValues = new HashSet<>();
				conditionValues.addAll(condition.getValue());
				HashMap<String, ArrayList<SdqlNode>> columnMap = store.getInvertedIndex()
						.get(condition.getColumnIndex());

				for (Entry<String, ArrayList<SdqlNode>> entry : columnMap.entrySet()) {
					if (!conditionValues.contains(entry.getKey())) {
						currentColumnNodesCollection.add(entry.getValue());
					}
				}

			}

		} else if (condition.getOperator() == Operator.LESS_THAN
				|| condition.getOperator() == Operator.LESS_THAN_EQUAL) {
			boolean inclusiveFlag = condition.getOperator() == Operator.LESS_THAN_EQUAL ? true : false;
			String value = condition.getValue().get(0);

			if (LogicalUtil.isRangeType(store, condition)) {
				String message = "Less than operator not supported for ranges, Condition: " + condition;
				log.error(message);
				throw new NonSupportedOperationException(message);
			}

			if (LogicalUtil.isIndexNumeric(store, condition)) {
				HashMap<Long, ArrayList<SdqlNode>> columnNumericMap = store.getInvertedNumericIndex()
						.get(condition.getColumnIndex());
				OrderedKeys<Long> orderedNumerickeys = store.getInvertedNumericIndexKeys()
						.get(condition.getColumnIndex());

				List<Long> eligibleKeys = null;

				if (inclusiveFlag) {
					eligibleKeys = orderedNumerickeys
							.getAllLessThanEqual(NumericUtil.getNumericValue(store, value, condition.getColumnIndex()));
				} else {
					eligibleKeys = orderedNumerickeys
							.getAllLessThan(NumericUtil.getNumericValue(store, value, condition.getColumnIndex()));
				}

				for (Long key : eligibleKeys) {
					currentColumnNodesCollection.add(columnNumericMap.get(key));
				}
			} else {
				HashMap<String, ArrayList<SdqlNode>> columnMap = store.getInvertedIndex()
						.get(condition.getColumnIndex());
				OrderedKeys<NullableOrderedString> orderedStringkeys = store.getInvertedIndexKeys()
						.get(condition.getColumnIndex());

				List<NullableOrderedString> eligibleKeys = null;

				if (inclusiveFlag) {
					eligibleKeys = orderedStringkeys.getAllLessThanEqual(new NullableOrderedString(value));
				} else {
					eligibleKeys = orderedStringkeys.getAllLessThan(new NullableOrderedString(value));
				}

				for (NullableOrderedString key : eligibleKeys) {
					currentColumnNodesCollection.add(columnMap.get(key.toString()));
				}
			}

		} else if (condition.getOperator() == Operator.GREATER_THAN
				|| condition.getOperator() == Operator.GREATER_THAN_EQUAL) {
			boolean inclusiveFlag = condition.getOperator() == Operator.GREATER_THAN_EQUAL ? true : false;
			String value = condition.getValue().get(0);

			if (LogicalUtil.isRangeType(store, condition)) {
				String message = "Greater than operator not supported for ranges, Condition: " + condition;
				log.error(message);
				throw new NonSupportedOperationException(message);
			}

			if (LogicalUtil.isIndexNumeric(store, condition)) {
				HashMap<Long, ArrayList<SdqlNode>> columnNumericMap = store.getInvertedNumericIndex()
						.get(condition.getColumnIndex());
				OrderedKeys<Long> orderedNumerickeys = store.getInvertedNumericIndexKeys()
						.get(condition.getColumnIndex());

				List<Long> eligibleKeys = null;

				if (inclusiveFlag) {
					eligibleKeys = orderedNumerickeys.getAllGreaterThanEqual(
							NumericUtil.getNumericValue(store, value, condition.getColumnIndex()));
				} else {
					eligibleKeys = orderedNumerickeys
							.getAllGreaterThan(NumericUtil.getNumericValue(store, value, condition.getColumnIndex()));
				}
				for (Long key : eligibleKeys) {
					currentColumnNodesCollection.add(columnNumericMap.get(key));
				}
			} else {
				HashMap<String, ArrayList<SdqlNode>> columnMap = store.getInvertedIndex()
						.get(condition.getColumnIndex());
				OrderedKeys<NullableOrderedString> orderedStringkeys = store.getInvertedIndexKeys()
						.get(condition.getColumnIndex());

				List<NullableOrderedString> eligibleKeys = null;

				if (inclusiveFlag) {
					eligibleKeys = orderedStringkeys.getAllGreaterThanEqual(new NullableOrderedString(value));
				} else {
					eligibleKeys = orderedStringkeys.getAllGreaterThan(new NullableOrderedString(value));
				}
				for (NullableOrderedString key : eligibleKeys) {
					currentColumnNodesCollection.add(columnMap.get(key.toString()));
				}
			}

		} else if (condition.getOperator() == Operator.BETWEEN) {

			if (LogicalUtil.isRangeType(store, condition)) {
				String message = "Between operator not supported for ranges, Condition: " + condition;
				log.error(message);
				throw new NonSupportedOperationException(message);
			}

			String lowerValue = condition.getLowerValue();
			String upperValue = condition.getUpperValue();

			if (lowerValue == null || upperValue == null) {
				String message = "Insufficient data with Between operator, both lower and upper range miust be specified, condition: "
						+ condition;
				throw new InsufficientDataException(message);
			}

			if (LogicalUtil.isIndexNumeric(store, condition)) {
				HashMap<Long, ArrayList<SdqlNode>> columnNumericMap = store.getInvertedNumericIndex()
						.get(condition.getColumnIndex());
				OrderedKeys<Long> orderedNumerickeys = store.getInvertedNumericIndexKeys()
						.get(condition.getColumnIndex());

				List<Long> eligibleKeys = null;

				eligibleKeys = orderedNumerickeys.getAllBetween(
						NumericUtil.getNumericValue(store, lowerValue, condition.getColumnIndex()),
						NumericUtil.getNumericValue(store, upperValue, condition.getColumnIndex()));

				for (Long key : eligibleKeys) {
					currentColumnNodesCollection.add(columnNumericMap.get(key));
				}
			} else {
				HashMap<String, ArrayList<SdqlNode>> columnMap = store.getInvertedIndex()
						.get(condition.getColumnIndex());
				OrderedKeys<NullableOrderedString> orderedStringkeys = store.getInvertedIndexKeys()
						.get(condition.getColumnIndex());

				List<NullableOrderedString> eligibleKeys = null;

				eligibleKeys = orderedStringkeys.getAllBetween(new NullableOrderedString(lowerValue),
						new NullableOrderedString(upperValue));

				for (NullableOrderedString key : eligibleKeys) {
					currentColumnNodesCollection.add(columnMap.get(key.toString()));
				}
			}

		}

		return currentColumnNodesCollection;

	}

	private <T> TreeSet<Integer> getFilterColPos(Set<String> filterSet, Store<T> store) {

		TreeSet<Integer> colPosSet = new TreeSet<>();

		if (filterSet.isEmpty()) {
			for (int i = 0; i < store.getSdqlColumns().length; i++) {
				colPosSet.add(i);
			}
		} else {
			for (String colName : filterSet) {
				if (store.getColumnIndex().get(colName.toUpperCase()) != null) {
					colPosSet.add(store.getColumnIndex().get(colName.toUpperCase()));
				}
			}
		}

		return colPosSet;
	}

	private <T> TreeSet<Integer> getFilterColPos(Store<T> store) {

		TreeSet<Integer> colPosSet = new TreeSet<>();

		for (int i = 0; i < store.getSdqlColumns().length; i++) {
			colPosSet.add(i);
		}

		return colPosSet;
	}

	private <T> Condition[] sortConditions(Store<T> store, List<Condition> conditions) {

		Condition[] resultantConditions = new Condition[conditions.size()];
		Map<String, Integer> columnIndex = store.getColumnIndex();
		int i = 0;
		for (Condition condition : conditions) {
			Integer colIndex = columnIndex.get(condition.getColumnName().toUpperCase());
			if (colIndex != null) {
				if (!store.getSdqlColumns()[colIndex].isIndexed()) {
					String message = condition.getColumnName() + " is restriced from being searched";
					log.error(message);
					throw new NonSupportedOperationException(message);
				}

				resultantConditions[i++] = condition;
				condition.setColumnIndex(colIndex);

				if (condition.getValue() != null) {
					ListIterator<String> conditionValueIterator = condition.getValue().listIterator();

					while (conditionValueIterator.hasNext()) {
						String value = conditionValueIterator.next().trim();
						
						if (store.getSdqlColumns()[colIndex].isStemmedIndex()) {
							SnowballStemmer stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
							value = stemmer.stem(value).toString();
						}
						
						if (store.getSdqlColumns()[colIndex].isCaseSensitive()) {
							value = value.toLowerCase();
						}
						
						conditionValueIterator.set(value);
					}
				} else if (condition.getLowerValue() != null && condition.getUpperValue() != null) {
					condition.setLowerValue(condition.getLowerValue().trim());
					condition.setUpperValue(condition.getUpperValue().trim());
				}

			} else {
				String message = condition.getColumnName() + " is not a valid column";
				log.error(message);
				throw new ColumnNotFoundException(message);
			}
		}

		Arrays.sort(resultantConditions);

		return resultantConditions;
	}

	private <T> void buildPreFixMap(SdqlNode currentNode, int lastConditionColPos, TreeSet<Integer> filterColPos,
			Store<T> store, Map<String, String> resultObject, Object resultPojo, boolean buildPojo) {

		int firstColPos = filterColPos.first();
		while (lastConditionColPos >= firstColPos) {

			if (filterColPos.contains(lastConditionColPos)) {
				writeResultObject(currentNode, lastConditionColPos, store, resultObject, resultPojo, buildPojo);

			}

			lastConditionColPos--;
			currentNode = currentNode.getParent();
		}

	}

	private <T> void writeResultObject(SdqlNode currentNode, int lastConditionColPos, Store<T> store,
			Map<String, String> resultObject, Object resultPojo, boolean buildPojo) {
		String columnName = store.getSdqlColumns()[lastConditionColPos].getColumnName();

		if (currentNode.getStringValue().equals(SdqlConstants.NULL)) {

			if (buildPojo && resultPojo != null) {
				writePojoValue(currentNode, lastConditionColPos, store, resultPojo, null);

			} else {
				resultObject.put(columnName, null);
			}

		} else {
			if (store.getSdqlColumns()[lastConditionColPos].isNumeric()) {
				String numericString = currentNode.getStringValue();
				if (numericString.endsWith(".00")) {
					numericString = numericString.substring(0, numericString.length() - 3);
				}
				if (buildPojo && resultPojo != null) {
					writePojoValue(currentNode, lastConditionColPos, store, resultPojo, numericString);
				} else {
					resultObject.put(columnName, numericString);
				}

			} else {
				if (buildPojo && resultPojo != null) {
					writePojoValue(currentNode, lastConditionColPos, store, resultPojo, currentNode.getStringValue());
				} else {
					resultObject.put(columnName, currentNode.getStringValue());
				}

			}
		}

	}

	private <T> void writePojoValue(SdqlNode currentNode, int lastConditionColPos, Store<T> store, Object resultPojo,
			String value) {

		SdqlColumn sdqlColumn = store.getSdqlColumns()[lastConditionColPos];
		Class<?> columnType = sdqlColumn.getGetter().getReturnType();

		try {
			if (value == null) {
				if (columnType == java.lang.Character.TYPE) {
					sdqlColumn.getSetter().invoke(resultPojo, ' ');
				} else {
					sdqlColumn.getSetter().invoke(resultPojo, (Object) null);
				}
			} else if (sdqlColumn.isNumeric()) {
				if (columnType == java.lang.Byte.TYPE || columnType == Byte.class) {
					sdqlColumn.getSetter().invoke(resultPojo, Byte.parseByte(value));
				} else if (columnType == java.lang.Short.TYPE || columnType == Short.class) {
					sdqlColumn.getSetter().invoke(resultPojo, Short.parseShort(value));
				} else if (columnType == java.lang.Integer.TYPE || columnType == Integer.class) {
					sdqlColumn.getSetter().invoke(resultPojo, Integer.parseInt(value));
				} else if (columnType == java.lang.Long.TYPE || columnType == Long.class) {
					sdqlColumn.getSetter().invoke(resultPojo, Long.parseLong(value));
				} else if (columnType == java.lang.Float.TYPE || columnType == Float.class) {
					sdqlColumn.getSetter().invoke(resultPojo, Float.parseFloat(value));
				} else if (columnType == java.lang.Double.TYPE || columnType == Double.class) {
					sdqlColumn.getSetter().invoke(resultPojo, Double.parseDouble(value));
				}
			} else if (sdqlColumn.isEnum()) {
				try {
					Enum enumValue = Enum.valueOf(sdqlColumn.getEnumClass(), value);
					sdqlColumn.getSetter().invoke(resultPojo, enumValue);
				} catch (Exception ex) {
					sdqlColumn.getSetter().invoke(resultPojo,(Object) null);
				}
			} else {
				if (columnType == java.lang.Character.TYPE || columnType == Character.class) {
					sdqlColumn.getSetter().invoke(resultPojo, value.charAt(0));
				} else if (columnType == java.lang.Boolean.TYPE || columnType == Boolean.class) {
					sdqlColumn.getSetter().invoke(resultPojo, Boolean.parseBoolean(value));
				} else {
					sdqlColumn.getSetter().invoke(resultPojo, value);
				}
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error("Insufficient access to POJO field", e);
			throw new RuntimeException("Insufficient Access to Pojo fields");
		}

	}

	private <T> void buildPostfixMap(List<Map<String, String>> result, List<Object> pojoResult, SdqlNode currentNode,
			int nextColPos, TreeSet<Integer> filterColPos, int lasColPos, Store<T> store,
			Map<String, String> resultObject, Object resultPojo, boolean buildPojo, Integer resultLimit) {

		if (resultLimit != null && ((pojoResult != null && pojoResult.size() >= resultLimit)
				|| (result != null && result.size() >= resultLimit))) {
			return;
		}

		if (nextColPos > lasColPos) {
			if (buildPojo && resultPojo != null) {
				Object pojoCopy = copyPojo(store, resultPojo);
				pojoResult.add(pojoCopy);
			} else {
				Map<String, String> newResultObject = new HashMap<>();
				newResultObject.putAll(resultObject);
				result.add(newResultObject);
			}

			return;
		}

		for (SdqlNode childNode : currentNode.getChildren()) {
			if (filterColPos.contains(nextColPos)) {
				writeResultObject(childNode, nextColPos, store, resultObject, resultPojo, buildPojo);
			}
			buildPostfixMap(result, pojoResult, childNode, nextColPos + 1, filterColPos, lasColPos, store, resultObject,
					resultPojo, buildPojo, resultLimit);
		}
	}

	private <T> Object copyPojo(Store<T> store, Object resultPojo) {

		Object pojoCopy;
		try {
			pojoCopy = resultPojo.getClass().getDeclaredConstructor().newInstance();
			for (SdqlColumn sdqlColumn : store.getSdqlColumns()) {
				sdqlColumn.getSetter().invoke(pojoCopy, sdqlColumn.getGetter().invoke(resultPojo));
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			log.error("Insufficient access to POJO field", e);
			throw new RuntimeException("Insufficient access to POJO field");
		}

		return pojoCopy;
	}

	/**
	 * This function just normalizes null text in all conditions, to either its
	 * appropriate string or long value
	 * 
	 * @param store      The data store being searched
	 * @param conditions list of conditions or search criteria.
	 */
	private <T> void normalizeNull(Store<T> store, Condition[] conditions) {

		for (Condition condition : conditions) {

			if (condition.getValue() != null) {
				boolean isNullFound = false;
				for (int i = 0; i < condition.getValue().size(); i++) {
					if (condition.getValue().get(i).equalsIgnoreCase(SdqlConstants.NULL)) {
						if (store.getSdqlColumns()[condition.getColumnIndex()].isNumeric()) {
							condition.getValue().set(i, SdqlConstants.LONG_NULL.toString());
							isNullFound = true;
						} else {
							condition.getValue().set(i, SdqlConstants.NULL);
							isNullFound = true;
						}
					}
				}
				if (condition.getOperator() == Operator.CONTAINS_EITHER_INCLUDING_NULL) {
					condition.setOperator(Operator.CONTAINS_EITHER);
					if (!isNullFound) {
						condition.getValue().add(SdqlConstants.NULL);
					}
				}
			} else {
				if (condition.getLowerValue().equalsIgnoreCase(SdqlConstants.NULL)) {
					if (store.getSdqlColumns()[condition.getColumnIndex()].isNumeric()) {
						condition.setLowerValue(SdqlConstants.LONG_NULL.toString());
					} else {
						condition.setLowerValue(SdqlConstants.NULL);
					}
				}

				if (condition.getUpperValue().equalsIgnoreCase(SdqlConstants.NULL)) {
					if (store.getSdqlColumns()[condition.getColumnIndex()].isNumeric()) {
						condition.setUpperValue(SdqlConstants.LONG_NULL.toString());
					} else {
						condition.setUpperValue(SdqlConstants.NULL);
					}
				}
			}

		}
	}

	/**
	 * Given two sorted list of sdql nodes, this function returns the duplicate
	 * nodes, present in the two lists
	 * 
	 * @param currentNodes       First sorted list of sdql nodes.
	 * @param newQualifyingNodes Second sorted list of sdql nodes.
	 * @return It returns the duplicate nodes in two sorted lists.
	 */
	private ArrayList<SdqlNode> getDuplicates(ArrayList<SdqlNode> currentNodes,
			ArrayList<SdqlNode> newQualifyingNodes) {

		if (currentNodes == null) {
			currentNodes = new ArrayList<>();
		}

		if (newQualifyingNodes == null) {
			newQualifyingNodes = new ArrayList<>();
		}

		ArrayList<SdqlNode> result = new ArrayList<>();

		int currentNodePointer = 0;
		int newQualifyingPointer = 0;

		while (currentNodePointer < currentNodes.size() && newQualifyingPointer < newQualifyingNodes.size()) {

			if (currentNodes.get(currentNodePointer).getLowerBound() < newQualifyingNodes.get(newQualifyingPointer)
					.getLowerBound()) {
				currentNodePointer++;

			} else if (currentNodes.get(currentNodePointer).getLowerBound() > newQualifyingNodes
					.get(newQualifyingPointer).getLowerBound()) {
				newQualifyingPointer++;
			} else {
				result.add(currentNodes.get(currentNodePointer));
				currentNodePointer++;
				newQualifyingPointer++;
			}

		}

		return result;
	}

}
