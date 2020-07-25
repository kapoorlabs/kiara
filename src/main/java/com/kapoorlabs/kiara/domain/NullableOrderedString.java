package com.kapoorlabs.kiara.domain;

import com.kapoorlabs.kiara.constants.SdqlConstants;

/**
 * This class represents a wrapper built on top of a String, in which Null
 * values are ordered in the lowest position.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
public class NullableOrderedString implements Comparable<NullableOrderedString> {

	String value;

	public NullableOrderedString(String value) {
		this.value = value == null ? SdqlConstants.NULL : value;
	}

	@Override
	public int compareTo(NullableOrderedString o) {
		if (this.value.equals(SdqlConstants.NULL) && o.value.equals(SdqlConstants.NULL)) {
			return 0;
		}

		if (this.value.equals(SdqlConstants.NULL)) {
			return -1;
		}

		if (o.value.equals(SdqlConstants.NULL)) {
			return 1;
		}
		return this.value.compareTo(o.value);
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof NullableOrderedString))
			return false;
		NullableOrderedString other = (NullableOrderedString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
	

}
