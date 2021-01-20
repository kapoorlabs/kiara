package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.OneEditAway;
import com.kapoorlabs.kiara.domain.annotations.StemmedIndex;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain=true)
public class MovieTestObject {
	
	private String color;
	
	@OneEditAway
	@CommaSeperatedStrings
	private String actors;
	
	@OneEditAway
	private String directorName;

	
	private Integer titleYear;
	
	private Double aspectRatio;
	
	private long budget;
	
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
