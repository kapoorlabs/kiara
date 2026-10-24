package com.kapoorlabs.kiara.test.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kapoorlabs.kiara.util.StemmerUtil;

public class StemmerUtilTest {

	@Test
	public void stemsEnglishSuffixes() {
		assertEquals("run", StemmerUtil.stemEnglish("running"));
		assertEquals("relat", StemmerUtil.stemEnglish("relational"));
		assertEquals("generous", StemmerUtil.stemEnglish("generously"));
		assertEquals("cat", StemmerUtil.stemEnglish("cats"));
		assertEquals("poni", StemmerUtil.stemEnglish("ponies"));
	}

	@Test
	public void preservesSnowballExceptions() {
		assertEquals("sky", StemmerUtil.stemEnglish("skies"));
		assertEquals("die", StemmerUtil.stemEnglish("dying"));
		assertEquals("children", StemmerUtil.stemEnglish("children"));
		assertEquals("men", StemmerUtil.stemEnglish("men"));
	}

	@Test
	public void preservesCaseSensitiveBehavior() {
		assertEquals("Run", StemmerUtil.stemEnglish("Running"));
		assertEquals("RUNNING", StemmerUtil.stemEnglish("RUNNING"));
	}

	@Test
	public void handlesEmptyAndNonWordInputs() {
		assertEquals("", StemmerUtil.stemEnglish(""));
		assertEquals("a", StemmerUtil.stemEnglish("a"));
		assertEquals("123", StemmerUtil.stemEnglish("123"));
		assertEquals("O'Reilli", StemmerUtil.stemEnglish("O'Reilly"));
		assertEquals("the quick brown fox", StemmerUtil.stemEnglish("the quick brown fox"));
	}

	@Test
	public void keepsCallsIndependent() {
		assertEquals("run", StemmerUtil.stemEnglish("running"));
		assertEquals("", StemmerUtil.stemEnglish(""));
		assertEquals("sky", StemmerUtil.stemEnglish("skies"));
		assertEquals("run", StemmerUtil.stemEnglish("running"));
	}
}
