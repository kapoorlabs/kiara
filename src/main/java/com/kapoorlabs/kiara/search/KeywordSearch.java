package com.kapoorlabs.kiara.search;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.kapoorlabs.kiara.constants.ExceptionConstants;
import com.kapoorlabs.kiara.constants.SdqlConstants;
import com.kapoorlabs.kiara.domain.Condition;
import com.kapoorlabs.kiara.domain.KeywordConditionsPair;
import com.kapoorlabs.kiara.domain.KeywordSearchResult;
import com.kapoorlabs.kiara.domain.MatchesForKeyword;
import com.kapoorlabs.kiara.domain.Operator;
import com.kapoorlabs.kiara.domain.SdqlColumn;
import com.kapoorlabs.kiara.domain.Store;
import com.kapoorlabs.kiara.exception.InsufficientDataException;
import com.kapoorlabs.kiara.util.SpellCheckUtil;

import lombok.extern.slf4j.Slf4j;
import opennlp.tools.stemmer.snowball.SnowballStemmer;

@Slf4j
public class KeywordSearch {

	private long[] factorialMap;

	public KeywordSearch(int maxKeywords) {

		generateFactorialMap(maxKeywords);

	}

	/**
	 * This method returns a list of matching column id's for every given keyword
	 * 
	 * @param keywords - List of keywords that is being searched
	 * @param store    - The Store that is being searched
	 * @return list of matching column id's for every given keyword
	 */

	private <T> ArrayList<MatchesForKeyword> getMatchesForKeyword(Set<String> keywords, Store<T> store) {

		ArrayList<MatchesForKeyword> matchesForKeywords = new ArrayList<>();

		if (keywords == null || keywords.isEmpty()) {
			return matchesForKeywords;
		}
		
		if (store == null || store.getSdqlColumns().length == 0) {
			throw new InsufficientDataException(ExceptionConstants.EMPTY_STORE_SEARCH);
		}

		for (String keyword : keywords) {

			keyword = keyword == null ? SdqlConstants.NULL : keyword;

			keyword = keyword.trim();

			MatchesForKeyword matchesForKeyword = new MatchesForKeyword();
			String searchKeyword = keyword;

			for (int i = 0; i < store.getInvertedIndex().size(); i++) {

				if (store.getSdqlColumns()[i].isCaseSensitive()) {
					searchKeyword = searchKeyword.toLowerCase();
				}

				if (store.getSdqlColumns()[i].isStemmedIndex()) {

					SnowballStemmer stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
					String stemmedKey = stemmer.stem(searchKeyword).toString();

					if (store.getInvertedIndex().get(i).containsKey(stemmedKey)) {
						matchesForKeyword.getColMatches().add(i);
						matchesForKeyword.setKeyword(stemmedKey);
					}

				} else {

					if (store.getInvertedIndex().get(i).containsKey(searchKeyword)) {
						matchesForKeyword.getColMatches().add(i);
						matchesForKeyword.setKeyword(searchKeyword);
					}

				}
			}

			if (!matchesForKeyword.getColMatches().isEmpty()) {
				if (matchesForKeywords.size() >= factorialMap.length - 1) {
					log.error(
							"Given size of keywords exceeds maximum limit {}. Limiting the keywords search under maximum limit",
							factorialMap.length - 1);
					break;
				}
				matchesForKeywords.add(matchesForKeyword);
			} else {
				String autoCorrectedWord = SpellCheckUtil.getOneEditKeyword(keyword, store.getSpellCheckTrie());
				if (autoCorrectedWord != null) {

					matchesForKeyword.setKeyword(autoCorrectedWord);

					for (int i = 0; i < store.getInvertedIndex().size(); i++) {

						if (store.getInvertedIndex().get(i).containsKey(autoCorrectedWord)) {
							matchesForKeyword.getColMatches().add(i);
						}
					}

					if (!matchesForKeyword.getColMatches().isEmpty()) {
						if (matchesForKeywords.size() >= factorialMap.length - 1) {
							log.error(
									"Given size of keywords exceeds maximum limit {}. Limiting the keywords search under maximum limit",
									factorialMap.length - 1);
							break;
						}
						matchesForKeywords.add(matchesForKeyword);
					}

				}
			}

		}

		return matchesForKeywords;
	}

