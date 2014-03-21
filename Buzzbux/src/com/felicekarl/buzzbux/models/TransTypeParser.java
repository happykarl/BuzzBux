package com.felicekarl.buzzbux.models;

import java.util.Locale;

public class TransTypeParser {
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
}
