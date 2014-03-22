package com.felicekarl.buzzbux.models;

import java.util.Locale;

public class LocaleParser {
	public static Locale parseLocale(String str) {
		if ( str.toUpperCase(Locale.US).contains("en_US".toUpperCase(Locale.US)) ||
				str.toUpperCase(Locale.US).contains("US".toUpperCase(Locale.US)) ) {
			return Locale.US;
		} else if ( str.toUpperCase(Locale.US).contains("ko_KR".toUpperCase(Locale.US)) ||
				str.toUpperCase(Locale.US).contains("KOREA".toUpperCase(Locale.US)) ) {
			return Locale.KOREA;
		}
		return null;
	}
}
