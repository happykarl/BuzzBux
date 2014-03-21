package com.felicekarl.buzzbux.models;

import java.text.NumberFormat;
import java.util.Locale;

public class Money {
	private int value;
	private Locale locale;
	
	public Money(Locale locale, int value) {
		this.locale = locale;
		this.value = value;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		if (locale.equals(Locale.US)) {
			return nf.format(value/100.0f);
		} else if (locale.equals(Locale.KOREA)) {
			return nf.format(value);
		}
		return null;
	}
}
