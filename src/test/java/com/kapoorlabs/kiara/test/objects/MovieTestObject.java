package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.OneEditAway;
import com.kapoorlabs.kiara.domain.annotations.StemmedIndex;

import lombok.Data;

@Data
public class MovieTestObject {
	
	private String color;
	
	@OneEditAway
	@CommaSeperatedStrings
	private String actors;
	
	@OneEditAway
	private String directorName;

	
	@OneEditAway
	@CommaSeperatedStrings
	private String genres;
	
	@OneEditAway
	private String movieTitle;
	
	
	@OneEditAway
	@StemmedIndex
	@CommaSeperatedStrings
	private String plotKeywords;

}
