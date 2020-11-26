package com.kapoorlabs.kiara.domain;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeywordConditionsPair implements Comparable <KeywordConditionsPair>{
	
	private Set<String> keywords;
	
	private List<Condition> conditions;

	@Override
	public int compareTo(KeywordConditionsPair o) {
		if (this.keywords.size() > o.keywords.size()) {
			return -1;
		} else if (this.keywords.size() == o.keywords.size()) {
			return 0;
		} else {
			return 1;
		}
	}
	
	

}
