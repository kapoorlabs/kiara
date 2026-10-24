package com.kapoorlabs.kiara.util;

import org.tartarus.snowball.ext.englishStemmer;

public final class StemmerUtil {

	private StemmerUtil() {
	}

	public static String stemEnglish(String value) {
		englishStemmer stemmer = new englishStemmer();
		stemmer.setCurrent(value);
		stemmer.stem();
		return stemmer.getCurrent();
	}
}
