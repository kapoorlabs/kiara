package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.IgnoreIndex;

import lombok.Data;

@Data
public class BooksTestObject {

	@IgnoreIndex
	private int id;

	private String isbn;
	
	@CommaSeperatedStrings
	private String authors;
	
	private Integer year;
	
	private String title;
	
	private String language;
	
	private Double rating;
	
	

}
