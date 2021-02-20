package com.kapoorlabs.kiara.domain;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchesForKeyword {
	
	private String keyword;
	
	private ArrayList<Integer> colMatches = new ArrayList<>();
	
	public MatchesForKeyword(String keyword) {
		
		this.keyword = keyword;
		
	}

}
