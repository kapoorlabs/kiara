package com.kapoorlabs.kiara.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config {

	private String country;
	
	private String originCity;
	
	private String airline;
	
	private Double surCharge;
		
}
