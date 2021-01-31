package com.kapoorlabs.kiara.domain;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeywordSearchResult<T> implements Comparable<KeywordSearchResult<T>> {
	
	Set<String> keywords;
	
	List<T> result;

	@Override
	public int compareTo(KeywordSearchResult<T> o) {
		
		if (this.keywords.size() > o.keywords.size()) {
			return -1;
		} else if (this.keywords.size() == o.keywords.size()) {
			return 0;
		} else {
			return 1;
		}

	}
	
	

}