	/**
	 * This public method takes in a Strings and the Data Store that should be
	 * searched and returns the best match keyword result. Keywords in the String
	 * are separated by spaces.
	 * 
	 * @param sentence    - A String containing words that could individually has a
	 *                    match in store.
	 * @param store       - Data store that is being searched
	 * @param resultLimit This integer argument will limit your search results to
	 *                    this upper limit
	 * @return It returns a set of keywords that best matched and List of results.
	 */
	public <T> KeywordSearchResult<T> getBestMatch(String sentence, Store<T> store, Integer resultLimit) {

		return getBestMatch(sentence, store, null, resultLimit);

	}

	/**
	 * This public method takes in a Set of keywords or phrases and the Data Store
	 * that should be searched and returns the best match keyword/phrase result.
	 * 
	 * @param keywords    - A set of keywords/phrases.
	 * @param store       - Data store that is being searched
	 * @param resultLimit This integer argument will limit your search results to
	 *                    this upper limit
	 * @return It returns a set of keywords that best matched and List of results.
	 */
	public <T> KeywordSearchResult<T> getBestMatch(Set<String> keywords, Store<T> store, Integer resultLimit) {

		return getBestMatch(keywords, store, null, resultLimit);
	}

	/**
	 * This public method takes in a Strings and the Data Store that should be
	 * searched and returns the best match keyword result. Keywords in the String
	 * are separated by spaces.
	 * 
	 * @param sentence      - A String containing words that could individually has
	 *                      a match in store.
	 * @param store         - Data store that is being searched
	 * @param preConditions - preconditions that should apply to keyword search
	 * @param resultLimit   This integer argument will limit your search results to
	 *                      this upper limit
	 * @return It returns a set of keywords that best matched and List of results.
	 */
	public <T> KeywordSearchResult<T> getBestMatch(String sentence, Store<T> store, List<Condition> preConditions,
			Integer resultLimit) {

		if (sentence == null || sentence.trim().isEmpty()) {
			return new KeywordSearchResult<T>(new HashSet<>(), new ArrayList<>());
		}

		sentence = SpellCheckUtil.removeStopWords(sentence);
		Set<String> keywords = new HashSet<>(Arrays.asList(sentence.split(" ")));

		return getBestMatch(keywords, store, preConditions, resultLimit);

	}

	/**
	 * This public method takes in a Set of keywords or phrases and the Data Store
	 * that should be searched and returns the best match keyword/phrase result.
	 * 
	 * @param keywords      - A set of keywords/phrases.
	 * @param store         - Data store that is being searched
	 * @param preConditions - preconditions that should apply to keyword search
	 * @param resultLimit   This integer argument will limit your search results to
	 *                      this upper limit
	 * @return It returns a set of keywords that best matched and List of results.
	 */
	public <T> KeywordSearchResult<T> getBestMatch(Set<String> keywords, Store<T> store, List<Condition> preConditions,
			Integer resultLimit) {

		KeywordSearchResult<T> keywordSearchResult = new KeywordSearchResult<T>(new HashSet<>(), new ArrayList<>());

		ArrayList<MatchesForKeyword> matchesForKeywords = getMatchesForKeyword(keywords, store);

		int totalKeywords = matchesForKeywords.size();

		if (totalKeywords == 0) {
			return keywordSearchResult;
		}

		Stack<KeywordConditionsPair>[] queryStack = new Stack[totalKeywords];
		long[] processedQueryCount = new long[totalKeywords];
		long[] maxQueryCount = new long[totalKeywords];

		KeywordSearchResult<T> searchResult = getBestMatchHelper(matchesForKeywords, store, 0, new HashMap<>(),
				new HashSet<>(), totalKeywords, maxQueryCount, processedQueryCount, queryStack, preConditions,
				resultLimit);

		if (searchResult != null) {
			keywordSearchResult = searchResult;
		} else {
			for (int i = totalKeywords; i >= 1; i--) {
				searchResult = processQueryStack(i - 1, store, queryStack, resultLimit);
				if (searchResult != null) {
					keywordSearchResult = searchResult;
					break;
				}
			}
		}

		return keywordSearchResult;

	}

