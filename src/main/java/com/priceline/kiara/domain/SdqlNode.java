package com.priceline.kiara.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * Data in the database is stored in the form of an inverted indexed Trie.
 * SdqlNode defines the node used in the Trie data structure, that builds the
 * database.
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
@Data
public class SdqlNode {

	private String stringValue;

	private Double doubleValue;

	private Map<String, SdqlNode> children;

	private SdqlNode parent;

	int lowerBound;

	int upperBound;

	public SdqlNode(String stringValue, Double doubleValue) {

		this.stringValue = stringValue;
		this.doubleValue = doubleValue;
		this.children = new HashMap<>();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doubleValue == null) ? 0 : doubleValue.hashCode());
		result = prime * result + lowerBound;
		result = prime * result + ((parent == null) ? 0 : parent.stringValue.hashCode());
		result = prime * result + ((stringValue == null) ? 0 : stringValue.hashCode());
		result = prime * result + upperBound;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SdqlNode))
			return false;
		SdqlNode other = (SdqlNode) obj;
		if (doubleValue == null) {
			if (other.doubleValue != null)
				return false;
		} else if (!doubleValue.equals(other.doubleValue))
			return false;
		if (lowerBound != other.lowerBound)
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (parent != other.parent)
			return false;
		if (stringValue == null) {
			if (other.stringValue != null)
				return false;
		} else if (!stringValue.equals(other.stringValue))
			return false;
		if (upperBound != other.upperBound)
			return false;
		return true;
	}

}
