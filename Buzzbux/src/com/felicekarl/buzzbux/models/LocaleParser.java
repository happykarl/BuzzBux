package com.felicekarl.buzzbux.models;

import java.util.Locale;

public class LocaleParser {
	public static Locale parseLocale(String str) {
		if (str.toUpperCase(Locale.US).contains("US")) {
			return Locale.US;
		} else if (str.toUpperCase(Locale.US).contains("KOREA")) {
			return Locale.KOREA;
		}
		return null;
	}
}
