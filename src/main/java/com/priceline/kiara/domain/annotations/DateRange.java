package com.priceline.kiara.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.priceline.kiara.constants.SdqlConstants;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateRange {
	
	public String value() default SdqlConstants.DEFAULT_DATE_FORMAT;

}