package com.felicekarl.buzzbux.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
/**
 * Money.
 * @author Karl
 *
 */
public class Money {
	/**
	 * Integer value of Money.
	 */
    private int value = 0;
    /**
     * Locale of Money. (ex> US Dollar / Korea Won)
     */
    private Locale locale;
	/**
	 * Initiate instance with Locale and Value info.
	 * @param pLocale Locale 
	 * @param pValue integer value of money
	 */
    public Money(Locale pLocale, int pValue) {
    	locale = pLocale;
    	value = pValue;
    }
    /**
	 * Set Locale of Money.
	 * @param pLocale Locale of Money
	 */
    public void setLocale(Locale pLocale) {
    	locale = pLocale;
    }
	/**
	 * Get Locale of Money.
	 * @return Locale of Money
	 */
    public Locale getLocale() {
    	return locale;
    }
	/**
	 * Set integer value of Money.
	 * @param pValue integer value of Money
	 */
    public void setValue(int pValue) {
    	value = pValue;
    }
	/**
	 * Get integer value of Money.
	 * @return integer value of Money
	 */
    public int getValue() {
    	return value;
    }
    @Override
    public String toString() {
    	NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    	String symbol = nf.getCurrency().getSymbol();
    	DecimalFormat df = new DecimalFormat(symbol + "#,##0.00;-" + symbol + "#,##0.00");
    	if (locale.equals(Locale.US)) {
            return df.format(value / 100.0f);
        } else if (locale.equals(Locale.KOREA)) {
        	return df.format(value);
        }
    	return null;
    }
}
