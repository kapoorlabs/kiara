package com.priceline.kiara.test.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.priceline.kiara.domain.Range;
import com.priceline.kiara.domain.SearchableRange;

public class SearchableRangeTest {

	@Test
	public void SearchableRangeTest_1() {


		SearchableRange searchableRange = getTestRange_1();
		Set<Range> expectedRanges = new HashSet<>();

		expectedRanges.add(new Range(-1L, 10L)); // 2 occurances
		expectedRanges.add(new Range(Long.MIN_VALUE, 0L));
		expectedRanges.add(new Range(0L, Long.MAX_VALUE));
		expectedRanges.add(new Range(0L, 24000L));
		expectedRanges.add(new Range(-300L, 300L));
		expectedRanges.add(new Range(0L, 0L)); // 2 occurances

		List<Range> resultRanges = searchableRange.getInRange(0L);

		assertEquals(8, resultRanges.size());

		for (Range range : resultRanges) {
			assertTrue(expectedRanges.contains(range));
		}

	}
	
	@Test
	public void SearchableRangeTest_2() {


		SearchableRange searchableRange = getTestRange_1();
		Set<Range> expectedRanges = new HashSet<>();

		expectedRanges.add(new Range(Long.MIN_VALUE, 0L));


		List<Range> resultRanges = searchableRange.getInRange(Long.MIN_VALUE);

		assertEquals(1, resultRanges.size());

		for (Range range : resultRanges) {
			assertTrue(expectedRanges.contains(range));
		}

	}
	
	@Test
	public void SearchableRangeTest_3() {


		SearchableRange searchableRange = getTestRange_1();
		Set<Range> expectedRanges = new HashSet<>();

		expectedRanges.add(new Range(0L, Long.MAX_VALUE));


		List<Range> resultRanges = searchableRange.getInRange(Long.MAX_VALUE);

		assertEquals(1, resultRanges.size());

		for (Range range : resultRanges) {
			assertTrue(expectedRanges.contains(range));
		}

	}
	
	@Test
	public void SearchableRangeTest_4() {


		SearchableRange searchableRange = getTestRange_2();

		List<Range> resultRanges = searchableRange.getInRange(Long.MAX_VALUE);

		assertEquals(0, resultRanges.size());

	}
	
	@Test
	public void SearchableRangeTest_5() {


		SearchableRange searchableRange = getTestRange_2();

		List<Range> resultRanges = searchableRange.getInRange(Long.MAX_VALUE);

		assertEquals(0, resultRanges.size());

	}
	
	
	@Test
	public void SearchableRangeTest_6() {


		SearchableRange searchableRange = getTestRange_2();

		List<Range> resultRanges = searchableRange.getInRange(1000L);
		
		Set<Range> expectedRanges = new HashSet<>();

		expectedRanges.add(new Range(0L, 24000L));
		expectedRanges.add(new Range(1000L, 1000L));

		assertEquals(2, resultRanges.size());
		
		for (Range range : resultRanges) {
			assertTrue(expectedRanges.contains(range));
		}

	}
	
	@Test
	public void SearchableRangeTest_7() {


		SearchableRange searchableRange = getTestRange_3();

		List<Range> resultRanges = searchableRange.getInRange(3L);
		
		Set<Range> expectedRanges = new HashSet<>();

		expectedRanges.add(new Range(-1L, 10L));
		expectedRanges.add(new Range(-1L, 4L));
		expectedRanges.add(new Range(2L, 4L));
		expectedRanges.add(new Range(1L, 3L));
		expectedRanges.add(new Range(-3L, 10L));

		assertEquals(5, resultRanges.size());
		
		for (Range range : resultRanges) {
			assertTrue(expectedRanges.contains(range));
		}

	}
	
	@Test
	public void SearchableRangeTest_8() {

		SearchableRange searchableRange = getTestRange_3();

		List<Range> resultRanges = searchableRange.getInRange(-3L);
		
		Set<Range> expectedRanges = new HashSet<>();

		expectedRanges.add(new Range(-3L, 10L));

		assertEquals(1, resultRanges.size());
		
		for (Range range : resultRanges) {
			assertTrue(expectedRanges.contains(range));
		}

	}
	
	@Test
	public void SearchableRangeTest_9() {


		SearchableRange searchableRange = getTestRange_3();

		List<Range> resultRanges = searchableRange.getInRange(11L);

		assertEquals(0, resultRanges.size());


	}
	
	
	
	private SearchableRange getTestRange_1() {
		
		SearchableRange searchableRange = new SearchableRange();
		searchableRange.insert(new Range(-1L, 10L));
		searchableRange.insert(new Range(Long.MIN_VALUE, 0L));
		searchableRange.insert(new Range(0L, Long.MAX_VALUE));
		searchableRange.insert(new Range(0L, 24000L));
		searchableRange.insert(new Range(4566778L, 9566778L));
		searchableRange.insert(new Range(-300L, 300L));
		searchableRange.insert(new Range(1000L, 1000L));
		searchableRange.insert(new Range(0L, 0L));
		searchableRange.insert(new Range(589L, 589L));
		searchableRange.insert(new Range(0L, 0L));
		searchableRange.insert(new Range(-1L, 10L));
		searchableRange.insert(new Range(4567L, 7767L));

		searchableRange.prepareForSearch();
		
		return searchableRange;
		
	}
	
	private SearchableRange getTestRange_2() {
		
		SearchableRange searchableRange = new SearchableRange();
		searchableRange.insert(new Range(-1L, 10L));
		searchableRange.insert(new Range(0L, 24000L));
		searchableRange.insert(new Range(4566778L, 9566778L));
		searchableRange.insert(new Range(-300L, 300L));
		searchableRange.insert(new Range(1000L, 1000L));
		searchableRange.insert(new Range(0L, 0L));
		searchableRange.insert(new Range(589L, 589L));
		searchableRange.insert(new Range(0L, 0L));
		searchableRange.insert(new Range(-1L, 10L));
		searchableRange.insert(new Range(4567L, 7767L));

		searchableRange.prepareForSearch();
		
		return searchableRange;
		
	}
	
	private SearchableRange getTestRange_3() {
		
		SearchableRange searchableRange = new SearchableRange();
		searchableRange.insert(new Range(-1L, 10L));
		searchableRange.insert(new Range(9L, 10L));
		searchableRange.insert(new Range(-2L, 0L));
		searchableRange.insert(new Range(-1L, 4L));
		searchableRange.insert(new Range(2L, 4L));
		searchableRange.insert(new Range(1L, 3L));
		searchableRange.insert(new Range(-3L, 10L));

		searchableRange.prepareForSearch();
		
		return searchableRange;
		
	}


}
