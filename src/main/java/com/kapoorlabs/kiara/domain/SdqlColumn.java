package com.kapoorlabs.kiara.domain;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * List of SdqlColumns builds the actual schema for the store. Each SdqlColumn
 * maps to a field in the POJO, that was used to create the list of Sdqlcolumns.
 * Each SdqlColumn carries metadata, for the fields that builds up schema for the
 * database. 
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
@Data
public class SdqlColumn {

	private String columnName;

	private boolean isNumeric;
	
	private boolean isIndexed;
	
	private boolean isOneEditCapable;
	
	private boolean isStemmedIndex;
	
	private boolean isCaseSensitive;

	private boolean isEnum;

	private Class<Enum> enumClass;

	private Method getter;
	
	private Method setter;

	private SecondaryType secondaryType;

	public SdqlColumn() {
		super();
		this.isIndexed = true;
	}

}
