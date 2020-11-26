package com.kapoorlabs.kiara.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeywordSearchResult implements Comparable<KeywordSearchResult> {
	
	Set<String> keywords;
	
	List<Map<String, String>> result;

	@Override
	public int compareTo(KeywordSearchResult o) {
		
		if (this.keywords.size() > o.keywords.size()) {
			return -1;
		} else if (this.keywords.size() == o.keywords.size()) {
			return 0;
		} else {
			return 1;
		}

	}
	
	

}
