package com.priceline.kiara.test.objects;



import com.priceline.kiara.domain.annotations.DateFormat;
import com.priceline.kiara.domain.annotations.DateRange;

import lombok.Data;

@Data
public class DeathRecordsTestObject {
	
	private String gender;
	
	@DateRange(value="yyyy-MM-dd")
	private String birthDeathRange;
	
	@DateFormat(value="yyyy-MM-dd")
	private String recordDate; 

}
