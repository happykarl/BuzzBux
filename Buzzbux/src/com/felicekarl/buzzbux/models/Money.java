package com.felicekarl.buzzbux.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import android.util.Log;

public class Money {
	private static final String TAG = Money.class.getSimpleName();
	
	private int value = 0;
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
		Log.d(TAG, "locale.toString(): " + locale.toString());
		Log.d(TAG, "Locale.US: " + Locale.US.toString());
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		String symbol = nf.getCurrency().getSymbol();
		DecimalFormat df = new DecimalFormat(symbol + "#,##0.00;-" + symbol + "#,##0.00");
		if (locale.equals(Locale.US)) {
			return df.format(value/100.0f);
		} else if (locale.equals(Locale.KOREA)) {
			return df.format(value);
		}
		return null;
	}
}
