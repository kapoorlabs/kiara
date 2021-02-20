package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CaseInsensitive;
import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.OneEditAway;
import com.kapoorlabs.kiara.domain.annotations.StemmedIndex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
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
	
	
	@CaseInsensitive
	@OneEditAway
	@StemmedIndex
	@CommaSeperatedStrings
	private String plotKeywords;

}
