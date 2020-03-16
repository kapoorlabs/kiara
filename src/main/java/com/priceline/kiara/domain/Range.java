package com.priceline.kiara.domain;

import lombok.Data;

@Data
/**
 * This class represents a single range. In case of alphanumeric ranges, prefix
 * will hold the aplhanumeric part, while lowerLimit and upperLimit will hold
 * numeric parts of the range. In case of strictly date ranges, prefix will
 * always by an empty string.
 * 
 *
 * @author Anuj Kapoor
 * @author www.priceline.com
 * @version 1.0
 * @since 1.0
 */
public class Range implements Comparable<Range> {

	private String prefix;

	private Long lowerLimit;

	private Long upperLimit;

	@Override
	public int compareTo(Range o) {

		if (this.lowerLimit == o.lowerLimit) {
			return 0;
		} else if (this.lowerLimit > o.lowerLimit) {
			return 1;
		} else {
			return -1;
		}
	}

	public Range() {

		this.prefix = null;
		this.lowerLimit = null;
		this.upperLimit = null;
	}

	public Range(String prefix, Long lowerLimit, Long upperLimit) {

		this.prefix = prefix;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	public Range(Long lowerLimit, Long upperLimit) {

		this.prefix = "";
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Range))
			return false;
		Range other = (Range) obj;
		if (lowerLimit == null) {
			if (other.lowerLimit != null)
				return false;
		} else if (!lowerLimit.equals(other.lowerLimit))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (upperLimit == null) {
			if (other.upperLimit != null)
				return false;
		} else if (!upperLimit.equals(other.upperLimit))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lowerLimit == null) ? 0 : lowerLimit.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((upperLimit == null) ? 0 : upperLimit.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return prefix + lowerLimit + "-" + upperLimit;
	}

}
