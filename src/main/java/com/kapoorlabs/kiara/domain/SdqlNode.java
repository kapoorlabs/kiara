 package com.kapoorlabs.kiara.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Data in the database is stored in the form of an inverted indexed Trie.
 * SdqlNode defines the node used in the Trie data structure, that builds the
 * database.
 *
 * @author Anuj Kapoor
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
public class SdqlNode {

	private String stringValue;

	private Double doubleValue;

	private List<SdqlNode> children;

	private SdqlNode parent;

	int lowerBound;

	int upperBound;

	public SdqlNode(String stringValue, Double doubleValue) {

		this.stringValue = stringValue;
		this.doubleValue = doubleValue;
		this.children = new LinkedList<>();

	}

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

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SdqlNode))
			return false;
		SdqlNode other = (SdqlNode) obj;
		return Objects.equals(doubleValue, other.doubleValue) &&
		       Objects.equals(lowerBound, other.lowerBound) &&
		       Objects.equals(parent, other.parent) &&
		       Objects.equals(stringValue, other.stringValue) &&
		       Objects.equals(upperBound, other.upperBound);
	}

}
