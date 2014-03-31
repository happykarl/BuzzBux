package com.felicekarl.buzzbux.models;

import java.util.Locale;
/**
 * ServiceHolder to parse String into TransType.
 * @author Karl
 *
 */
public class TransTypeParser {
	/**
	 * Parse String into TransType.
	 * @param str string to be parsed
	 * @return TransType if parsing is succeeded.
	 */
    public static TransType parseTransType(String str) {
        if (str.toUpperCase(Locale.US).contains(TransType.DEPOSIT.toString().toUpperCase(Locale.US))) {
            return TransType.DEPOSIT;
        } else if (str.toUpperCase(Locale.US).contains(TransType.WITHDRAWAL.toString().toUpperCase(Locale.US))) {
            return TransType.WITHDRAWAL;
        } else if (str.toUpperCase(Locale.US).contains(TransType.DEBIT.toString().toUpperCase(Locale.US))) {
            return TransType.DEBIT;
        } else if (str.toUpperCase(Locale.US).contains(TransType.CREDIT.toString().toUpperCase(Locale.US))) {
            return TransType.CREDIT;
        } else if (str.toUpperCase(Locale.US).contains(TransType.REFUND.toString().toUpperCase(Locale.US))) {
            return TransType.REFUND;
        } else if (str.toUpperCase(Locale.US).contains(TransType.VOID.toString().toUpperCase(Locale.US))) {
            return TransType.VOID;
        }
        return null;
    }
    /**
     * Convert TransType into String (0 or 1).
     * @param type TransType
     * @return 0 if TransType is income, 1 if TransType is expense.
     */
    public static String parseSign(TransType type) {
		// add 
        if ( type.equals(TransType.DEPOSIT) || type.equals(TransType.REFUND) ) {
            return "0";
        // substract
        } else if ( type.equals(TransType.WITHDRAWAL) || type.equals(TransType.DEBIT) || type.equals(TransType.CREDIT) || type.equals(TransType.VOID) ) {
            return "1";
        }
        return null;
    }
}
