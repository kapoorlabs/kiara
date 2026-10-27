package com.kapoorlabs.kiara.test.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.kapoorlabs.kiara.domain.Range;

public class RangeTest {

	@Test
	public void equalNumericLowerBoundsCompareAsZeroForDistinctBoxes() {
		for (long lower : new long[] { Long.MIN_VALUE, -1000L, -129L, 0L, 127L, 128L, 1000L,
				20454L, 1700000000L, Long.MAX_VALUE }) {
			Range first = range(lower, lower);
			Range second = range(lower, Long.MAX_VALUE);

			assertNotSame(first.getLowerLimit(), second.getLowerLimit());
			assertEquals(0, first.compareTo(second));
			assertEquals(0, second.compareTo(first));
		}
	}

	@Test
	public void orderingDependsOnlyOnNumericLowerBounds() {
		Range first = new Range("AA", 1000L, 1100L);
		Range second = new Range("BB", 1000L, 2000L);

		assertNotEquals(first, second);
		assertEquals(0, first.compareTo(second));
		assertEquals(0, second.compareTo(first));
		assertTrue(range(Long.MIN_VALUE, 0L).compareTo(range(Long.MAX_VALUE, Long.MAX_VALUE)) < 0);
		assertTrue(range(Long.MAX_VALUE, Long.MAX_VALUE).compareTo(range(Long.MIN_VALUE, 0L)) > 0);
	}

	@Test
	public void comparatorObeysOrderingLaws() {
		List<Range> ranges = new ArrayList<>();
		for (long lower : new long[] { Long.MIN_VALUE, -1000L, -129L, 0L, 128L, 1000L, 20454L,
				1700000000L, Long.MAX_VALUE }) {
			ranges.add(range(lower, lower));
			ranges.add(range(lower, Long.MAX_VALUE));
		}

		for (Range first : ranges) {
			assertEquals(0, first.compareTo(first));
			for (Range second : ranges) {
				int comparison = Integer.signum(first.compareTo(second));
				assertEquals(-comparison, Integer.signum(second.compareTo(first)));
				for (Range third : ranges) {
					if (comparison == 0) {
						assertEquals(Integer.signum(first.compareTo(third)),
								Integer.signum(second.compareTo(third)));
					}
					if (comparison <= 0 && second.compareTo(third) <= 0) {
						assertTrue(first.compareTo(third) <= 0);
					}
				}
			}
		}
	}

	@Test
	public void sortsManyRangesWithRepeatedLowerBounds() {
		Random random = new Random(20261028L);
		List<Range> ranges = new ArrayList<>();
		List<Long> expected = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			long lower = 20000L + random.nextInt(365);
			ranges.add(range(lower, lower + random.nextInt(121)));
			expected.add(lower);
		}

		Collections.sort(expected);
		Collections.sort(ranges);

		for (int i = 0; i < ranges.size(); i++) {
			assertEquals(expected.get(i), ranges.get(i).getLowerLimit());
		}
	}

	@Test
	public void preservesComparisonOfUninitializedRanges() {
		assertEquals(0, new Range().compareTo(new Range()));
	}

	@Test
	public void rejectsComparingOneNullAndOneNumericLowerBound() {
		assertThrows(NullPointerException.class, () -> new Range().compareTo(range(1000L, 1100L)));
		assertThrows(NullPointerException.class, () -> range(1000L, 1100L).compareTo(new Range()));
		assertThrows(NullPointerException.class, () -> range(1000L, 1100L).compareTo(null));
	}

	private static Range range(long lower, long upper) {
		// Separate boxes keep the regression independent of JVM boxing caches.
		return new Range(new Long(lower), Long.valueOf(upper));
	}
}