	/**
	 * This public method takes in a Strings and the Data Store that should be
	 * searched and returns the minimum match keyword result. Keywords in the String
	 * are separated by spaces. By minimum match it means that the result has only the keywords entered and nothing more.
	 * 
	 * @param sentence - A String containing words that could individually has a
	 *                 match in store.
	 * @param store    - Data store that is being searched
	 * @return Minimum matched results.
	 */
	public <T> KeywordSearchResult<T> getMinimumMatch(String sentence, Store<T> store) {

		KeywordSearchResult<T> minimumMatches = new KeywordSearchResult<>(new HashSet<>(), new LinkedList<>());

		KeywordSearchResult<T> searchresults = getBestMatch(sentence, store, null);

		for (T searchResult : searchresults.getResult()) {
			Set<String> valueSet = new HashSet<>();

			for (SdqlColumn sdqlColumn : store.getSdqlColumns()) {

				if (sdqlColumn.isNumeric()) {
					continue;
				}

				try {
					Object value = sdqlColumn.getGetter().invoke(searchResult);
					if (value != null) {
						valueSet.add(value.toString());
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
					log.error("Insufficient access to POJO field", ex);
					throw new RuntimeException("Insufficient access to POJO field");
				}

			}

			if (searchresults.getKeywords().size() == valueSet.size()) {
				minimumMatches.getResult().add(searchResult);
			}

		}

		if (!minimumMatches.getResult().isEmpty()) {

			minimumMatches.setKeywords(searchresults.getKeywords());

		}

		return minimumMatches;

	}

	private <T> KeywordSearchResult<T> getBestMatchHelper(ArrayList<MatchesForKeyword> matchesForKeywords,
			Store<T> store, int keywordPos, HashMap<Integer, Set<String>> combination, Set<String> keywords,
			int bestPossibleMatchScore, long[] maxQueryCount, long[] processedQueryCount,
			Stack<KeywordConditionsPair>[] queryStack, List<Condition> preConditions, Integer resultLimit) {

		KeywordSearchResult<T> result = null;

		if (keywordPos == matchesForKeywords.size()) {

			if (keywords.isEmpty()) {
				return result;
			}

			Set<String> keywordClone = new HashSet<>(keywords);
			KeywordConditionsPair keywordConditionsPair = new KeywordConditionsPair(keywordClone,
					formConditions(store, combination, preConditions));

			if (queryStack[keywordClone.size() - 1] == null) {
				queryStack[keywordClone.size() - 1] = new Stack<>();
			}

			queryStack[keywordClone.size() - 1].push(keywordConditionsPair);
			processedQueryCount[keywordClone.size() - 1]++;

			if (keywordClone.size() == bestPossibleMatchScore) {
				result = processQueryStack(bestPossibleMatchScore - 1, store, queryStack, resultLimit);
				long maxQueryNumber = maxQueryCount[bestPossibleMatchScore - 1];

				if (maxQueryNumber == 0) {
					maxQueryNumber = getMaxQueryCount(matchesForKeywords, queryStack.length, bestPossibleMatchScore);
					maxQueryCount[bestPossibleMatchScore - 1] = maxQueryNumber;
				}
				if (processedQueryCount[bestPossibleMatchScore - 1] == maxQueryNumber) {
					bestPossibleMatchScore--;
				}
			}

			return result;

		}

		for (int i = 0; i < matchesForKeywords.get(keywordPos).getColMatches().size(); i++) {

			if (!combination.containsKey(matchesForKeywords.get(keywordPos).getColMatches().get(i))) {
				combination.put(matchesForKeywords.get(keywordPos).getColMatches().get(i), new HashSet<>());
			}

			combination.get(matchesForKeywords.get(keywordPos).getColMatches().get(i))
					.add(matchesForKeywords.get(keywordPos).getKeyword());

			keywords.add(matchesForKeywords.get(keywordPos).getKeyword());

			result = getBestMatchHelper(matchesForKeywords, store, keywordPos + 1, combination, keywords,
					bestPossibleMatchScore, maxQueryCount, processedQueryCount, queryStack, preConditions, resultLimit);

			if (result != null) {
				return result;
			}

			if (combination.get(matchesForKeywords.get(keywordPos).getColMatches().get(i)).size() == 1) {
				combination.remove(matchesForKeywords.get(keywordPos).getColMatches().get(i));
			} else {
				combination.get(matchesForKeywords.get(keywordPos).getColMatches().get(i))
						.remove(matchesForKeywords.get(keywordPos).getKeyword());
			}
			keywords.remove(matchesForKeywords.get(keywordPos).getKeyword());

		}

		return getBestMatchHelper(matchesForKeywords, store, keywordPos + 1, combination, keywords,
				bestPossibleMatchScore, maxQueryCount, processedQueryCount, queryStack, preConditions, resultLimit);

	}

	/**
	 * This method takes in Typed Store and keyword matches combinations as input
	 * and returns List of Conditions, that should form the search query.
	 * 
	 * @param <T>         Type of the store
	 * @param store       In memory data store
	 * @param combination Combination is a hash map that has column indexes as the
	 *                    keys, and each key points to set of keywords that matches
	 *                    that column. Combination can be viewed as a set of
	 *                    possible keyword combinations, that should be checked, if
	 *                    they match a record.
	 * @return It returns list of conditions that be executed to get data from the
	 *         store.
	 */
	private <T> List<Condition> formConditions(Store<T> store, HashMap<Integer, Set<String>> combination,
			List<Condition> preConditions) {

		List<Condition> formedConditions = new LinkedList<Condition>();

		for (Integer key : combination.keySet()) {
			formedConditions.add(new Condition(store.getSdqlColumns()[key].getColumnName(), Operator.CONTAINS_ALL,
					combination.get(key)));
		}

		if (preConditions != null && !preConditions.isEmpty()) {
			formedConditions.addAll(preConditions);
		}

		return formedConditions;

	}

	/**
	 * This method will process query stack of a given set of keywords and would
	 * return a result, if query returns any non zero result. For example we will
	 * call this method to process all queries that matches exactly 3 keywords, This
	 * method will process all queries that could match 3 keywords.
	 * 
	 * @param <T>         Type of store
	 * @param index       index of stack, which represents the number of keywords
	 * @param store       Store to search
	 * @param queryStack  List of query stack, the index in the list matches the
	 *                    number of keywords and the stack has all the queries for
	 *                    that number of keywords
	 * @param resultLimit This integer argument will limit your search results to
	 *                    this upper limit
	 * @return Returns result if found, else null.
	 */
	private <T> KeywordSearchResult<T> processQueryStack(int index, Store<T> store,
			Stack<KeywordConditionsPair>[] queryStack, Integer resultLimit) {

		Stack<KeywordConditionsPair> stack = queryStack[index];
		KeywordSearchResult<T> result = null;

		StoreSearch storeSearch = new StoreSearch();

		while (!stack.isEmpty()) {
			KeywordConditionsPair keywordConditionsPair = stack.pop();
			List<T> serchResults = storeSearch.query(store, keywordConditionsPair.getConditions(), resultLimit);
			if (serchResults.size() > 0) {
				result = new KeywordSearchResult<>(keywordConditionsPair.getKeywords(), serchResults);
				break;
			}
		}

		return result;
	}

	private void generateFactorialMap(int n) {
		factorialMap = new long[n + 1];
		long fact = 1;
		factorialMap[0] = 1;
		for (int i = 1; i <= n; i++) {
			fact = fact * i;
			factorialMap[i] = fact;
		}
	}

	/**
	 * This evaluates the N choose R number.
	 * 
	 * @param n Total number of keywords
	 * @param r number of keywords that make a match at the same time.
	 * @return return total number of possible n choose r combinations
	 */
	private long getMaxQueryCount(int n, int r) {
		return factorialMap[n] / (factorialMap[r] * factorialMap[n - r]);
	}

	/**
	 * This method gives the total number of possible queries, that can be formed
	 * for a given subset of keywords, out of total keywords. This takes into
	 * account that a single keyword can form more than one query depending on its
	 * matches with other columns.
	 * 
	 * @param matchesForKeywords keyword and its column matches
	 * @param n                  Total number of keywords
	 * @param r                  Subset of total keywords
	 * @return total number of queries that can be formed for the given subset of
	 *         keywords
	 */
	private long getMaxQueryCount(ArrayList<MatchesForKeyword> matchesForKeywords, int n, int r) {
		long baseCount = getMaxQueryCount(n, r);

		for (MatchesForKeyword matchesForKeyword : matchesForKeywords) {
			if (matchesForKeyword.getColMatches().size() > 1) {
				baseCount += (matchesForKeyword.getColMatches().size() - 1) * getMaxQueryCount(n - 1, r - 1);
			}
		}
		return baseCount;
	}

}
